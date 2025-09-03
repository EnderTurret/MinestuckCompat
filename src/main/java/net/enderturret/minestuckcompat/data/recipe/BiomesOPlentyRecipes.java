package net.enderturret.minestuckcompat.data.recipe;

import static biomesoplenty.api.item.BOPItems.*;
import static com.mraof.minestuck.api.alchemy.GristTypes.*;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.mraof.minestuck.item.MSItems;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.neoforged.neoforge.common.Tags;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.api.data.AbstractRecipeProvider;
import net.enderturret.minestuckcompat.api.data.RenamingRecipeOutput;

@Internal
public final class BiomesOPlentyRecipes extends AbstractRecipeProvider {

	public BiomesOPlentyRecipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		final RecipeOutput output = new RenamingRecipeOutput(_recipeOutput, MinestuckCompat.MOD_ID, "biomesoplenty")
				.withConditions(modLoaded("biomesoplenty"));

		gristCost(MUSIC_DISC_WANDERER).grist(BUILD, 15).grist(AMBER, 5).grist(COBALT, 5).grist(CHALK, 8).build(output);

		// Small Plants

		gristCost(SPROUT).grist(BUILD, 1).build(output);
		gristCost(BUSH).grist(BUILD, 1).build(output);
		gristCost(CLOVER).grist(BUILD, 1).build(output);
		gristCost(DUNE_GRASS).grist(BUILD, 1).build(output);
		gristCost(DESERT_GRASS).grist(BUILD, 1).build(output);
		gristCost(DEAD_GRASS).grist(BUILD, 1).build(output);
		gristCost(TUNDRA_SHRUB).grist(BUILD, 1).build(output);
		gristCost(ENDERPHYTE).grist(BUILD, 1).build(output);

		// Tall Plants

		gristCost(HIGH_GRASS).grist(AMBER, 1).build(output);
		gristCost(HIGH_GRASS_PLANT).grist(AMBER, 1).build(output);
		sourceGristCost(HUGE_LILY_PAD).multiplier(4).source(Items.LILY_PAD).build(output);
		gristCost(HUGE_CLOVER_PETAL).grist(AMBER, 1).build(output);
		gristCost(BARLEY).grist(AMBER, 2).build(output);
		gristCost(SEA_OATS).grist(AMBER, 2).build(output);
		gristCost(CATTAIL).grist(AMBER, 2).build(output);
		gristCost(REED).grist(AMBER, 2).build(output);
		gristCost(WATERGRASS).grist(AMBER, 2).build(output);

		// Miscellaneous Plants

		gristCost(DEAD_BRANCH).grist(BUILD, 1).build(output);
		gristCost(WILLOW_VINE).grist(BUILD, 2).grist(AMBER, 1).build(output);
		gristCost(SPANISH_MOSS).grist(BUILD, 2).grist(AMBER, 1).build(output);
		gristCost(SPANISH_MOSS_PLANT).grist(BUILD, 2).grist(AMBER, 1).build(output);

		// Environmental Blocks

		sourceGristCost(ORIGIN_GRASS_BLOCK).source(Items.GRASS_BLOCK).build(output);
		gristCost(BARNACLES).grist(CAULK, 3).build(output);
		gristCost(DRIED_SALT).grist(BUILD, 4).grist(CHALK, 8).grist(SULFUR, 1).build(output);
		gristCost(WISPJELLY).grist(BUILD, 2).grist(CAULK, 6).build(output);

		gristCost(WEBBING).grist(BUILD, 9).build(output);
		gristCost(HANGING_COBWEB).grist(BUILD, 9).build(output);
		gristCost(HANGING_COBWEB_STRAND).grist(BUILD, 9).build(output);
		gristCost(STRINGY_COBWEB).grist(BUILD, 6).build(output);
		gristCost(SPIDER_EGG).grist(BUILD, 27).build(output);

		gristCost(BRAMBLE).grist(BUILD, 1).grist(SULFUR, 1).build(output);
		gristCost(BRAMBLE_LEAVES).grist(BUILD, 1).grist(SULFUR, 1).build(output);
		sourceGristCost(THERMAL_CALCITE).grist(SULFUR, 4).source(Items.CALCITE).build(output);
		sourceGristCost(THERMAL_CALCITE_VENT).source(THERMAL_CALCITE).build(output);
		gristCost(BRIMSTONE).grist(BUILD, 2).grist(SULFUR, 1).build(output);

