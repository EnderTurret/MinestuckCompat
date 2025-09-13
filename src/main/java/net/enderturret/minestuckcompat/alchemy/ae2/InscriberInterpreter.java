package net.enderturret.minestuckcompat.alchemy.ae2;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;

import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;

import appeng.recipes.handlers.InscriberProcessType;
import appeng.recipes.handlers.InscriberRecipe;

public final class InscriberInterpreter extends AbstractCostAddingRecipeInterpreter {

	public static final MapCodec<InscriberInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			COST_FIELD.forGetter(InscriberInterpreter::addedCost)
			).apply(instance, InscriberInterpreter::new));

	public InscriberInterpreter(GristSet.Immutable addedCost) {
		super(addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
		MutableGristSet totalCost = MutableGristSet.newDefault();

		if (recipe instanceof InscriberRecipe r) {
			// Ignore press ingredients (since they're not consumed).
			if (r.getProcessType() != InscriberProcessType.INSCRIBE)
				if (!account(totalCost, callback, r.getTopOptional())) return null;
			if (!account(totalCost, callback, r.getMiddleInput())) return null;
			if (!account(totalCost, callback, r.getBottomOptional())) return null;
		}
		else
			totalCost = ingredientCost(recipe, callback);

		return finalizeGristCosts(totalCost, recipe);
	}
}