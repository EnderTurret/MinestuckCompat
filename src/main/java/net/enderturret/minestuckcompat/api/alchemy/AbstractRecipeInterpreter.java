package net.enderturret.minestuckcompat.api.alchemy;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import net.neoforged.neoforge.common.crafting.SizedIngredient;

public abstract class AbstractRecipeInterpreter implements RecipeInterpreter {

	@Override
	public List<Item> getOutputItems(Recipe<?> recipe) {
		final ItemStack stack = recipe.getResultItem(getLookupProvider());
		return stack.isEmpty() ? List.of() : List.of(stack.getItem());
	}

	@Override
	@Nullable
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
		if (recipe.isSpecial())
			return null;

		return finalizeGristCosts(ingredientCost(recipe, callback), recipe.getResultItem(getLookupProvider()).getCount());
	}

	protected HolderLookup.Provider getLookupProvider() {
		//return VanillaRegistries.createLookup();
		return null;
	}

	protected MutableGristSet ingredientCost(Recipe<?> recipe, GeneratorCallback callback) {
		final MutableGristSet totalCost = MutableGristSet.newDefault();

		for (Ingredient ingredient : recipe.getIngredients())
			if (!account(totalCost, callback, ingredient))
				return null;

		return totalCost;
	}

	public static boolean account(MutableGristSet total, GeneratorCallback callback, SizedIngredient ingredient) {
		final GristSet ingredientCost = callback.lookupCostFor(ingredient.ingredient());
		if (ingredientCost == null) return false;

		for (int i = 0; i < ingredient.count(); i++)
			total.add(ingredientCost);

		return true;
	}

	public static boolean account(MutableGristSet total, GeneratorCallback callback, Ingredient ingredient) {
		final GristSet ingredientCost = callback.lookupCostFor(ingredient);
		if (ingredientCost == null) return false;

		total.add(ingredientCost);

		return true;
	}

	public static boolean account(MutableGristSet total, GeneratorCallback callback, Item item) {
		final GristSet ingredientCost = callback.lookupCostFor(item);
		if (ingredientCost == null) return false;

		total.add(ingredientCost);

		return true;
	}

	public static GristSet scale(GristSet cost, float scale, boolean roundDown) {
		return scale == 1 ? cost : cost.mutableCopy().scale(scale, roundDown).asImmutable();
	}

	protected GristSet finalizeGristCosts(MutableGristSet totalCost, int resultCount) {
		if (totalCost != null)
			// Do not round down because it's better to have something cost a little to much than it possibly costing nothing.
			totalCost.scale(1F / resultCount, false);

		return totalCost;
	}

	@Override
	public void reportPreliminaryLookups(Recipe<?> recipe, LookupTracker tracker) {
		for (Ingredient ing : recipe.getIngredients())
			tracker.report(ing);
	}
}