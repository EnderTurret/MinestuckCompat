package net.enderturret.minestuckcompat.alchemy.jei;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.item.components.MSItemComponents;

import net.minecraft.world.item.ItemStack;

import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;

@Internal
public class FrogSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {

	@Override
	public @Nullable Object getSubtypeData(ItemStack ingredient, UidContext context) {
		return ingredient.get(MSItemComponents.FROG_TRAITS);
	}

	@Override
	@SuppressWarnings("deprecation")
	public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) { return ""; }
}