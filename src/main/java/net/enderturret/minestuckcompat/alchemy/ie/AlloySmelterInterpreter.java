package net.enderturret.minestuckcompat.alchemy.ie;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.Item;

import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;

import blusunrize.immersiveengineering.api.crafting.AlloyRecipe;

public final class AlloySmelterInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<AlloyRecipe> {

	public static final MapCodec<AlloySmelterInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			COST_FIELD.forGetter(AlloySmelterInterpreter::addedCost)
			).apply(instance, AlloySmelterInterpreter::new));

	public AlloySmelterInterpreter(GristSet.Immutable addedCost) {
		super(AlloyRecipe.class, addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	public List<Item> getOutputItemsTyped(AlloyRecipe recipe) {
		return MultiblockInterpreter.safeResolve(recipe.output);
	}

	@Override
	@Nullable
	public MutableGristSet generateCost(MutableGristSet totalCost, AlloyRecipe recipe, Item output, GeneratorCallback callback) {
		if (!account(totalCost, callback, recipe.input0.getBaseIngredient(), recipe.input0.getCount()))
			return null;
		if (!account(totalCost, callback, recipe.input1.getBaseIngredient(), recipe.input1.getCount()))
			return null;
		return totalCost;
	}

	@Override
	public void reportPreliminaryLookupsTyped(AlloyRecipe recipe, LookupTracker tracker) {
		tracker.report(recipe.input0.getBaseIngredient());
		tracker.report(recipe.input1.getBaseIngredient());
	}
}