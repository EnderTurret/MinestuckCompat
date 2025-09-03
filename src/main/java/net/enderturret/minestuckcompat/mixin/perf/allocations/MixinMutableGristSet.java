package net.enderturret.minestuckcompat.mixin.perf.allocations;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;
import com.mraof.minestuck.api.alchemy.MutableGristSet;

@Mixin(MutableGristSet.class)
public interface MixinMutableGristSet {

	@Overwrite
	public default MutableGristSet add(GristSet set) {
		final MutableGristSet self = (MutableGristSet) this;

		for (Map.Entry<GristType, Long> entry : set.asMap().entrySet())
			self.add(entry.getKey(), entry.getValue());

		return self;
	}

	@Overwrite
	public default MutableGristSet scale(float scale, boolean roundDown) {
		final MutableGristSet self = (MutableGristSet) this;

		for (Map.Entry<GristType, Long> entry : self.asMap().entrySet()) {
			final long amount = entry.getValue();
			self.set(entry.getKey(), roundDown ? (long) (amount * scale) : roundToNonZero(amount * scale));
		}

		return self;
	}

	@Shadow
	private static int roundToNonZero(float value) { return 0; }
}