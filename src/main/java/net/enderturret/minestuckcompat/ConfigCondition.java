package net.enderturret.minestuckcompat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ICondition;

public record ConfigCondition(String option, String modId) implements ICondition {

	public static final MapCodec<ConfigCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
			Codec.STRING.optionalFieldOf("option", "").forGetter(ConfigCondition::option),
			Codec.STRING.optionalFieldOf("modid", "").forGetter(ConfigCondition::modId)
			).apply(builder, ConfigCondition::new));

	public ConfigCondition {}
	public ConfigCondition(String modId) { this("", modId); }

	@Override
	public MapCodec<? extends ICondition> codec() {
		return CODEC;
	}

	@Override
	public boolean test(IContext context) {
		return switch (option) {
			case "" -> ModList.get().isLoaded(modId) && MinestuckCompatConfig.common().isModEnabled(modId);
			default -> {
				MinestuckCompat.LOGGER.warn("Unknown config option: {}", option);
				yield false;
			}
		};
	}

	@Override
	public String toString() {
		return "config(\"" + modId + "\")";
	}
}