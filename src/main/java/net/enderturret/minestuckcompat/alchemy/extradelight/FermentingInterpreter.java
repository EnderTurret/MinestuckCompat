package net.enderturret.minestuckcompat.alchemy.extradelight;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.lance5057.extradelight.ExtraDelightItems;
import com.lance5057.extradelight.workstations.vat.recipes.VatRecipe;
import com.lance5057.extradelight.workstations.vat.recipes.VatRecipe.StageIngredient;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.AnalyzableRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.FluidHelper;

public final class FermentingInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<VatRecipe> implements AnalyzableRecipeInterpreter {

	public static final MapCodec<FermentingInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			COST_FIELD.forGetter(FermentingInterpreter::addedCost),
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("cost_per_day", GristSet.EMPTY).forGetter(FermentingInterpreter::costPerDay),
			Codec.FLOAT.optionalFieldOf("cost_per_day_multiplier", 1f).forGetter(FermentingInterpreter::costPerDayMultiplier)
			).apply(instance, FermentingInterpreter::new));

	private final GristSet.Immutable costPerDay;
	private final float costPerDayMultiplier;

	/**
	 * Constructs a new {@code FermentingInterpreter}.
	 * @param addedCost The grist cost added for all recipes processed by this interpreter.
	 * @param costPerDay The grist cost added for each day the input item must be fermented for.
	 * @param costPerDayMultiplier A multiplier applied to the cumulative cost. Allows scaling the full day cost down.
	 */
	public FermentingInterpreter(GristSet.Immutable addedCost, GristSet.Immutable costPerDay, float costPerDayMultiplier) {
		super(VatRecipe.class, addedCost);
		this.costPerDay = costPerDay;
		this.costPerDayMultiplier = costPerDayMultiplier;
	}

	public GristSet.Immutable costPerDay() {
		return costPerDay;
	}

	public float costPerDayMultiplier() {
		return costPerDayMultiplier;
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	protected List<ItemStack> getOutputItemStacksTyped(VatRecipe recipe) {
		return List.of(recipe.getResultItem(null));
	}

	@Override
	@Nullable
	protected MutableGristSet generateCost(MutableGristSet totalCost, VatRecipe recipe, Item output, GeneratorCallback callback) {

		int time = 0;
		for (StageIngredient ing : recipe.getStageIngredients()) {
			if (!account(totalCost, callback, ing.ingredient))
				return null;

			time += ing.time / 24000;
		}

		totalCost.add(scale(this.costPerDay, time * costPerDayMultiplier, false));

		if (!FluidHelper.account(totalCost, callback, recipe.getFluid()))
			return null;

		for (int i = 0; i < recipe.getUsedItem().getCount(); i++)
			if (!account(totalCost, callback, recipe.getUsedItem().getItem()))
				return null;

		return totalCost;
	}

	@Override
	protected void reportPreliminaryLookupsTyped(VatRecipe recipe, LookupTracker tracker) {
	}

	@Override
	public void reportCraftingStation(Recipe<?> recipe, LookupTracker tracker) {
		tracker.report(ExtraDelightItems.VAT.asItem());
	}
}