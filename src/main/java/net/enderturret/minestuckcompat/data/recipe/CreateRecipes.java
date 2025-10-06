package net.enderturret.minestuckcompat.data.recipe;

import static com.mraof.minestuck.api.alchemy.GristTypes.*;
import static com.simibubi.create.AllBlocks.*;
import static com.simibubi.create.AllItems.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.ApiStatus.Internal;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.enderturret.minestuckcompat.ConfigCondition;
import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.data.util.AbstractRecipeProvider;
import net.enderturret.minestuckcompat.data.util.RenamingRecipeOutput;

@Internal
public final class CreateRecipes extends AbstractRecipeProvider {

	public CreateRecipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		final RecipeOutput output = new RenamingRecipeOutput(_recipeOutput, MinestuckCompat.MOD_ID, "create")
				.withConditions(new ConfigCondition("create"));

		// Toolboxes, Valve Handles

		final var brownBox = TOOLBOXES.get(DyeColor.BROWN);
		for (DyeColor color : DyeColor.values()) {
			if (color != DyeColor.BROWN)
				sourceGristCost(TOOLBOXES.get(color).asItem()).source(color.getTag()).source(brownBox.asItem()).build(output);

			sourceGristCost(DYED_VALVE_HANDLES.get(color).asItem()).source(color.getTag()).source(COPPER_VALVE_HANDLE.asItem()).build(output);
		}

		// Materials

		gristCost(lookup("create", "asurine")).grist(BUILD, 2).grist(COBALT, 1).build(output);
		gristCost(lookup("create", "crimsite")).grist(BUILD, 2).grist(GARNET, 1).build(output);
		gristCost(lookup("create", "limestone")).grist(BUILD, 2).grist(CHALK, 1).build(output);
		gristCost(lookup("create", "ochrum")).grist(BUILD, 2).grist(SULFUR, 1).build(output);
		gristCost(lookup("create", "scorchia")).grist(BUILD, 2).grist(TAR, 1).build(output);
		gristCost(lookup("create", "scoria")).grist(BUILD, 2).grist(RUST, 1).build(output);
		gristCost(lookup("create", "veridium")).grist(BUILD, 2).grist(QUARTZ, 1).build(output);

		// 4 Bamboo + 1/4 Water
		gristCost(PULP).grist(BUILD, 4).grist(COBALT, 1).build(output);
		sourceGristCost(CINDER_FLOUR).grist(MERCURY, 1).source(Items.NETHERRACK).build(output);

		sourceGristCost(BLAZE_CAKE_BASE).grist(MERCURY, 1).source(Items.EGG).source(Items.SUGAR).source(CINDER_FLOUR.asItem()).build(output);
		sourceGristCost(BLAZE_CAKE).grist(TAR, 4).source(BLAZE_CAKE_BASE.asItem()).build(output);

		sourceGristCost(CRUSHED_BAUXITE).source(c("ingots/aluminum")).build(output);
		sourceGristCost(CRUSHED_COPPER).source(c("ingots/copper")).build(output);
		sourceGristCost(CRUSHED_GOLD).source(c("ingots/gold")).build(output);
		sourceGristCost(CRUSHED_IRON).source(c("ingots/iron")).build(output);
		sourceGristCost(CRUSHED_LEAD).source(c("ingots/lead")).build(output);
		sourceGristCost(CRUSHED_NICKEL).source(c("ingots/nickel")).build(output);
		sourceGristCost(CRUSHED_OSMIUM).source(c("ingots/osmium")).build(output);
		sourceGristCost(CRUSHED_PLATINUM).source(c("ingots/platinum")).build(output);
		sourceGristCost(CRUSHED_QUICKSILVER).source(c("ingots/quicksilver")).build(output);
		sourceGristCost(CRUSHED_SILVER).source(c("ingots/silver")).build(output);
		sourceGristCost(CRUSHED_TIN).source(c("ingots/tin")).build(output);
		sourceGristCost(CRUSHED_URANIUM).source(c("ingots/uranium")).build(output);
		sourceGristCost(CRUSHED_ZINC).source(c("ingots/zinc")).build(output);
		gristCost(POWDERED_OBSIDIAN).grist(BUILD, 2).grist(TAR, 4).grist(COBALT, 2).grist(MERCURY, 1).build(output);
		gristCost(EXP_NUGGET).grist(URANIUM, 1).build(output);

		// Food

