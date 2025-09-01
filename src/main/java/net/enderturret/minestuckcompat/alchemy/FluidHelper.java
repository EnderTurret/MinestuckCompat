package net.enderturret.minestuckcompat.alchemy;

import java.util.Collection;

import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;

import net.minecraft.world.item.ItemStack;

import net.neoforged.neoforge.fluids.FluidStack;

public final class FluidHelper {

	private static final int BUCKET_SIZE = 1000;

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
}