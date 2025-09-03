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

public final class SimpleRecipeInterpreter extends AbstractRecipeInterpreter {

	public static final MapCodec<SimpleRecipeInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.BOOL.optionalFieldOf("ignore_special", true).forGetter(SimpleRecipeInterpreter::ignoreSpecial),
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("added_cost", GristSet.EMPTY).forGetter(SimpleRecipeInterpreter::addedCost)
			).apply(instance, SimpleRecipeInterpreter::new));

	private final boolean ignoreSpecial;
	private final GristSet.Immutable addedCost;

	public SimpleRecipeInterpreter(boolean ignoreSpecial, GristSet.Immutable addedCost) {
		this.ignoreSpecial = ignoreSpecial;
		this.addedCost = addedCost;
	}

	public SimpleRecipeInterpreter(boolean ignoreSpecial) {
		this(ignoreSpecial, GristSet.EMPTY);
	}

	public boolean ignoreSpecial() {
		return ignoreSpecial;
	}

	public GristSet.Immutable addedCost() {
		return addedCost;
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

		totalCost.add(addedCost);

		return finalizeGristCosts(totalCost, recipe.getResultItem(getLookupProvider()).getCount());
	}
}