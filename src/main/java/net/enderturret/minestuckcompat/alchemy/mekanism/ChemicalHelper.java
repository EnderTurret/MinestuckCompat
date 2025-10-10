package net.enderturret.minestuckcompat.alchemy.mekanism;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;

import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;

import net.enderturret.minestuckcompat.alchemy.mekanism.ChemicalConversionManager.ChemicalConversion;

import mekanism.api.chemical.Chemical;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.chemical.ChemicalIngredient;

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

		addScaled(total, cost, ingredient.amount() / ((double) conversion.getValue().worth()));

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
			final List<ChemicalConversion> conversions = ChemicalConversionManager.getConversions(holder);
			if (conversions == null) continue;

			for (ChemicalConversion con : conversions) {
				final GristSet set = callback.lookupCostFor(con.item().value());
				if (set != null && (minCost == null || (set.getValue() / con.worth()) < (minCost.getValue() / minCon.worth()))) {
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
}