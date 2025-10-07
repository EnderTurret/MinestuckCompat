package net.enderturret.minestuckcompat.mixin.feature.smithing_fix;

import java.lang.reflect.Field;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.SmithingInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import net.enderturret.minestuckcompat.api.alchemy.AbstractRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.AnalyzableRecipeInterpreter;

@Mixin(SmithingInterpreter.class)
public abstract class MixinSmithingInterpreter implements RecipeInterpreter, AnalyzableRecipeInterpreter {

	@Shadow
	@Final
	private static Field templateField;
	@Shadow
	@Final
	private static Field baseField;
	@Shadow
	@Final
	private static Field additionField;

	@Overwrite
	@Override
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
		try {
			final MutableGristSet totalCost = MutableGristSet.newDefault();

			final Ingredient template = (Ingredient) templateField.get(recipe);
			if (!AbstractRecipeInterpreter.account(totalCost, callback, template)) return null;

			final Ingredient base = (Ingredient) baseField.get(recipe);
			if (!AbstractRecipeInterpreter.account(totalCost, callback, base)) return null;

			final Ingredient addition = (Ingredient) additionField.get(recipe);
			if (!AbstractRecipeInterpreter.account(totalCost, callback, addition)) return null;

			totalCost.scale(1F / recipe.getResultItem(null).getCount(), false);

			return totalCost;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void reportPreliminaryLookups(Recipe<?> recipe, LookupTracker tracker) {
		try {
			tracker.report((Ingredient) templateField.get(recipe));
			tracker.report((Ingredient) baseField.get(recipe));
			tracker.report((Ingredient) additionField.get(recipe));
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void reportCraftingStation(Recipe<?> recipe, LookupTracker tracker) {
		tracker.report(Items.SMITHING_TABLE);
	}
}