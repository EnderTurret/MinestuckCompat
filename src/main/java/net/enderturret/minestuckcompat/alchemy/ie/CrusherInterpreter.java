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
import net.minecraft.world.item.crafting.Recipe;

import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.AnalyzableRecipeInterpreter;

import blusunrize.immersiveengineering.api.crafting.CrusherRecipe;
import blusunrize.immersiveengineering.common.register.IEBlocks;

/**
 * A recipe interpreter for Immersive Engineering's {@linkplain CrusherRecipe crusher recipes}.
 * @author EnderTurret
 */
public final class CrusherInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<CrusherRecipe> implements AnalyzableRecipeInterpreter {

	public static final MapCodec<CrusherInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			COST_FIELD.forGetter(CrusherInterpreter::addedCost)
			).apply(instance, CrusherInterpreter::new));

	/**
	 * Constructs a new {@code CrusherInterpreter}.
	 * @param addedCost The grist cost added for all recipes processed by this interpreter.
	 */
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

	@Override
	public void reportCraftingStation(Recipe<?> recipe, LookupTracker tracker) {
		tracker.report(IEBlocks.MetalDecoration.ENGINEERING_HEAVY.asItem());
	}
}