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
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.AnalyzableRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.FluidHelper;

/**
 * A recipe interpreter intended for Create's {@linkplain AllRecipeTypes#COMPACTING compacting}, {@linkplain AllRecipeTypes#MIXING mixing}, and {@linkplain AllRecipeTypes#FILLING filling} recipes.
 * Unlike many other recipe interpreters, this one attempts to handle fluid ingredients.
 * @author EnderTurret
 */
public final class FluidProcessingInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<StandardProcessingRecipe<?>> implements AnalyzableRecipeInterpreter {

	public static final MapCodec<FluidProcessingInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(FluidProcessingInterpreter::addedCost),
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("heated_cost", GristSet.EMPTY).forGetter(FluidProcessingInterpreter::heatedCost)
			).apply(instance, FluidProcessingInterpreter::new));

	private final GristSet.Immutable heatedCost;

	/**
	 * Constructs a new {@code FluidProcessingInterpreter}.
	 * @param addedCost The grist cost added for all recipes processed by this interpreter.
	 * @param heatedCost The grist cost added for "heated" recipes, i.e. those requiring a blaze burner.
	 */
	@SuppressWarnings({ "cast", "unchecked" })
	public FluidProcessingInterpreter(GristSet.Immutable addedCost, GristSet.Immutable heatedCost) {
		super((Class<StandardProcessingRecipe<?>>) (Class) StandardProcessingRecipe.class, addedCost);
		this.heatedCost = heatedCost;
	}

	public GristSet.Immutable heatedCost() {
		return heatedCost;
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	protected List<ItemStack> getOutputItemStacksTyped(StandardProcessingRecipe<?> recipe) {
		return recipe.getRollableResults().stream().map(o -> o.getStack()).toList();
	}

	@Override
	@Nullable
	protected MutableGristSet generateCost(MutableGristSet totalCost, StandardProcessingRecipe<?> recipe, Item output, GeneratorCallback callback) {
		for (Ingredient ing : recipe.getIngredients())
			if (!account(totalCost, callback, ing))
				return null;

		for (SizedFluidIngredient ing : recipe.getFluidIngredients())
			if (!FluidHelper.account(totalCost, callback, ing))
				return null;

		return totalCost;
	}

	@Override
	@Nullable
	protected MutableGristSet ingredientCost(Recipe<?> recipe, GeneratorCallback callback) {
		return MutableGristSet.newDefault();
	}

	@Override
	protected void reportPreliminaryLookupsTyped(StandardProcessingRecipe<?> recipe, LookupTracker tracker) {
		for (Ingredient ing : recipe.getIngredients())
			tracker.report(ing);

		for (SizedFluidIngredient ing : recipe.getFluidIngredients())
			FluidHelper.report(tracker, ing.ingredient());
	}

	@Override
	public void reportCraftingStation(Recipe<?> recipe, LookupTracker tracker) {
		final RecipeType<?> type = recipe.getType();
		if (type == AllRecipeTypes.COMPACTING.getType()) {
			tracker.report(AllBlocks.BASIN.asItem());
			tracker.report(AllBlocks.MECHANICAL_PRESS.asItem());
		}
		if (type == AllRecipeTypes.MIXING.getType()) {
			tracker.report(AllBlocks.BASIN.asItem());
			tracker.report(AllBlocks.MECHANICAL_MIXER.asItem());
		}
		if (type == AllRecipeTypes.FILLING.getType()) tracker.report(AllBlocks.SPOUT.asItem());

		if (recipe instanceof StandardProcessingRecipe<?> r && r.getRequiredHeat() != HeatCondition.NONE)
			tracker.report(AllBlocks.LIT_BLAZE_BURNER.asItem());
	}
}