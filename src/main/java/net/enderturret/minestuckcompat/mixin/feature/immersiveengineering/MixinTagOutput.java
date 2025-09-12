package net.enderturret.minestuckcompat.mixin.feature.immersiveengineering;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.datafixers.util.Either;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.enderturret.minestuckcompat.MinestuckCompat;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.TagOutput;

/**
 * This is a mixin ensuring that if we load a {@code TagOutput} too early (and thus permanently resolving it to an empty tag) then we will know about it.
 * @author EnderTurret
 */
@Mixin(TagOutput.class)
public abstract class MixinTagOutput {

	@Shadow
	private Either<IngredientWithSize, ItemStack> rawData;

	@Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/datafixers/util/Either;map(Ljava/util/function/Function;Ljava/util/function/Function;)Ljava/lang/Object;"), method = "get")
	private void checkTag(CallbackInfoReturnable<ItemStack> cir) {
		if (!rawData.left().isPresent()) return;

		final ItemStack[] stacks = rawData.left().get().getMatchingStacks();

		if (stacks.length == 1 && stacks[0].getItem() == Items.BARRIER)
			MinestuckCompat.LOGGER.error("Tag is empty", new Throwable("stacktrace"));
	}
}