		sourceGristCost(BAR_OF_CHOCOLATE).grist(CHALK, 2).grist(MERCURY, 1).source(Items.SUGAR).source(Items.COCOA_BEANS).build(output);
		sourceGristCost(BUILDERS_TEA).grist(BUILD, 8).grist(CHALK, 1).grist(COBALT, 1).source(Items.GLASS_BOTTLE).build(output);
		sourceGristCost(CHOCOLATE_BERRIES).source(BAR_OF_CHOCOLATE.asItem()).source(Items.SWEET_BERRIES).build(output);
		sourceGristCost(lookup("create", "chocolate_bucket")).grist(RUST, 27).multiplier(4).source(BAR_OF_CHOCOLATE.asItem()).build(output);
		sourceGristCost(WHEAT_FLOUR).grist(MERCURY, 1).source(Items.WHEAT).build(output);
		sourceGristCost(SWEET_ROLL).grist(CHALK, 2).source(Items.BREAD).build(output);
		sourceGristCost(HONEYED_APPLE).grist(AMBER, 1).grist(GOLD, 1).source(Items.APPLE).build(output);
		sourceGristCost(lookup("create", "honey_bucket")).grist(AMBER, 4).grist(GOLD, 4).source(Items.BUCKET).build(output);

		// Stonecutter Stuff
		//
		// These definitions are necessary because of a bug/limitation in Minestuck's grist calculator.
		// See Ae2Recipes for details.

		variantSet(output, "minecraft", "andesite");
		variantSet(output, "minecraft", "calcite");
		variantSet(output, "minecraft", "deepslate");
		variantSet(output, "minecraft", "diorite");
		variantSet(output, "minecraft", "dripstone_block");
		variantSet(output, "minecraft", "granite");
		variantSet(output, "minecraft", "tuff");
		variantSet(output, "create", "asurine");
		variantSet(output, "create", "crimsite");
		variantSet(output, "create", "limestone");
		variantSet(output, "create", "ochrum");
		variantSet(output, "create", "scorchia");
		variantSet(output, "create", "scoria");
		variantSet(output, "create", "veridium");
		sourceGristCost(lookup("create", "tiled_glass")).source(Items.GLASS).build(output);
		sourceGristCost(lookup("create", "framed_glass")).source(Items.GLASS).build(output);
		sourceGristCost(lookup("create", "horizontal_framed_glass")).source(Items.GLASS).build(output);
		sourceGristCost(lookup("create", "vertical_framed_glass")).source(Items.GLASS).build(output);

		// Copper

		sourceGristCost(lookup("create", "exposed_copper_shingles")).grist(RUST, 1).source(lookup("create", "copper_shingles")).build(output);
		sourceGristCost(lookup("create", "weathered_copper_shingles")).grist(RUST, 1).source(lookup("create", "copper_shingles")).build(output);
		sourceGristCost(lookup("create", "oxidized_copper_shingles")).grist(RUST, 1).source(lookup("create", "copper_shingles")).build(output);
		sourceGristCost(lookup("create", "waxed_exposed_copper_shingles")).grist(RUST, 1).source(lookup("create", "waxed_copper_shingles")).build(output);
		sourceGristCost(lookup("create", "waxed_weathered_copper_shingles")).grist(RUST, 1).source(lookup("create", "waxed_copper_shingles")).build(output);
		sourceGristCost(lookup("create", "waxed_oxidized_copper_shingles")).grist(RUST, 1).source(lookup("create", "waxed_copper_shingles")).build(output);

		sourceGristCost(lookup("create", "exposed_copper_tiles")).grist(RUST, 1).source(lookup("create", "copper_tiles")).build(output);
		sourceGristCost(lookup("create", "weathered_copper_tiles")).grist(RUST, 1).source(lookup("create", "copper_tiles")).build(output);
		sourceGristCost(lookup("create", "oxidized_copper_tiles")).grist(RUST, 1).source(lookup("create", "copper_tiles")).build(output);
		sourceGristCost(lookup("create", "waxed_exposed_copper_tiles")).grist(RUST, 1).source(lookup("create", "waxed_copper_tiles")).build(output);
		sourceGristCost(lookup("create", "waxed_weathered_copper_tiles")).grist(RUST, 1).source(lookup("create", "waxed_copper_tiles")).build(output);
		sourceGristCost(lookup("create", "waxed_oxidized_copper_tiles")).grist(RUST, 1).source(lookup("create", "waxed_copper_tiles")).build(output);

