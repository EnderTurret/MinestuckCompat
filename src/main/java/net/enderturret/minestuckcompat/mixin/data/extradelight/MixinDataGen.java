package net.enderturret.minestuckcompat.mixin.data.extradelight;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.lance5057.extradelight.data.DataGen;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mixin(DataGen.class)
public abstract class MixinDataGen {

	/**
	 * @reason Disable ExtraDelight's datagen to avoid a dev-time dependency
	 * @param event
	 */
	@Overwrite
	@SubscribeEvent // Leave this in so NeoForge doesn't crash trying to be smart.
	public static void gatherData(GatherDataEvent event) {}
}