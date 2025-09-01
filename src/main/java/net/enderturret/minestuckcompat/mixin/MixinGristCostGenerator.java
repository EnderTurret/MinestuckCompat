package net.enderturret.minestuckcompat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mraof.minestuck.alchemy.recipe.generator.GristCostGenerator;

import net.minecraft.world.item.crafting.RecipeManager;

import net.enderturret.minestuckcompat.alchemy.MixinHooks;

@Mixin(GristCostGenerator.class)
public abstract class MixinGristCostGenerator {

	@Inject(at = @At("TAIL"), method = "run")
	private static void minestuckcompat$printUnalchemizableItems(RecipeManager recipeManager, CallbackInfo ci) {
		MixinHooks.checkItemsWithoutGristCost(recipeManager);
	}

	private static Object minestuckcompat$process;

	@ModifyExpressionValue(at = @At(value = "NEW", target = "com/mraof/minestuck/alchemy/recipe/generator/GristCostGenerator$GeneratorProcess"), method = "run")
	@Coerce
	private static Object minestuckcompat$captureProcess(@Coerce Object process) {
		minestuckcompat$process = process;
		return process;
	}

	@Inject(at = @At(value = "NEW", target = "it/unimi/dsi/fastutil/objects/Object2IntOpenHashMap"), method = "run")
	private static void minestuckcompat$discoverAdditionalProviders(RecipeManager recipeManager, CallbackInfo ci) {
		final GeneratorProcessAccess access = (GeneratorProcessAccess) minestuckcompat$process;
		MixinHooks.generateAdditionalGristCosts(access);
		minestuckcompat$process = null;
	}
}