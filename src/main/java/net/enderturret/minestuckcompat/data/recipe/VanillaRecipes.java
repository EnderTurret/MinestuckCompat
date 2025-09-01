package net.enderturret.minestuckcompat.data.recipe;

import static com.mraof.minestuck.api.alchemy.GristTypes.*;
import static net.minecraft.world.item.Items.*;

import java.util.concurrent.CompletableFuture;

import com.mraof.minestuck.api.alchemy.GristTypes;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import net.enderturret.minestuckcompat.MinestuckCompat;

public final class VanillaRecipes extends AbstractRecipeProvider {

	public VanillaRecipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		final RecipeOutput output = new RenamingRecipeOutput(_recipeOutput, MinestuckCompat.MOD_ID, "vanilla");

		gristCost(DEAD_HORN_CORAL_BLOCK).grist(BUILD, 8).build(output);
		gristCost(DEAD_HORN_CORAL).grist(BUILD, 6).build(output);
		gristCost(DEAD_HORN_CORAL_FAN).grist(BUILD, 5).build(output);
		gristCost(HORN_CORAL_BLOCK).grist(BUILD, 2).grist(AMBER, 4).grist(GARNET, 2).build(output);
		gristCost(HORN_CORAL).grist(AMBER, 4).grist(GARNET, 2).build(output);
		gristCost(HORN_CORAL_FAN).grist(AMBER, 4).grist(GARNET, 1).build(output);

		gristCost(MUSIC_DISC_CREATOR).grist(BUILD, 15).grist(RUST, 8).grist(AMBER, 5).grist(SULFUR, 5).build(output);
		gristCost(MUSIC_DISC_CREATOR_MUSIC_BOX).grist(BUILD, 15).grist(IODINE, 8).grist(AMBER, 5).grist(SULFUR, 5).build(output);
		gristCost(MUSIC_DISC_OTHERSIDE).grist(BUILD, 15).grist(GristTypes.QUARTZ, 8).grist(COBALT, 5).grist(URANIUM, 5).build(output);
		gristCost(MUSIC_DISC_PRECIPICE).grist(BUILD, 15).grist(MERCURY, 8).grist(RUST, 5).grist(GristTypes.QUARTZ, 5).build(output);

		// Regular anvils have 279 Rust, so we apply equal decay to calculate these.
		gristCost(CHIPPED_ANVIL).grist(RUST, 186).build(output);
		gristCost(DAMAGED_ANVIL).grist(RUST, 93).build(output);

		// Minestuck doesn't understand smithing recipes.
		// I'd add an interpreter for them but then it'd (ideally) take into account the smithing template,
		// which I think would inflate the costs significantly.
		// Also, most smithing recipes only modify the data components, meaning there are no costs to calculate.
		sourceGristCost(NETHERITE_AXE).source(DIAMOND_AXE).source(NETHERITE_INGOT).build(output);
		sourceGristCost(NETHERITE_HOE).source(DIAMOND_HOE).source(NETHERITE_INGOT).build(output);
		sourceGristCost(NETHERITE_PICKAXE).source(DIAMOND_PICKAXE).source(NETHERITE_INGOT).build(output);
		sourceGristCost(NETHERITE_SHOVEL).source(DIAMOND_SHOVEL).source(NETHERITE_INGOT).build(output);
		sourceGristCost(NETHERITE_SWORD).source(DIAMOND_SWORD).source(NETHERITE_INGOT).build(output);
		sourceGristCost(NETHERITE_HELMET).source(DIAMOND_HELMET).source(NETHERITE_INGOT).build(output);
		sourceGristCost(NETHERITE_CHESTPLATE).source(DIAMOND_CHESTPLATE).source(NETHERITE_INGOT).build(output);
		sourceGristCost(NETHERITE_LEGGINGS).source(DIAMOND_LEGGINGS).source(NETHERITE_INGOT).build(output);
		sourceGristCost(NETHERITE_BOOTS).source(DIAMOND_BOOTS).source(NETHERITE_INGOT).build(output);

