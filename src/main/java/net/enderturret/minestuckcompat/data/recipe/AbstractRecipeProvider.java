package net.enderturret.minestuckcompat.data.recipe;

import java.util.concurrent.CompletableFuture;

import com.mraof.minestuck.api.alchemy.recipe.GristCostRecipeBuilder;
import com.mraof.minestuck.api.alchemy.recipe.SourceGristCostBuilder;
import com.mraof.minestuck.api.alchemy.recipe.combination.CombinationRecipeBuilder;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import net.neoforged.neoforge.common.conditions.IConditionBuilder;

public abstract class AbstractRecipeProvider extends RecipeProvider implements IConditionBuilder {

	protected AbstractRecipeProvider(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected abstract void buildRecipes(RecipeOutput recipeOutput);

	protected static GristCostRecipeBuilder gristCost(ItemLike item) {
		return GristCostRecipeBuilder.of(item);
	}

	protected static GristCostRecipeBuilder gristCost(TagKey<Item> tag) {
		return GristCostRecipeBuilder.of(tag);
	}

	protected static SourceGristCostBuilder sourceGristCost(ItemLike item) {
		return SourceGristCostBuilder.of(item);
	}

	protected static SourceGristCostBuilder sourceGristCost(TagKey<Item> tag) {
		return SourceGristCostBuilder.of(tag);
	}

	protected static CombinationRecipeBuilder combination(ItemLike item) {
		return CombinationRecipeBuilder.of(item);
	}

	protected static void oreCombinations(RecipeOutput output, ItemLike ingot, ItemLike block, ItemLike ore) {
		combination(block).or().input(ingot).input(Items.STONE).build(output);
		combination(ore).and().input(ingot).input(Items.STONE).build(output);
	}

	protected static TagKey<Item> c(String path) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
	}

	protected static Item lookup(String namespace, String path) {
		final Item ret = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(namespace, path));
		if (ret == Items.AIR) throw new IllegalArgumentException("No item called " + namespace + ":" + path);
		return ret;
	}
}