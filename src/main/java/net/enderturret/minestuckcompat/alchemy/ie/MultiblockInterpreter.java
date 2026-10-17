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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.AnalyzableRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.FluidHelper;

import blusunrize.immersiveengineering.api.crafting.BlueprintCraftingRecipe;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
import blusunrize.immersiveengineering.api.crafting.TagOutput;
import blusunrize.immersiveengineering.common.register.IEBlocks;

public final class MultiblockInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<MultiblockRecipe> implements AnalyzableRecipeInterpreter {

	public static final MapCodec<MultiblockInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			COST_FIELD.forGetter(MultiblockInterpreter::addedCost)
			).apply(instance, MultiblockInterpreter::new));

	public MultiblockInterpreter(GristSet.Immutable addedCost) {
		super(MultiblockRecipe.class, addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	private boolean warned = false;

	@Override
	public List<ItemStack> getOutputItemStacksTyped(MultiblockRecipe recipe) {
		if (recipe instanceof BlueprintCraftingRecipe r)
			return safeResolve(r.output);

		if (!warned) {
			MinestuckCompat.LOGGER.warn("Called for unhandled MultiblockRecipe type {}! This may resolve tags too early!", recipe.getClass().getSimpleName());
			warned = true;
		}

		return super.getOutputItemStacks(recipe);
	}

	@Override
	@Nullable
	public MutableGristSet generateCost(MutableGristSet totalCost, MultiblockRecipe recipe, Item output, GeneratorCallback callback) {
		if (recipe.getItemInputs() != null)
			for (IngredientWithSize ing : recipe.getItemInputs())
				if (!account(totalCost, callback, ing.getBaseIngredient(), ing.getCount()))
					return null;

		if (recipe.getFluidInputs() != null)
			for (SizedFluidIngredient ing : recipe.getFluidInputs())
				if (!FluidHelper.account(totalCost, callback, ing))
					return null;

		return totalCost;
	}

	@Override
	public void reportPreliminaryLookupsTyped(MultiblockRecipe recipe, LookupTracker tracker) {
		if (recipe.getItemInputs() != null)
			for (IngredientWithSize ing : recipe.getItemInputs())
				tracker.report(ing.getBaseIngredient());
	}

	@Override
	public void reportCraftingStation(Recipe<?> recipe, LookupTracker tracker) {
		if (recipe instanceof BlueprintCraftingRecipe)
			tracker.report(IEBlocks.WoodenDevices.CRAFTING_TABLE.asItem());
	}

	static List<ItemStack> safeResolve(TagOutput output) {
		return List.of(output.get());
	}
}