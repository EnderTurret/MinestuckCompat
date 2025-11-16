package net.enderturret.minestuckcompat.api.alchemy;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;

/**
 * A {@link RecipeInterpreter} that functions like the default, but with support for added costs and including special recipes.
 * @author EnderTurret
 */
public final class SimpleRecipeInterpreter extends AbstractCostAddingRecipeInterpreter {

	public static final MapCodec<SimpleRecipeInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.BOOL.optionalFieldOf("ignore_special", true).forGetter(SimpleRecipeInterpreter::ignoreSpecial),
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(SimpleRecipeInterpreter::addedCost)
			).apply(instance, SimpleRecipeInterpreter::new));

	private final boolean ignoreSpecial;

	/**
	 * Constructs a new {@code SimpleRecipeInterpreter}.
	 * @param ignoreSpecial Whether or not to ignore recipes that return {@code true} in {@link Recipe#isSpecial()}.
	 * @param addedCost The grist cost added for all recipes processed by this interpreter.
	 */
	public SimpleRecipeInterpreter(boolean ignoreSpecial, GristSet.Immutable addedCost) {
		super(addedCost);
		this.ignoreSpecial = ignoreSpecial;
	}

	/**
	 * No-cost version of {@link #SimpleRecipeInterpreter(boolean, com.mraof.minestuck.api.alchemy.GristSet.Immutable)}.
	 * @param ignoreSpecial Whether or not to ignore recipes that return {@code true} in {@link Recipe#isSpecial()}.
	 */
	public SimpleRecipeInterpreter(boolean ignoreSpecial) {
		this(ignoreSpecial, GristSet.EMPTY);
	}

	public boolean ignoreSpecial() {
		return ignoreSpecial;
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	@Nullable
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
		if (ignoreSpecial && recipe.isSpecial())
			return null;

		final MutableGristSet totalCost = ingredientCost(recipe, callback);
		if (totalCost == null) return null;

		return finalizeGristCosts(totalCost, recipe, output, callback);
	}
}