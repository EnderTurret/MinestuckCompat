package net.enderturret.minestuckcompat.api.alchemy;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

/**
 * A version of {@link AbstractRecipeInterpreter} that adds a per-recipe added grist cost.
 * @author EnderTurret
 */
public abstract class AbstractCostAddingRecipeInterpreter extends AbstractRecipeInterpreter {

	protected final GristSet.Immutable addedCost;

	/**
	 * The cost field {@code MapCodec}. Add this to your {@code AbstractCostAddingRecipeInterpreter} subclass's codec.
	 */
	protected static final MapCodec<GristSet.Immutable> COST_FIELD = GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY);

	/**
	 * Constructs a new {@code AbstractCostAddingRecipeInterpreter}.
	 * @param addedCost The grist cost added for all recipes processed by this interpreter.
	 */
	protected AbstractCostAddingRecipeInterpreter(GristSet.Immutable addedCost) {
		this.addedCost = addedCost;
	}

	public GristSet.Immutable addedCost() {
		return addedCost;
	}

	@Override
	@Nullable
	protected GristSet finalizeGristCosts(@Nullable MutableGristSet totalCost, int resultCount) {
		if (totalCost != null)
			totalCost.add(addedCost);

		return super.finalizeGristCosts(totalCost, resultCount);
	}

	/**
	 * A typed subclass of {@link AbstractCostAddingRecipeInterpreter} that allows implementors to work with a specific recipe type instead of {@code Recipe<?>}.
	 *
	 * @author EnderTurret
	 *
	 * @param <T> The recipe class.
	 */
	public static abstract class Typed<T extends Recipe<?>> extends AbstractCostAddingRecipeInterpreter {

		protected final Class<T> recipeClass;

		/**
		 * Constructs a {@code Typed} {@code AbstractCostAddingRecipeInterpreter}.
		 * @param recipeClass The interpreter's recipe type class.
		 * @param addedCost The grist cost added for all recipes processed by this interpreter.
		 */
		protected Typed(Class<T> recipeClass, GristSet.Immutable addedCost) {
			super(addedCost);
			this.recipeClass = recipeClass;
		}

		/**
		 * Typed version of {@link #getOutputItems(Recipe)}.
		 * @param recipe The recipe to retrieve the output items of.
		 * @return The list of output items from the recipe.
		 */
		protected abstract List<ItemStack> getOutputItemStacksTyped(T recipe);

		/**
		 * Typed version of {@link #generateCost(Recipe, Item, GeneratorCallback)}.
		 * @param totalCost The total grist cost of the recipe.
		 * @param recipe The recipe to generate the grist cost of.
		 * @param output The recipe's output item.
		 * @param callback The grist cost generator callback, for fetching item grist costs.
		 * @return {@code totalCost}, or {@code null} if one of the recipe's ingredients has a {@code null} grist cost.
		 */
		@Nullable
		protected abstract MutableGristSet generateCost(MutableGristSet totalCost, T recipe, Item output, GeneratorCallback callback);

		/**
		 * Typed version of {@link #reportPreliminaryLookups(Recipe, LookupTracker)}.
		 * @param recipe The recipe to report the ingredients of.
		 * @param tracker The {@code LookupTracker} to report the ingredients to.
		 */
		protected abstract void reportPreliminaryLookupsTyped(T recipe, LookupTracker tracker);

		@Override
		@SuppressWarnings("unchecked")
		public List<ItemStack> getOutputItemStacks(Recipe<?> recipe) {
			if (recipeClass.isAssignableFrom(recipe.getClass()))
				return getOutputItemStacksTyped((T) recipe);

			return super.getOutputItemStacks(recipe);
		}

		@Override
		@Nullable
		@SuppressWarnings("unchecked")
		public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
			MutableGristSet totalCost = ingredientCost(recipe, callback);
			if (totalCost == null) return null;

			if (recipeClass.isAssignableFrom(recipe.getClass()))
				totalCost = generateCost(totalCost, (T) recipe, output, callback);

			if (totalCost == null) return null;

			return finalizeGristCosts(totalCost, recipe, output, callback);
		}

		@Override
		@SuppressWarnings("unchecked")
		public void reportPreliminaryLookups(Recipe<?> recipe, LookupTracker tracker) {
			super.reportPreliminaryLookups(recipe, tracker);
			if (recipeClass.isAssignableFrom(recipe.getClass()))
				reportPreliminaryLookupsTyped((T) recipe, tracker);
		}
	}
}