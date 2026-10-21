package net.enderturret.minestuckcompat.mixin.perf.small_grist_set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;

import net.enderturret.minestuckcompat.perf.ImmutableIntGristSet;
import net.enderturret.minestuckcompat.perf.MutableIntGristSet;

@Mixin(MutableGristSet.class)
public interface MixinMutableGristSet {

	@Overwrite
	public static MutableGristSet newDefault() {
		return new MutableIntGristSet();
	}

	@Overwrite
	public default GristSet.Immutable asImmutable() {
		return ImmutableIntGristSet.create((MutableGristSet) this);
	}
}