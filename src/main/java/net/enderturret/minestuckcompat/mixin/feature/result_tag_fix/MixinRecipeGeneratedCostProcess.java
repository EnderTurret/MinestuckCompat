package net.enderturret.minestuckcompat.mixin.feature.result_tag_fix;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeHolder;

import net.enderturret.minestuckcompat.alchemy.RecipeGeneratedCostProcessSupplierHooks;

@Mixin(targets = "com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedCostProcess")
public abstract class MixinRecipeGeneratedCostProcess implements RecipeGeneratedCostProcessSupplierHooks {

	@Shadow
	@Final
	@Mutable
	private Map<Item, List<Pair<RecipeHolder<?>, RecipeInterpreter>>> lookupMap;

	@Unique
	private Supplier<Map<Item, List<Pair<RecipeHolder<?>, RecipeInterpreter>>>> minestuckcompat$lazyLookupMap;

	@Override
	public void minestuckcompat$setLazyLookupMap(Supplier<Map<Item, List<Pair<RecipeHolder<?>, RecipeInterpreter>>>> value) {
		minestuckcompat$lazyLookupMap = value;
	}

	@Inject(at = @At("HEAD"), method = "itemSet")
	private void minestuckcompat$resolveLookupMap$1(CallbackInfoReturnable<Set<Item>> cir) {
		if (lookupMap == null)
			lookupMap = minestuckcompat$lazyLookupMap.get();
	}

	@Inject(at = @At("HEAD"), method = "reportPreliminaryLookups")
	private void minestuckcompat$resolveLookupMap$2(CallbackInfo ci) {
		if (lookupMap == null)
			lookupMap = minestuckcompat$lazyLookupMap.get();
	}

	@Inject(at = @At("HEAD"), method = "costFromRecipes")
	private void minestuckcompat$resolveLookupMap$3(CallbackInfoReturnable<GristSet> cir) {
		if (lookupMap == null)
			lookupMap = minestuckcompat$lazyLookupMap.get();
	}
}