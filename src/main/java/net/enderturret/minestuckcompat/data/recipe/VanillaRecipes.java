package net.enderturret.minestuckcompat.data.recipe;

import static com.mraof.minestuck.api.alchemy.GristTypes.*;
import static net.minecraft.world.item.Items.*;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.mraof.minestuck.api.alchemy.GristTypes;
import com.mraof.minestuck.item.MSItems;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import net.enderturret.minestuckcompat.ConfigCondition;
import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.data.util.AbstractRecipeProvider;
import net.enderturret.minestuckcompat.data.util.RenamingRecipeOutput;

@Internal
public final class VanillaRecipes extends AbstractRecipeProvider {

	public VanillaRecipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		final RecipeOutput output = new RenamingRecipeOutput(_recipeOutput, MinestuckCompat.MOD_ID, "vanilla")
				.withConditions(new ConfigCondition("minecraft"));

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
		gristCost(TRIAL_KEY).grist(SHALE, 9).grist(TAR, 48).build(output);
		sourceGristCost(OMINOUS_BOTTLE).grist(TAR, 16).source(GLASS_BOTTLE).build(output);
		sourceGristCost(OMINOUS_TRIAL_KEY).source(OMINOUS_BOTTLE).source(TRIAL_KEY).build(output);

		//
		// Combination Recipes
		//

		// Deepslate Ores

		combination(DEEPSLATE_COAL_ORE).and().input(Items.COAL).input(Items.DEEPSLATE).build(output);
		combination(DEEPSLATE_IRON_ORE).and().input(Items.IRON_INGOT).input(Items.DEEPSLATE).build(output);
		combination(DEEPSLATE_COPPER_ORE).and().input(Items.COPPER_INGOT).input(Items.DEEPSLATE).build(output);
		combination(DEEPSLATE_GOLD_ORE).and().input(Items.GOLD_INGOT).input(Items.DEEPSLATE).build(output);
		combination(DEEPSLATE_REDSTONE_ORE).and().input(Items.REDSTONE).input(Items.DEEPSLATE).build(output);
		combination(DEEPSLATE_EMERALD_ORE).and().input(Items.EMERALD).input(Items.DEEPSLATE).build(output);
		combination(DEEPSLATE_LAPIS_ORE).and().input(Items.LAPIS_LAZULI).input(Items.DEEPSLATE).build(output);
		combination(DEEPSLATE_DIAMOND_ORE).and().input(Items.DIAMOND).input(Items.DEEPSLATE).build(output);

		// Sherds

		combination(ANGLER_POTTERY_SHERD).or().input(TERRACOTTA).input(FISHING_ROD).build(output);
		combination(ARCHER_POTTERY_SHERD).or().input(TERRACOTTA).input(BOW).build(output);
		combination(ARMS_UP_POTTERY_SHERD).or().input(TERRACOTTA).input(ARMOR_STAND).build(output);
		combination(BLADE_POTTERY_SHERD).or().input(TERRACOTTA).input(ItemTags.SWORDS).build(output);
		combination(BREWER_POTTERY_SHERD).or().input(TERRACOTTA).input(POTION).build(output);
		combination(BURN_POTTERY_SHERD).or().input(TERRACOTTA).input(FLINT_AND_STEEL).build(output);
		combination(DANGER_POTTERY_SHERD).or().input(TERRACOTTA).input(GUNPOWDER).build(output);
		combination(FLOW_POTTERY_SHERD).or().input(TERRACOTTA).input(FLOW_BANNER_PATTERN).build(output);
		combination(EXPLORER_POTTERY_SHERD).or().input(TERRACOTTA).input(MAP).build(output);
		// I think this one depicts an iron golem?
		combination(FRIEND_POTTERY_SHERD).or().input(TERRACOTTA).input(IRON_BLOCK).build(output);
		combination(GUSTER_POTTERY_SHERD).or().input(TERRACOTTA).input(GUSTER_BANNER_PATTERN).build(output);
		combination(HEART_POTTERY_SHERD).or().input(TERRACOTTA).input(BEETROOT).build(output);
		combination(HEARTBREAK_POTTERY_SHERD).or().input(HEART_POTTERY_SHERD).input(ItemTags.SWORDS).build(output);
		combination(HOWL_POTTERY_SHERD).or().input(TERRACOTTA).input(BONE).build(output);
		combination(MINER_POTTERY_SHERD).or().input(TERRACOTTA).input(ItemTags.PICKAXES).build(output);
		combination(MOURNER_POTTERY_SHERD).or().input(TERRACOTTA).input(SCULK_CATALYST).build(output);
		combination(PLENTY_POTTERY_SHERD).or().input(TERRACOTTA).input(CHEST).build(output);
		combination(PRIZE_POTTERY_SHERD).or().input(TERRACOTTA).input(Items.DIAMOND).build(output);
		combination(SCRAPE_POTTERY_SHERD).or().input(TERRACOTTA).input(ItemTags.AXES).build(output);
		combination(SHEAF_POTTERY_SHERD).or().input(TERRACOTTA).input(WHEAT).build(output);
		combination(SHELTER_POTTERY_SHERD).or().input(TERRACOTTA).input(ItemTags.SAPLINGS).build(output);
		combination(SKULL_POTTERY_SHERD).or().input(TERRACOTTA).input(WITHER_SKELETON_SKULL).build(output);
		combination(SNORT_POTTERY_SHERD).or().input(TERRACOTTA).input(SNIFFER_EGG).build(output);

