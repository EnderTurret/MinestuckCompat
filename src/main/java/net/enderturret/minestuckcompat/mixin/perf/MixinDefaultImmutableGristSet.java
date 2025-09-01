package net.enderturret.minestuckcompat.mixin.perf;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mraof.minestuck.api.alchemy.DefaultImmutableGristSet;
import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.perf.SmallImmutableGristSet;

@Mixin(DefaultImmutableGristSet.class)
public abstract class MixinDefaultImmutableGristSet {

	@Overwrite
	public static GristSet.Immutable create(List<GristAmount> amounts) {
		return SmallImmutableGristSet.create(amounts);
	}

	@Inject(at = @At("TAIL"), method = { "<init>(Ljava/util/Map;)V", "<init>(Lcom/google/common/collect/ImmutableMap$Builder;)V" })
	private void minestuckcompat$logCreation(CallbackInfo ci) {
		MinestuckCompat.LOGGER.info("DefaultImmutableGristSet created!"/*, new Throwable("stacktrace")*/);
	}
}