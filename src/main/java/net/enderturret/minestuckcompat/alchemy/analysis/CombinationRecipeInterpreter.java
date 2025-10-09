package net.enderturret.minestuckcompat.alchemy.analysis;

import java.util.List;

import com.mojang.serialization.MapCodec;
import com.mraof.minestuck.alchemy.recipe.RegularCombinationRecipe;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.recipe.combination.CombinationRecipe;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;

/**
 * A bare-bones {@link RecipeInterpreter} for {@link CombinationRecipe}, intended for the {@linkplain ObtainabilityAnalyzer obtainability analyzer}.
 * It doesn't even support generating grist costs.
 * @author EnderTurret
 */
final class CombinationRecipeInterpreter implements RecipeInterpreter {

	static final CombinationRecipeInterpreter INSTANCE = new CombinationRecipeInterpreter();

	@Override
	public List<Item> getOutputItems(Recipe<?> recipe) {
		if (recipe instanceof RegularCombinationRecipe r)
			return List.of(r.output().getItem());

		return List.of();
	}

	@Override
	public void reportPreliminaryLookups(Recipe<?> recipe, LookupTracker tracker) {
		if (recipe instanceof RegularCombinationRecipe r) {
			tracker.report(r.input1());
			tracker.report(r.input2());
		}
	}

	@Override
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) { throw new UnsupportedOperationException(); }

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() { throw new UnsupportedOperationException(); }
}