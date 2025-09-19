package net.enderturret.minestuckcompat.mixin.feature.jei_fixes;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import com.mraof.minestuck.api.alchemy.recipe.combination.CombinationMode;
import com.mraof.minestuck.api.alchemy.recipe.combination.JeiCombination;
import com.mraof.minestuck.jei.TotemLatheRecipeCategory;

import net.minecraft.resources.ResourceLocation;

import net.enderturret.minestuckcompat.alchemy.JeiHooks;

import mezz.jei.api.recipe.category.IRecipeCategory;

@Mixin(TotemLatheRecipeCategory.class)
public abstract class MixinTotemLatheRecipeCategory implements IRecipeCategory<JeiCombination> {

	@Override
	@Nullable
	public ResourceLocation getRegistryName(JeiCombination recipe) {
		return JeiHooks.getId(recipe, CombinationMode.AND);
	}
}