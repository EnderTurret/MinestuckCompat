package net.enderturret.minestuckcompat.mixin.perf.small_grist_set;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mraof.minestuck.alchemy.recipe.GristCost;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.recipe.GristCostRecipeBuilder;

import net.minecraft.world.item.crafting.Ingredient;

import net.enderturret.minestuckcompat.perf.SmallImmutableGristSet;

@Mixin(GristCostRecipeBuilder.class)
public abstract class MixinGristCostRecipeBuilder {

	@Redirect(
			at = @At(value = "NEW", target = "com/mraof/minestuck/alchemy/recipe/generator/GristCost"),
			method = "build(Lnet/minecraft/data/recipes/RecipeOutput;Lnet/minecraft/resources/ResourceLocation;)V")
	private GristCost minestuckcompat$shrinkSetForSourceCost(Ingredient ingredient, GristSet.Immutable addedCost, Optional<Integer> priority) {
		return new GristCost(ingredient, SmallImmutableGristSet.create(addedCost), priority);
	}
}