		// Trims

		combination(SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(COBBLESTONE).input(TRIPWIRE_HOOK).build(output);
		combination(VEX_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(COBBLESTONE).input(TOTEM_OF_UNDYING).build(output);
		combination(WILD_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(MOSSY_COBBLESTONE).input(MANGROVE_PROPAGULE).build(output);
		combination(COAST_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(COBBLESTONE).input(WATER_BUCKET).build(output);
		combination(DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(SANDSTONE).input(SAND).build(output);
		combination(WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(TERRACOTTA).input(LEATHER_BOOTS).build(output);
		combination(RAISER_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(TERRACOTTA).input(CARROT).build(output);
		combination(SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(TERRACOTTA).input(CRAFTING_TABLE).build(output);
		// I have absolutely no idea what to do with this one.
		combination(HOST_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(TERRACOTTA).input(ItemTags.SIGNS).build(output);
		combination(WARD_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(COBBLED_DEEPSLATE).input(SCULK_CATALYST).build(output);
		combination(SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(COBBLED_DEEPSLATE).input(SCULK_SENSOR).build(output);
		combination(TIDE_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(PRISMARINE).input(HEART_OF_THE_SEA).build(output);
		combination(SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(BLACKSTONE).input(PIGLIN_BANNER_PATTERN).build(output);
		combination(RIB_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(NETHERRACK).input(WITHER_SKELETON_SKULL).build(output);
		combination(EYE_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(END_STONE).input(ENDER_EYE).build(output);
		combination(SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(PURPUR_BLOCK).input(END_ROD).build(output);
		combination(FLOW_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(BREEZE_ROD).input(WIND_CHARGE).build(output);
		combination(BOLT_ARMOR_TRIM_SMITHING_TEMPLATE).or().input(COPPER_BLOCK).input(LIGHTNING_ROD).build(output);

		// Banner Patterns

		combination(GLOBE_BANNER_PATTERN).and().input(PAPER).input(DIRT).build(output);
		combination(PIGLIN_BANNER_PATTERN).and().input(PAPER).input(PIGLIN_HEAD).build(output);
		combination(FLOW_BANNER_PATTERN).and().input(PAPER).input(WIND_CHARGE).build(output);
		combination(GUSTER_BANNER_PATTERN).and().input(PAPER).input(BREEZE_ROD).build(output);

		// Miscellaneous

		combination(PIGLIN_HEAD).or().input(ZOMBIE_HEAD).input(CARROT).build(output);
		combination(SNIFFER_EGG).or().input(EGG).input(CLOCK).build(output);
		combination(WIND_CHARGE).or().input(FIRE_CHARGE).input(MSItems.FAN).build(output);
		combination(OMINOUS_TRIAL_KEY).or().input(TRIAL_KEY).input(OMINOUS_BOTTLE).build(output);
		combination(OMINOUS_BOTTLE).or().input(Items.GLASS_BOTTLE).input(Items.CROSSBOW).build(output);
		combination(TRIAL_KEY).or().input(Items.COPPER_INGOT).input(Items.WITHER_SKELETON_SKULL).build(output);

		//
		// Combinations for Minecraft content that's effectively unobtainable if you start in the Medium.
		//

		// Plants
		combination(SHORT_GRASS).or().namedInput(GRASS_BLOCK).input(ItemTags.SAPLINGS).build(output);
		combination(SHORT_GRASS).or().namedInput(MSItems.PETRIFIED_GRASS).input(WATER_BUCKET).build(output);
		combination(TALL_GRASS).or().input(SHORT_GRASS).input(BONE_MEAL).build(output);
		combination(FERN).or().input(SHORT_GRASS).input(PODZOL).build(output);
		combination(LARGE_FERN).or().namedInput(FERN).input(BONE_MEAL).build(output);
		combination(LARGE_FERN).or().namedInput(TALL_GRASS).input(PODZOL).build(output);
		combination(BAMBOO).and().input(STICK).input(ItemTags.SAPLINGS).build(output);
		combination(KELP).and().input(SUGAR_CANE).input(WATER_BUCKET).build(output);
		combination(SEAGRASS).and().input(SHORT_GRASS).input(WATER_BUCKET).build(output);
		combination(SEA_PICKLE).and().input(ItemTags.SMALL_FLOWERS).input(WATER_BUCKET).build(output);
		combination(RED_MUSHROOM).or().namedInput(POPPY).input(SCULK).build(output);
		combination(RED_MUSHROOM).and().namedInput(BROWN_MUSHROOM).input(REDSTONE).build(output);
		combination(BROWN_MUSHROOM).or().namedInput(DANDELION).input(SCULK).build(output);
		combination(BROWN_MUSHROOM).and().namedInput(RED_MUSHROOM).input(TERRACOTTA).build(output);
		combination(WARPED_FUNGUS).and().input(BROWN_MUSHROOM).input(NETHER_WART).build(output);
		combination(CRIMSON_FUNGUS).and().input(RED_MUSHROOM).input(NETHER_WART).build(output);

		combination(TUBE_CORAL).and().input(SEAGRASS).input(BLUE_CONCRETE).build(output);
		combination(BRAIN_CORAL).and().input(SEAGRASS).input(PINK_CONCRETE).build(output);
		combination(BUBBLE_CORAL).and().input(SEAGRASS).input(MAGENTA_CONCRETE).build(output);
		combination(FIRE_CORAL).and().input(SEAGRASS).input(RED_CONCRETE).build(output);
		combination(HORN_CORAL).and().input(SEAGRASS).input(YELLOW_CONCRETE).build(output);

		// Crops
		combination(WHEAT_SEEDS).or().input(WHEAT).input(ItemTags.SAPLINGS).build(output);
		combination(COCOA_BEANS).or().input(WHEAT_SEEDS).input(JUNGLE_LOG).build(output);
		combination(CARROT).or().input(WHEAT_SEEDS).input(SPIDER_EYE).build(output);
		combination(NETHER_WART).and().input(WHEAT).input(NETHERRACK).build(output);
		combination(BEETROOT_SEEDS).or().input(POTATO).input(WHEAT).build(output);
		combination(PUMPKIN_SEEDS).or().input(MELON_SEEDS).input(BONE).build(output);
		combination(MELON_SEEDS).and().input(WHEAT_SEEDS).input(WET_SPONGE).build(output);
		combination(SWEET_BERRIES).and().input(MELON_SLICE).input(ItemTags.LEAVES).build(output);
		combination(OAK_SAPLING).or().input(ItemTags.SAPLINGS).input(SHORT_GRASS).build(output);
		combination(BIRCH_SAPLING).and().input(OAK_SAPLING).input(FEATHER).build(output);
		combination(SPRUCE_SAPLING).or().input(ItemTags.SAPLINGS).input(SNOWBALL).build(output);
		combination(JUNGLE_SAPLING).and().input(ItemTags.SAPLINGS).input(VINE).build(output);

		// Resources
		combination(COPPER_INGOT).or().input(GOLD_INGOT).input(IRON_INGOT).build(output);
		combination(EMERALD).or().input(AMETHYST_SHARD).input(GLISTERING_MELON_SLICE).build(output);
		combination(LAPIS_LAZULI).or().input(Items.QUARTZ).input(MSItems.WAND).build(output);
		combination(Items.QUARTZ).or().input(IRON_INGOT).input(NETHERRACK).build(output);
		combination(HONEY_BLOCK).or().input(SLIME_BLOCK).input(SUGAR).build(output);
		combination(HONEYCOMB).and().input(HONEY_BOTTLE).input(MSItems.PLUTONIUM_CORE).build(output);
		combination(STRING).or().input(KELP).input(SPIDER_EYE).build(output);
		combination(SPIDER_EYE).and().input(ENDER_EYE).input(REDSTONE).build(output);
		combination(SLIME_BALL).and().input(MOSS_BLOCK).input(WATER_BUCKET).build(output);
		combination(SNOWBALL).and().input(ENDER_PEARL).input(SUGAR).build(output);
		combination(GUNPOWDER).and().input(REDSTONE).input(CHARCOAL).build(output);
		combination(RABBIT_FOOT).or().input(SPIDER_EYE).input(CARROT).build(output);
		combination(RABBIT).or().input(ROTTEN_FLESH).input(RABBIT_FOOT).build(output);
		combination(MUTTON).or().input(ROTTEN_FLESH).input(ItemTags.WOOL).build(output);
		combination(EGG).or().input(FEATHER).input(BONE_MEAL).build(output);

		// These are replacements for Minestuck's existing recipes so they don't conflict with the dust recipes in MekanismRecipes.
		combination(CLOCK).or().input(REDSTONE).input(GOLD_INGOT).build(output);
		combination(LIGHTNING_ROD).or().input(REDSTONE).input(COPPER_INGOT).build(output);
	}
}