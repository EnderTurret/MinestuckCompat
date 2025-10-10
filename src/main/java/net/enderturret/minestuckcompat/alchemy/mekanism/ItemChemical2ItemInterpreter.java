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

import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.MekanismRecipeTypes;
import mekanism.common.registries.MekanismBlocks;

/**
 * A recipe interpreter for Mekanism's {@link ItemStackChemicalToItemStackRecipe}.
 * @author EnderTurret
 */
public final class ItemChemical2ItemInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<ItemStackChemicalToItemStackRecipe> implements AnalyzableRecipeInterpreter {

	public static final MapCodec<ItemChemical2ItemInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			COST_FIELD.forGetter(ItemChemical2ItemInterpreter::addedCost)
			).apply(instance, ItemChemical2ItemInterpreter::new));

	/**
	 * Constructs a new {@code ItemChemical2ItemInterpreter}.
	 * @param addedCost The grist cost added for all recipes processed by this interpreter.
	 */
	public ItemChemical2ItemInterpreter(GristSet.Immutable addedCost) {
		super(ItemStackChemicalToItemStackRecipe.class, addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	public List<Item> getOutputItemsTyped(ItemStackChemicalToItemStackRecipe recipe) {
		return recipe.getOutputDefinition().stream()
				.map(ItemStack::getItem)
				.filter(Objects::nonNull)
				.toList();
	}

	@Override
	public MutableGristSet generateCost(MutableGristSet totalCost, ItemStackChemicalToItemStackRecipe recipe, Item output, GeneratorCallback callback) {
		if (!account(totalCost, callback, recipe.getItemInput().ingredient()))
			return null;

		if (!ChemicalHelper.account(totalCost, callback, recipe.getChemicalInput()))
			return null;

		return totalCost;
	}

	@Override
	@Nullable
	protected GristSet finalizeGristCosts(@Nullable MutableGristSet totalCost, Recipe<?> recipe) {
		return finalizeGristCosts(totalCost, recipe instanceof ItemStackChemicalToItemStackRecipe r
				? r.getOutputDefinition().stream().mapToInt(ItemStack::getCount).max().orElse(1)
				: recipe.getResultItem(getLookupProvider()).getCount());
	}

	@Override
	public void reportPreliminaryLookupsTyped(ItemStackChemicalToItemStackRecipe recipe, LookupTracker tracker) {
		tracker.report(recipe.getItemInput().ingredient().ingredient());
		ChemicalHelper.report(tracker, recipe.getChemicalInput().ingredient());
	}

	@Override
	public void reportCraftingStation(Recipe<?> recipe, LookupTracker tracker) {
		final RecipeType<?> type = recipe.getType();
		if (type == MekanismRecipeTypes.TYPE_COMPRESSING.get()) tracker.report(MekanismBlocks.OSMIUM_COMPRESSOR.asItem());
		else if (type == MekanismRecipeTypes.TYPE_INJECTING.get()) tracker.report(MekanismBlocks.CHEMICAL_INJECTION_CHAMBER.asItem());
		else if (type == MekanismRecipeTypes.TYPE_PURIFYING.get()) tracker.report(MekanismBlocks.PURIFICATION_CHAMBER.asItem());
		else if (type == MekanismRecipeTypes.TYPE_METALLURGIC_INFUSING.get()) tracker.report(MekanismBlocks.METALLURGIC_INFUSER.asItem());
		else if (type == MekanismRecipeTypes.TYPE_PAINTING.get()) tracker.report(MekanismBlocks.PAINTING_MACHINE.asItem());
		else if (type == MekanismRecipeTypes.TYPE_NUCLEOSYNTHESIZING.get()) tracker.report(MekanismBlocks.ANTIPROTONIC_NUCLEOSYNTHESIZER.asItem());
	}
}