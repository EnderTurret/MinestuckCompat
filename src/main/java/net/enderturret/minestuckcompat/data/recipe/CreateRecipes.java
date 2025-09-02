package net.enderturret.minestuckcompat.data.recipe;

import static com.mraof.minestuck.api.alchemy.GristTypes.*;
import static com.simibubi.create.AllBlocks.*;
import static com.simibubi.create.AllItems.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.enderturret.minestuckcompat.MinestuckCompat;

public final class CreateRecipes extends AbstractRecipeProvider {

	public CreateRecipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		final RecipeOutput output = new RenamingRecipeOutput(_recipeOutput, MinestuckCompat.MOD_ID, "create")
				.withConditions(modLoaded("create"));

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

		sourceGristCost(CRUSHING_WHEEL).grist(BUILD, 5).multiplier(8).source(ANDESITE_ALLOY.asItem()).build(output);
		sourceGristCost(WAND_OF_SYMMETRY).source(c("glass_blocks")).source(c("glass_blocks")).source(c("glass_blocks"))
				.source(Items.ENDER_PEARL).source(PRECISION_MECHANISM.asItem()).source(BRASS_INGOT.asItem()).source(c("obsidians")).build(output);
		sourceGristCost(EXTENDO_GRIP).source(BRASS_INGOT.asItem()).source(PRECISION_MECHANISM.asItem()).source(Items.STICK).source(Items.STICK)
				.source(Items.STICK).source(Items.STICK).source(Items.STICK).source(Items.STICK).source(BRASS_HAND.asItem()).build(output);
		sourceGristCost(POTATO_CANNON).source(ANDESITE_ALLOY.asItem()).source(PRECISION_MECHANISM.asItem()).source(FLUID_PIPE.asItem())
				.source(FLUID_PIPE.asItem()).source(FLUID_PIPE.asItem()).source(Items.COPPER_INGOT).source(Items.COPPER_INGOT).build(output);
		sourceGristCost(HAUNTED_BELL).grist(SHALE, 1).source(PECULIAR_BELL.asItem()).build(output);
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