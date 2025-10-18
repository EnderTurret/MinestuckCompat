package net.enderturret.minestuckcompat.alchemy.farmersdelight;

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

import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.AnalyzableRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.SimpleRecipeInterpreter;

import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

/**
 * A recipe interpreter intended for Farmers Delight's {@linkplain ModRecipeTypes#CUTTING cutting board} recipes.
 * While cutting board recipes can be read using the {@link SimpleRecipeInterpreter} (or the default one), these recipes can have multiple outputs.
 * @author EnderTurret
 */
public final class CuttingBoardInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<CuttingBoardRecipe> implements AnalyzableRecipeInterpreter {

	public static final MapCodec<CuttingBoardInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(CuttingBoardInterpreter::addedCost)
			).apply(instance, CuttingBoardInterpreter::new));

	/**
	 * Constructs a new {@code CuttingBoardInterpreter}.
	 * @param addedCost The grist cost added for all recipes processed by this interpreter.
	 */
	public CuttingBoardInterpreter(GristSet.Immutable addedCost) {
		super(CuttingBoardRecipe.class, addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	protected List<ItemStack> getOutputItemStacksTyped(CuttingBoardRecipe recipe) {
		return List.copyOf(recipe.getResults());
	}

	@Override
	@Nullable
	protected MutableGristSet generateCost(MutableGristSet totalCost, CuttingBoardRecipe recipe, Item output, GeneratorCallback callback) {
		return totalCost;
	}

	@Override
	protected void reportPreliminaryLookupsTyped(CuttingBoardRecipe recipe, LookupTracker tracker) {}

	@Override
	public void reportCraftingStation(Recipe<?> recipe, LookupTracker tracker) {
		tracker.report(ModItems.CUTTING_BOARD.get());
	}
}