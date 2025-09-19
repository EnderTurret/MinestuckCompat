package net.enderturret.minestuckcompat.alchemy;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.recipe.JeiGristCost;
import com.mraof.minestuck.api.alchemy.recipe.combination.CombinationMode;
import com.mraof.minestuck.api.alchemy.recipe.combination.JeiCombination;

import net.minecraft.resources.ResourceLocation;

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
}