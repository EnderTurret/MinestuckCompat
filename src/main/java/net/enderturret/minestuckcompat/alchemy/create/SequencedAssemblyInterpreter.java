package net.enderturret.minestuckcompat.alchemy.create;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.AnalyzableRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.FluidHelper;

/**
 * A recipe interpreter for Create's {@linkplain SequencedAssemblyRecipe sequenced assembly recipes}.
 * @author EnderTurret
 */
public final class SequencedAssemblyInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<SequencedAssemblyRecipe> implements AnalyzableRecipeInterpreter {

	public static final MapCodec<SequencedAssemblyInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(SequencedAssemblyInterpreter::addedCost),
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("deploy_cost", GristSet.EMPTY).forGetter(SequencedAssemblyInterpreter::deployCost),
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("press_cost", GristSet.EMPTY).forGetter(SequencedAssemblyInterpreter::pressCost),
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("fill_cost", GristSet.EMPTY).forGetter(SequencedAssemblyInterpreter::fillCost),
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("cut_cost", GristSet.EMPTY).forGetter(SequencedAssemblyInterpreter::cutCost)
			).apply(instance, SequencedAssemblyInterpreter::new));

	private final GristSet.Immutable deployCost;
	private final GristSet.Immutable pressCost;
	private final GristSet.Immutable fillCost;
	private final GristSet.Immutable cutCost;

	/**
	 * Constructs a new {@code SequencedAssemblyInterpreter}.
	 * @param addedCost The grist cost added for all recipes processed by this interpreter.
	 * @param deployCost The grist cost added for a deploy operation.
	 * @param pressCost The grist cost added for a press operation.
	 * @param fillCost The grist cost added for a fill operation.
	 * @param cutCost The grist cost added for a cut operation.
	 */
	public SequencedAssemblyInterpreter(GristSet.Immutable addedCost, GristSet.Immutable deployCost, GristSet.Immutable pressCost, GristSet.Immutable fillCost, GristSet.Immutable cutCost) {
		super(SequencedAssemblyRecipe.class, addedCost);
		this.deployCost = deployCost;
		this.pressCost = pressCost;
		this.fillCost = fillCost;
		this.cutCost = cutCost;
	}

	public GristSet.Immutable deployCost() {
		return deployCost;
	}

	public GristSet.Immutable pressCost() {
		return pressCost;
	}

	public GristSet.Immutable fillCost() {
		return fillCost;
	}

	public GristSet.Immutable cutCost() {
		return cutCost;
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	protected List<ItemStack> getOutputItemStacksTyped(SequencedAssemblyRecipe recipe) {
		final ItemStack result = recipe.getResultItem(null);
		final ItemStack transitionalItem = recipe.getTransitionalItem();

		// If the input is the transitional item, avoid generating a cost for the transitional item.
		// Query the ingredient directly so we don't accidently resolve tags too early.
		if (recipe.getIngredient().getValues().length == 1 && uncachedTest(recipe.getIngredient(), transitionalItem))
			return List.of(result);

		return List.of(result, transitionalItem);
	}

	private static boolean uncachedTest(Ingredient ingredient, ItemStack input) {
		// This may or may not be safe.
		if (ingredient.isCustom()) return ingredient.test(input);

		for (Ingredient.Value val : ingredient.getValues())
			if (val instanceof Ingredient.ItemValue item && item.item().is(input.getItem()))
				return true;
			else if (val instanceof Ingredient.TagValue tag && input.is(tag.tag()))
				return true;

		return false;
	}

	@Override
	@Nullable
	protected MutableGristSet generateCost(MutableGristSet totalCost, SequencedAssemblyRecipe recipe, Item output, GeneratorCallback callback) {
		if (!account(totalCost, callback, recipe.getIngredient()))
			return null;

		// If we're checking the incomplete item, stop here.
		if (output == recipe.getTransitionalItem().getItem()) return totalCost;

		final MutableGristSet sequenceCost = recipe.getLoops() == 1 ? totalCost : MutableGristSet.newDefault();

		for (SequencedRecipe seq : recipe.getSequence())
			if (!handleSequenceRecipe(recipe, seq.getRecipe(), sequenceCost, output, callback))
				return null;

		if (recipe.getLoops() > 1) {
			sequenceCost.scale(recipe.getLoops());
			totalCost.add(sequenceCost);
		}

		return totalCost;
	}

	@Override
	protected void reportPreliminaryLookupsTyped(SequencedAssemblyRecipe recipe, LookupTracker tracker) {
		tracker.report(recipe.getIngredient());

		for (SequencedRecipe seq : recipe.getSequence())
			if (seq.getRecipe() instanceof DeployerApplicationRecipe r2) {
				for (Ingredient ing : r2.getIngredients())
					if (!ing.test(recipe.getTransitionalItem()))
						tracker.report(ing);
			} else if (seq.getRecipe() instanceof FillingRecipe r2)
				FluidHelper.report(tracker, r2.getRequiredFluid().ingredient());
	}

	@Override
	public void reportCraftingStation(Recipe<?> recipe, LookupTracker tracker) {
		if (recipe instanceof SequencedAssemblyRecipe r)
			for (SequencedRecipe seq : r.getSequence())
				if (seq.getRecipe() instanceof DeployerApplicationRecipe)
					tracker.report(AllBlocks.DEPLOYER.asItem());
				else if (seq.getRecipe() instanceof PressingRecipe)
					tracker.report(AllBlocks.MECHANICAL_PRESS.asItem());
				else if (seq.getRecipe() instanceof FillingRecipe)
					tracker.report(AllBlocks.SPOUT.asItem());
				else if (seq.getRecipe() instanceof CuttingRecipe)
					tracker.report(AllBlocks.MECHANICAL_SAW.asItem());
	}

	private boolean handleSequenceRecipe(SequencedAssemblyRecipe r, Recipe<?> seq, MutableGristSet sequenceCost, Item output, GeneratorCallback callback) {
		if (seq instanceof DeployerApplicationRecipe r2) {
			sequenceCost.add(deployCost);
			for (Ingredient ing : r2.getIngredients())
				if (!ing.test(r.getTransitionalItem()))
					if (!account(sequenceCost, callback, ing))
						return false;
		}

		else if (seq instanceof PressingRecipe)
			sequenceCost.add(pressCost);

		else if (seq instanceof CuttingRecipe)
			sequenceCost.add(cutCost);

		else if (seq instanceof FillingRecipe r2) {
			sequenceCost.add(fillCost);
			final SizedFluidIngredient fluid = r2.getRequiredFluid();
			if (!FluidHelper.account(sequenceCost, callback, fluid))
				return false;
		}

		else {
			MinestuckCompat.LOGGER.warn("Don't know how to handle a {} in a SequencedAssemblyRecipe (from {})!", seq.getClass().getSimpleName(), output);
			return false;
		}

		return true;
	}
}