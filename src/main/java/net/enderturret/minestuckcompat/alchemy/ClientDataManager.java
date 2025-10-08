package net.enderturret.minestuckcompat.alchemy;

import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.util.List;
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
import net.enderturret.minestuckcompat.alchemy.analysis.BuiltinRecipeList;

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

			BuiltinRecipeList.parseResource(resourceManager, "minestuckcompat", "minestuckcompat/swapping_weapons.json", elem -> {
				SWAPPING_WEAPONS.putAll(CODEC.parse(JsonOps.INSTANCE, elem)
						.getOrThrow(RuntimeException::new));
				return List.of();
			});

			if (ModList.get().isLoaded("create") && ModList.get().isLoaded("jei"))
				JeiHooks.registerConversions();
		});
	}
}