		// I think Minecraft might use a 'special' recipe for these, which are opaque to Minestuck.
		sourceGristCost(BLACK_SHULKER_BOX).source(BLACK_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(BLUE_SHULKER_BOX).source(BLUE_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(BROWN_SHULKER_BOX).source(BROWN_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(CYAN_SHULKER_BOX).source(CYAN_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(GRAY_SHULKER_BOX).source(GRAY_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(GREEN_SHULKER_BOX).source(GREEN_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(LIGHT_BLUE_SHULKER_BOX).source(LIGHT_BLUE_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(LIGHT_GRAY_SHULKER_BOX).source(LIGHT_GRAY_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(LIME_SHULKER_BOX).source(LIME_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(MAGENTA_SHULKER_BOX).source(MAGENTA_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(ORANGE_SHULKER_BOX).source(ORANGE_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(PINK_SHULKER_BOX).source(PINK_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(PURPLE_SHULKER_BOX).source(PURPLE_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(RED_SHULKER_BOX).source(RED_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(WHITE_SHULKER_BOX).source(WHITE_DYE).source(SHULKER_BOX).build(output);
		sourceGristCost(YELLOW_SHULKER_BOX).source(YELLOW_DYE).source(SHULKER_BOX).build(output);

		// Ditto.
		// (We make these cost 5 diamonds instead of 7 just to make them a bit cheaper to obtain,
		//  since the high cost seems counterproductive for what is purely visual.)
		sourceGristCost(BOLT_ARMOR_TRIM_SMITHING_TEMPLATE).grist(SHALE, 81).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(COAST_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 2).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 4).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(EYE_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 4).grist(CAULK, 3).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(FLOW_ARMOR_TRIM_SMITHING_TEMPLATE).grist(GristTypes.DIAMOND, 50).grist(COBALT, 20).grist(MARBLE, 20).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(HOST_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 4).grist(SHALE, 4).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(RAISER_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 4).grist(SHALE, 4).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(RIB_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 1).grist(TAR, 1).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 2).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 4).grist(SHALE, 4).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 2).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 2).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE).grist(SHALE, 4).grist(AMETHYST, 2).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(TIDE_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 12).grist(COBALT, 4).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(VEX_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 2).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(WARD_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 2).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 4).grist(SHALE, 4).source(Items.DIAMOND).multiplier(5).build(output);
		sourceGristCost(WILD_ARMOR_TRIM_SMITHING_TEMPLATE).grist(BUILD, 2).source(Items.DIAMOND).multiplier(5).build(output);
		// This one isn't visual, but people end up needing infinite of them anyway.
		sourceGristCost(NETHERITE_UPGRADE_SMITHING_TEMPLATE).grist(BUILD, 1).grist(TAR, 1).source(Items.DIAMOND).multiplier(5).build(output);

		gristCost(ARMADILLO_SCUTE).grist(IODINE, 6).grist(SHALE, 5).grist(COBALT, 3).build(output);
		gristCost(BEE_NEST).grist(BUILD, 15).grist(AMBER, 6).grist(GOLD, 6).build(output);
		gristCost(GOAT_HORN).grist(CAULK, 5).grist(MERCURY, 10).build(output);
		gristCost(PIGLIN_HEAD).grist(CHALK, 18).grist(RUST, 6).grist(GOLD, 6).build(output);
		gristCost(BREEZE_ROD).grist(GristTypes.DIAMOND, 50).grist(COBALT, 20).grist(MARBLE, 20).build(output);
		gristCost(HEAVY_CORE).grist(BUILD, 96).grist(RUST, 144).build(output);
		gristCost(CARVED_PUMPKIN).grist(AMBER, 9).grist(CAULK, 4).build(output);
		sourceGristCost(ItemTags.DECORATED_POT_SHERDS).source(BRICK).multiplier(4).build(output);
		gristCost(FLOW_BANNER_PATTERN).grist(BUILD, 120).grist(MARBLE, 17).grist(GristTypes.DIAMOND, 13).build(output);
		gristCost(GUSTER_BANNER_PATTERN).grist(BUILD, 120).grist(MARBLE, 13).grist(GristTypes.DIAMOND, 17).build(output);
	}
}