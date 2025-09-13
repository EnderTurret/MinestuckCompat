package net.enderturret.minestuckcompat.alchemy.ae2;

import java.util.List;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;

import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;

import appeng.recipes.handlers.ChargerRecipe;

public final class ChargerInterpreter extends AbstractCostAddingRecipeInterpreter {

	public static final MapCodec<ChargerInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(ChargerInterpreter::addedCost)
			).apply(instance, ChargerInterpreter::new));

	public ChargerInterpreter(GristSet.Immutable addedCost) {
		super(addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	public List<Item> getOutputItems(Recipe<?> recipe) {
		if (recipe instanceof ChargerRecipe r)
			return List.of(r.getResultItem().getItem());

		return super.getOutputItems(recipe);
	}

	@Override
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
		MutableGristSet totalCost = MutableGristSet.newDefault();

		final int resultCount;
		//final float energyScale; // If they ever make recipes that require more/less charge.

		if (recipe instanceof ChargerRecipe r) {
			if (!account(totalCost, callback, r.getIngredient())) return null;

			resultCount = r.getResultItem().getCount();
			//energyScale = 1;
		}
		else {
			totalCost = ingredientCost(recipe, callback);
			resultCount = recipe.getResultItem(getLookupProvider()).getCount();
			//energyScale = 1;
		}

		//totalCost.add(scale(addedCost, energyScale, false));

		return finalizeGristCosts(totalCost, resultCount);
	}
}