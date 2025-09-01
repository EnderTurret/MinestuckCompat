package net.enderturret.minestuckcompat.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mraof.minestuck.alchemy.recipe.generator.recipe.DefaultInterpreter;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Recipe;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.MinestuckCompatConfig;

@Mixin(DefaultInterpreter.class)
public abstract class MixinDefaultInterpreter {

	@Inject(at = @At("RETURN"), method = "getOutputItems")
	private void minestuckcompat$printRecipeEligibilityFromOutput(Recipe<?> recipe, CallbackInfoReturnable<List<Item>> cir) {
		if (cir.getReturnValue().isEmpty() && MinestuckCompatConfig.common().debugDefaultInterpreterRecipeEligibility.getAsBoolean())
			MinestuckCompat.LOGGER.warn("Recipe {} (type {}, serializer {}) was unhandled because it had no result.",
					recipe,
					BuiltInRegistries.RECIPE_TYPE.getKey(recipe.getType()), BuiltInRegistries.RECIPE_SERIALIZER.getKey(recipe.getSerializer()));
	}

	@Inject(at = @At("HEAD"), method = "generateCost")
	private void minestuckcompat$printRecipeEligibility(Recipe<?> recipe, Item output, GeneratorCallback callback, CallbackInfoReturnable<MutableGristSet> cir) {
		if (recipe.isSpecial() && !(recipe instanceof CustomRecipe) && MinestuckCompatConfig.common().debugDefaultInterpreterRecipeEligibility.getAsBoolean()) {
			MinestuckCompat.LOGGER.warn("Recipe {} (type {}, serializer {}) was unhandled because it was marked special.",
					recipe,
					BuiltInRegistries.RECIPE_TYPE.getKey(recipe.getType()), BuiltInRegistries.RECIPE_SERIALIZER.getKey(recipe.getSerializer()));
		}

		else if (recipe.getIngredients().isEmpty() && MinestuckCompatConfig.common().debugDefaultInterpreterRecipeEligibility.getAsBoolean()) {
			MinestuckCompat.LOGGER.warn("Recipe {} (type {}, serializer {}) was unhandled because it had no ingredients.",
					recipe,
					BuiltInRegistries.RECIPE_TYPE.getKey(recipe.getType()), BuiltInRegistries.RECIPE_SERIALIZER.getKey(recipe.getSerializer()));
		}
	}
}