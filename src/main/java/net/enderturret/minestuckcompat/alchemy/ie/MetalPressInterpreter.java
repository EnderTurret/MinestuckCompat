package net.enderturret.minestuckcompat.alchemy.ie;

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

import net.enderturret.minestuckcompat.api.alchemy.AbstractRecipeInterpreter;

import blusunrize.immersiveengineering.api.crafting.MetalPressRecipe;

public final class MetalPressInterpreter extends AbstractRecipeInterpreter {

	public static final MapCodec<MetalPressInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(MetalPressInterpreter::addedCost)
			).apply(instance, MetalPressInterpreter::new));

	private final GristSet.Immutable addedCost;

	public MetalPressInterpreter(GristSet.Immutable addedCost) {
		this.addedCost = addedCost;
	}

	public GristSet.Immutable addedCost() {
		return addedCost;
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	@Nullable
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
		final MutableGristSet totalCost = ingredientCost(recipe, callback);
		if (totalCost == null) return null;

		if (recipe instanceof MetalPressRecipe r)
			if (!account(totalCost, callback, r.input.getBaseIngredient(), r.input.getCount()))
				return null;

		totalCost.add(addedCost);

		return finalizeGristCosts(totalCost, recipe.getResultItem(getLookupProvider()).getCount());
	}

	@Override
	public void reportPreliminaryLookups(Recipe<?> recipe, LookupTracker tracker) {
		super.reportPreliminaryLookups(recipe, tracker);

		if (recipe instanceof MetalPressRecipe r)
			tracker.report(r.input.getBaseIngredient());
	}
}