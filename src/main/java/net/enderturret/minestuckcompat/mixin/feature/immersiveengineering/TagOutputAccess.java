package net.enderturret.minestuckcompat.mixin.feature.immersiveengineering;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.mojang.datafixers.util.Either;

import net.minecraft.world.item.ItemStack;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.TagOutput;

/**
 * Allows access to the underlying ingredient so we can attempt to handle the contents without resolving tags too early.
 * @author EnderTurret
 */
@Mixin(TagOutput.class)
public interface TagOutputAccess {

	@Accessor("rawData")
	public Either<IngredientWithSize, ItemStack> minestuckcompat$getRawData();
}