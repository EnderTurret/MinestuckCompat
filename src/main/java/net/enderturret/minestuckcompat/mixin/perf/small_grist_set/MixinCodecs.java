package net.enderturret.minestuckcompat.mixin.perf.small_grist_set;

import java.util.List;
import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;
import com.mraof.minestuck.api.alchemy.GristTypes;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import net.enderturret.minestuckcompat.perf.SmallImmutableGristSet;

@Mixin(GristSet.Codecs.class)
public abstract class MixinCodecs {

	@Unique
	private static DataResult<List<GristAmount>> minestuckcompat$validateWithinLimit(List<GristAmount> list) {
		for (GristAmount amount : list)
			if (amount.amount() > Integer.MAX_VALUE)
				return DataResult.error(() -> "Grist amount exceeds maximum possible (" + Integer.MAX_VALUE + "): " + amount);

		return DataResult.success(list);
	}

	@Unique
	private static DataResult<Map<GristType, Long>> minestuckcompat$validateWithinLimit(Map<GristType, Long> map) {
		for (var entry : map.entrySet())
			if (entry.getValue() > Integer.MAX_VALUE)
				return DataResult.error(() -> "Grist amount exceeds maximum possible (" + Integer.MAX_VALUE + "): " + entry.getKey() + " = " + entry.getValue());

		return DataResult.success(map);
	}

	@ModifyExpressionValue(
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/serialization/Codec;xmap(Ljava/util/function/Function;Ljava/util/function/Function;)Lcom/mojang/serialization/Codec;"
					),
			method = "<clinit>",
			slice = @Slice(to = @At(
							value = "FIELD",
							target = "Lcom/mraof/minestuck/api/alchemy/GristSet$Codecs;NON_NEGATIVE_CODEC:Lcom/mojang/serialization/Codec;",
							opcode = Opcodes.PUTSTATIC
					)))
	private static Codec<GristSet.Immutable> minestuckcompat$wrapNonNegativeCodec(Codec<GristSet.Immutable> old) {
		return GristAmount.NON_NEGATIVE_LIST_CODEC.validate(MixinCodecs::minestuckcompat$validateWithinLimit).xmap(SmallImmutableGristSet::create, GristSet::asAmounts);
	}

	@ModifyExpressionValue(
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/serialization/codecs/UnboundedMapCodec;xmap(Ljava/util/function/Function;Ljava/util/function/Function;)Lcom/mojang/serialization/Codec;"
					),
			method = "<clinit>",
			slice = @Slice(
					from = @At(
							value = "FIELD",
							target = "Lcom/mraof/minestuck/api/alchemy/GristSet$Codecs;NON_NEGATIVE_CODEC:Lcom/mojang/serialization/Codec;",
							opcode = Opcodes.PUTSTATIC,
							shift = At.Shift.AFTER
					), to = @At(
							value = "FIELD",
							target = "Lcom/mraof/minestuck/api/alchemy/GristSet$Codecs;MAP_CODEC:Lcom/mojang/serialization/Codec;",
							opcode = Opcodes.PUTSTATIC
					)))
	private static Codec<GristSet.Immutable> minestuckcompat$wrapMapCodec(Codec<GristSet.Immutable> old) {
		return Codec.unboundedMap(GristTypes.REGISTRY.byNameCodec(), Codec.LONG).validate(MixinCodecs::minestuckcompat$validateWithinLimit).xmap(SmallImmutableGristSet::create, GristSet::asMap);
	}

	@ModifyExpressionValue(
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/serialization/Codec;xmap(Ljava/util/function/Function;Ljava/util/function/Function;)Lcom/mojang/serialization/Codec;"
					),
			method = "<clinit>",
			slice = @Slice(
					from = @At(
							value = "FIELD",
							target = "Lcom/mraof/minestuck/api/alchemy/GristSet$Codecs;MAP_CODEC:Lcom/mojang/serialization/Codec;",
							opcode = Opcodes.PUTSTATIC,
							shift = At.Shift.AFTER
					), to = @At(
							value = "FIELD",
							target = "Lcom/mraof/minestuck/api/alchemy/GristSet$Codecs;LIST_CODEC:Lcom/mojang/serialization/Codec;",
							opcode = Opcodes.PUTSTATIC
					)))
	private static Codec<GristSet.Immutable> minestuckcompat$wrapListCodec(Codec<GristSet.Immutable> old) {
		return GristAmount.LIST_CODEC.validate(MixinCodecs::minestuckcompat$validateWithinLimit).xmap(SmallImmutableGristSet::create, GristSet::asAmounts);
	}

	@ModifyExpressionValue(
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/network/codec/StreamCodec;composite(Lnet/minecraft/network/codec/StreamCodec;Ljava/util/function/Function;Ljava/util/function/Function;)Lnet/minecraft/network/codec/StreamCodec;"
					),
			method = "<clinit>")
	private static StreamCodec<RegistryFriendlyByteBuf, GristSet.Immutable> minestuckcompat$wrapStreamCodec(StreamCodec<RegistryFriendlyByteBuf, GristSet.Immutable> old) {
		return StreamCodec.composite(
				GristAmount.STREAM_CODEC.apply(ByteBufCodecs.list()),
				GristSet::asAmounts,
				SmallImmutableGristSet::create
		);
	}
}