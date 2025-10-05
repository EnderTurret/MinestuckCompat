package net.enderturret.minestuckcompat.alchemy;

import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.Item;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

import net.enderturret.minestuckcompat.MinestuckCompat;

@Internal
@EventBusSubscriber(modid = MinestuckCompat.MOD_ID, value = Dist.CLIENT)
public final class ClientDataManager {

	public static final Map<Item, Item> SWAPPING_WEAPONS = new LinkedHashMap<>();

	private static final Codec<Item> ITEM_CODEC = BuiltInRegistries.ITEM.byNameCodec();
	private static final Codec<Map<Item, Item>> CODEC = Codec.unboundedMap(ITEM_CODEC, ITEM_CODEC);

	@SubscribeEvent
	static void registerClientReloadListeners(RegisterClientReloadListenersEvent e) {
		e.registerReloadListener((ResourceManagerReloadListener) resourceManager -> {
			SWAPPING_WEAPONS.clear();

			for (Resource resource : resourceManager.getResourceStack(ResourceLocation.fromNamespaceAndPath(MinestuckCompat.MOD_ID, "minestuckcompat/swapping_weapons.json"))) {
				try (BufferedReader br = resource.openAsReader()) {
					final JsonElement elem = JsonParser.parseReader(br);
					SWAPPING_WEAPONS.putAll(CODEC.decode(JsonOps.INSTANCE, elem)
							.getOrThrow(RuntimeException::new)
							.getFirst());
				} catch (Exception e1) {
					MinestuckCompat.LOGGER.warn("Exception reading swapping_weapons.json from {}:", resource.sourcePackId(), e1);
				}
			}

			if (ModList.get().isLoaded("create") && ModList.get().isLoaded("jei"))
				JeiHooks.registerConversions();
		});
	}
}