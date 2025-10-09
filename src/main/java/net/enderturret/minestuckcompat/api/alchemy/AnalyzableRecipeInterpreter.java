package net.enderturret.minestuckcompat.api.alchemy;

import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.crafting.Recipe;

import net.enderturret.minestuckcompat.alchemy.analysis.ObtainabilityAnalyzer;

/**
 * <p>
 * An extension to {@link RecipeInterpreter} that allows the {@linkplain ObtainabilityAnalyzer obtainability analyzer} to take into account a recipe's crafting station.
 * </p>
 * <p>
 * A modder can implement this interface in their recipe interpreter by adding an implementation of the {@link #reportCraftingStation(Recipe, LookupTracker)} method.
 * Then they can add this interface using a mixin if a runtime dependency is not desired, or otherwise add the interface directly.
 * (If the former, make sure your mixin config plugin excludes the mixin when Minestuck Compat isn't present!)
 * </p>
 * @author EnderTurret
 */
public interface AnalyzableRecipeInterpreter {

	/**
	 * Reports the specified recipe's crafting station to the {@link LookupTracker}.
	 * @param recipe The recipe to report the crafting station of.
	 * @param tracker The {@code LookupTracker} to report the crafting station to.
	 */
	public void reportCraftingStation(Recipe<?> recipe, LookupTracker tracker);
}