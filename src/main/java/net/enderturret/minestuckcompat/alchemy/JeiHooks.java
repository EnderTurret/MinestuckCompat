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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

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

	public static void registerConversions() {
		// Lipstick <=> Chainsaw
		registerRecipricolConversion(MSItems.LIPSTICK_CHAINSAW, MSItems.LIPSTICK);
		registerRecipricolConversion(MSItems.CAKESAW, MSItems.CAKESAW_LIPSTICK);
		registerRecipricolConversion(MSItems.MAGENTA_MAULER, MSItems.MAGENTA_MAULER_LIPSTICK);
		registerRecipricolConversion(MSItems.THISTLEBLOWER, MSItems.THISTLEBLOWER_LIPSTICK);
		registerRecipricolConversion(MSItems.HAND_CRANKED_VAMPIRE_ERASER, MSItems.HAND_CRANKED_VAMPIRE_ERASER_LIPSTICK);
		registerRecipricolConversion(MSItems.EMERALD_IMMOLATOR, MSItems.EMERALD_IMMOLATOR_LIPSTICK);
		registerRecipricolConversion(MSItems.OBSIDIATOR, MSItems.OBSIDIATOR_LIPSTICK);
		registerRecipricolConversion(MSItems.DEVILS_DELIGHT, MSItems.DEVILS_DELIGHT_LIPSTICK);
		registerRecipricolConversion(MSItems.DEMONBANE_RAGRIPPER, MSItems.DEMONBANE_RAGRIPPER_LIPSTICK);
		registerRecipricolConversion(MSItems.FROSTTOOTH, MSItems.FROSTTOOTH_LIPSTICK);

		// Miscellaneous
		registerRecipricolConversion(MSItems.CROCKER_SPOON, MSItems.CROCKER_FORK);
		registerRecipricolConversion(MSItems.ACE_OF_CLUBS, MSItems.CLUB_OF_FELONY);
		registerRecipricolConversion(MSItems.ACE_OF_DIAMONDS, MSItems.CUESTICK);
		registerRecipricolConversion(MSItems.ACE_OF_HEARTS, MSItems.TV_ANTENNA);
		registerRecipricolConversion(MSItems.ACE_OF_SPADES, MSItems.HORSE_HITCHER);
	}

	private static void registerRecipricolConversion(ItemLike from, ItemLike to) {
		registerConversion(from, to);
		registerConversion(to, from);
	}

	private static void registerConversion(ItemLike from, ItemLike to) {
		MysteriousItemConversionCategory.RECIPES.add(ConversionRecipe.create(new ItemStack(from), new ItemStack(to)));
	}
}