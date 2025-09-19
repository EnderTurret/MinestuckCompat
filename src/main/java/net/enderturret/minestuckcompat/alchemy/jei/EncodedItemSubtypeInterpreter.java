package net.enderturret.minestuckcompat.alchemy.jei;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.item.components.EncodedItemComponent;
import com.mraof.minestuck.item.components.MSItemComponents;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;

@Internal
public class EncodedItemSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {

	@Override
	public @Nullable Object getSubtypeData(ItemStack ingredient, UidContext context) {
		final EncodedItemComponent encoded = ingredient.get(MSItemComponents.ENCODED_ITEM);
		return encoded == null ? Items.AIR : encoded.item();
	}

	@Override
	@SuppressWarnings("deprecation")
	public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) { return ""; }
}