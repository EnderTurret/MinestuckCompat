package net.enderturret.minestuckcompat.mixin.perf.small_grist_set;

import java.util.Arrays;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;

import net.enderturret.minestuckcompat.perf.EmptyGristSet;
import net.enderturret.minestuckcompat.perf.SmallImmutableGristSet;

@Mixin(GristSet.class)
public interface MixinGristSet {

	@Overwrite
	public static GristSet.Immutable of(GristAmount... amounts) {
		return SmallImmutableGristSet.create(Arrays.asList(amounts));
	}

	@Definition(id = "emptyMap", method = "Ljava/util/Collections;emptyMap()Ljava/util/Map;")
	@Expression("::emptyMap")
	@ModifyExpressionValue(
			at = @At(value = "MIXINEXTRAS:EXPRESSION"),
			method = "<clinit>")
	private static GristSet.Immutable minestuckcompat$replaceEmpty(GristSet.Immutable originalValue) {
		return EmptyGristSet.INSTANCE;
	}
}