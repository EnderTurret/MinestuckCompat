package net.enderturret.minestuckcompat.api.alchemy;

import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.crafting.Recipe;

public interface AnalyzableRecipeInterpreter {

	public void reportCraftingStation(Recipe<?> recipe, LookupTracker tracker);
}