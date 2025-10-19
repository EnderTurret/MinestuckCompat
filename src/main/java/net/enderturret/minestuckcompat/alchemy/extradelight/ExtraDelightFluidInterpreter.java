package net.enderturret.minestuckcompat.alchemy.extradelight;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.lance5057.extradelight.ExtraDelightItems;
import com.lance5057.extradelight.ExtraDelightRecipes;
import com.lance5057.extradelight.workstations.chiller.ChillerRecipe;
import com.lance5057.extradelight.workstations.mixingbowl.recipes.MixingBowlRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.AnalyzableRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.FluidHelper;

public final class ExtraDelightFluidInterpreter extends AbstractCostAddingRecipeInterpreter implements AnalyzableRecipeInterpreter {

	public static final MapCodec<ExtraDelightFluidInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(ExtraDelightFluidInterpreter::addedCost)
			).apply(instance, ExtraDelightFluidInterpreter::new));

	/**
	 * Constructs a new {@code ExtraDelightFluidInterpreter}.
	 * @param addedCost The grist cost added for all recipes processed by this interpreter.
	 */
	public ExtraDelightFluidInterpreter(GristSet.Immutable addedCost) {
		super(addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	public List<ItemStack> getOutputItemStacks(Recipe<?> recipe) {
		return super.getOutputItemStacks(recipe);
	}

	@Override
	protected @Nullable MutableGristSet ingredientCost(Recipe<?> recipe, GeneratorCallback callback) {
		final MutableGristSet totalCost = MutableGristSet.newDefault();

		for (Ingredient ing : recipe.getIngredients())
			if (!account(totalCost, callback, ing))
				return null;

		for (SizedFluidIngredient ing : getFluidInputs(recipe))
			if (!FluidHelper.account(totalCost, callback, ing))
				return null;

		return totalCost;
	}

	@Override
	@Nullable
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
		final GristSet ret = super.generateCost(recipe, output, callback);
		if (ret == null) return null;

		ItemStack container = ItemStack.EMPTY;

		if (recipe instanceof MixingBowlRecipe r)
			container = r.getContainer();
		else if (recipe instanceof ChillerRecipe r)
			container = r.getOutputContainer();

		if (!container.isEmpty() && !account((MutableGristSet) ret, callback, container.getItem()))
			return null;

		return ret;
	}

	@Override
	public void reportPreliminaryLookups(Recipe<?> recipe, LookupTracker tracker) {
		for (Ingredient ing : recipe.getIngredients())
			tracker.report(ing);

		for (SizedFluidIngredient ing : getFluidInputs(recipe))
			FluidHelper.report(tracker, ing.ingredient());
	}

	private Iterable<SizedFluidIngredient> getFluidInputs(Recipe<?> recipe) {
		if (recipe instanceof MixingBowlRecipe r)
			return r.getFluids();

		if (recipe instanceof ChillerRecipe r)
			return r.getFluid().isEmpty() ? List.of() : List.of(SizedFluidIngredient.of(r.getFluid()));

		return List.of();
	}

	@Override
	public void reportCraftingStation(Recipe<?> recipe, LookupTracker tracker) {
		final RecipeType<?> type = recipe.getType();

		if (type == ExtraDelightRecipes.MIXING_BOWL.get())
			tracker.report(ExtraDelightItems.MIXING_BOWL.get());

		if (type == ExtraDelightRecipes.CHILLER.get())
			tracker.report(ExtraDelightItems.CHILLER.get());
	}
}