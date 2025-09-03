package net.enderturret.minestuckcompat.api.alchemy;

import java.util.ArrayList;
import java.util.Map;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratedCostProvider;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.GristCostResult;

import net.minecraft.world.item.Item;

import net.neoforged.bus.api.Event;

import net.enderturret.minestuckcompat.alchemy.SimpleCostProvider;
import net.enderturret.minestuckcompat.mixin.feature.outside_grist_costs.GeneratorProcessAccess;

public final class RegisterGristCostProvidersEvent extends Event {

	private final Map<Item, GristSet.Immutable> generatedCosts;
	private final GeneratorProcessAccess access;

	@Internal
	public RegisterGristCostProvidersEvent(Map<Item, GristSet.Immutable> generatedCosts, GeneratorProcessAccess access) {
		this.generatedCosts = generatedCosts;
		this.access = access;
	}

	public void registerGristCostProvider(Item item, GristSet.Immutable cost) {
		registerGristCostProvider(item, (i, callback) -> cost);
	}

	public void registerGristCostProvider(Item item, Item source) {
		registerGristCostProvider(item, (i, callback) -> callback.lookupCostFor(source));
	}

	public void registerGristCostProvider(Item item, GristCostProvider provider) {
		register(item, new SimpleCostProvider() {
			@Override
			protected @Nullable GristSet generateCost(Item item, GeneratorCallback callback) {
				return provider.generate(item, callback);
			}

			@Override
			protected void putResult(Item item, @Nullable GristCostResult result) {
				if (result == null) return;
				generatedCosts.putIfAbsent(item, result.cost().asImmutable());
			}
		});
	}

	private void register(Item item, GeneratedCostProvider provider) {
		access.minestuckcompat$getProvidersByItem().computeIfAbsent(item, k -> new ArrayList<>()).add(provider);
		access.minestuckcompat$getProviders().add(provider);
	}

	public static interface GristCostProvider {
		public GristSet generate(Item item, GeneratorCallback callback);
	}
}