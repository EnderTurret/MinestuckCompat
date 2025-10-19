package net.enderturret.minestuckcompat.mixin.feature.debug;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mraof.minestuck.alchemy.recipe.generator.recipe.InterpreterTypes;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;
import com.mraof.minestuck.api.alchemy.GristTypes;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeHolder;

import net.enderturret.minestuckcompat.MinestuckCompat;

@Mixin(targets = "com/mraof/minestuck/alchemy/recipe/generator/recipe/RecipeGeneratedCostProcess")
public abstract class MixinRecipeGeneratedCostProcess {

	@SuppressWarnings("deprecation")
	@Inject(at = @At("RETURN"), method = "costForRecipe")
	private void minestuckcompat$checkNegativeCosts(RecipeHolder<?> recipe, RecipeInterpreter interpreter, Item item, GeneratorCallback callback, CallbackInfoReturnable<GristSet> cir) {
		final GristSet ret = cir.getReturnValue();
		if (ret == null) return;

		for (GristType type : GristTypes.REGISTRY) {
			// Skip checking for negative artifact grist costs.
			if (type == GristTypes.ARTIFACT.get()) continue;

			if (ret.getGrist(type) < 0) {
				MinestuckCompat.LOGGER.warn("Interpreter {} ({}) generated a negative grist cost for result {} (remainder {}) from recipe {}",
						InterpreterTypes.REGISTRY.getKey(interpreter.codec()), interpreter.getClass().getName(),
						item.builtInRegistryHolder().getRegisteredName(),
						item.hasCraftingRemainingItem() ? item.getCraftingRemainingItem().builtInRegistryHolder().getRegisteredName() : "null",
						recipe.id());
				break;
			}
		}
	}
}