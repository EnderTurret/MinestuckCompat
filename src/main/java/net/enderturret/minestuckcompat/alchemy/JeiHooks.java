package net.enderturret.minestuckcompat.alchemy;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.recipe.JeiGristCost;
import com.mraof.minestuck.api.alchemy.recipe.combination.CombinationMode;
import com.mraof.minestuck.api.alchemy.recipe.combination.JeiCombination;
import com.mraof.minestuck.item.MSItems;
import com.simibubi.create.compat.jei.ConversionRecipe;
import com.simibubi.create.compat.jei.category.MysteriousItemConversionCategory;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import net.enderturret.minestuckcompat.MinestuckCompatConfig;

/**
 * Various hooks for Minestuck Compat's JEI integration.
 * @author EnderTurret
 */
@Internal
public final class JeiHooks {

	private static final Map<JeiCombination, ResourceLocation> AND_IDS = new HashMap<>();
	private static final Map<JeiCombination, ResourceLocation> OR_IDS = new HashMap<>();
	private static final Map<JeiGristCost, ResourceLocation> GRIST_IDS = new HashMap<>();

	/**
	 * Retrieves the ID of the specified combination that was previously set by {@link #putId(JeiCombination, CombinationMode, ResourceLocation)}.
	 * @param combination The combination to retrieve the ID for.
	 * @param mode The mode of the combination.
	 * @return The ID of the combination, or {@code null} if one was not set.
	 */
	@Nullable
	public static ResourceLocation getId(JeiCombination combination, CombinationMode mode) {
		return (mode == CombinationMode.AND ? AND_IDS : OR_IDS).get(combination);
	}

	/**
	 * Retrieves the ID of the specified grist cost that was previously set by {@link #putId(JeiGristCost, ResourceLocation)}.
	 * @param cost The grist cost to retrieve the ID for.
	 * @return The ID of the grist cost, or {@code null} if one was not set.
	 */
	@Nullable
	public static ResourceLocation getId(JeiGristCost cost) {
		return GRIST_IDS.get(cost);
	}

	/**
	 * Associates the specified combination with the specified ID.
	 * @param combination The combination.
	 * @param mode The mode of the combination.
	 * @param id The ID of the combination.
	 */
	public static void putId(JeiCombination combination, CombinationMode mode, ResourceLocation id) {
		(mode == CombinationMode.AND ? AND_IDS : OR_IDS).put(combination, id);
	}

	/**
	 * Associates the specified grist cost with the specified ID.
	 * @param cost The grist cost.
	 * @param id The ID of the grist cost.
	 */
	public static void putId(JeiGristCost cost, ResourceLocation id) {
		GRIST_IDS.put(cost, id);
	}

	private static boolean registered = false;

	/**
	 * Registers all of Minestuck Compat's "mysterious conversions".
	 */
	public static void registerConversions() {
		if (!MinestuckCompatConfig.common().mysteriousConversionRecipes.get()) return;

		if (registered) return;
		else registered = true;

		for (Map.Entry<Item, Item> entry : ClientDataManager.SWAPPING_WEAPONS.entrySet()) {
			registerConversion(entry.getKey(), entry.getValue());
			registerConversion(entry.getValue(), entry.getKey());
		}

		registerConversion(MSItems.CANDY_CANE, MSItems.SHARP_CANDY_CANE);
		registerConversion(MSItems.FROSTTOOTH, MSItems.ICE_SHARD);
	}

	private static void registerRecipricolConversion(ItemLike from, ItemLike to) {
		registerConversion(from, to);
		registerConversion(to, from);
	}

	private static void registerConversion(ItemLike from, ItemLike to) {
		MysteriousItemConversionCategory.RECIPES.add(ConversionRecipe.create(new ItemStack(from), new ItemStack(to)));
	}
}