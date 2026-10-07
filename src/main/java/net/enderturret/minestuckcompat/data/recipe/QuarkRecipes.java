package net.enderturret.minestuckcompat.data.recipe;

import static com.mraof.minestuck.api.alchemy.GristTypes.*;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.mraof.minestuck.block.AspectTreeBlocks;
import com.mraof.minestuck.item.MSItems;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.enderturret.minestuckcompat.ConfigCondition;
import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.data.util.AbstractRecipeProvider;
import net.enderturret.minestuckcompat.data.util.RenamingRecipeOutput;

import appeng.core.definitions.AEParts;

@Internal
public final class QuarkRecipes extends AbstractRecipeProvider {

	public QuarkRecipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		final RecipeOutput output = new RenamingRecipeOutput(_recipeOutput, MinestuckCompat.MOD_ID, "quark")
				.withConditions(new ConfigCondition("quark"));

		// Corundum

		gristCost(q("red_corundum")).grist(GARNET, 4).grist(AMETHYST, 12).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("red_corundum_cluster")).grist(GARNET, 3).grist(AMETHYST, 9).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("orange_corundum")).grist(IODINE, 4).grist(AMETHYST, 12).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("orange_corundum_cluster")).grist(IODINE, 3).grist(AMETHYST, 9).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("yellow_corundum")).grist(SULFUR, 4).grist(AMETHYST, 12).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("yellow_corundum_cluster")).grist(SULFUR, 3).grist(AMETHYST, 9).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("green_corundum")).grist(URANIUM, 4).grist(AMETHYST, 12).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("green_corundum_cluster")).grist(URANIUM, 3).grist(AMETHYST, 9).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("blue_corundum")).grist(BUILD, 4).grist(AMETHYST, 12).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("blue_corundum_cluster")).grist(BUILD, 3).grist(AMETHYST, 9).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("indigo_corundum")).grist(COBALT, 4).grist(AMETHYST, 12).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("indigo_corundum_cluster")).grist(COBALT, 3).grist(AMETHYST, 9).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("violet_corundum")).grist(MARBLE, 4).grist(AMETHYST, 12).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("violet_corundum_cluster")).grist(MARBLE, 3).grist(AMETHYST, 9).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("white_corundum")).grist(CHALK, 4).grist(AMETHYST, 12).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("white_corundum_cluster")).grist(CHALK, 3).grist(AMETHYST, 9).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("black_corundum")).grist(TAR, 4).grist(AMETHYST, 12).build(output.withConditions(itemExists("quark", "red_corundum")));
		gristCost(q("black_corundum_cluster")).grist(TAR, 3).grist(AMETHYST, 9).build(output.withConditions(itemExists("quark", "red_corundum")));

		// Glass shards
		// So we have two options for these:
		// a) make them all 1/4 their corresponding glass (which in practice makes them equal to the blocks in terms of grist)
		// b) make them all cost 1 build (which loses the dye component)
		// Neither of these are great options. Either you can easily duplicate grist (block->shard) or you can easily obtain colored glass blocks (shard->block).
		// Let's go with option b) as the lesser evil.

		gristCost(tag("quark", "shards")).grist(BUILD, 1).build(output.withConditions(itemExists("quark", "white_shard")));

		gristCost(q("dirty_glass")).grist(BUILD, 1).grist(RUST, 1).build(output.withConditions(itemExists("quark", "dirty_glass")));

		// Stone types

		gristCost(q("jasper")).grist(BUILD, 2).grist(RUST, 1).build(output.withConditions(itemExists("quark", "jasper")));
		gristCost(q("limestone")).grist(BUILD, 2).grist(CHALK, 1).build(output.withConditions(itemExists("quark", "limestone")));
		gristCost(q("shale")).grist(BUILD, 2).grist(SHALE, 1).build(output.withConditions(itemExists("quark", "shale")));
		gristCost(q("permafrost")).grist(BUILD, 2).grist(COBALT, 2).build(output.withConditions(itemExists("quark", "permafrost"))); // Is permafrost even a stone?

		gristCost(q("myalite")).grist(BUILD, 4).grist(SHALE, 3).build(output.withConditions(itemExists("quark", "myalite"))); // Based on the cost for End Stone.
		gristCost(q("dusky_myalite")).grist(BUILD, 4).grist(SHALE, 3).grist(TAR, 2).build(output.withConditions(itemExists("quark", "dusky_myalite")));
		gristCost(q("myalite_crystal")).grist(SHALE, 3).grist(AMETHYST, 9).build(output.withConditions(itemExists("quark", "myalite_crystal"))); // Based on the cost for Amethyst Blocks.

		// Parrot eggs

		gristCost(q("egg_parrot_blue")).grist(AMBER, 5).build(output.withConditions(itemExists("quark", "egg_parrot_blue")));
		gristCost(q("egg_parrot_gray")).grist(AMBER, 5).build(output.withConditions(itemExists("quark", "egg_parrot_gray")));
		gristCost(q("egg_parrot_green")).grist(AMBER, 5).build(output.withConditions(itemExists("quark", "egg_parrot_green")));
		gristCost(q("egg_parrot_red_blue")).grist(AMBER, 5).build(output.withConditions(itemExists("quark", "egg_parrot_red_blue")));
		gristCost(q("egg_parrot_yellow_blue")).grist(AMBER, 5).build(output.withConditions(itemExists("quark", "egg_parrot_yellow_blue")));

		// Plants

		gristCost(q("glow_lichen_growth")).grist(SULFUR, 5).build(output.withConditions(itemExists("quark", "glow_lichen_growth")));
		gristCost(q("chorus_twist")).grist(SHALE, 2).grist(AMETHYST, 1).grist(CAULK, 1).build(output.withConditions(itemExists("quark", "chorus_twist")));
		gristCost(q("chorus_weeds")).grist(SHALE, 2).grist(AMETHYST, 1).grist(CAULK, 1).build(output.withConditions(itemExists("quark", "chorus_weeds")));
		gristCost(q("ancient_fruit")).grist(AMBER, 7).grist(IODINE, 2).build(output.withConditions(itemExists("quark", "ancient_fruit")));
		gristCost(q("glow_shroom")).grist(IODINE, 3).grist(COBALT, 1).build(output.withConditions(itemExists("quark", "glow_shroom")));
		gristCost(q("glow_shroom_block")).grist(BUILD, 2).grist(IODINE, 3).grist(COBALT, 1).build(output.withConditions(itemExists("quark", "glow_shroom")));
		gristCost(q("glow_shroom_stem")).grist(BUILD, 4).grist(IODINE, 3).grist(COBALT, 1).build(output.withConditions(itemExists("quark", "glow_shroom")));
		gristCost(q("glow_shroom_ring")).grist(IODINE, 2).grist(COBALT, 1).build(output.withConditions(itemExists("quark", "glow_shroom")));

		// Ambience Discs

		// Format: 15 build, 8 record grist, 5 center grist A, 5 center grist B
		gristCost(q("music_disc_drips")).grist(BUILD, 15).grist(QUARTZ, 8).grist(COBALT, 5).grist(DIAMOND, 5).build(output.withConditions(itemExists("quark", "music_disc_drips")));
		gristCost(q("music_disc_ocean")).grist(BUILD, 15).grist(QUARTZ, 8).grist(COBALT, 5).grist(CAULK, 5).build(output.withConditions(itemExists("quark", "music_disc_drips")));
		gristCost(q("music_disc_rain")).grist(BUILD, 15).grist(QUARTZ, 8).grist(COBALT, 5).grist(CHALK, 5).build(output.withConditions(itemExists("quark", "music_disc_drips")));
		gristCost(q("music_disc_wind")).grist(BUILD, 15).grist(QUARTZ, 8).grist(AMBER, 5).grist(SULFUR, 5).build(output.withConditions(itemExists("quark", "music_disc_drips")));
		gristCost(q("music_disc_fire")).grist(BUILD, 15).grist(QUARTZ, 8).grist(RUST, 5).grist(GARNET, 5).build(output.withConditions(itemExists("quark", "music_disc_drips")));
		gristCost(q("music_disc_clock")).grist(BUILD, 15).grist(QUARTZ, 8).grist(SULFUR, 5).grist(GOLD, 5).build(output.withConditions(itemExists("quark", "music_disc_drips")));
		gristCost(q("music_disc_crickets")).grist(BUILD, 15).grist(QUARTZ, 8).grist(AMBER, 5).grist(URANIUM, 5).build(output.withConditions(itemExists("quark", "music_disc_drips")));
		gristCost(q("music_disc_chatter")).grist(BUILD, 15).grist(QUARTZ, 8).grist(SHALE, 5).grist(AMETHYST, 5).build(output.withConditions(itemExists("quark", "music_disc_drips")));

		// Miscellaneous

		sourceGristCost(q("framed_glass")).multiplier(4).source(Items.GLASS).source(Items.IRON_INGOT).build(output.withConditions(itemExists("quark", "framed_glass")));
		gristCost(q("diamond_heart")).grist(DIAMOND, 18).grist(BUILD, 4).grist(QUARTZ, 4).build(output.withConditions(itemExists("quark", "diamond_heart"))); // A diamond, two stone, and some quartz to make the stone cost more exciting.
		gristCost(q("dragon_scale")).grist(TAR, 50).grist(URANIUM, 50).grist(ZILLIUM, 1).build(output.withConditions(itemExists("quark", "dragon_scale")));
		gristCost(q("music_disc_endermosh")).grist(BUILD, 15).grist(SHALE, 8).grist(GARNET, 5).grist(RUBY, 5).build(output.withConditions(itemExists("quark", "music_disc_endermosh")));
		gristCost(q("crab_leg")).grist(IODINE, 10).build(output.withConditions(itemExists("quark", "crab_leg")));
		gristCost(q("crab_shell")).grist(IODINE, 8).grist(SHALE, 9).grist(COBALT, 9).build(output.withConditions(itemExists("quark", "crab_shell")));
		sourceGristCost(q("bottled_cloud")).grist(CHALK, 8).source(Items.GLASS_BOTTLE).build(output.withConditions(itemExists("quark", "bottled_cloud")));
		gristCost(q("ravager_hide")).grist(IODINE, 6).grist(TAR, 6).grist(MERCURY, 3).build(output.withConditions(itemExists("quark", "ravager_hide")));
		gristCost(q("soul_bead")).grist(TAR, 12).grist(COBALT, 7).grist(DIAMOND, 2).build(output.withConditions(itemExists("quark", "soul_bead")));
		gristCost(q("smithing_template_rune")).grist(BUILD, 4).grist(CAULK, 4 * 7).grist(AMETHYST, 12 * 7).build(output.withConditions(itemExists("quark", "smithing_template_rune")));
		gristCost(q("forgotten_hat")).grist(IODINE, 15).grist(CHALK, 9).build(output.withConditions(itemExists("quark", "forgotten_hat")));

		//
		// Combination Recipes
		//

		saplingCombinations(output.withConditions(itemExists("quark", "ancient_sapling")), q("ancient_sapling"), q("ancient_log"), q("ancient_leaves"));
		saplingCombinations(output.withConditions(itemExists("quark", "blue_blossom_sapling")), q("blue_blossom_sapling"), null, q("blue_blossom_leaves"), false);
		saplingCombinations(output.withConditions(itemExists("quark", "lavender_blossom_sapling")), q("lavender_blossom_sapling"), null, q("lavender_blossom_leaves"), false);
		saplingCombinations(output.withConditions(itemExists("quark", "orange_blossom_sapling")), q("orange_blossom_sapling"), null, q("orange_blossom_leaves"), false);
		saplingCombinations(output.withConditions(itemExists("quark", "yellow_blossom_sapling")), q("yellow_blossom_sapling"), null, q("yellow_blossom_leaves"), false);
		saplingCombinations(output.withConditions(itemExists("quark", "red_blossom_sapling")), q("red_blossom_sapling"), null, q("red_blossom_leaves"), false);

		woodCombinations(output.withConditions(itemExists("quark", "ancient_planks")), q("ancient_planks"), q("ancient_planks_slab"), q("ancient_planks_stairs"), q("ancient_door"), q("ancient_fence"), q("ancient_fence_gate"), q("ancient_trapdoor"));
		woodCombinations(output.withConditions(itemExists("quark", "azalea_planks")), q("azalea_planks"), q("azalea_planks_slab"), q("azalea_planks_stairs"), q("azalea_door"), q("azalea_fence"), q("azalea_fence_gate"), q("azalea_trapdoor"));
		woodCombinations(output.withConditions(itemExists("quark", "blossom_planks")), q("blossom_planks"), q("blossom_planks_slab"), q("blossom_planks_stairs"), q("blossom_door"), q("blossom_fence"), q("blossom_fence_gate"), q("blossom_trapdoor"));

		// Saplings:
		combination(q("blue_blossom_sapling")).or().input(Items.DARK_OAK_SAPLING).input(Items.SNOWBALL).build(output.withConditions(itemExists("quark", "blue_blossom_sapling")));
		combination(q("lavender_blossom_sapling")).or().input(Items.DARK_OAK_SAPLING).input(Items.LILY_PAD).build(output.withConditions(itemExists("quark", "lavender_blossom_sapling")));
		combination(q("orange_blossom_sapling")).or().input(Items.DARK_OAK_SAPLING).input(Items.ACACIA_SAPLING).build(output.withConditions(itemExists("quark", "orange_blossom_sapling")));
		combination(q("yellow_blossom_sapling")).or().input(Items.DARK_OAK_SAPLING).input(Items.OAK_SAPLING).build(output.withConditions(itemExists("quark", "yellow_blossom_sapling")));
		combination(q("red_blossom_sapling")).or().input(Items.DARK_OAK_SAPLING).input(Items.RED_TERRACOTTA).build(output.withConditions(itemExists("quark", "red_blossom_sapling")));

		// Corundum:
		combination(q("red_corundum")).and().input(Items.AMETHYST_BLOCK).input(Items.RED_STAINED_GLASS).build(output.withConditions(itemExists("quark", "red_corundum")));
		combination(q("orange_corundum")).and().input(Items.AMETHYST_BLOCK).input(Items.ORANGE_STAINED_GLASS).build(output.withConditions(itemExists("quark", "orange_corundum")));
		combination(q("yellow_corundum")).and().input(Items.AMETHYST_BLOCK).input(Items.YELLOW_STAINED_GLASS).build(output.withConditions(itemExists("quark", "yellow_corundum")));
		combination(q("green_corundum")).and().input(Items.AMETHYST_BLOCK).input(Items.LIME_STAINED_GLASS).build(output.withConditions(itemExists("quark", "green_corundum")));
		combination(q("blue_corundum")).and().input(Items.AMETHYST_BLOCK).input(Items.LIGHT_BLUE_STAINED_GLASS).build(output.withConditions(itemExists("quark", "blue_corundum")));
		combination(q("indigo_corundum")).and().input(Items.AMETHYST_BLOCK).input(Items.BLUE_STAINED_GLASS).build(output.withConditions(itemExists("quark", "indigo_corundum")));
		combination(q("violet_corundum")).and().input(Items.AMETHYST_BLOCK).input(Items.MAGENTA_STAINED_GLASS).build(output.withConditions(itemExists("quark", "violet_corundum")));
		combination(q("white_corundum")).and().input(Items.AMETHYST_BLOCK).input(Items.WHITE_STAINED_GLASS).build(output.withConditions(itemExists("quark", "white_corundum")));
		combination(q("black_corundum")).and().input(Items.AMETHYST_BLOCK).input(Items.GRAY_STAINED_GLASS).build(output.withConditions(itemExists("quark", "black_corundum")));

		combination(q("red_corundum_cluster")).and().input(Items.AMETHYST_CLUSTER).input(Items.RED_STAINED_GLASS).build(output.withConditions(itemExists("quark", "red_corundum")));
		combination(q("orange_corundum_cluster")).and().input(Items.AMETHYST_CLUSTER).input(Items.ORANGE_STAINED_GLASS).build(output.withConditions(itemExists("quark", "orange_corundum")));
		combination(q("yellow_corundum_cluster")).and().input(Items.AMETHYST_CLUSTER).input(Items.YELLOW_STAINED_GLASS).build(output.withConditions(itemExists("quark", "yellow_corundum")));
		combination(q("green_corundum_cluster")).and().input(Items.AMETHYST_CLUSTER).input(Items.LIME_STAINED_GLASS).build(output.withConditions(itemExists("quark", "green_corundum")));
		combination(q("blue_corundum_cluster")).and().input(Items.AMETHYST_CLUSTER).input(Items.LIGHT_BLUE_STAINED_GLASS).build(output.withConditions(itemExists("quark", "blue_corundum")));
		combination(q("indigo_corundum_cluster")).and().input(Items.AMETHYST_CLUSTER).input(Items.BLUE_STAINED_GLASS).build(output.withConditions(itemExists("quark", "indigo_corundum")));
		combination(q("violet_corundum_cluster")).and().input(Items.AMETHYST_CLUSTER).input(Items.MAGENTA_STAINED_GLASS).build(output.withConditions(itemExists("quark", "violet_corundum")));
		combination(q("white_corundum_cluster")).and().input(Items.AMETHYST_CLUSTER).input(Items.WHITE_STAINED_GLASS).build(output.withConditions(itemExists("quark", "white_corundum")));
		combination(q("black_corundum_cluster")).and().input(Items.AMETHYST_CLUSTER).input(Items.GRAY_STAINED_GLASS).build(output.withConditions(itemExists("quark", "black_corundum")));

		// Stones

		combination(q("jasper")).and().input(Items.QUARTZ_BLOCK).input(Items.RAW_IRON).build(output.withConditions(itemExists("quark", "jasper")));
		combination(q("limestone")).and().input(Items.STONE).input(Items.MANGROVE_LOG).build(output.withConditions(itemExists("quark", "limestone")));
		combination(q("shale")).and().input(Items.STONE).input(Items.CYAN_TERRACOTTA).build(output.withConditions(itemExists("quark", "shale")));
		combination(q("permafrost")).and().input(Items.STONE).input(Items.BLUE_ICE).build(output.withConditions(itemExists("quark", "permafrost")));
		combination(q("myalite")).or().input(Items.STONE).input(Items.CHORUS_FRUIT).build(output.withConditions(itemExists("quark", "myalite")));
		combination(q("myalite_crystal")).or().input(q("myalite")).namedInput(Items.ENDER_PEARL).build(output.withConditions(itemExists("quark", "myalite")));
		combination(q("myalite_crystal")).and().input(q("myalite")).namedInput(Items.GLASS).build(output.withConditions(itemExists("quark", "myalite")));
		combination(q("dusky_myalite")).or().input(q("myalite")).input(Items.INK_SAC).build(output.withConditions(itemExists("quark", "myalite")));

		// There's no regular cat plush, so this will have to do.
		combination(q("jasper")).or().input(Items.STONE).namedInput(MSItems.PLUSH_MUTATED_CAT).build(output.withConditions(itemExists("quark", "jasper")));
		combination(q("limestone")).or().input(Items.STONE).namedInput(Items.LIME_DYE).build(output.withConditions(itemExists("quark", "limestone")));
		combination(q("dusky_myalite")).and().input(q("myalite")).namedInput(Items.CLOCK).build(output.withConditions(itemExists("quark", "myalite")));

		// Music Discs
		combination(q("music_disc_drips")).or().input(Items.MUSIC_DISC_CAT).input(Items.POTION).build(output.withConditions(itemExists("quark", "music_disc_drips")));
		combination(q("music_disc_ocean")).or().input(Items.MUSIC_DISC_CAT).input(Items.WATER_BUCKET).build(output.withConditions(itemExists("quark", "music_disc_ocean")));
		combination(q("music_disc_rain")).or().input(Items.MUSIC_DISC_CAT).input(Items.POINTED_DRIPSTONE).build(output.withConditions(itemExists("quark", "music_disc_drips")));
		combination(q("music_disc_wind")).or().input(Items.MUSIC_DISC_CAT).input(Items.WIND_CHARGE).build(output.withConditions(itemExists("quark", "music_disc_drips")));
		combination(q("music_disc_fire")).or().input(Items.MUSIC_DISC_CAT).input(Items.FIRE_CHARGE).build(output.withConditions(itemExists("quark", "music_disc_drips")));
		combination(q("music_disc_clock")).or().input(Items.MUSIC_DISC_CAT).input(Items.CLOCK).build(output.withConditions(itemExists("quark", "music_disc_drips")));
		combination(q("music_disc_crickets")).or().input(Items.MUSIC_DISC_CAT).input(MSItems.GRASSHOPPER).build(output.withConditions(itemExists("quark", "music_disc_drips")));
		combination(q("music_disc_chatter")).or().input(Items.MUSIC_DISC_CAT).input(Items.NOTE_BLOCK).build(output.withConditions(itemExists("quark", "music_disc_drips")));

		// Miscellaneous

		combination(q("pickarang")).and().input(MSItems.SHURIKEN).input(Items.DIAMOND_PICKAXE).build(output.withConditions(itemExists("quark", "pickarang")));
		combination(q("tiny_potato")).or().input(Items.POTATO).namedInput(MSItems.WAND).build(output.withConditions(itemExists("quark", "tiny_potato")));
		combination(q("tiny_potato")).or().input(Items.POTATO).namedInput(AspectTreeBlocks.HOPE_ASPECT_SAPLING).build(output.withConditions(itemExists("quark", "tiny_potato")));
		combination(q("crab_leg")).or().input(Items.BEEF).input(MSItems.CLAW_SICKLE).build(output.withConditions(itemExists("quark", "crab_leg")));
		combination(q("crab_shell")).or().input(q("crab_leg")).input(Items.TURTLE_SCUTE).build(output.withConditions(itemExists("quark", "crab_shell")));
		combination(q("glow_lichen_growth")).and().input(Items.GLOW_LICHEN).input(Items.SHORT_GRASS).build(output.withConditions(itemExists("quark", "glow_lichen_growth")));
		combination(q("chorus_weeds")).and().input(Items.CHORUS_FRUIT).input(Items.SHORT_GRASS).build(output.withConditions(itemExists("quark", "chorus_weeds")));
		combination(q("chorus_twist")).and().input(q("chorus_weeds")).input(Items.NAUTILUS_SHELL).build(output.withConditions(itemExists("quark", "chorus_twist")));
		combination(q("glow_shroom")).or().input(MSItems.GLOWING_MUSHROOM).input(Items.DEEPSLATE).build(output.withConditions(itemExists("quark", "glow_shroom")));
	}

	private static Item q(String id) {
		return lookup("quark", id);
	}
}