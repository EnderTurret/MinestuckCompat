package net.enderturret.minestuckcompat.alchemy.create;

import java.util.List;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratorCallback;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.api.alchemy.AbstractRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.FluidHelper;

public final class SequencedAssemblyInterpreter extends AbstractRecipeInterpreter {

	public static final MapCodec<SequencedAssemblyInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("deploy_cost", GristSet.EMPTY).forGetter(SequencedAssemblyInterpreter::deployCost),
			GristSet.Codecs.MAP_CODEC.optionalFieldOf("press_cost", GristSet.EMPTY).forGetter(SequencedAssemblyInterpreter::pressCost)
			).apply(instance, SequencedAssemblyInterpreter::new));

	private final GristSet.Immutable deployCost;
	private final GristSet.Immutable pressCost;

	public SequencedAssemblyInterpreter(GristSet.Immutable deployCost, GristSet.Immutable pressCost) {
		this.deployCost = deployCost;
		this.pressCost = pressCost;
	}

	public GristSet.Immutable deployCost() {
		return deployCost;
	}

	public GristSet.Immutable pressCost() {
		return pressCost;
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	public List<Item> getOutputItems(Recipe<?> recipe) {
		if (recipe instanceof SequencedAssemblyRecipe r)
			return List.of(r.getResultItem(null).getItem(), r.getTransitionalItem().getItem());

		return super.getOutputItems(recipe);
	}

	@Override
	public GristSet generateCost(Recipe<?> recipe, Item output, GeneratorCallback callback) {
		if (!(recipe instanceof SequencedAssemblyRecipe r))
			return super.generateCost(recipe, output, callback);

		MutableGristSet totalCost = MutableGristSet.newDefault();

		account(totalCost, callback, r.getIngredient());

		// If we're checking the incomplete item, stop here.
		if (output == r.getTransitionalItem().getItem()) return totalCost;

		final MutableGristSet sequenceCost = r.getLoops() == 1 ? totalCost : MutableGristSet.newDefault();

		for (SequencedRecipe seq : r.getSequence())
			if (!handleSequenceRecipe(r, seq.getRecipe(), sequenceCost, output, callback))
				return null;

		if (r.getLoops() > 1) {
			sequenceCost.scale(r.getLoops());
			totalCost.add(sequenceCost);
		}

		return finalizeGristCosts(totalCost, 1);
	}

	private boolean handleSequenceRecipe(SequencedAssemblyRecipe r, Recipe<?> seq, MutableGristSet sequenceCost, Item output, GeneratorCallback callback) {
		if (seq instanceof DeployerApplicationRecipe r2) {
			sequenceCost.add(deployCost);
			for (Ingredient ing : r2.getIngredients())
				if (!ing.test(r.getTransitionalItem()))
					if (!account(sequenceCost, callback, ing))
						return false;
		}

		else if (seq instanceof PressingRecipe r2) {
			sequenceCost.add(pressCost);
		}

		else if (seq instanceof FillingRecipe r2) {
			final FluidIngredient fluid = r2.getRequiredFluid();
			if (!FluidHelper.account(sequenceCost, callback, fluid.getMatchingFluidStacks(), fluid.getRequiredAmount()))
				return false;
		}

		else {
			MinestuckCompat.LOGGER.warn("Don't know how to handle a {} in a SequencedAssemblyRecipe (from {})!", seq.getClass().getSimpleName(), output);
			return false;
		}

		return true;
	}
}