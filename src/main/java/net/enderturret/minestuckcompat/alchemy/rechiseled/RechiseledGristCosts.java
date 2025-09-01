package net.enderturret.minestuckcompat.alchemy.rechiseled;

import java.util.function.BiConsumer;

import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratedCostProvider;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.GristCostResult;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;
import com.supermartijn642.rechiseled.chiseling.ChiselingEntry;
import com.supermartijn642.rechiseled.chiseling.ChiselingRecipe;
import com.supermartijn642.rechiseled.chiseling.ChiselingRecipes;

import net.minecraft.world.item.Item;

public final class RechiseledGristCosts {

	public static void generateAdditionalGristCosts(BiConsumer<Item, GeneratedCostProvider> registrar, BiConsumer<Item, @Nullable GristCostResult> callback) {
		for (ChiselingRecipe recipe : ChiselingRecipes.getAllRecipes()) {
			if (recipe.getEntries().isEmpty()) continue;

			Item original = null;

			for (ChiselingEntry entry : recipe.getEntries())
				if (entry.hasRegularItem() && isVanilla(entry.getRegularItem())) {
					original = entry.getRegularItem();
					break;
				}

			if (original == null) continue;

			final RechiseledCostProvider provider = new RechiseledCostProvider(callback, original);

			for (ChiselingEntry entry : recipe.getEntries()) {
				if (entry.hasRegularItem() && !isVanilla(entry.getRegularItem()))
					registrar.accept(entry.getRegularItem(), provider);

				if (entry.hasConnectingItem())
					registrar.accept(entry.getConnectingItem(), provider);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private static boolean isVanilla(Item item) {
		return item.builtInRegistryHolder().key().location().getNamespace().equals("minecraft");
	}

	private static final class RechiseledCostProvider implements GeneratedCostProvider {

		private final BiConsumer<Item, @Nullable GristCostResult> callback;
		private final Item base;

		private GristCostResult result;
		private boolean generated = false;

		private RechiseledCostProvider(BiConsumer<Item, @Nullable GristCostResult> callback, Item base) {
			this.callback = callback;
			this.base = base;
		}

		@Override
		public GristCostResult generate(Item item, GeneratorCallback callback) {
			if (!generated || !callback.shouldUseSavedResult()) {
				final GristSet set = callback.lookupCostFor(base);
				final GristCostResult ret = GristCostResult.ofOrNull(set);

				if (callback.shouldSaveResult())
					generated = true;

				result = ret;
			}

			this.callback.accept(item, result);

			return result;
		}

		@Override
		public void reportPreliminaryLookups(LookupTracker tracker) {
			tracker.report(base);
		}

		@SuppressWarnings("deprecation")
		@Override
		public String toString() {
			return "RechiseledCostProvider[" + base.builtInRegistryHolder().key().location() + "]";
		}
	}
}