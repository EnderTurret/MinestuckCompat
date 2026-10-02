package net.enderturret.minestuckcompat.data.util;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.recipe.ContainerGristCostBuilder;
import com.mraof.minestuck.api.alchemy.recipe.GristCostRecipeBuilder;
import com.mraof.minestuck.api.alchemy.recipe.SourceGristCostBuilder;
import com.mraof.minestuck.api.alchemy.recipe.combination.CombinationRecipeBuilder;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public abstract class AbstractRecipeProvider extends AbstractOverridingRecipeProvider {

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

	protected static ContainerGristCostBuilder containerGristCost(ItemLike item) {
		return ContainerGristCostBuilder.of(item);
	}

	protected static CombinationRecipeBuilder combination(ItemLike item) {
		return CombinationRecipeBuilder.of(item);
	}

	/**
	 * Adds combination recipes for:
	 * <ul>
	 * <li>{@code ingot || minecraft:stone ==> block}</li>
	 * <li>{@code ingot && minecraft:stone ==> ore}</li>
	 * <li>{@code rawOre && minecraft:stone ==> rawOreBlock}</li>
	 * <li>{@code ingot && minecraft:deepslate ==> deepslateOre}</li>
	 * </ul>
	 * @param output The {@code RecipeOutput}.
	 * @param ingot The ingot of the ore.
	 * @param block The "storage" block of the ore.
	 * @param ore The ore itself.
	 * @param deepslateOre The deepslate version of the ore.
	 * @param rawOre The raw ore.
	 * @param rawOreBlock The "storage" block of the raw ore.
	 */
	protected static void oreCombinations(RecipeOutput output, ItemLike ingot, ItemLike block, ItemLike ore, @Nullable ItemLike deepslateOre, @Nullable ItemLike rawOre, @Nullable ItemLike rawOreBlock) {
		combination(block).or().input(ingot).input(Items.STONE).build(output);
		combination(ore).and().input(ingot).input(Items.STONE).build(output);
		if (rawOre != null && rawOreBlock != null)
			combination(rawOreBlock).and().input(rawOre).input(Items.STONE).build(output);
		if (deepslateOre != null)
			combination(deepslateOre).and().input(ingot).input(Items.DEEPSLATE).build(output);
	}

	/**
	 * Adds combination recipes for:
	 * <ul>
	 * <li>{@code log || leaves ==> sapling} (if {@code includePrimary} is {@code true})</li>
	 * <li>{@code minecraft:stick && leaves ==> sapling}</li>
	 * <li>{@code minecraft:wheat_seeds && leaves ==> sapling}</li>
	 * </ul>
	 * @param output The {@code RecipeOutput}.
	 * @param sapling The sapling of the wood type.
	 * @param log The log of the wood type.
	 * @param leaves The leaves of the wood type.
	 * @param includePrimary If {@code true}, generates an additional recipe involving the log. Pass {@code false} if the wood type has no unique log.
	 */
	protected static void saplingCombinations(RecipeOutput output, ItemLike sapling, ItemLike log, ItemLike leaves, boolean includePrimary) {
		if (includePrimary)
			combination(sapling).or().input(log).input(leaves).build(output);
		combination(sapling).and().namedInput(Items.STICK).input(leaves).build(output);
		combination(sapling).and().namedInput(Items.WHEAT_SEEDS).input(leaves).build(output);
	}

	/**
	 * <p>
	 * Adds combination recipes for:
	 * <ul>
	 * <li>{@code log || leaves ==> sapling}</li>
	 * <li>{@code minecraft:stick && leaves ==> sapling}</li>
	 * <li>{@code minecraft:wheat_seeds && leaves ==> sapling}</li>
	 * </ul>
	 * </p>
	 * <p>
	 * This is a version of {@link #saplingCombinations(RecipeOutput, ItemLike, ItemLike, ItemLike, boolean)} that generates all combinations.
	 * </p>
	 * @param output The {@code RecipeOutput}.
	 * @param sapling The sapling of the wood type.
	 * @param log The log of the wood type.
	 * @param leaves The leaves of the wood type.
	 */
	protected static void saplingCombinations(RecipeOutput output, ItemLike sapling, ItemLike log, ItemLike leaves) {
		saplingCombinations(output, sapling, log, leaves, true);
	}

	/**
	 * Adds combination recipes for:
	 * <ul>
	 * <li>{@code planks || slab ==> stairs}</li>
	 * <li>{@code door || fence ==> fence gate}</li>
	 * <li>{@code door || slab ==> trapdoor}</li>
	 * </ul>
	 * @param output The {@code RecipeOutput}.
	 * @param planks The planks of the wood type.
	 * @param slab The slab of the wood type.
	 * @param stairs The stairs of the wood type.
	 * @param door The door of the wood type.
	 * @param fence The fence of the wood type.
	 * @param fenceGate The fence gate of the wood type.
	 * @param trapdoor The trapdoor of the wood type.
	 */
	protected static void woodCombinations(RecipeOutput output, @Nullable ItemLike planks, @Nullable ItemLike slab, @Nullable ItemLike stairs, @Nullable ItemLike door, @Nullable ItemLike fence, @Nullable ItemLike fenceGate, @Nullable ItemLike trapdoor) {
		if (stairs != null && planks != null && slab != null)
			combination(stairs).or().input(planks).input(slab).build(output);
		if (fenceGate != null && door != null && fence != null)
			combination(fenceGate).or().input(door).input(fence).build(output);
		if (trapdoor != null && door != null && slab != null)
			combination(trapdoor).or().input(door).input(slab).build(output);
	}

	protected static TagKey<Item> tag(String namespace, String path) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, path));
	}

	protected static TagKey<Item> c(String path) {
		return tag("c", path);
	}

	protected static Item lookup(String namespace, String path) {
		final Item ret = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(namespace, path));
		if (ret == Items.AIR) throw new IllegalArgumentException("No item called " + namespace + ":" + path);
		return ret;
	}
}