package net.enderturret.minestuckcompat.alchemy.mekanism;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import mekanism.api.chemical.Chemical;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.chemical.ChemicalIngredient;
import mekanism.common.registries.MekanismItems;

/**
 * Various helper methods for calculating grist costs of chemicals.
 * @author EnderTurret
 */
public final class ChemicalHelper {

	/**
	 * Adds the cheapest chemical's grist cost to the specified {@code MutableGristSet}, scaled to its amount.
	 * @param total The total cost to add the chemical cost to.
	 * @param callback The grist cost generator callback, for fetching item grist costs.
	 * @param ingredient The {@code ChemicalStackIngredient} to add the grist cost of.
	 * @return {@code true} if the grist cost was added, or {@code false} if the chemical does not have a grist cost.
	 */
	public static boolean account(MutableGristSet total, GeneratorCallback callback, ChemicalStackIngredient ingredient) {
		final MutableObject<ChemicalConversion> conversion = new MutableObject<>(null);
		final GristSet cost = lookup(callback, ingredient.ingredient(), conversion);
		if (cost == null) return false;

		addScaled(total, cost, ingredient.amount() / ((double) conversion.getValue().worth));

		return true;
	}

	/**
	 * Attempts to look up the grist cost for the specified {@code ChemicalIngredient}.
	 * If none of the accepted chemicals have item conversions, {@code null} is returned.
	 * @param callback The grist cost generator callback, for fetching item grist costs.
	 * @param ingredient The chemical ingredient to look up the grist cost for.
	 * @param conversion An optional holder for the exact chosen conversion. May be {@code null}.
	 * @return The grist cost of the chemical ingredient, or {@code null}.
	 */
	@Nullable
	public static GristSet lookup(GeneratorCallback callback, ChemicalIngredient ingredient, @Nullable MutableObject<ChemicalConversion> conversion) {
		if (ingredient.hasNoChemicals()) return GristSet.EMPTY;

		GristSet minCost = null;
		ChemicalConversion minCon = null;

		for (Holder<Chemical> holder : ingredient.getChemicalHolders()) {
			final List<ChemicalConversion> conversions = CHEMICAL_TO_ITEM.get(holder.getKey().location());
			if (conversions == null) continue;

			for (ChemicalConversion con : conversions) {
				final GristSet set = callback.lookupCostFor(con.item().value());
				if (set != null && (minCost == null || (set.getValue() / con.worth) < (minCost.getValue() / minCon.worth))) {
					minCost = set;
					minCon = con;
				}
			}
		}
		if (conversion != null) conversion.setValue(minCon);

		return minCost;
	}

	/**
	 * Adds the specified {@code value} grist cost to the specified {@link MutableGristSet}, scaled to the specified {@code scale}.
	 * @param target The grist set to add {@code value} to.
	 * @param value The grist set to add to {@code target}.
	 * @param scale The number to scale the contents of {@code value} to.
	 */
	public static void addScaled(MutableGristSet target, GristSet value, double scale) {
		for (Map.Entry<GristType, Long> entry : value.asMap().entrySet()) {
			final long scaled = Mth.ceil(entry.getValue() * scale);
			target.add(entry.getKey(), scaled < 1 ? 1 : scaled);
		}
	}

	private static final Map<ResourceLocation, List<ChemicalConversion>> CHEMICAL_TO_ITEM;

	static {
		final Map<ResourceLocation, List<ChemicalConversion>> map = new HashMap<>();

		map.put(ResourceLocation.fromNamespaceAndPath("mekanism", "bio"), List.of(
				new ChemicalConversion(MekanismItems.BIO_FUEL, 5)
				));
		map.put(ResourceLocation.fromNamespaceAndPath("mekanism", "carbon"), List.of(
				new ChemicalConversion(Items.CHARCOAL, 20), new ChemicalConversion(Items.COAL, 10), new ChemicalConversion(Items.COAL_BLOCK, 90), new ChemicalConversion(MekanismItems.ENRICHED_CARBON, 80)
				));
		map.put(ResourceLocation.fromNamespaceAndPath("mekanism", "diamond"), List.of(
				new ChemicalConversion(MekanismItems.DIAMOND_DUST, 10), new ChemicalConversion(MekanismItems.ENRICHED_DIAMOND, 80)));
		map.put(ResourceLocation.fromNamespaceAndPath("mekanism", "gold"), List.of(
				new ChemicalConversion(ResourceLocation.fromNamespaceAndPath("mekanism", "dust_gold"), 10), new ChemicalConversion(MekanismItems.ENRICHED_GOLD, 80)));
		map.put(ResourceLocation.fromNamespaceAndPath("mekanism", "hydrogen_chloride"), List.of(
				new ChemicalConversion(MekanismItems.SALT, 2)));
		map.put(ResourceLocation.fromNamespaceAndPath("mekanism", "osmium"), List.of(
				new ChemicalConversion(ResourceLocation.fromNamespaceAndPath("mekanism", "ingot_osmium"), 200), new ChemicalConversion(ResourceLocation.fromNamespaceAndPath("mekanism", "block_osmium"), 1800)));
		map.put(ResourceLocation.fromNamespaceAndPath("mekanism", "redstone"), List.of(
				new ChemicalConversion(Items.REDSTONE, 10), new ChemicalConversion(Items.REDSTONE_BLOCK, 90), new ChemicalConversion(MekanismItems.ENRICHED_REDSTONE, 80)));
		map.put(ResourceLocation.fromNamespaceAndPath("mekanism", "refined_obsidian"), List.of(
				new ChemicalConversion(ResourceLocation.fromNamespaceAndPath("mekanism", "dust_refined_obsidian"), 10), new ChemicalConversion(MekanismItems.ENRICHED_OBSIDIAN, 80)));
		map.put(ResourceLocation.fromNamespaceAndPath("mekanism", "tin"), List.of(
				new ChemicalConversion(ResourceLocation.fromNamespaceAndPath("mekanism", "dust_tin"), 10), new ChemicalConversion(MekanismItems.ENRICHED_TIN, 80)));
		//map.put(ResourceLocation.fromNamespaceAndPath("mekanism", "antimatter"), List.of(MekanismItems.ANTIMATTER_PELLET.asItem()));
		map.put(ResourceLocation.fromNamespaceAndPath("mekanism", "oxygen"), List.of(new ChemicalConversion(Items.FLINT, 10)));
		//map.put(ResourceLocation.fromNamespaceAndPath("mekanism", "steam"), List.of(MekanismItems.ANTIMATTER_PELLET.asItem()));
		//map.put(ResourceLocation.fromNamespaceAndPath("mekanism", "water_vapor"), List.of(MekanismItems.ANTIMATTER_PELLET.asItem()));

		CHEMICAL_TO_ITEM = Map.copyOf(map);
	}

	@SuppressWarnings("deprecation")
	private static record ChemicalConversion(Holder<Item> item, int worth) {
		private ChemicalConversion {}
		private ChemicalConversion(Item item, int worth) {
			this(item.builtInRegistryHolder(), worth);
		}
		private ChemicalConversion(ResourceLocation id, int worth) {
			this(BuiltInRegistries.ITEM.getHolder(id).orElseThrow(() -> new NoSuchElementException(id.toString())), worth);
		}
	}
}