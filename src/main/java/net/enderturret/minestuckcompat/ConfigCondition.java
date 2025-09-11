package net.enderturret.minestuckcompat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.neoforged.neoforge.common.conditions.ICondition;

public record ConfigCondition(String modId) implements ICondition {

	public static final MapCodec<ConfigCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
			Codec.STRING.fieldOf("modid").forGetter(ConfigCondition::modId)
			).apply(builder, ConfigCondition::new));

	@Override
	public MapCodec<? extends ICondition> codec() {
		return CODEC;
	}

	@Override
	public boolean test(IContext context) {
		return MinestuckCompatConfig.common().isModEnabled(modId);
	}

	@Override
	public String toString() {
		return "config(\"" + modId + "\")";
	}
}