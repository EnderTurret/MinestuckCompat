package net.enderturret.minestuckcompat.mixin.feature.jei_fixes;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mraof.minestuck.api.alchemy.recipe.JeiGristCost;
import com.mraof.minestuck.api.alchemy.recipe.combination.CombinationMode;
import com.mraof.minestuck.api.alchemy.recipe.combination.JeiCombination;
import com.mraof.minestuck.item.MSItems;

import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import net.enderturret.minestuckcompat.alchemy.JeiHooks;
import net.enderturret.minestuckcompat.alchemy.jei.BoondollarSubtypeInterpreter;
import net.enderturret.minestuckcompat.alchemy.jei.EncodedItemSubtypeInterpreter;
import net.enderturret.minestuckcompat.alchemy.jei.FrogSubtypeInterpreter;

import mezz.jei.api.registration.ISubtypeRegistration;

@Mixin(MinestuckJeiPlugin.class)
public abstract class MixinMinestuckJeiPlugin {

	@ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lcom/mraof/minestuck/api/alchemy/recipe/GristCostRecipe;getJeiCosts(Lnet/minecraft/world/level/Level;)Ljava/util/List;"), method = "lambda$registerRecipes$1")
	private static List<JeiGristCost> minestuckcompat$findCostIds(List<JeiGristCost> original, Level level, RecipeHolder<?> holder) {
		for (JeiGristCost cost : original) JeiHooks.putId(cost, holder.id());
		return original;
	}

	@ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lcom/mraof/minestuck/api/alchemy/recipe/combination/CombinationRecipe;getJeiCombinations()Ljava/util/List;"), method = "lambda$registerRecipes$2")
	private static List<JeiCombination> minestuckcompat$findANDIds(List<JeiCombination> original, RecipeHolder<?> holder) {
		for (JeiCombination cost : original) JeiHooks.putId(cost, CombinationMode.AND, holder.id());
		return original;
	}

	@ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lcom/mraof/minestuck/api/alchemy/recipe/combination/CombinationRecipe;getJeiCombinations()Ljava/util/List;"), method = "lambda$registerRecipes$4")
	private static List<JeiCombination> minestuckcompat$findORIds(List<JeiCombination> original, RecipeHolder<?> holder) {
		for (JeiCombination cost : original) JeiHooks.putId(cost, CombinationMode.OR, holder.id());
		return original;
	}

	@Inject(at = @At("TAIL"), method = "registerItemSubtypes")
	private void minestuckcompat$registerSubtypes(ISubtypeRegistration registration, CallbackInfo ci) {
		registration.registerSubtypeInterpreter(MSItems.CAPTCHA_CARD.asItem(), new EncodedItemSubtypeInterpreter());
		registration.registerSubtypeInterpreter(MSItems.CRUXITE_DOWEL.asItem(), new EncodedItemSubtypeInterpreter());
		registration.registerSubtypeInterpreter(MSItems.BOONDOLLARS.asItem(), new BoondollarSubtypeInterpreter());
		registration.registerSubtypeInterpreter(MSItems.FROG.asItem(), new FrogSubtypeInterpreter());
	}
}