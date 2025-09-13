package net.enderturret.minestuckcompat.alchemy.ie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import net.neoforged.fml.ModList;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.FluidHelper;
import net.enderturret.minestuckcompat.mixin.feature.immersiveengineering.TagOutputAccess;

import blusunrize.immersiveengineering.api.EnumMetals;
import blusunrize.immersiveengineering.api.crafting.BlueprintCraftingRecipe;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
import blusunrize.immersiveengineering.api.crafting.TagOutput;
import blusunrize.immersiveengineering.common.register.IEItems;
import blusunrize.immersiveengineering.common.register.IEItems.Metals;

public final class MultiblockInterpreter extends AbstractCostAddingRecipeInterpreter {

	public static final MapCodec<MultiblockInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(MultiblockInterpreter::addedCost)
			).apply(instance, MultiblockInterpreter::new));

	public MultiblockInterpreter(GristSet.Immutable addedCost) {
		super(addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	private boolean warned = false;

	@Override
	public List<Item> getOutputItems(Recipe<?> recipe) {
		if (recipe instanceof BlueprintCraftingRecipe r)
			return safeResolve(r.output);

		if (recipe instanceof MultiblockRecipe && !warned) {
			MinestuckCompat.LOGGER.warn("Called for unhandled MultiblockRecipe type {}! This may resolve tags too early!", recipe.getClass().getSimpleName());
			warned = true;
		}

		return super.getOutputItems(recipe);
	}

	static List<Item> safeResolve(TagOutput output) {
		if (output instanceof TagOutputAccess access) {
			final Either<IngredientWithSize, ItemStack> either = access.minestuckcompat$getRawData();
			if (either.right().isPresent())
				return List.of(either.right().get().getItem());

			final Ingredient ing = either.left().get().getBaseIngredient();
			final List<Item> ret = new ArrayList<>();

			for (Ingredient.Value val : ing.getValues()) {
				if (val instanceof Ingredient.ItemValue item)
					ret.add(item.item().getItem());
				else if (val instanceof Ingredient.TagValue tag) {
					if (tagToIeItem == null) buildTagMap();
					final Item item = tagToIeItem.get(tag.tag().location());
					if (item == null)
						MinestuckCompat.LOGGER.info("Missing entry for TagOutput {}", tag.tag().location());
					else
						ret.add(item);
				}
			}

			return List.copyOf(ret);
		}

		return List.of();
	}

	private static Map<ResourceLocation, Item> tagToIeItem = null;

	// FIXME: This is a terrible workaround. What if we tried moving the interpreter map building to when the GristCostGenerator runs instead?
	private static void buildTagMap() {
		tagToIeItem = new HashMap<>();

		for (EnumMetals metal : EnumMetals.values()) {
			final String name = metal.tagName();
			tagToIeItem.put(c("dusts/" + name), Metals.DUSTS.get(metal).asItem());
			tagToIeItem.put(c("ingots/" + name), Metals.INGOTS.get(metal).asItem()); // Also includes the vanilla ingots.
			tagToIeItem.put(c("plates/" + name), Metals.PLATES.get(metal).asItem());
		}

		tagToIeItem.put(c("dusts/coal_coke"), IEItems.Ingredients.DUST_COKE.asItem());
		tagToIeItem.put(c("dusts/hop_graphite"), IEItems.Ingredients.DUST_HOP_GRAPHITE.asItem());

		tagToIeItem.put(c("rods/aluminum"), IEItems.Ingredients.STICK_ALUMINUM.asItem());
		tagToIeItem.put(c("rods/iron"), IEItems.Ingredients.STICK_IRON.asItem());
		tagToIeItem.put(c("rods/steel"), IEItems.Ingredients.STICK_STEEL.asItem());

		tagToIeItem.put(c("wires/aluminum"), IEItems.Ingredients.WIRE_ALUMINUM.asItem());
		tagToIeItem.put(c("wires/copper"), IEItems.Ingredients.WIRE_COPPER.asItem());
		tagToIeItem.put(c("wires/electrum"), IEItems.Ingredients.WIRE_ELECTRUM.asItem());
		tagToIeItem.put(c("wires/lead"), IEItems.Ingredients.WIRE_LEAD.asItem());
		tagToIeItem.put(c("wires/steel"), IEItems.Ingredients.WIRE_STEEL.asItem());

		if (ModList.get().isLoaded("mekanism")) {
			tagToIeItem.put(c("dusts/osmium"), BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("mekanism", "dust_osmium")));
			tagToIeItem.put(c("dusts/tin"), BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("mekanism", "dust_tin")));
			tagToIeItem.put(c("dusts/bronze"), BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("mekanism", "dust_bronze")));
			tagToIeItem.put(c("dusts/coal"), BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("mekanism", "dust_coal")));
			tagToIeItem.put(c("gems/fluorite"), BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("mekanism", "fluorite")));
			tagToIeItem.put(c("ingots/bronze"), BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("mekanism", "ingot_bronze")));
		}
		if (ModList.get().isLoaded("create")) {
			tagToIeItem.put(c("ingots/brass"), BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("create", "brass_ingot")));
			tagToIeItem.put(c("plates/brass"), BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("create", "brass_plate")));
		}
	}

	private static ResourceLocation c(String path) {
		return ResourceLocation.fromNamespaceAndPath("c", path);
	}

	@Override
	@Nullable
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
		final MutableGristSet totalCost = ingredientCost(recipe, callback);
		if (totalCost == null) return null;

		if (recipe instanceof MultiblockRecipe r) {
			if (r.getItemInputs() != null)
				for (IngredientWithSize ing : r.getItemInputs())
					if (!account(totalCost, callback, ing.getBaseIngredient(), ing.getCount()))
						return null;

			if (r.getFluidInputs() != null)
				for (SizedFluidIngredient ing : r.getFluidInputs())
					if (!FluidHelper.account(totalCost, callback, ing))
						return null;
		}

		return finalizeGristCosts(totalCost, recipe);
	}

	@Override
	public void reportPreliminaryLookups(Recipe<?> recipe, LookupTracker tracker) {
		super.reportPreliminaryLookups(recipe, tracker);

		if (recipe instanceof MultiblockRecipe r)
			if (r.getItemInputs() != null)
				for (IngredientWithSize ing : r.getItemInputs())
					tracker.report(ing.getBaseIngredient());
	}
}