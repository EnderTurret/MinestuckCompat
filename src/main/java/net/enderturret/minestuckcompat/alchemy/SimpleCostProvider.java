package net.enderturret.minestuckcompat.alchemy;

import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratedCostProvider;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.GristCostResult;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.Item;

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

	@Nullable
	protected abstract GristSet generateCost(Item item, GeneratorCallback callback);

	protected abstract void putResult(Item item, @Nullable GristCostResult result);

	@Override
	public void reportPreliminaryLookups(LookupTracker tracker) {}
}