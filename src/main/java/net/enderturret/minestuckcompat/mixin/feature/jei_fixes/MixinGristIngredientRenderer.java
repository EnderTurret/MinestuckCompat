package net.enderturret.minestuckcompat.mixin.feature.jei_fixes;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.jei.GristIngredientRenderer;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

@Mixin(GristIngredientRenderer.class)
public abstract class MixinGristIngredientRenderer {

	@WrapWithCondition(at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 1), method = "getTooltip")
	private boolean minestuckcompat$hideIdWithoutAdvancedTooltips(List<Component> list, Object element, GristAmount ingredient, TooltipFlag tooltipFlag) {
		return tooltipFlag.isAdvanced();
	}
}