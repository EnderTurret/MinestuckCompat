package net.enderturret.minestuckcompat.alchemy;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.recipe.JeiGristCost;
import com.mraof.minestuck.api.alchemy.recipe.combination.CombinationMode;
import com.mraof.minestuck.api.alchemy.recipe.combination.JeiCombination;
import com.simibubi.create.compat.jei.ConversionRecipe;
import com.simibubi.create.compat.jei.category.MysteriousItemConversionCategory;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import net.enderturret.minestuckcompat.MinestuckCompatConfig;

@Internal
public final class JeiHooks {

	private static final Map<JeiCombination, ResourceLocation> AND_IDS = new HashMap<>();
	private static final Map<JeiCombination, ResourceLocation> OR_IDS = new HashMap<>();
	private static final Map<JeiGristCost, ResourceLocation> GRIST_IDS = new HashMap<>();

	@Nullable
	public static ResourceLocation getId(JeiCombination combination, CombinationMode mode) {
		return (mode == CombinationMode.AND ? AND_IDS : OR_IDS).get(combination);
	}

	@Nullable
	public static ResourceLocation getId(JeiGristCost cost) {
		return GRIST_IDS.get(cost);
	}

	public static void putId(JeiCombination combination, CombinationMode mode, ResourceLocation id) {
		(mode == CombinationMode.AND ? AND_IDS : OR_IDS).put(combination, id);
	}

	public static void putId(JeiGristCost cost, ResourceLocation id) {
		GRIST_IDS.put(cost, id);
	}

	private static boolean registered = false;

	public static void registerConversions() {
		if (!MinestuckCompatConfig.common().mysteriousConversionRecipes.get()) return;

		if (registered) return;
		else registered = true;

		for (Map.Entry<Item, Item> entry : ClientDataManager.SWAPPING_WEAPONS.entrySet()) {
			registerConversion(entry.getKey(), entry.getValue());
			registerConversion(entry.getValue(), entry.getKey());
		}
	}

	private static void registerRecipricolConversion(ItemLike from, ItemLike to) {
		registerConversion(from, to);
		registerConversion(to, from);
	}

	private static void registerConversion(ItemLike from, ItemLike to) {
		MysteriousItemConversionCategory.RECIPES.add(ConversionRecipe.create(new ItemStack(from), new ItemStack(to)));
	}
}