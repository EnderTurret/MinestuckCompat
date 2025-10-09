package net.enderturret.minestuckcompat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.serialization.MapCodec;

import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.core.Holder;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import net.enderturret.minestuckcompat.alchemy.rechiseled.RechiseledGristCosts;
import net.enderturret.minestuckcompat.api.init.MCInterpreterTypes;
import net.enderturret.minestuckcompat.command.AnalyzerCommand;

/**
 * Minestuck Compat's main mod class.
 * @author EnderTurret
 */
@Mod(MinestuckCompat.MOD_ID)
public final class MinestuckCompat {

	public static final String MOD_ID = "minestuckcompat";
	public static final Logger LOGGER = LoggerFactory.getLogger("MinestuckCompat");

	private static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, MOD_ID);

	private static final Holder<MapCodec<? extends ICondition>> CONFIG_CONDITION = CONDITIONS.register("config", () -> ConfigCondition.CODEC);

	public MinestuckCompat(ModContainer mc, IEventBus modBus) {
		mc.registerConfig(ModConfig.Type.COMMON, MinestuckCompatConfig.COMMON_SPEC);
		MCInterpreterTypes.REGISTRY.register(modBus);
		CONDITIONS.register(modBus);

		if (ModList.get().isLoaded("rechiseled"))
			NeoForge.EVENT_BUS.register(RechiseledGristCosts.class);

		NeoForge.EVENT_BUS.addListener(this::registerCommands);
	}

	private void registerCommands(RegisterCommandsEvent e) {
		if (e.getCommandSelection() == CommandSelection.INTEGRATED)
			AnalyzerCommand.register(e.getDispatcher());
	}
}