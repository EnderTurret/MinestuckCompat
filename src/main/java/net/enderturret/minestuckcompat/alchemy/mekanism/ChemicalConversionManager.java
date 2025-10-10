package net.enderturret.minestuckcompat.alchemy.mekanism;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import net.neoforged.bus.api.SubscribeEvent;

import net.enderturret.minestuckcompat.api.alchemy.GenerateGristCostsEvent;

import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ItemStackToChemicalRecipe;
import mekanism.api.recipes.MekanismRecipeTypes;
import mekanism.api.recipes.basic.BasicChemicalConversionRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;

public final class ChemicalConversionManager {

	@Nullable
	private static Map<Holder<Chemical>, List<ChemicalConversion>> chemicalToItem;

	/**
	 * Retrieves the list of item to chemical conversions associated with the specified chemical.
	 * @param chemical The chemical to retrieve the list for.
	 * @return The list of items that convert into the chemical.
	 */
	public static List<ChemicalConversion> getConversions(Holder<Chemical> chemical) {
		return Objects.requireNonNull(chemicalToItem, "getConversions() called outside of grist cost generation!").get(chemical);
	}

	@SubscribeEvent
	static void beforeGristCostGeneration(GenerateGristCostsEvent.Pre e) {
		chemicalToItem = new HashMap<>();

		for (RecipeHolder<ItemStackToChemicalRecipe> holder : e.getRecipeManager().getAllRecipesFor(MekanismRecipeTypes.TYPE_CHEMICAL_CONVERSION.get())) {
			final BasicChemicalConversionRecipe recipe = (BasicChemicalConversionRecipe) holder.value();
			final ItemStackIngredient input = recipe.getInput();
			final ChemicalStack output = recipe.getOutputRaw();

			final List<ChemicalConversion> list = chemicalToItem.computeIfAbsent(
					output.getChemicalHolder(), k -> new ArrayList<>());

			for (ItemStack stack : input.getRepresentations())
				list.add(new ChemicalConversion(stack.getItemHolder(), (int) output.getAmount()));
		}

		for (Holder<Chemical> key : chemicalToItem.keySet())
			chemicalToItem.compute(key, (k, v) -> List.copyOf(Set.copyOf(v)));
	}

	@SubscribeEvent
	static void afterGristCostGeneration(GenerateGristCostsEvent.Post e) {
		chemicalToItem = null;
	}

	/**
	 * Represents a single conversion of an item into some amount of a chemical.
	 * @param item The item that can be converted.
	 * @param worth How much of the chemical the item is worth when converted.
	 * @author EnderTurret
	 */
	public static record ChemicalConversion(Holder<Item> item, int worth) {}
}