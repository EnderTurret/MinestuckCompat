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
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.AnalyzableRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.FluidHelper;

public final class SequencedAssemblyInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<SequencedAssemblyRecipe> implements AnalyzableRecipeInterpreter {

	public static final MapCodec<SequencedAssemblyInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(SequencedAssemblyInterpreter::addedCost),
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("deploy_cost", GristSet.EMPTY).forGetter(SequencedAssemblyInterpreter::deployCost),
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("press_cost", GristSet.EMPTY).forGetter(SequencedAssemblyInterpreter::pressCost)
			).apply(instance, SequencedAssemblyInterpreter::new));

	private final GristSet.Immutable deployCost;
	private final GristSet.Immutable pressCost;

	public SequencedAssemblyInterpreter(GristSet.Immutable addedCost, GristSet.Immutable deployCost, GristSet.Immutable pressCost) {
		super(SequencedAssemblyRecipe.class, addedCost);
		this.deployCost = deployCost;
		this.pressCost = pressCost;
	}

	public GristSet.Immutable deployCost() {
		return deployCost;
	}

	public GristSet.Immutable pressCost() {
		return pressCost;
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	protected List<Item> getOutputItemsTyped(SequencedAssemblyRecipe recipe) {
		return List.of(recipe.getResultItem(null).getItem(), recipe.getTransitionalItem().getItem());
	}

	@Override
	@Nullable
	protected MutableGristSet generateCost(MutableGristSet totalCost, SequencedAssemblyRecipe recipe, Item output, GeneratorCallback callback) {
		account(totalCost, callback, recipe.getIngredient());

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
				FluidHelper.report(tracker, r2.getRequiredFluid().getMatchingFluidStacks().get(0));
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
	}

	private boolean handleSequenceRecipe(SequencedAssemblyRecipe r, Recipe<?> seq, MutableGristSet sequenceCost, Item output, GeneratorCallback callback) {
		if (seq instanceof DeployerApplicationRecipe r2) {
			sequenceCost.add(deployCost);
			for (Ingredient ing : r2.getIngredients())
				if (!ing.test(r.getTransitionalItem()))
					if (!account(sequenceCost, callback, ing))
						return false;
		}

		else if (seq instanceof PressingRecipe r2) {
			sequenceCost.add(pressCost);
		}

		else if (seq instanceof FillingRecipe r2) {
			final FluidIngredient fluid = r2.getRequiredFluid();
			if (!FluidHelper.account(sequenceCost, callback, fluid.getMatchingFluidStacks(), fluid.getRequiredAmount()))
				return false;
		}

		else {
			MinestuckCompat.LOGGER.warn("Don't know how to handle a {} in a SequencedAssemblyRecipe (from {})!", seq.getClass().getSimpleName(), output);
			return false;
		}

		return true;
	}
}