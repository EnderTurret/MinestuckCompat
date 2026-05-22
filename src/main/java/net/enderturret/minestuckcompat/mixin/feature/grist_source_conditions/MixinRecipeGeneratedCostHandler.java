package net.enderturret.minestuckcompat.mixin.feature.grist_source_conditions;

import java.util.List;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedCostHandler;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedCostHandler.SourceEntry;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;

import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition;

import net.enderturret.minestuckcompat.MinestuckCompatConfig;
import net.enderturret.minestuckcompat.alchemy.ContextHolder;
import net.enderturret.minestuckcompat.alchemy.MixinHooks;

@Mixin(RecipeGeneratedCostHandler.class)
public abstract class MixinRecipeGeneratedCostHandler extends SimplePreparableReloadListener<List<RecipeGeneratedCostHandler.SourceEntry>> {

	@Unique
	private static final ThreadLocal<ContextHolder> MINESTUCKCOMPAT$CONTEXT = ThreadLocal.withInitial(() -> null);

	@Inject(at = @At("HEAD"), method = "prepare")
	private void minestuckcompat$setupContext(CallbackInfoReturnable<List<RecipeGeneratedCostHandler.SourceEntry>> cir) {
		MINESTUCKCOMPAT$CONTEXT.set(new ContextHolder(getRegistryLookup(), getContext()));
	}

	@Inject(at = @At("RETURN"), method = "prepare")
	private void minestuckcompat$teardownContext(CallbackInfoReturnable<List<RecipeGeneratedCostHandler.SourceEntry>> cir) {
		MINESTUCKCOMPAT$CONTEXT.set(null);
	}

	@Redirect(at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;parse(Lcom/mojang/serialization/DynamicOps;Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;"), method = "lambda$prepare$0")
	private static DataResult<List<SourceEntry>> minestuckcompat$useConditionalSources(Codec<List<SourceEntry>> codec, DynamicOps<JsonElement> ops, Object input, List<?> _list, Resource resource) {
		final boolean isExtraStuck = !MinestuckCompatConfig.common().useExtraStuckInterpreters.getAsBoolean()
				&& resource.sourcePackId().contains("extrastuck");

		final ContextHolder context = MINESTUCKCOMPAT$CONTEXT.get();
		if (context == null) throw new IllegalStateException("No context available");

		final ConditionalOps<JsonElement> conOps = new ConditionalOps<>(RegistryOps.create(ops, context.registryAccess()), context.context());
		final JsonElement jsonInput = (JsonElement) input;

		return MixinHooks.CONDITIONAL_SOURCE_ENTRY_LIST.parse(conOps, jsonInput)
				.map(list -> list.stream()
						.filter(Optional::isPresent)
						.map(Optional::get)
						.filter(entry -> !isExtraStuck || !MixinHooks.isExcludedExtraStuckInterpreter(entry))
						.toList());
	}
}