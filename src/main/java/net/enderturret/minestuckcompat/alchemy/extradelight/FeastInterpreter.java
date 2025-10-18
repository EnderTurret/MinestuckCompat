package net.enderturret.minestuckcompat.alchemy.extradelight;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.lance5057.extradelight.blocks.RecipeFeastBlock;
import com.lance5057.extradelight.blocks.RecipeFondueFeastBlock;
import com.lance5057.extradelight.recipe.FeastRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;

public final class FeastInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<FeastRecipe> {

	public static final MapCodec<FeastInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			COST_FIELD.forGetter(FeastInterpreter::addedCost)
			).apply(instance, FeastInterpreter::new));

	/**
	 * Constructs a new {@code FeastInterpreter}.
	 * @param addedCost The grist cost added for all recipes processed by this interpreter.
	 */
	public FeastInterpreter(GristSet.Immutable addedCost) {
		super(FeastRecipe.class, addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	protected List<ItemStack> getOutputItemStacksTyped(FeastRecipe recipe) {
		return List.of(recipe.getResultItem(null));
	}

	@Override
	@Nullable
	protected MutableGristSet ingredientCost(Recipe<?> recipe, GeneratorCallback callback) {
		return MutableGristSet.newDefault();
	}

	@Override
	@Nullable
	protected MutableGristSet generateCost(MutableGristSet totalCost, FeastRecipe recipe, Item output, GeneratorCallback callback) {
		if (!account(totalCost, callback, recipe.getFeastStack().getItem()))
			return null;

		if (!account(totalCost, callback, recipe.getContainer()))
			return null;

		return totalCost;
	}

	@Override
	@Nullable
	protected GristSet finalizeGristCosts(@Nullable MutableGristSet totalCost, Recipe<?> recipe, Item output) {
		if (recipe instanceof FeastRecipe r) {
			final Block block = r.getFeast().getBlock();
			final int result;
			if (block instanceof RecipeFeastBlock feast)
				result = feast.getMaxServings();
			else if (block instanceof RecipeFondueFeastBlock feast)
				result = feast.getMaxServings();
			else {
				MinestuckCompat.LOGGER.warn("[ExtraDelight] Unknown feast block type {}", block.getClass());
				result = 1;
			}

			return finalizeGristCosts(totalCost, result);
		}

		return super.finalizeGristCosts(totalCost, recipe, output);
	}

	@Override
	protected void reportPreliminaryLookupsTyped(FeastRecipe recipe, LookupTracker tracker) {
		tracker.report(recipe.getFeastStack());
		tracker.report(recipe.getContainer());
	}
}