		sourceGristCost(BLOOD_BUCKET).source(MSItems.BLOOD_BUCKET.asItem()).build(output);
		gristCost(BLOOD).grist(IODINE, 8).grist(GARNET, 8).build(output);
		sourceGristCost(FLESH).grist(BUILD, 1).multiplier(2).source(Items.ROTTEN_FLESH).build(output);
		sourceGristCost(POROUS_FLESH).grist(BUILD, 1).multiplier(2).source(Items.ROTTEN_FLESH).build(output);
		sourceGristCost(FLESH_TENDONS).grist(BUILD, 1).source(Items.ROTTEN_FLESH).build(output);
		sourceGristCost(FLESH_TENDONS_STRAND).grist(BUILD, 1).source(Items.ROTTEN_FLESH).build(output);
		sourceGristCost(EYEBULB).grist(AMBER, 2).source(Items.ROTTEN_FLESH).build(output);
		sourceGristCost(HAIR).grist(BUILD, 1).source(Items.ROTTEN_FLESH).build(output);
		sourceGristCost(PUS_BUBBLE).grist(BUILD, 1).source(Items.ROTTEN_FLESH).build(output);

		gristCost(TOADSTOOL).grist(IODINE, 5).build(output);
		gristCost(TOADSTOOL_BLOCK).grist(BUILD, 2).grist(IODINE, 5).build(output);
		gristCost(GLOWSHROOM).grist(IODINE, 3).grist(COBALT, 1).build(output);
		gristCost(GLOWSHROOM_BLOCK).grist(BUILD, 2).grist(IODINE, 3).grist(COBALT, 1).build(output);
		gristCost(GLOWING_MOSS_BLOCK).grist(MARBLE, 1).grist(COBALT, 1).build(output);
		gristCost(GLOWING_MOSS_CARPET).grist(MARBLE, 1).grist(COBALT, 1).build(output);
		gristCost(GLOWWORM_SILK).grist(MARBLE, 1).grist(COBALT, 1).build(output);
		gristCost(GLOWWORM_SILK_STRAND).grist(MARBLE, 1).grist(COBALT, 1).build(output);

		gristCost(LAVENDER).grist(IODINE, 1).grist(AMETHYST, 3).build(output);
		sourceGristCost(TALL_LAVENDER).multiplier(2).source(LAVENDER).build(output);
		gristCost(WHITE_LAVENDER).grist(IODINE, 1).grist(CHALK, 3).build(output);
		sourceGristCost(TALL_WHITE_LAVENDER).multiplier(2).source(WHITE_LAVENDER).build(output);
		gristCost(BLUE_HYDRANGEA).grist(IODINE, 2).grist(COBALT, 2).grist(CHALK, 2).build(output);
		gristCost(GOLDENROD).grist(IODINE, 2).grist(AMBER, 6).build(output);
		gristCost(ORANGE_COSMOS).grist(IODINE, 1).grist(AMBER, 2).grist(GARNET, 2).build(output);
		gristCost(PINK_DAFFODIL).grist(IODINE, 1).grist(GARNET, 2).grist(CHALK, 2).build(output);
		gristCost(WILDFLOWER).grist(IODINE, 1).grist(AMETHYST, 1).build(output);
		gristCost(WHITE_PETALS).grist(IODINE, 1).grist(CHALK, 1).build(output);
		gristCost(ICY_IRIS).grist(IODINE, 1).grist(COBALT, 1).grist(CHALK, 1).build(output);
		gristCost(VIOLET).grist(IODINE, 1).grist(AMETHYST, 1).build(output);
		sourceGristCost(ROSE).source(Items.POPPY).build(output);
		sourceGristCost(WILTED_LILY).grist(TAR, 1).source(Items.LILY_OF_THE_VALLEY).build(output);
		gristCost(BURNING_BLOSSOM).grist(IODINE, 1).grist(SULFUR, 3).build(output);
		gristCost(PINK_HIBISCUS).grist(IODINE, 1).grist(GARNET, 2).grist(CHALK, 2).build(output);
		gristCost(WATERLILY).grist(IODINE, 1).grist(GARNET, 2).grist(CHALK, 2).build(output);
		gristCost(ENDBLOOM).grist(IODINE, 1).grist(AMBER, 1).build(output);
		gristCost(GLOWFLOWER).grist(IODINE, 2).grist(MERCURY, 2).build(output);
		gristCost(LUMALOOP).grist(IODINE, 3).build(output);
		gristCost(LUMALOOP_PLANT).grist(IODINE, 3).build(output);
		gristCost(TINY_CACTUS).grist(IODINE, 1).grist(AMBER, 1).build(output);

