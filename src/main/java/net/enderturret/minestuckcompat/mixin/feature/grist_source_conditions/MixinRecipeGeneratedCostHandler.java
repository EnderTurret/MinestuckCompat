package net.enderturret.minestuckcompat.mixin.feature.grist_source_conditions;

import java.util.List;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedCostHandler;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedCostHandler.SourceEntry;

import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.RegistryOps;

import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition;

import net.enderturret.minestuckcompat.alchemy.MixinHooks;

@Mixin(RecipeGeneratedCostHandler.class)
public abstract class MixinRecipeGeneratedCostHandler {

	@Redirect(at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;parse(Lcom/mojang/serialization/DynamicOps;Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;"), method = "lambda$prepare$0")
	private static DataResult<List<SourceEntry>> minestuckcompat$useConditionalSources(Codec<List<SourceEntry>> codec, DynamicOps<JsonElement> ops, Object input) {
		final ConditionalOps<JsonElement> conOps = new ConditionalOps<>(RegistryOps.create(ops, VanillaRegistries.createLookup()), ICondition.IContext.EMPTY);
		final JsonElement jsonInput = (JsonElement) input;
		return MixinHooks.CONDITIONAL_SOURCE_ENTRY_LIST.parse(conOps, jsonInput)
				.map(list -> list.stream().filter(Optional::isPresent).map(Optional::get).toList());
	}
}