package net.enderturret.minestuckcompat.api.alchemy;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;

public abstract class AbstractCostAddingRecipeInterpreter extends AbstractRecipeInterpreter {

	protected final GristSet.Immutable addedCost;

	protected static final MapCodec<GristSet.Immutable> COST_FIELD = GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY);

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

	public static abstract class Typed<T extends Recipe<?>> extends AbstractCostAddingRecipeInterpreter {

		protected final Class<T> recipeClass;

		protected Typed(Class<T> recipeClass, GristSet.Immutable addedCost) {
			super(addedCost);
			this.recipeClass = recipeClass;
		}

		protected abstract List<Item> getOutputItemsTyped(T recipe);

		@Nullable
		protected abstract MutableGristSet generateCost(MutableGristSet totalCost, T recipe, Item output, GeneratorCallback callback);

		protected abstract void reportPreliminaryLookupsTyped(T recipe, LookupTracker tracker);

		@Override
		@SuppressWarnings("unchecked")
		public List<Item> getOutputItems(Recipe<?> recipe) {
			if (recipeClass.isAssignableFrom(recipe.getClass()))
				return getOutputItemsTyped((T) recipe);

			return super.getOutputItems(recipe);
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

			return finalizeGristCosts(totalCost, recipe);
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