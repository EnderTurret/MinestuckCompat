package net.enderturret.minestuckcompat.api.alchemy;

import java.util.ArrayList;
import java.util.Map;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratedCostProvider;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.GristCostResult;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.Item;

import net.neoforged.bus.api.Event;

import net.enderturret.minestuckcompat.alchemy.SimpleCostProvider;
import net.enderturret.minestuckcompat.mixin.feature.outside_grist_costs.GeneratorProcessAccess;

/**
 * {@code RegisterGristCostProvidersEvent} allows registering custom {@link GristCostProvider GristCostProviders}, which can provide items with grist costs.
 * This is intended for scenarios where a mod does not use Minecraft's recipe system for its own recipes or gameplay behavior, to still allow compatibility
 * mods to make those available to Minestuck's grist cost generator.
 * @author EnderTurret
 */
public final class RegisterGristCostProvidersEvent extends Event {

	private final Map<Item, GristSet.Immutable> generatedCosts;
	private final GeneratorProcessAccess access;

	@Internal
	public RegisterGristCostProvidersEvent(Map<Item, GristSet.Immutable> generatedCosts, GeneratorProcessAccess access) {
		this.generatedCosts = generatedCosts;
		this.access = access;
	}

	/**
	 * Registers a grist cost for the given item.
	 * @param item The item to register the grist cost for.
	 * @param cost The grist cost the item should have.
	 */
	public void registerGristCostProvider(Item item, GristSet.Immutable cost) {
		registerGristCostProvider(item, (i, callback) -> cost);
	}

	/**
	 * Registers a source grist cost for the given item.
	 * In other words, makes {@code item} inherit its grist cost from {@code source}.
	 * @param item The item to register the grist cost for.
	 * @param source The source item to inherit the grist cost from.
	 */
	public void registerGristCostProvider(Item item, Item source) {
		registerGristCostProvider(item, new GristCostProvider() {
			@Override
			@Nullable
			public GristSet generate(Item item, GeneratorCallback callback) {
				return callback.lookupCostFor(source);
			}

			@Override
			public void reportPreliminaryLookups(LookupTracker tracker) {
				tracker.report(source);
			}
		});
	}

	/**
	 * Registers a grist cost provider for the given item.
	 * @param item The item to register the grist cost provider for.
	 * @param provider The grist cost provider.
	 */
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

			@Override
			public void reportPreliminaryLookups(LookupTracker tracker) {
				provider.reportPreliminaryLookups(tracker);
			}
		});
	}

	private void register(Item item, GeneratedCostProvider provider) {
		access.minestuckcompat$getProvidersByItem().computeIfAbsent(item, k -> new ArrayList<>()).add(provider);
		access.minestuckcompat$getProviders().add(provider);
	}

	/**
	 * Provides an item with its grist cost.
	 * @author EnderTurret
	 */
	public static interface GristCostProvider {

		/**
		 * Generates a grist cost for the specified item.
		 * @param item The item to generate a grist cost for.
		 * @param callback The callback for looking up grist costs.
		 * @return A grist cost for the item, or {@code null} if one could not be generated.
		 */
		@Nullable
		public GristSet generate(Item item, GeneratorCallback callback);

		/**
		 * Reports any dependent items to the {@link LookupTracker}, so grist costs can be generated for them first.
		 * @param tracker The {@code LookupTracker}.
		 */
		public default void reportPreliminaryLookups(LookupTracker tracker) {}
	}
}