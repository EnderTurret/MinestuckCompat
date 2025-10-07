package net.enderturret.minestuckcompat.alchemy.mekanism;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;

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
import net.minecraft.world.item.crafting.RecipeType;

import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.AnalyzableRecipeInterpreter;

import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.MekanismRecipeTypes;
import mekanism.common.registries.MekanismBlocks;

public final class Item2ItemInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<ItemStackToItemStackRecipe> implements AnalyzableRecipeInterpreter {

	public static final MapCodec<Item2ItemInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			COST_FIELD.forGetter(Item2ItemInterpreter::addedCost)
			).apply(instance, Item2ItemInterpreter::new));

	public Item2ItemInterpreter(GristSet.Immutable addedCost) {
		super(ItemStackToItemStackRecipe.class, addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	public List<Item> getOutputItemsTyped(ItemStackToItemStackRecipe recipe) {
		return recipe.getOutputDefinition().stream()
				.map(ItemStack::getItem)
				.filter(Objects::nonNull)
				.toList();
	}

	@Override
	protected MutableGristSet generateCost(MutableGristSet totalCost, ItemStackToItemStackRecipe recipe, Item output, GeneratorCallback callback) {
		if (!account(totalCost, callback, recipe.getInput().ingredient()))
			return null;

		return totalCost;
	}

	@Override
	@Nullable
	protected GristSet finalizeGristCosts(@Nullable MutableGristSet totalCost, Recipe<?> recipe) {
		return finalizeGristCosts(totalCost, recipe instanceof ItemStackToItemStackRecipe r
				? r.getOutputDefinition().stream().mapToInt(ItemStack::getCount).max().orElse(1)
				: recipe.getResultItem(getLookupProvider()).getCount());
	}

	@Override
	public void reportPreliminaryLookupsTyped(ItemStackToItemStackRecipe recipe, LookupTracker tracker) {
		tracker.report(recipe.getInput().ingredient().ingredient());
	}

	@Override
	public void reportCraftingStation(Recipe<?> recipe, LookupTracker tracker) {
		final RecipeType<?> type = recipe.getType();
		if (type == MekanismRecipeTypes.TYPE_CRUSHING.get()) tracker.report(MekanismBlocks.CRUSHER.asItem());
		else if (type == MekanismRecipeTypes.TYPE_ENRICHING.get()) tracker.report(MekanismBlocks.ENRICHMENT_CHAMBER.asItem());
		else if (type == MekanismRecipeTypes.TYPE_SMELTING.get()) tracker.report(MekanismBlocks.ENERGIZED_SMELTER.asItem());
	}
}