package net.enderturret.minestuckcompat.mixin.feature.jei_fixes;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import com.mraof.minestuck.api.alchemy.recipe.JeiGristCost;
import com.mraof.minestuck.jei.GristCostRecipeCategory;

import net.minecraft.resources.ResourceLocation;

import net.enderturret.minestuckcompat.alchemy.JeiHooks;

import mezz.jei.api.recipe.category.IRecipeCategory;

@Mixin(GristCostRecipeCategory.class)
public abstract class MixinGristCostRecipeCategory implements IRecipeCategory<JeiGristCost> {

	@Override
	@Nullable
	public ResourceLocation getRegistryName(JeiGristCost recipe) {
		return JeiHooks.getId(recipe);
	}
}