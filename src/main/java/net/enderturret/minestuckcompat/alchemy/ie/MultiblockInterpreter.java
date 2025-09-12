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

import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import net.enderturret.minestuckcompat.api.alchemy.AbstractRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.FluidHelper;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;

public final class MultiblockInterpreter extends AbstractRecipeInterpreter {

	public static final MapCodec<MultiblockInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(MultiblockInterpreter::addedCost)
			).apply(instance, MultiblockInterpreter::new));

	private final GristSet.Immutable addedCost;

	public MultiblockInterpreter(GristSet.Immutable addedCost) {
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

		if (recipe instanceof MultiblockRecipe r) {
			if (r.getItemInputs() != null)
				for (IngredientWithSize ing : r.getItemInputs())
					if (!account(totalCost, callback, ing.getBaseIngredient(), ing.getCount()))
						return null;

			if (r.getFluidInputs() != null)
				for (SizedFluidIngredient ing : r.getFluidInputs())
					if (!FluidHelper.account(totalCost, callback, ing))
						return null;
		}

		totalCost.add(addedCost);

		return finalizeGristCosts(totalCost, recipe.getResultItem(getLookupProvider()).getCount());
	}

	@Override
	public void reportPreliminaryLookups(Recipe<?> recipe, LookupTracker tracker) {
		super.reportPreliminaryLookups(recipe, tracker);

		if (recipe instanceof MultiblockRecipe r)
			if (r.getItemInputs() != null)
				for (IngredientWithSize ing : r.getItemInputs())
					tracker.report(ing.getBaseIngredient());
	}
}