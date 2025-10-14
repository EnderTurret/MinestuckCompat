/*
package net.enderturret.minestuckcompat.alchemy.gtceu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.kind.GTRecipe;
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

import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.api.alchemy.AbstractCostAddingRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.AnalyzableRecipeInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.FluidHelper;

public final class GTRecipeInterpreter extends AbstractCostAddingRecipeInterpreter.Typed<GTRecipe> implements AnalyzableRecipeInterpreter {

	public static final MapCodec<GTRecipeInterpreter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			COST_FIELD.forGetter(GTRecipeInterpreter::addedCost)
			).apply(instance, GTRecipeInterpreter::new));

	public GTRecipeInterpreter(GristSet.Immutable addedCost) {
		super(GTRecipe.class, addedCost);
	}

	@Override
	public MapCodec<? extends RecipeInterpreter> codec() {
		return CODEC;
	}

	@Override
	public List<Item> getOutputItemsTyped(GTRecipe recipe) {
		return recipe.getOutputContents(ItemRecipeCapability.CAP).stream()
				.flatMap(c -> {
					if (c.getContent() instanceof SizedIngredient sized)
						return Arrays.stream(sized.getItems()).map(ItemStack::getItem);
					else
						MinestuckCompat.LOGGER.info("Unexpected output {} {}", c.getContent(), c.getContent().getClass());

					return Stream.empty();
				})
				.toList();
	}

	@Override
	@Nullable
	public MutableGristSet generateCost(MutableGristSet totalCost, GTRecipe recipe, Item output, GeneratorCallback callback) {
		for (Content content : recipe.getInputContents(ItemRecipeCapability.CAP))
			if (content.getContent() instanceof SizedIngredient ing) {
				if (!account(totalCost, callback, ing))
					return null;
			}

		for (Content content : recipe.getInputContents(FluidRecipeCapability.CAP))
			if (content.getContent() instanceof SizedFluidIngredient ing) {
				if (!FluidHelper.account(totalCost, callback, ing))
					return null;
		}

		return totalCost;
	}

	@Override
	public void reportPreliminaryLookupsTyped(GTRecipe recipe, LookupTracker tracker) {
		for (Content content : recipe.getInputContents(ItemRecipeCapability.CAP)) {
			if (content.getContent() instanceof SizedIngredient ing)
				tracker.report(ing.ingredient());
			else
				MinestuckCompat.LOGGER.warn("Unexpected item input {} {}", content.getContent(), content.getContent().getClass());
		}

		for (Content content : recipe.getInputContents(FluidRecipeCapability.CAP)) {
			if (content.getContent() instanceof SizedFluidIngredient ing)
				FluidHelper.report(tracker, ing.ingredient());
			else
				MinestuckCompat.LOGGER.info("Unexpected fluid input {} {}", content.getContent(), content.getContent().getClass());
		}
	}

	@Override
	public void reportCraftingStation(Recipe<?> recipe, LookupTracker tracker) {
	}
}
*/