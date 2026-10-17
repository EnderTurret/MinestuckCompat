package net.enderturret.minestuckcompat.mixin.feature.result_tag_fix;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedCostHandler;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedCostHandler.SourceEntry;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import net.neoforged.neoforge.common.util.Lazy;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.alchemy.RecipeGeneratedCostProcessSupplierHooks;

@Mixin(RecipeGeneratedCostHandler.class)
public abstract class MixinRecipeGeneratedCostHandler {

	@Shadow
	@Final
	private RecipeManager recipeManager;

	// Step 1: replace original prepareRecipeMap() call with null.
	@Redirect(at = @At(value = "INVOKE", target = "Lcom/mraof/minestuck/alchemy/recipe/generator/recipe/RecipeGeneratedCostHandler;prepareRecipeMap(Ljava/util/List;Lnet/minecraft/world/item/crafting/RecipeManager;)Ljava/util/Map;"), method = "apply")
	private Map<Item, List<Pair<RecipeHolder<?>, RecipeInterpreter>>> minestuckcompat$removeMap(RecipeGeneratedCostHandler self, List<SourceEntry> sources, RecipeManager recipeManager) {
		return null;
	}

	// Step 2: add minestuckcompat$lazyPrepareRecipeMap() to instantiated cost process.
	@Inject(at = @At(value = "RETURN"), method = "apply")
	private void minestuckcompat$addLazyMap(List<SourceEntry> sources, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
		try {
			final Field processField = RecipeGeneratedCostHandler.class.getDeclaredField("process");
			processField.setAccessible(true);
			final Object process = processField.get(this);
			if (process != null)
				((RecipeGeneratedCostProcessSupplierHooks) process).minestuckcompat$setLazyLookupMap(minestuckcompat$lazyPrepareRecipeMap(sources, recipeManager));
		} catch (Exception e) {
			MinestuckCompat.LOGGER.error("Exception setting lazy lookup map in RecipeGeneratedCostProcess:", e);
		}
	}

	@Unique
	private Supplier<Map<Item, List<Pair<RecipeHolder<?>, RecipeInterpreter>>>> minestuckcompat$lazyPrepareRecipeMap(List<SourceEntry> sources, RecipeManager recipeManager) {
		return Lazy.of(() -> prepareRecipeMap(sources, recipeManager));
	}

	@Shadow
	abstract Map<Item, List<Pair<RecipeHolder<?>, RecipeInterpreter>>> prepareRecipeMap(List<SourceEntry> sources, RecipeManager recipeManager);
}