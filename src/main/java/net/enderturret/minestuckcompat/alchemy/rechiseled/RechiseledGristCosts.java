package net.enderturret.minestuckcompat.alchemy.rechiseled;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.supermartijn642.rechiseled.api.chiseling.ChiselingBlockShape;
import com.supermartijn642.rechiseled.api.chiseling.ChiselingEntry;
import com.supermartijn642.rechiseled.api.chiseling.ChiselingRecipe;
import com.supermartijn642.rechiseled.api.chiseling.ChiselingRecipeManager;
import com.supermartijn642.rechiseled.api.chiseling.ItemWithWorth;

import net.minecraft.world.item.Item;

import net.neoforged.bus.api.SubscribeEvent;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.api.alchemy.RegisterGristCostProvidersEvent;

/**
 * Handles generating grist costs for Rechiseled's blocks.
 * @author EnderTurret
 */
@Internal
public final class RechiseledGristCosts {

	@SubscribeEvent
	static void registerProviders(RegisterGristCostProvidersEvent event) {
		try {
			register0(event);
		} catch (Exception e) {
			MinestuckCompat.LOGGER.error("Exception registering grist cost providers for Rechiseled:", e);
		}
	}

	private static void register0(RegisterGristCostProvidersEvent event) {
		final ChiselingBlockShape[] shapes = ChiselingBlockShape.values();

		for (ChiselingRecipe recipe : ChiselingRecipeManager.get(false).getAllRecipes()) {
			if (recipe.entries().isEmpty()) continue;

			Item original = null;

			for (ChiselingEntry entry : recipe.entries()) {
				if (!entry.hasRegularItem(ChiselingBlockShape.BLOCK)) continue;
				final Item item = entry.getRegularItem(ChiselingBlockShape.BLOCK).item();
				if (isVanilla(item)) {
					original = item;
					break;
				}
			}

			if (original == null) continue;

			for (ChiselingEntry entry : recipe.entries()) {
				for (ChiselingBlockShape shape : shapes) {
					if (entry.hasRegularItem(shape) && !isVanilla(entry.getRegularItem(shape).item())) {
						final ItemWithWorth item = entry.getRegularItem(shape);
						event.registerGristCostProvider(item.item(), original, item.worth());
					}

					if (entry.hasConnectingItem(shape)) {
						final ItemWithWorth item = entry.getConnectingItem(shape);
						event.registerGristCostProvider(item.item(), original, item.worth());
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private static boolean isVanilla(Item item) {
		return item.builtInRegistryHolder().key().location().getNamespace().equals("minecraft");
	}
}