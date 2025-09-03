package net.enderturret.minestuckcompat.mixin.perf.small_grist_set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;

import net.enderturret.minestuckcompat.perf.SmallImmutableGristSet;
import net.enderturret.minestuckcompat.perf.SmallMutableGristSet;

@Mixin(MutableGristSet.class)
public interface MixinMutableGristSet {

	@Overwrite
	public static MutableGristSet newDefault() {
		return new SmallMutableGristSet();
	}

	@Overwrite
	public default GristSet.Immutable asImmutable() {
		return SmallImmutableGristSet.create((MutableGristSet) this);
	}
}