package net.enderturret.minestuckcompat.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedCostHandler;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedCostHandler.SourceEntry;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;

import net.enderturret.minestuckcompat.alchemy.MixinHooks;

@Mixin(RecipeGeneratedCostHandler.class)
public abstract class MixinRecipeGeneratedCostHandler {

	@Shadow
	private RecipeManager recipeManager;

	@Inject(at = @At("RETURN"), method = "apply")
	private void minestuckcompat$printUnhandledRecipes(List<SourceEntry> sources, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
		MixinHooks.checkRecipesWithoutInterpreters(recipeManager, sources);
	}
}