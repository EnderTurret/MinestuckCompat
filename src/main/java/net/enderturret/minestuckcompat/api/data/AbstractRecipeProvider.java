package net.enderturret.minestuckcompat.api.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

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

	protected static void oreCombinations(RecipeOutput output, ItemLike ingot, ItemLike block, ItemLike ore, @Nullable ItemLike rawOre, @Nullable ItemLike rawOreBlock) {
		combination(block).or().input(ingot).input(Items.STONE).build(output);
		combination(ore).and().input(ingot).input(Items.STONE).build(output);
		if (rawOre != null && rawOreBlock != null)
			combination(rawOreBlock).and().input(rawOre).input(Items.STONE).build(output);
	}

	protected static void saplingCombinations(RecipeOutput output, ItemLike sapling, ItemLike log, ItemLike leaves, boolean includePrimary) {
		if (includePrimary)
			combination(sapling).or().input(log).input(leaves).build(output);
		combination(sapling).and().namedInput(Items.STICK).input(leaves).build(output);
		combination(sapling).and().namedInput(Items.WHEAT_SEEDS).input(leaves).build(output);
	}

	protected static void saplingCombinations(RecipeOutput output, ItemLike sapling, ItemLike log, ItemLike leaves) {
		saplingCombinations(output, sapling, log, leaves, true);
	}

	protected static void woodCombinations(RecipeOutput output, ItemLike planks, ItemLike slab, ItemLike stairs, ItemLike door, ItemLike fence, ItemLike fenceGate, ItemLike trapdoor) {
		combination(stairs).or().input(planks).input(slab).build(output);
		combination(fenceGate).or().input(door).input(fence).build(output);
		combination(trapdoor).or().input(door).input(slab).build(output);
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