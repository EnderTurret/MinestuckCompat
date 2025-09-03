package net.enderturret.minestuckcompat.mixin.feature.outside_grist_costs;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mraof.minestuck.api.alchemy.GristSet;

import net.minecraft.world.item.Item;

import net.enderturret.minestuckcompat.alchemy.MixinHooks;

@Mixin(targets = "com/mraof/minestuck/alchemy/recipe/generator/recipe/RecipeGeneratedCostProcess")
public abstract class MixinRecipeGeneratedCostProcess {

	@Shadow
	@Final
	private Map<Item, GristSet.Immutable> generatedCosts;

	@Inject(at = @At("TAIL"), method = "<init>")
	private void minestuckcompat$captureCostMap(CallbackInfo ci) {
		MixinHooks.generatedCosts = generatedCosts;
	}
}