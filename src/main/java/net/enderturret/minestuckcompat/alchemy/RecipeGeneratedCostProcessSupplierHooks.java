package net.enderturret.minestuckcompat.alchemy;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.ApiStatus.Internal;

import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeHolder;

/**
 * Hooks for implementing Minestuck Compat's fix for tags being unavailable in {@link RecipeInterpreter#getOutputItems(net.minecraft.world.item.crafting.Recipe)}.
 * @author EnderTurret
 */
@Internal
public interface RecipeGeneratedCostProcessSupplierHooks {

	public void minestuckcompat$setLazyLookupMap(Supplier<Map<Item, List<Pair<RecipeHolder<?>, RecipeInterpreter>>>> value);
}