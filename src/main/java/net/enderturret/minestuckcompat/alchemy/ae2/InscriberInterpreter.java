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

import net.enderturret.minestuckcompat.api.alchemy.AbstractRecipeInterpreter;

import appeng.recipes.handlers.InscriberProcessType;
import appeng.recipes.handlers.InscriberRecipe;

public final class InscriberInterpreter extends AbstractRecipeInterpreter {

	public static final MapCodec<InscriberInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(InscriberInterpreter::addedCost)
			).apply(instance, InscriberInterpreter::new));

	private final GristSet.Immutable addedCost;

	public InscriberInterpreter(GristSet.Immutable addedCost) {
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
	public List<Item> getOutputItems(Recipe<?> recipe) {
		if (recipe instanceof InscriberRecipe r)
			return List.of(r.getResultItem().getItem());

		return super.getOutputItems(recipe);
	}

	@Override
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
		MutableGristSet totalCost = MutableGristSet.newDefault();

		final int resultCount;
		if (recipe instanceof InscriberRecipe r) {
			// Ignore press ingredients (since they're not consumed).
			if (r.getProcessType() != InscriberProcessType.INSCRIBE)
				if (!account(totalCost, callback, r.getTopOptional())) return null;
			if (!account(totalCost, callback, r.getMiddleInput())) return null;
			if (!account(totalCost, callback, r.getBottomOptional())) return null;

			resultCount = r.getResultItem().getCount();
		}
		else {
			totalCost = ingredientCost(recipe, callback);
			resultCount = recipe.getResultItem(getLookupProvider()).getCount();
		}

		totalCost.add(addedCost);

		return finalizeGristCosts(totalCost, resultCount);
	}
}