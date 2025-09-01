package net.enderturret.minestuckcompat.data.recipe;

import java.util.concurrent.CompletableFuture;

import com.mraof.minestuck.api.alchemy.recipe.GristCostRecipeBuilder;
import com.mraof.minestuck.api.alchemy.recipe.SourceGristCostBuilder;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import net.neoforged.neoforge.common.conditions.IConditionBuilder;

public abstract class AbstractRecipeProvider extends RecipeProvider implements IConditionBuilder {

	protected AbstractRecipeProvider(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected abstract void buildRecipes(RecipeOutput recipeOutput);

	protected final GristCostRecipeBuilder gristCost(ItemLike item) {
		return GristCostRecipeBuilder.of(item);
	}

	protected final GristCostRecipeBuilder gristCost(TagKey<Item> tag) {
		return GristCostRecipeBuilder.of(tag);
	}

	protected final SourceGristCostBuilder sourceGristCost(ItemLike item) {
		return SourceGristCostBuilder.of(item);
	}

	protected final SourceGristCostBuilder sourceGristCost(TagKey<Item> tag) {
		return SourceGristCostBuilder.of(tag);
	}
}