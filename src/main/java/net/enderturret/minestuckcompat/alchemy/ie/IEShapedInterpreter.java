package net.enderturret.minestuckcompat.alchemy.ie;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;

import blusunrize.immersiveengineering.common.crafting.fluidaware.AbstractShapedRecipe;

public final class IEShapedInterpreter extends AbstractCostAddingRecipeInterpreter {

	public static final MapCodec<IEShapedInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			COST_FIELD.forGetter(IEShapedInterpreter::addedCost)
			).apply(instance, IEShapedInterpreter::new));

	public IEShapedInterpreter(GristSet.Immutable addedCost) {
		super(addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	public List<ItemStack> getOutputItemStacks(Recipe<?> recipe) {
		return super.getOutputItemStacks(recipe instanceof AbstractShapedRecipe r ? r.toVanilla() : recipe);
	}

	@Override
	@Nullable
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
		if (recipe instanceof AbstractShapedRecipe r)
			recipe = r.toVanilla();

		final MutableGristSet totalCost = ingredientCost(recipe, callback);
		if (totalCost == null) return null;

		return finalizeGristCosts(totalCost, recipe, output, callback);
	}

	@Override
	public void reportPreliminaryLookups(Recipe<?> recipe, LookupTracker tracker) {
		super.reportPreliminaryLookups(recipe instanceof AbstractShapedRecipe r ? r.toVanilla() : recipe, tracker);
	}
}