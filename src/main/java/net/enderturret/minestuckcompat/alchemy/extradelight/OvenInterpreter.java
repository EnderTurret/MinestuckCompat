package net.enderturret.minestuckcompat.alchemy.extradelight;

import org.jetbrains.annotations.Nullable;

import com.lance5057.extradelight.workstations.oven.recipes.OvenRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;

import net.minecraft.world.item.crafting.Recipe;

import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;

public final class OvenInterpreter extends AbstractCostAddingRecipeInterpreter {

	public static final MapCodec<OvenInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			COST_FIELD.forGetter(OvenInterpreter::addedCost)
			).apply(instance, OvenInterpreter::new));

	/**
	 * Constructs a new {@code OvenInterpreter}.
	 * @param addedCost The grist cost added for all recipes processed by this interpreter.
	 */
	public OvenInterpreter(GristSet.Immutable addedCost) {
		super(addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	@Nullable
	protected MutableGristSet ingredientCost(Recipe<?> recipe, GeneratorCallback callback) {
		final MutableGristSet ret = super.ingredientCost(recipe, callback);
		if (ret == null) return null;

		if (recipe instanceof OvenRecipe r && !r.getOutputContainer().isEmpty() && !account(ret, callback, r.getOutputContainer().getItem()))
			return null;

		return ret;
	}
}