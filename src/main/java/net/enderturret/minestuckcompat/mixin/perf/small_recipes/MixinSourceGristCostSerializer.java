package net.enderturret.minestuckcompat.mixin.perf.small_recipes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mraof.minestuck.alchemy.recipe.generator.SourceGristCost;
import com.mraof.minestuck.api.alchemy.GristSet;

@Mixin(SourceGristCost.Serializer.class)
public abstract class MixinSourceGristCostSerializer {

	@Redirect(
			at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;fieldOf(Ljava/lang/String;)Lcom/mojang/serialization/MapCodec;"),
			method = "*",
			slice = @Slice(
					from = @At(value = "CONSTANT", args = "stringValue=grist_cost"),
					to = @At(value = "CONSTANT", args = "stringValue=priority")
					))
	private static MapCodec<GristSet.Immutable> minestuckcompat$makeCostOptional(Codec<GristSet.Immutable> codec, String name) {
		if (!"grist_cost".equals(name)) throw new IllegalStateException("Unexpected field name: " + name);
		return codec.optionalFieldOf(name, GristSet.EMPTY);
	}
}