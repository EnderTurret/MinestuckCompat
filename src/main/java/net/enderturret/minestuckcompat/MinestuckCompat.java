package net.enderturret.minestuckcompat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

import net.enderturret.minestuckcompat.alchemy.rechiseled.RechiseledGristCosts;
import net.enderturret.minestuckcompat.api.init.MCInterpreterTypes;

@Mod(MinestuckCompat.MOD_ID)
public final class MinestuckCompat {

	public static final String MOD_ID = "minestuckcompat";
	public static final Logger LOGGER = LoggerFactory.getLogger("MinestuckCompat");

	public MinestuckCompat(ModContainer mc, IEventBus modBus) {
		mc.registerConfig(ModConfig.Type.COMMON, MinestuckCompatConfig.COMMON_SPEC);
		MCInterpreterTypes.REGISTRY.register(modBus);

		if (ModList.get().isLoaded("rechiseled"))
			NeoForge.EVENT_BUS.register(RechiseledGristCosts.class);
	}
}