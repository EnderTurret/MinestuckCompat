package net.enderturret.minestuckcompat.mixin.perf.small_grist_set;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mraof.minestuck.alchemy.recipe.generator.ContainerGristCost;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.recipe.ContainerGristCostBuilder;

import net.minecraft.world.item.crafting.Ingredient;

import net.enderturret.minestuckcompat.perf.SmallImmutableGristSet;

@Mixin(ContainerGristCostBuilder.class)
public abstract class MixinContainerGristCostBuilder {

	@Redirect(
			at = @At(value = "NEW", target = "com/mraof/minestuck/alchemy/recipe/generator/ContainerGristCost"),
			method = "build(Lnet/minecraft/data/recipes/RecipeOutput;Lnet/minecraft/resources/ResourceLocation;)V")
	private ContainerGristCost minestuckcompat$shrinkSetForContainerCost(Ingredient ingredient, GristSet.Immutable addedCost, Optional<Integer> priority) {
		return new ContainerGristCost(ingredient, SmallImmutableGristSet.create(addedCost), priority);
	}
}