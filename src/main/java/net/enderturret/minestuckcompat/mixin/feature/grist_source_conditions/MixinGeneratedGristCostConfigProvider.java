package net.enderturret.minestuckcompat.mixin.feature.grist_source_conditions;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mraof.minestuck.data.GeneratedGristCostConfigProvider;

import net.enderturret.minestuckcompat.data.IGeneratedGristCostConfigProviderExtensions;

@Mixin(GeneratedGristCostConfigProvider.class)
public abstract class MixinGeneratedGristCostConfigProvider implements IGeneratedGristCostConfigProviderExtensions {

	@ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/DataResult;getOrThrow()Ljava/lang/Object;"), method = "run")
	private Object minestuckcompat$allowModifications(Object elem) {
		return modify((JsonElement) elem);
	}
}