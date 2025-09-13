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
import net.minecraft.world.item.crafting.Recipe;

import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;

import blusunrize.immersiveengineering.api.crafting.MetalPressRecipe;

public final class MetalPressInterpreter extends AbstractCostAddingRecipeInterpreter {

	public static final MapCodec<MetalPressInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(MetalPressInterpreter::addedCost)
			).apply(instance, MetalPressInterpreter::new));

	public MetalPressInterpreter(GristSet.Immutable addedCost) {
		super(addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	public List<Item> getOutputItems(Recipe<?> recipe) {
		if (recipe instanceof MetalPressRecipe r)
			return MultiblockInterpreter.safeResolve(r.output);

		return super.getOutputItems(recipe);
	}

	@Override
	@Nullable
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
		final MutableGristSet totalCost = ingredientCost(recipe, callback);
		if (totalCost == null) return null;

		if (recipe instanceof MetalPressRecipe r)
			if (!account(totalCost, callback, r.input.getBaseIngredient(), r.input.getCount()))
				return null;

		return finalizeGristCosts(totalCost, recipe);
	}

	@Override
	public void reportPreliminaryLookups(Recipe<?> recipe, LookupTracker tracker) {
		super.reportPreliminaryLookups(recipe, tracker);

		if (recipe instanceof MetalPressRecipe r)
			tracker.report(r.input.getBaseIngredient());
	}
}