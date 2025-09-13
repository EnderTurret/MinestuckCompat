package net.enderturret.minestuckcompat.mixin.perf.small_grist_set;

import java.util.List;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mraof.minestuck.alchemy.recipe.generator.SourceGristCost;
import com.mraof.minestuck.alchemy.recipe.generator.SourceGristCost.Source;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.recipe.SourceGristCostBuilder;

import net.minecraft.world.item.crafting.Ingredient;

import net.enderturret.minestuckcompat.perf.SmallImmutableGristSet;

@Mixin(SourceGristCostBuilder.class)
public abstract class MixinSourceGristCostBuilder {

	@Redirect(
			at = @At(value = "NEW", target = "com/mraof/minestuck/alchemy/recipe/generator/SourceGristCost"),
			method = "build(Lnet/minecraft/data/recipes/RecipeOutput;Lnet/minecraft/resources/ResourceLocation;)V")
	private SourceGristCost minestuckcompat$shrinkSetForSourceCost(Ingredient ingredient, List<Source> sources, float multiplier, GristSet.Immutable addedCost, Optional<Integer> priority) {
		return new SourceGristCost(ingredient, sources, multiplier, SmallImmutableGristSet.create(addedCost), priority);
	}
}