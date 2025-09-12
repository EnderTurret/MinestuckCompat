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

import blusunrize.immersiveengineering.api.crafting.AlloyRecipe;

public final class AlloySmelterInterpreter extends AbstractRecipeInterpreter {

	public static final MapCodec<AlloySmelterInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(AlloySmelterInterpreter::addedCost)
			).apply(instance, AlloySmelterInterpreter::new));

	private final GristSet.Immutable addedCost;

	public AlloySmelterInterpreter(GristSet.Immutable addedCost) {
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

		if (recipe instanceof AlloyRecipe r) {
			if (!account(totalCost, callback, r.input0.getBaseIngredient(), r.input0.getCount()))
				return null;
			if (!account(totalCost, callback, r.input1.getBaseIngredient(), r.input1.getCount()))
				return null;
		}

		totalCost.add(addedCost);

		return finalizeGristCosts(totalCost, recipe.getResultItem(getLookupProvider()).getCount());
	}

	@Override
	public void reportPreliminaryLookups(Recipe<?> recipe, LookupTracker tracker) {
		super.reportPreliminaryLookups(recipe, tracker);

		if (recipe instanceof AlloyRecipe r) {
			tracker.report(r.input0.getBaseIngredient());
			tracker.report(r.input1.getBaseIngredient());
		}
	}
}