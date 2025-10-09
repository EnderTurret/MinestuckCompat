package net.enderturret.minestuckcompat.alchemy;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratedCostProvider;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.GristCostResult;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.Item;

/**
 * A base implementation of {@link GeneratedCostProvider} to take care of caching et al.
 * @author EnderTurret
 */
@Internal
public abstract class SimpleCostProvider implements GeneratedCostProvider {

	private GristCostResult result;
	private boolean generated = false;

	@Override
	public GristCostResult generate(Item item, GeneratorCallback callback) {
		if (!generated || !callback.shouldUseSavedResult()) {
			final GristSet set = generateCost(item, callback);
			final GristCostResult ret = GristCostResult.ofOrNull(set);

			if (callback.shouldSaveResult())
				generated = true;

			result = ret;
		}

		putResult(item, result);

		return result;
	}

	/**
	 * Generates a grist cost for the specified item.
	 * @param item The item to generate a grist cost for.
	 * @param callback The callback for looking up grist costs.
	 * @return A grist cost for the item, or {@code null} if one could not be generated.
	 */
	@Nullable
	protected abstract GristSet generateCost(Item item, GeneratorCallback callback);

	/**
	 * Places the grist cost result for the specified item in the {@code generatedCosts} map.
	 * @param item The item to set the grist cost for.
	 * @param result The item's grist cost.
	 */
	protected abstract void putResult(Item item, @Nullable GristCostResult result);

	@Override
	public void reportPreliminaryLookups(LookupTracker tracker) {}
}