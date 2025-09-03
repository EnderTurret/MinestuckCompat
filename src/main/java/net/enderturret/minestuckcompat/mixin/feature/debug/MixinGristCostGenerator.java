package net.enderturret.minestuckcompat.mixin.feature.debug;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mraof.minestuck.alchemy.recipe.generator.GristCostGenerator;

import net.minecraft.world.item.crafting.RecipeManager;

import net.enderturret.minestuckcompat.alchemy.MixinHooks;

@Mixin(GristCostGenerator.class)
public abstract class MixinGristCostGenerator {

	@Inject(at = @At("TAIL"), method = "run")
	private static void minestuckcompat$printUnalchemizableItems(RecipeManager recipeManager, CallbackInfo ci) {
		MixinHooks.checkItemsWithoutGristCost(recipeManager);
	}
}