		// Glitchy Items

		gristCost(NULL_PLANT).grist(BUILD, 1).build(output);
		gristCost(NULL_LEAVES).grist(BUILD, 1).build(output);
		gristCost(NULL_BLOCK).grist(BUILD, 2).build(output);
		gristCost(LIQUID_NULL).grist(BUILD, 8).build(output);
		sourceGristCost(LIQUID_NULL_BUCKET).grist(BUILD, 8).source(Items.BUCKET).build(output);
		gristCost(ANOMALY).grist(BUILD, 32).build(output);

		// Rose Quartz

		gristCost(ROSE_QUARTZ_CHUNK).grist(QUARTZ, 4).grist(RUBY, 4).build(output);
		gristCost(ROSE_QUARTZ_CLUSTER).grist(QUARTZ, 9).grist(RUBY, 9).build(output);
		gristCost(LARGE_ROSE_QUARTZ_BUD).grist(QUARTZ, 7).grist(RUBY, 7).build(output);
		gristCost(MEDIUM_ROSE_QUARTZ_BUD).grist(QUARTZ, 5).grist(RUBY, 5).build(output);
		gristCost(SMALL_ROSE_QUARTZ_BUD).grist(QUARTZ, 3).grist(RUBY, 3).build(output);

		// Potted Plants (why do these have items)

