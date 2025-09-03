package net.enderturret.minestuckcompat.alchemy.rechiseled;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.supermartijn642.rechiseled.chiseling.ChiselingEntry;
import com.supermartijn642.rechiseled.chiseling.ChiselingRecipe;
import com.supermartijn642.rechiseled.chiseling.ChiselingRecipes;

import net.minecraft.world.item.Item;

import net.neoforged.bus.api.SubscribeEvent;

import net.enderturret.minestuckcompat.api.alchemy.RegisterGristCostProvidersEvent;

@Internal
public final class RechiseledGristCosts {

	@SubscribeEvent
	public static void registerProviders(RegisterGristCostProvidersEvent event) {
		for (ChiselingRecipe recipe : ChiselingRecipes.getAllRecipes()) {
			if (recipe.getEntries().isEmpty()) continue;

			Item original = null;

			for (ChiselingEntry entry : recipe.getEntries())
				if (entry.hasRegularItem() && isVanilla(entry.getRegularItem())) {
					original = entry.getRegularItem();
					break;
				}

			if (original == null) continue;

			for (ChiselingEntry entry : recipe.getEntries()) {
				if (entry.hasRegularItem() && !isVanilla(entry.getRegularItem()))
					event.registerGristCostProvider(entry.getRegularItem(), original);

				if (entry.hasConnectingItem())
					event.registerGristCostProvider(entry.getConnectingItem(), original);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private static boolean isVanilla(Item item) {
		return item.builtInRegistryHolder().key().location().getNamespace().equals("minecraft");
	}
}