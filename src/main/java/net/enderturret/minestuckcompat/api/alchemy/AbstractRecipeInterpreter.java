package net.enderturret.minestuckcompat.api.alchemy;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;
import com.mraof.minestuck.api.alchemy.GristTypes;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import net.neoforged.neoforge.common.crafting.SizedIngredient;

/**
 * MinestuckCompat's base implementation of {@link RecipeInterpreter}.
 * @author EnderTurret
 */
public abstract class AbstractRecipeInterpreter implements RecipeInterpreter {

	@Override
	public final List<Item> getOutputItems(Recipe<?> recipe) {
		final List<ItemStack> stacks = getOutputItemStacks(recipe);
		if (stacks.isEmpty()) return List.of();

		final List<Item> ret = new ArrayList<>(stacks.size());
		for (ItemStack stack : stacks) ret.add(stack.getItem());

		return List.copyOf(ret);
	}

	public List<ItemStack> getOutputItemStacks(Recipe<?> recipe) {
		final ItemStack stack = recipe.getResultItem(getLookupProvider());
		return stack.isEmpty() ? List.of() : List.of(stack);
	}

	@Override
	@Nullable
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
		if (recipe.isSpecial())
			return null;

		return finalizeGristCosts(ingredientCost(recipe, callback), recipe, output, callback);
	}

	@Override
	public void reportPreliminaryLookups(Recipe<?> recipe, LookupTracker tracker) {
		for (Ingredient ing : recipe.getIngredients())
			if (ing != null) // ExtraDelight drying recipes have null ingredients, for some reason.
				tracker.report(ing);
	}

	/**
	 * <p>
	 * Returns the lookup provider to pass to {@link Recipe#getResultItem(net.minecraft.core.HolderLookup.Provider) Recipe.getResultItem(HolderLookup.Provider)}.
	 * </p>
	 * <p>
	 * This method by default returns {@code null} as few if any recipes actually use the lookup provider, and
	 * creating one adds significant time to grist cost generation.
	 * </p>
	 * @return The lookup provider.
	 */
	@Nullable
	protected HolderLookup.Provider getLookupProvider() {
		//return VanillaRegistries.createLookup();
		return null;
	}

	/**
	 * Tallies the cost of all ingredients in the specified recipe and returns the total grist set.
	 * If any of the ingredients lack a grist cost, {@code null} is returned.
	 * @param recipe The recipe.
	 * @param callback The callback for looking up grist costs.
	 * @return The total grist set, or {@code null} if an ingredient lacks a cost.
	 */
	@Nullable
	protected MutableGristSet ingredientCost(Recipe<?> recipe, GeneratorCallback callback) {
		final MutableGristSet totalCost = MutableGristSet.newDefault();

		for (Ingredient ingredient : recipe.getIngredients())
			// ExtraDelight drying recipes have null ingredients, for some reason.
			if (ingredient != null && !account(totalCost, callback, ingredient))
				return null;

		return totalCost;
	}

	@Nullable
	@SuppressWarnings("deprecation")
	protected MutableGristSet containerCost(MutableGristSet totalCost, Recipe<?> recipe, Item output, GeneratorCallback callback) {
		if (!output.hasCraftingRemainingItem()) return totalCost;

		final Item remainder = output.getCraftingRemainingItem();
		final GristSet remainderCost = callback.lookupCostFor(remainder);
		if (remainderCost == null) return null;

		// HACK: If the total cost doesn't satisfy the remainder cost, add it back in.
		for (GristType type : GristTypes.REGISTRY)
			if (totalCost.getGrist(type) < remainderCost.getGrist(type)) {
				totalCost.add(remainderCost);
				break;
			}

		return totalCost;
	}

	@Nullable
	protected GristSet finalizeGristCosts(@Nullable MutableGristSet totalCost, Recipe<?> recipe, Item output, GeneratorCallback callback) {
		if (totalCost == null) return null;

		for (ItemStack out : getOutputItemStacks(recipe))
			if (out.getItem() == output)
				return containerCost((MutableGristSet) finalizeGristCosts(totalCost, out.getCount()), recipe, output, callback);

		return containerCost((MutableGristSet) finalizeGristCosts(totalCost, 1), recipe, output, callback);
	}

	/**
	 * Called to finalize the grist cost — currently just to scale it to the result size.
	 * @param totalCost The total grist cost of the item.
	 * @param resultCount The number of items the recipe produces.
	 * @return The finalized grist set.
	 */
	@Nullable
	protected GristSet finalizeGristCosts(@Nullable MutableGristSet totalCost, int resultCount) {
		if (totalCost != null)
			// Do not round down because it's better to have something cost a little too much than it possibly costing nothing.
			totalCost.scale(1F / resultCount, false);

		return totalCost;
	}

	/**
	 * Adds the grist cost of the specified {@code SizedIngredient} to the specified grist set.
	 * @param total The grist set to add the cost to.
	 * @param callback The callback for looking up grist costs.
	 * @param ingredient The ingredient.
	 * @return {@code true} if a cost was added, or {@code false} if one was not found.
	 */
	public static boolean account(MutableGristSet total, GeneratorCallback callback, SizedIngredient ingredient) {
		return account(total, callback, ingredient.ingredient(), ingredient.count());
	}

	/**
	 * Adds the grist cost of the specified {@code Ingredient} and count to the specified grist set.
	 * @param total The grist set to add the cost to.
	 * @param callback The callback for looking up grist costs.
	 * @param ingredient The ingredient.
	 * @param count The number of items the ingredient requires.
	 * @return {@code true} if a cost was added, or {@code false} if one was not found.
	 */
	public static boolean account(MutableGristSet total, GeneratorCallback callback, Ingredient ingredient, int count) {
		final GristSet ingredientCost = callback.lookupCostFor(ingredient);
		if (ingredientCost == null) return false;

		for (int i = 0; i < count; i++)
			total.add(ingredientCost);

		return true;
	}

	/**
	 * Adds the grist cost of the specified {@code Ingredient} to the specified grist set.
	 * @param total The grist set to add the cost to.
	 * @param callback The callback for looking up grist costs.
	 * @param ingredient The ingredient.
	 * @return {@code true} if a cost was added, or {@code false} if one was not found.
	 */
	public static boolean account(MutableGristSet total, GeneratorCallback callback, Ingredient ingredient) {
		final GristSet ingredientCost = callback.lookupCostFor(ingredient);
		if (ingredientCost == null) return false;

		total.add(ingredientCost);

		return true;
	}

	/**
	 * Adds the grist cost of the specified {@code Item} to the specified grist set.
	 * @param total The grist set to add the cost to.
	 * @param callback The callback for looking up grist costs.
	 * @param item The item.
	 * @return {@code true} if a cost was added, or {@code false} if one was not found.
	 */
	public static boolean account(MutableGristSet total, GeneratorCallback callback, Item item) {
		final GristSet ingredientCost = callback.lookupCostFor(item);
		if (ingredientCost == null) return false;

		total.add(ingredientCost);

		return true;
	}

	/**
	 * Scales the specified grist set by the specified amount.
	 * @param cost The grist set to scale.
	 * @param scale The scale.
	 * @param roundDown Whether to round down versus round up.
	 * @return The scaled grist set.
	 */
	public static GristSet scale(GristSet cost, float scale, boolean roundDown) {
		return scale == 1 ? cost : cost.mutableCopy().scale(scale, roundDown).asImmutable();
	}
}