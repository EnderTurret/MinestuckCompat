package net.enderturret.minestuckcompat.mixin.perf.small_grist_set.intern;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedCostHandler;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;

import net.enderturret.minestuckcompat.alchemy.MixinHooks;

@Mixin(RecipeGeneratedCostHandler.class)
public abstract class MixinRecipeGeneratedCostHandler {

	@Inject(at = @At("RETURN"), method = "write")
	private void minestuckcompat$nukeInterner$server(CallbackInfo ci) {
		// Only wipe the interner on servers, so that the client can de-duplicate the integrated server's GristSets.
		if (FMLEnvironment.dist != Dist.CLIENT)
			MixinHooks.wipeInterner();
	}

	@Inject(at = @At("RETURN"), method = "read")
	private static void minestuckcompat$nukeInterner$client(CallbackInfoReturnable<RecipeGeneratedCostHandler> cir) {
		MixinHooks.wipeInterner();
	}
}