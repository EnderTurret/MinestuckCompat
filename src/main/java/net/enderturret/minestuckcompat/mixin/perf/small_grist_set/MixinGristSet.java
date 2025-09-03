package net.enderturret.minestuckcompat.mixin.perf.small_grist_set;

import java.util.Arrays;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;

import net.enderturret.minestuckcompat.perf.SmallImmutableGristSet;

@Mixin(GristSet.class)
public interface MixinGristSet {

	@Overwrite
	public static GristSet.Immutable of(GristAmount... amounts) {
		return SmallImmutableGristSet.create(Arrays.asList(amounts));
	}
}