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

import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

/**
 * A recipe interpreter intended for Farmers Delight's {@linkplain ModRecipeTypes#COOKING cooking} recipes.
 * @author EnderTurret
 */
public final class CookingInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<CookingPotRecipe> implements AnalyzableRecipeInterpreter {

	public static final MapCodec<CookingInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			COST_FIELD.forGetter(CookingInterpreter::addedCost)
			).apply(instance, CookingInterpreter::new));

	/**
	 * Constructs a new {@code CookingInterpreter}.
	 * @param addedCost The grist cost added for all recipes processed by this interpreter.
	 */
	public CookingInterpreter(GristSet.Immutable addedCost) {
		super(CookingPotRecipe.class, addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	protected List<ItemStack> getOutputItemStacksTyped(CookingPotRecipe recipe) {
		return List.of(recipe.getResultItem(null));
	}

	@Override
	@Nullable
	protected MutableGristSet generateCost(MutableGristSet totalCost, CookingPotRecipe recipe, Item output, GeneratorCallback callback) {
		if (!recipe.getOutputContainer().isEmpty() && !account(totalCost, callback, recipe.getOutputContainer().getItem()))
			return null;

		return totalCost;
	}

	@Override
	protected void reportPreliminaryLookupsTyped(CookingPotRecipe recipe, LookupTracker tracker) {
		tracker.report(recipe.getOutputContainer());
	}

	@Override
	public void reportCraftingStation(Recipe<?> recipe, LookupTracker tracker) {
		tracker.report(ModItems.COOKING_POT.get());
	}
}