package net.enderturret.minestuckcompat.data.recipe;

import static com.mraof.minestuck.api.alchemy.GristTypes.*;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.ApiStatus.Internal;

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
	}

	private static Item q(String id) {
		return lookup("quark", id);
	}
}