package net.enderturret.minestuckcompat.api.alchemy;

import java.util.Arrays;
import java.util.Collection;

import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.ItemStack;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

/**
 * Utilities for calculating fluid grist costs.
 * @author EnderTurret
 */
public final class FluidHelper {

	private static final int BUCKET_SIZE = 1000;

	/**
	 * Adds the cheapest fluid's grist cost to the specified {@code MutableGristSet}, scaled to its amount.
	 * @param total The grist set to add the cost to.
	 * @param callback The callback for looking up grist costs.
	 * @param ingredient The fluid ingredient to find a grist cost for.
	 * @return {@code true} if a grist cost was added, or {@code false} if one could not be found.
	 */
	public static boolean account(MutableGristSet total, GeneratorCallback callback, SizedFluidIngredient ingredient) {
		return account(total, callback, Arrays.asList(ingredient.ingredient().getStacks()), ingredient.amount());
	}

	/**
	 * Adds the cheapest fluid's grist cost to the specified {@code MutableGristSet}.
	 * @param total The grist set to add the cost to.
	 * @param callback The callback for looking up grist costs.
	 * @param ingredient The fluid ingredient to find a grist cost for.
	 * @return {@code true} if a grist cost was added, or {@code false} if one could not be found.
	 */
	public static boolean account(MutableGristSet total, GeneratorCallback callback, FluidIngredient ingredient) {
		return account(total, callback, Arrays.asList(ingredient.getStacks()), 1000);
	}

	/**
	 * Adds the cheapest fluid's grist cost to the specified {@code MutableGristSet}, scaled to its amount.
	 * @param total The grist set to add the cost to.
	 * @param callback The callback for looking up grist costs.
	 * @param ingredient A collection of fluids to find a grist cost for. The {@code FluidStack}'s count is ignored.
	 * @param count The fluid count.
	 * @return {@code true} if a grist cost was added, or {@code false} if one could not be found.
	 */
	public static boolean account(MutableGristSet total, GeneratorCallback callback, Collection<FluidStack> ingredient, int count) {
		MutableGristSet best = null;
		for (FluidStack stack : ingredient) {
			final MutableGristSet cost = lookup(stack, callback);
			if (best == null || (cost != null && best.getValue() > cost.getValue()))
				best = cost;
		}

		if (best != null) {
			final float scale = (float) count / BUCKET_SIZE;
			best.scale(scale, false);
			total.add(best);
			return true;
		}

		return false;
	}

	/**
	 * Looks up the grist cost for the specified fluid, based on its bucket grist cost.
	 * @param fluid The fluid to lookup the cost for.
	 * @param callback The callback for looking up grist costs.
	 * @return The grist cost for the specified fluid, or {@code null} if its bucket has no cost.
	 */
	@Nullable
	public static MutableGristSet lookup(FluidStack fluid, GeneratorCallback callback) {
		final ItemStack bucket = fluid.getFluidType().getBucket(fluid);
		final GristSet bucketCost = callback.lookupCostFor(bucket);
		if (bucketCost == null) return null;

		final ItemStack remainder = bucket.getCraftingRemainingItem();
		final GristSet remainderCost = callback.lookupCostFor(remainder);
		if (remainderCost == null) return null;

		return remainderCost.mutableCopy().scale(-1).add(bucketCost);
	}

	/**
	 * Reports the specified fluid to the {@code LookupTracker}.
	 * @param tracker The {@code LookupTracker} to report the fluid to.
	 * @param fluid The fluid to report.
	 */
	public static void report(LookupTracker tracker, FluidStack fluid) {
		tracker.report(fluid.getFluidType().getBucket(fluid));
	}
}