		sourceGristCost(POTTED_BURNING_BLOSSOM).source(BURNING_BLOSSOM).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_CYPRESS_SAPLING).source(CYPRESS_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_DEAD_SAPLING).source(DEAD_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_EMPYREAL_SAPLING).source(EMPYREAL_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_ENDBLOOM).source(ENDBLOOM).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_FIR_SAPLING).source(FIR_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_FLOWERING_OAK_SAPLING).source(FLOWERING_OAK_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_GLOWFLOWER).source(GLOWFLOWER).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_GLOWSHROOM).source(GLOWSHROOM).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_HELLBARK_SAPLING).source(HELLBARK_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_JACARANDA_SAPLING).source(JACARANDA_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_LAVENDER).source(LAVENDER).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_MAGIC_SAPLING).source(MAGIC_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_MAHOGANY_SAPLING).source(MAHOGANY_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_ORANGE_COSMOS).source(ORANGE_COSMOS).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_ORANGE_MAPLE_SAPLING).source(ORANGE_MAPLE_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_ORIGIN_SAPLING).source(ORIGIN_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_PALM_SAPLING).source(PALM_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_PINE_SAPLING).source(PINE_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_PINK_DAFFODIL).source(PINK_DAFFODIL).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_PINK_HIBISCUS).source(PINK_HIBISCUS).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_RAINBOW_BIRCH_SAPLING).source(RAINBOW_BIRCH_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_RED_MAPLE_SAPLING).source(RED_MAPLE_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_REDWOOD_SAPLING).source(REDWOOD_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_ROSE).source(ROSE).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_SNOWBLOSSOM_SAPLING).source(SNOWBLOSSOM_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_SPROUT).source(SPROUT).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_TINY_CACTUS).source(TINY_CACTUS).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_TOADSTOOL).source(TOADSTOOL).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_UMBRAN_SAPLING).source(UMBRAN_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_VIOLET).source(VIOLET).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_WHITE_LAVENDER).source(WHITE_LAVENDER).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_WILLOW_SAPLING).source(WILLOW_SAPLING).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_WILTED_LILY).source(WILTED_LILY).source(Items.FLOWER_POT).build(output);
		sourceGristCost(POTTED_YELLOW_MAPLE_SAPLING).source(YELLOW_MAPLE_SAPLING).source(Items.FLOWER_POT).build(output);

		gristCost(BOP_ICON).grist(BUILD, 1000).grist(ZILLIUM, 3).build(output);

		//
		// Combination Recipes
		//

		saplingCombinations(output, ORIGIN_SAPLING, Items.OAK_LOG, ORIGIN_LEAVES);
		saplingCombinations(output, FLOWERING_OAK_SAPLING, Items.OAK_LOG, FLOWERING_OAK_LEAVES);
		saplingCombinations(output, CYPRESS_SAPLING, Items.SPRUCE_LOG, CYPRESS_LEAVES);
		saplingCombinations(output, SNOWBLOSSOM_SAPLING, Items.CHERRY_LOG, SNOWBLOSSOM_LEAVES);
		saplingCombinations(output, RAINBOW_BIRCH_SAPLING, Items.BIRCH_LOG, RAINBOW_BIRCH_LEAVES);
		saplingCombinations(output, FIR_SAPLING, FIR_LOG, FIR_LEAVES);
		saplingCombinations(output, PINE_SAPLING, PINE_LOG, PINE_LEAVES);
		saplingCombinations(output, RED_MAPLE_SAPLING, MAPLE_LOG, RED_MAPLE_LEAVES);
		saplingCombinations(output, ORANGE_MAPLE_SAPLING, MAPLE_LOG, ORANGE_MAPLE_LEAVES);
		saplingCombinations(output, YELLOW_MAPLE_SAPLING, MAPLE_LOG, YELLOW_MAPLE_LEAVES);
		saplingCombinations(output, REDWOOD_SAPLING, REDWOOD_LOG, REDWOOD_LEAVES);
		saplingCombinations(output, MAHOGANY_SAPLING, MAHOGANY_LOG, MAHOGANY_LEAVES);
		saplingCombinations(output, JACARANDA_SAPLING, JACARANDA_LOG, JACARANDA_LEAVES);
		saplingCombinations(output, PALM_SAPLING, PALM_LOG, PALM_LEAVES);
		saplingCombinations(output, WILLOW_SAPLING, WILLOW_LOG, WILLOW_LEAVES);
		saplingCombinations(output, DEAD_SAPLING, DEAD_LOG, DEAD_LEAVES);
		saplingCombinations(output, MAGIC_SAPLING, MAGIC_LOG, MAGIC_LEAVES);
		saplingCombinations(output, UMBRAN_SAPLING, UMBRAN_LOG, UMBRAN_LEAVES);
		saplingCombinations(output, HELLBARK_SAPLING, HELLBARK_LOG, HELLBARK_LEAVES);
		saplingCombinations(output, EMPYREAL_SAPLING, EMPYREAL_LOG, EMPYREAL_LEAVES);

		woodCombinations(output, FIR_PLANKS, FIR_SLAB, FIR_STAIRS, FIR_DOOR, FIR_FENCE, FIR_FENCE_GATE, FIR_TRAPDOOR);
		woodCombinations(output, PINE_PLANKS, PINE_SLAB, PINE_STAIRS, PINE_DOOR, PINE_FENCE, PINE_FENCE_GATE, PINE_TRAPDOOR);
		woodCombinations(output, MAPLE_PLANKS, MAPLE_SLAB, MAPLE_STAIRS, MAPLE_DOOR, MAPLE_FENCE, MAPLE_FENCE_GATE, MAPLE_TRAPDOOR);
		woodCombinations(output, REDWOOD_PLANKS, REDWOOD_SLAB, REDWOOD_STAIRS, REDWOOD_DOOR, REDWOOD_FENCE, REDWOOD_FENCE_GATE, REDWOOD_TRAPDOOR);
		woodCombinations(output, MAHOGANY_PLANKS, MAHOGANY_SLAB, MAHOGANY_STAIRS, MAHOGANY_DOOR, MAHOGANY_FENCE, MAHOGANY_FENCE_GATE, MAHOGANY_TRAPDOOR);
		woodCombinations(output, JACARANDA_PLANKS, JACARANDA_SLAB, JACARANDA_STAIRS, JACARANDA_DOOR, JACARANDA_FENCE, JACARANDA_FENCE_GATE, JACARANDA_TRAPDOOR);
		woodCombinations(output, PALM_PLANKS, PALM_SLAB, PALM_STAIRS, PALM_DOOR, PALM_FENCE, PALM_FENCE_GATE, PALM_TRAPDOOR);
		woodCombinations(output, WILLOW_PLANKS, WILLOW_SLAB, WILLOW_STAIRS, WILLOW_DOOR, WILLOW_FENCE, WILLOW_FENCE_GATE, WILLOW_TRAPDOOR);
		woodCombinations(output, DEAD_PLANKS, DEAD_SLAB, DEAD_STAIRS, DEAD_DOOR, DEAD_FENCE, DEAD_FENCE_GATE, DEAD_TRAPDOOR);
		woodCombinations(output, MAGIC_PLANKS, MAGIC_SLAB, MAGIC_STAIRS, MAGIC_DOOR, MAGIC_FENCE, MAGIC_FENCE_GATE, MAGIC_TRAPDOOR);
		woodCombinations(output, UMBRAN_PLANKS, UMBRAN_SLAB, UMBRAN_STAIRS, UMBRAN_DOOR, UMBRAN_FENCE, UMBRAN_FENCE_GATE, UMBRAN_TRAPDOOR);
		woodCombinations(output, HELLBARK_PLANKS, HELLBARK_SLAB, HELLBARK_STAIRS, HELLBARK_DOOR, HELLBARK_FENCE, HELLBARK_FENCE_GATE, HELLBARK_TRAPDOOR);
		woodCombinations(output, EMPYREAL_PLANKS, EMPYREAL_SLAB, EMPYREAL_STAIRS, EMPYREAL_DOOR, EMPYREAL_FENCE, EMPYREAL_FENCE_GATE, EMPYREAL_TRAPDOOR);

		combination(ORIGIN_LEAVES).or().input(Items.OAK_LEAVES).input(Items.CLOCK).build(output);
		combination(FLOWERING_OAK_LEAVES).and().input(Items.OAK_LEAVES).input(ItemTags.FLOWERS).build(output);
		combination(CYPRESS_LEAVES).or().input(Items.SPRUCE_LEAVES).input(Items.TALL_GRASS).build(output);
		combination(SNOWBLOSSOM_LEAVES).and().input(Items.CHERRY_LEAVES).input(Items.SNOWBALL).build(output);
		combination(RAINBOW_BIRCH_LEAVES).and().input(Items.BIRCH_LEAVES).input(MSItems.BI_DYE).build(output);
		combination(FIR_LEAVES).or().input(CYPRESS_LEAVES).input(Items.TALL_GRASS).build(output);
		combination(PINE_LEAVES).or().input(ItemTags.LEAVES).input(Items.STICK).build(output);
		combination(RED_MAPLE_LEAVES).and().input(Items.SPRUCE_LEAVES).input(Items.RED_DYE).build(output);
		combination(ORANGE_MAPLE_LEAVES).and().input(Items.SPRUCE_LEAVES).input(Items.ORANGE_DYE).build(output);
		combination(YELLOW_MAPLE_LEAVES).and().input(Items.SPRUCE_LEAVES).input(Items.YELLOW_DYE).build(output);
		combination(REDWOOD_LEAVES).or().input(Items.BIRCH_LEAVES).input(Items.TALL_GRASS).build(output);
		combination(MAHOGANY_LEAVES).or().input(Items.JUNGLE_LEAVES).input(Items.MANGROVE_LEAVES).build(output);
		combination(JACARANDA_LEAVES).or().input(Items.CHERRY_LEAVES).input(LAVENDER).build(output);
		combination(PALM_LEAVES).or().input(Items.JUNGLE_LEAVES).input(Items.WATER_BUCKET).build(output);
		combination(WILLOW_LEAVES).or().input(Items.MANGROVE_LEAVES).input(Items.VINE).build(output);
		combination(DEAD_LEAVES).and().input(ItemTags.LEAVES).input(Items.DEAD_BUSH).build(output);
		combination(MAGIC_LEAVES).or().input(ItemTags.LEAVES).input(MSItems.EIGHTBALL).build(output);
		combination(UMBRAN_LEAVES).or().input(ItemTags.LEAVES).input(MSItems.GRIMOIRE).build(output);
		combination(HELLBARK_LEAVES).and().input(Items.MANGROVE_LEAVES).input(BRAMBLE).build(output);
		combination(EMPYREAL_LEAVES).or().input(Items.OAK_LEAVES).input(Items.QUARTZ).build(output);
		combination(BRAMBLE_LEAVES).and().input(Items.OAK_LEAVES).input(BRAMBLE).build(output);

		combination(ROSE).or().input(Items.POPPY).input(Items.CLOCK).build(output);
		combination(VIOLET).or().input(Items.SMALL_AMETHYST_BUD).input(Items.TORCHFLOWER).build(output);
		combination(LAVENDER).or().input(Items.FERN).input(Items.ALLIUM).build(output);
		combination(TALL_LAVENDER).or().input(LAVENDER).input(Items.TALL_GRASS).build(output);
		combination(WHITE_LAVENDER).and().input(LAVENDER).input(Items.WHITE_DYE).build(output);
		combination(TALL_WHITE_LAVENDER).and().namedInput(TALL_LAVENDER).input(Items.WHITE_DYE).build(output);
		combination(TALL_WHITE_LAVENDER).or().namedInput(WHITE_LAVENDER).input(Items.TALL_GRASS).build(output);
		combination(BLUE_HYDRANGEA).or().input(Items.PEONY).input(Items.BLUE_ORCHID).build(output);
		combination(GOLDENROD).and().input(Items.ROSE_BUSH).input(Items.YELLOW_DYE).build(output);
		combination(ORANGE_COSMOS).or().input(Items.OXEYE_DAISY).input(Items.YELLOW_GLAZED_TERRACOTTA).build(output);
		combination(PINK_DAFFODIL).and().input(Items.SPORE_BLOSSOM).input(Items.PINK_TULIP).build(output);
		combination(PINK_HIBISCUS).or().input(MSItems.GAMEGRL_MAGAZINE).input(Items.COOKIE).build(output);
		combination(WILDFLOWER).or().input(Items.PINK_PETALS).input(MSItems.BI_DYE).build(output);
		combination(WHITE_PETALS).or().input(Items.PINK_PETALS).input(Items.WHITE_DYE).build(output);
		combination(ICY_IRIS).or().input(Items.TALL_GRASS).input(Items.ICE).build(output);
		combination(GLOWFLOWER).or().input(Items.LILY_OF_THE_VALLEY).input(GLOWSHROOM).build(output);
		combination(WILTED_LILY).and().input(Items.LILY_OF_THE_VALLEY).input(Items.DEAD_BUSH).build(output);
		combination(BURNING_BLOSSOM).or().input(ORANGE_COSMOS).input(Items.FLINT_AND_STEEL).build(output);
		combination(ENDBLOOM).or().input(Items.SHORT_GRASS).input(Items.END_STONE).build(output);

		combination(SPROUT).and().input(Items.SHORT_GRASS).input(ItemTags.SAPLINGS).build(output);
		combination(BUSH).or().input(Items.SHORT_GRASS).input(Items.SWEET_BERRIES).build(output);
		combination(HIGH_GRASS).and().input(Items.TALL_GRASS).input(Items.LARGE_FERN).build(output);
		combination(CLOVER).and().input(Items.PINK_PETALS).input(Tags.Items.DYES_GREEN).build(output);
		combination(HUGE_CLOVER_PETAL).or().input(CLOVER).input(HIGH_GRASS).build(output);
		combination(HUGE_LILY_PAD).and().input(Items.LILY_PAD).input(HUGE_CLOVER_PETAL).build(output);
		combination(WATERLILY).or().input(Items.SPORE_BLOSSOM).input(Items.LILY_PAD).build(output);
		combination(DUNE_GRASS).or().input(Items.SHORT_GRASS).input(Items.SAND).build(output);
		combination(DESERT_GRASS).or().input(DUNE_GRASS).input(Items.SHEARS).build(output);
		combination(DEAD_GRASS).and().input(Items.DEAD_BUSH).input(Items.SHORT_GRASS).build(output);
		combination(TUNDRA_SHRUB).or().input(Items.DEAD_BUSH).input(Items.SNOWBALL).build(output);
		combination(ENDERPHYTE).or().input(Items.SHORT_GRASS).input(Items.END_STONE).build(output);
		combination(LUMALOOP).or().input(HIGH_GRASS).input(Items.END_STONE).build(output);
		combination(BARLEY).and().input(Items.WHEAT).input(Items.TALL_GRASS).build(output);
		combination(SEA_OATS).or().input(BARLEY).input(Items.SAND).build(output);
		combination(CATTAIL).or().input(BARLEY).input(Items.WATER_BUCKET).build(output);
		combination(REED).or().input(Items.SUGAR_CANE).input(Items.DEAD_BUSH).build(output);
		combination(WATERGRASS).and().input(Items.TALL_GRASS).input(Items.WATER_BUCKET).build(output);
		combination(TINY_CACTUS).or().input(Items.CACTUS).input(SPROUT).build(output);

		combination(BRAMBLE).or().input(Items.NETHER_WART).input(Items.ROSE_BUSH).build(output);
		combination(WHITE_SAND).and().input(Items.SAND).input(Tags.Items.DYES_WHITE).build(output);
		combination(ORANGE_SAND).and().input(Items.SAND).input(Tags.Items.DYES_ORANGE).build(output);
		combination(BLACK_SAND).and().input(Items.SAND).input(Tags.Items.DYES_BLACK).build(output);
		combination(THERMAL_CALCITE).or().input(Items.CALCITE).input(Items.FIRE_CHARGE).build(output);
		combination(DRIED_SALT).or().input(Items.MUD).input(Items.COARSE_DIRT).build(output);
		combination(EYEBULB).and().input(Items.TALL_GRASS).input(Items.ENDER_EYE).build(output);
		combination(HAIR).and().input(Items.SHORT_GRASS).input(Items.ROTTEN_FLESH).build(output);
		combination(BRIMSTONE).or().input(Items.DRIPSTONE_BLOCK).input(Items.HONEYCOMB).build(output);
		combination(ROSE_QUARTZ_BLOCK).or().input(Items.GLASS).input(Items.RED_DYE).build(output);
		combination(ROSE_QUARTZ_CLUSTER).or().input(Items.POINTED_DRIPSTONE).input(ROSE_QUARTZ_CHUNK).build(output);
		combination(ROSE_QUARTZ_CHUNK).and().input(Items.AMETHYST_SHARD).input(Tags.Items.DYES_RED).build(output);
		combination(WISPJELLY).or().input(Items.SLIME_BLOCK).input(Items.WIND_CHARGE).build(output);
		combination(TOADSTOOL).or().input(TOADSTOOL_BLOCK).input(Items.SHEARS).build(output);
		combination(TOADSTOOL_BLOCK).or().input(Tags.Items.MUSHROOMS).input(SMOOTH_ORANGE_SANDSTONE).build(output);
		combination(GLOWSHROOM).or().input(Items.BROWN_MUSHROOM).input(Items.GLOWSTONE_DUST).build(output);
		combination(GLOWING_MOSS_BLOCK).and().input(Items.GRASS_BLOCK).input(GLOWSHROOM).build(output);
		combination(GLOWWORM_SILK).and().input(GLOWING_MOSS_BLOCK).input(Items.STRING).build(output);
		combination(SPIDER_EGG).and().input(Items.COBWEB).input(Items.EGG).build(output);
		combination(HANGING_COBWEB).or().input(Items.COBWEB).input(Items.VINE).build(output);
		combination(WEBBING).and().input(Items.COBWEB).input(Items.GLOW_LICHEN).build(output);
		combination(ORIGIN_GRASS_BLOCK).or().input(Items.GRASS_BLOCK).input(Items.CLOCK).build(output);
		combination(DEAD_BRANCH).and().input(DEAD_LOG).input(Items.STICK).build(output);
		combination(NULL_LEAVES).and().input(ItemTags.LEAVES).input(NULL_BLOCK).build(output);
		combination(NULL_PLANT).and().input(Items.SHORT_GRASS).input(NULL_BLOCK).build(output);
		combination(LIQUID_NULL_BUCKET).and().input(NULL_BLOCK).input(Items.WATER_BUCKET).build(output);
		// This one's a joke about old unobtainable items (and how easy it was to typo and get a missing texture one).
		combination(NULL_BLOCK).and().input(Items.SMOOTH_STONE).input(Items.RED_MUSHROOM_BLOCK).build(output);
		combination(BLOOD_BUCKET).and().input(MSItems.BLOOD_BUCKET).input(Items.ROTTEN_FLESH).build(output);
		combination(POROUS_FLESH).and().input(FLESH).input(Items.MAGMA_BLOCK).build(output);
		combination(FLESH_TENDONS).and().input(FLESH).input(Items.VINE).build(output);

		// Miscellaneous Recipes

		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, new ItemStack(GLOWING_MOSS_CARPET, 3))
				.pattern("##").define('#', GLOWING_MOSS_BLOCK)
				.unlockedBy(getHasName(GLOWING_MOSS_BLOCK), has(GLOWING_MOSS_BLOCK))
				.save(output);
	}
}