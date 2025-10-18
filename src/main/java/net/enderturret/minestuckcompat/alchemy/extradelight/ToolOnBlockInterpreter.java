package net.enderturret.minestuckcompat.alchemy.extradelight;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.lance5057.extradelight.recipe.ToolOnBlockRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;

public final class ToolOnBlockInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<ToolOnBlockRecipe> {

	public static final MapCodec<ToolOnBlockInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			COST_FIELD.forGetter(ToolOnBlockInterpreter::addedCost)
			).apply(instance, ToolOnBlockInterpreter::new));

	/**
	 * Constructs a new {@code ToolOnBlockInterpreter}.
	 * @param addedCost The grist cost added for all recipes processed by this interpreter.
	 */
	public ToolOnBlockInterpreter(GristSet.Immutable addedCost) {
		super(ToolOnBlockRecipe.class, addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	protected List<ItemStack> getOutputItemStacksTyped(ToolOnBlockRecipe recipe) {
		return List.of(new ItemStack(recipe.getOut()));
	}

	@Override
	@Nullable
	protected MutableGristSet generateCost(MutableGristSet totalCost, ToolOnBlockRecipe recipe, Item output, GeneratorCallback callback) {
		if (!account(totalCost, callback, recipe.getIn().asItem()))
			return null;

		return totalCost;
	}

	@Override
	protected void reportPreliminaryLookupsTyped(ToolOnBlockRecipe recipe, LookupTracker tracker) {
		tracker.report(recipe.getIn().asItem());
		tracker.report(recipe.getTool());
	}
}