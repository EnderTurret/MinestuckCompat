package net.enderturret.minestuckcompat.alchemy.mekanism;

import java.util.List;
import java.util.Objects;

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

import net.enderturret.minestuckcompat.api.alchemy.AbstractRecipeInterpreter;

import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;

public final class ItemChemical2ItemInterpreter extends AbstractRecipeInterpreter {

	public static final MapCodec<ItemChemical2ItemInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(ItemChemical2ItemInterpreter::addedCost)
			).apply(instance, ItemChemical2ItemInterpreter::new));

	private final GristSet.Immutable addedCost;

	public ItemChemical2ItemInterpreter(GristSet.Immutable addedCost) {
		this.addedCost = addedCost;
	}

	public GristSet.Immutable addedCost() {
		return addedCost;
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	public List<Item> getOutputItems(Recipe<?> recipe) {
		if (recipe instanceof ItemStackChemicalToItemStackRecipe r)
			return r.getOutputDefinition().stream()
					.map(ItemStack::getItem)
					.filter(Objects::nonNull)
					.toList();

		return super.getOutputItems(recipe);
	}

	@Override
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
		final MutableGristSet totalCost = ingredientCost(recipe, callback);

		final int resultCount;
		if (recipe instanceof ItemStackChemicalToItemStackRecipe r) {
			if (!account(totalCost, callback, r.getItemInput().ingredient()))
				return null;

			if (!ChemicalHelper.account(totalCost, callback, r.getChemicalInput()))
				return null;

			resultCount = r.getOutputDefinition().stream()
					.mapToInt(ItemStack::getCount)
					.max().orElse(1);
		}
		else resultCount = recipe.getResultItem(getLookupProvider()).getCount();

		totalCost.add(addedCost);

		return finalizeGristCosts(totalCost, resultCount);
	}

	@Override
	public void reportPreliminaryLookups(Recipe<?> recipe, LookupTracker tracker) {
		if (recipe instanceof ItemStackChemicalToItemStackRecipe r)
			tracker.report(r.getItemInput().ingredient().ingredient());

		super.reportPreliminaryLookups(recipe, tracker);
	}
}