		// Miscellaneous

		sourceGristCost(BLAZE_BURNER).grist(SULFUR, 20).source(Items.BLAZE_ROD).source(EMPTY_BLAZE_BURNER.asItem()).build(output);

		//
		// Combination Recipes
		//

		oreCombinations(output, ZINC_INGOT, ZINC_BLOCK, ZINC_ORE, DEEPSLATE_ZINC_ORE, RAW_ZINC, RAW_ZINC_BLOCK);
		combination(ZINC_INGOT).and().input(Items.COPPER_INGOT).input(c("gems/emerald")).build(output);

		combination(BLAZE_BURNER).or().input(EMPTY_BLAZE_BURNER.asItem()).input(Items.BLAZE_ROD).build(output);
		combination(RAW_ZINC).and().input(ZINC_INGOT).input(Items.RAW_IRON).build(output);

		combination(CRUSHED_BAUXITE).and().input(c("ingots/aluminum")).input(Items.GRAVEL).build(output.withConditions(not(tagEmpty(c("ingots/aluminum")))));
		combination(CRUSHED_COPPER).and().input(c("ingots/copper")).input(Items.GRAVEL).build(output);
		combination(CRUSHED_GOLD).and().input(c("ingots/gold")).input(Items.GRAVEL).build(output);
		combination(CRUSHED_IRON).and().input(c("ingots/iron")).input(Items.GRAVEL).build(output);
		combination(CRUSHED_LEAD).and().input(c("ingots/lead")).input(Items.GRAVEL).build(output.withConditions(not(tagEmpty(c("ingots/lead")))));
		combination(CRUSHED_NICKEL).and().input(c("ingots/nickel")).input(Items.GRAVEL).build(output.withConditions(not(tagEmpty(c("ingots/nickel")))));
		combination(CRUSHED_OSMIUM).and().input(c("ingots/osmium")).input(Items.GRAVEL).build(output.withConditions(not(tagEmpty(c("ingots/osmium")))));
		combination(CRUSHED_PLATINUM).and().input(c("ingots/platinum")).input(Items.GRAVEL).build(output.withConditions(not(tagEmpty(c("ingots/platinum")))));
		combination(CRUSHED_QUICKSILVER).and().input(c("ingots/quicksilver")).input(Items.GRAVEL).build(output.withConditions(not(tagEmpty(c("ingots/quicksilver")))));
		combination(CRUSHED_SILVER).and().input(c("ingots/silver")).input(Items.GRAVEL).build(output.withConditions(not(tagEmpty(c("ingots/silver")))));
		combination(CRUSHED_TIN).and().input(c("ingots/tin")).input(Items.GRAVEL).build(output.withConditions(not(tagEmpty(c("ingots/tin")))));
		combination(CRUSHED_URANIUM).and().input(c("ingots/uranium")).input(Items.GRAVEL).build(output.withConditions(not(tagEmpty(c("ingots/uranium")))));
		combination(CRUSHED_ZINC).and().input(c("ingots/zinc")).input(Items.GRAVEL).build(output);

		combination(lookup("create", "asurine")).and().input(Items.AMETHYST_BLOCK).input(Items.DIAMOND).build(output);
		combination(lookup("create", "crimsite")).and().input(ROSE_QUARTZ_BLOCK).input(Items.NETHER_WART_BLOCK).build(output);
		combination(lookup("create", "limestone")).and().input(Items.STONE).input(Items.PAPER).build(output);
		combination(lookup("create", "ochrum")).and().input(lookup("create", "limestone")).input(Items.STRIPPED_OAK_LOG).build(output);
		combination(lookup("create", "veridium")).and().input(Items.DARK_PRISMARINE).input(ZINC_INGOT).build(output);
	}

	private void variantSet(RecipeOutput output, String baseTypeDomain, String baseTypeName) {
		final Item baseItem = lookup(baseTypeDomain, baseTypeName);
		if ("dripstone_block".equals(baseTypeName)) baseTypeName = "dripstone";

		for (String kind : List.of("%s_pillar", "cut_%s", "cut_%s_bricks", "layered_%s", "polished_cut_%s", "small_%s_bricks")) {
			final String itemKind = kind.formatted(baseTypeName);
			final Item item = lookup("create", itemKind);
			sourceGristCost(item).source(baseItem).build(output);
		}
	}
}