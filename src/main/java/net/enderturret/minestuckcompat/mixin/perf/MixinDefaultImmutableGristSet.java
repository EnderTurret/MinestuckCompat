package net.enderturret.minestuckcompat.mixin.perf;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.mraof.minestuck.api.alchemy.DefaultImmutableGristSet;
import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;

import net.enderturret.minestuckcompat.perf.SmallImmutableGristSet;

@Mixin(DefaultImmutableGristSet.class)
public abstract class MixinDefaultImmutableGristSet {

	@Overwrite
	public static GristSet.Immutable create(List<GristAmount> amounts) {
		return SmallImmutableGristSet.create(amounts);
	}
}