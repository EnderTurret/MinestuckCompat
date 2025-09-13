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

import blusunrize.immersiveengineering.api.crafting.CrusherRecipe;

public final class CrusherInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<CrusherRecipe> {

	public static final MapCodec<CrusherInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			COST_FIELD.forGetter(CrusherInterpreter::addedCost)
			).apply(instance, CrusherInterpreter::new));

	public CrusherInterpreter(GristSet.Immutable addedCost) {
		super(CrusherRecipe.class, addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	public List<Item> getOutputItemsTyped(CrusherRecipe recipe) {
		return MultiblockInterpreter.safeResolve(recipe.output);
	}

	@Override
	@Nullable
	public MutableGristSet generateCost(MutableGristSet totalCost, CrusherRecipe recipe, Item output, GeneratorCallback callback) {
		if (!account(totalCost, callback, recipe.input))
			return null;
		return totalCost;
	}

	@Override
	public void reportPreliminaryLookupsTyped(CrusherRecipe recipe, LookupTracker tracker) {
		tracker.report(recipe.input);
	}
}