package net.enderturret.minestuckcompat.data.recipe;

import static com.mraof.minestuck.api.alchemy.GristTypes.*;
import static vectorwing.farmersdelight.common.registry.ModItems.*;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.ApiStatus.Internal;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import net.neoforged.neoforge.common.Tags;

import net.enderturret.minestuckcompat.ConfigCondition;
import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.api.data.AbstractRecipeProvider;
import net.enderturret.minestuckcompat.api.data.RenamingRecipeOutput;

@Internal
public final class FarmersDelightRecipes extends AbstractRecipeProvider {

	public FarmersDelightRecipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		final RecipeOutput output = new RenamingRecipeOutput(_recipeOutput, MinestuckCompat.MOD_ID, "farmersdelight")
				.withConditions(new ConfigCondition("farmersdelight"));

		gristCost(STRAW.get()).grist(BUILD, 1).build(output);
		gristCost(TREE_BARK.get()).grist(BUILD, 1).build(output);

		gristCost(RICE_PANICLE.get()).grist(AMBER, 2).grist(IODINE, 2).build(output);
		gristCost(CABBAGE.get()).grist(AMBER, 3).grist(CHALK, 1).build(output);
		gristCost(TOMATO.get()).grist(AMBER, 1).grist(CHALK, 1).grist(RUST, 2).build(output);
		gristCost(ROTTEN_TOMATO.get()).grist(AMBER, 1).grist(SHALE, 2).grist(RUST, 1).build(output);
		gristCost(ONION.get()).grist(AMBER, 2).grist(IODINE, 2).build(output);
		gristCost(CABBAGE_SEEDS.get()).grist(AMBER, 1).grist(CHALK, 1).build(output);
		gristCost(TOMATO_SEEDS.get()).grist(AMBER, 1).grist(RUST, 1).build(output);

		sourceGristCost(HAM.get()).grist(CHALK, 3).multiplier(2).source(Items.PORKCHOP).build(output);
		sourceGristCost(SMOKED_HAM.get()).grist(TAR, 4).source(HAM.get()).build(output);

		sourceGristCost(ROAST_CHICKEN.get()).grist(BUILD, 1).multiplier(0.25F).source(ROAST_CHICKEN_BLOCK.get()).build(output);
		sourceGristCost(HONEY_GLAZED_HAM.get()).grist(BUILD, 1).multiplier(0.25F).source(HONEY_GLAZED_HAM_BLOCK.get()).build(output);
		sourceGristCost(SHEPHERDS_PIE.get()).grist(BUILD, 1).multiplier(0.25F).source(SHEPHERDS_PIE_BLOCK.get()).build(output);
		sourceGristCost(STUFFED_PUMPKIN.get()).grist(BUILD, 1).multiplier(0.25F).source(STUFFED_PUMPKIN_BLOCK.get()).build(output);

		sourceGristCost(WILD_CABBAGES.get()).grist(AMBER, 2).source(CABBAGE_SEEDS.get()).build(output);
		sourceGristCost(WILD_TOMATOES.get()).grist(AMBER, 2).source(TOMATO_SEEDS.get()).build(output);
		sourceGristCost(WILD_ONIONS.get()).grist(AMBER, 2).source(ONION.get()).source(Items.ALLIUM).build(output);
		sourceGristCost(WILD_CARROTS.get()).grist(AMBER, 2).source(Items.CARROT).build(output);
		sourceGristCost(WILD_POTATOES.get()).grist(AMBER, 2).source(Items.POTATO).build(output);
		sourceGristCost(WILD_BEETROOTS.get()).grist(AMBER, 2).source(Items.BEETROOT).build(output);
		sourceGristCost(SANDY_SHRUB.get()).grist(AMBER, 2).multiplier(0.5F).source(Items.BEETROOT_SEEDS).build(output);
		sourceGristCost(WILD_RICE.get()).grist(AMBER, 2).source(RICE.get()).build(output);
		sourceGristCost(BROWN_MUSHROOM_COLONY.get()).multiplier(5).source(Items.BROWN_MUSHROOM).build(output);
		sourceGristCost(RED_MUSHROOM_COLONY.get()).multiplier(5).source(Items.RED_MUSHROOM).build(output);

		sourceGristCost(BLACK_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_BLACK).build(output);
		sourceGristCost(BLUE_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_BLUE).build(output);
		sourceGristCost(BROWN_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_BROWN).build(output);
		sourceGristCost(CYAN_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_CYAN).build(output);
		sourceGristCost(GRAY_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_GRAY).build(output);
		sourceGristCost(GREEN_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_GREEN).build(output);
		sourceGristCost(LIGHT_BLUE_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_LIGHT_BLUE).build(output);
		sourceGristCost(LIGHT_GRAY_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_LIGHT_GRAY).build(output);
		sourceGristCost(LIME_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_LIME).build(output);
		sourceGristCost(MAGENTA_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_MAGENTA).build(output);
		sourceGristCost(ORANGE_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_ORANGE).build(output);
		sourceGristCost(PINK_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_PINK).build(output);
		sourceGristCost(PURPLE_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_PURPLE).build(output);
		sourceGristCost(RED_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_RED).build(output);
		sourceGristCost(WHITE_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_WHITE).build(output);
		sourceGristCost(YELLOW_CANVAS_SIGN.get()).source(CANVAS_SIGN.get()).source(Tags.Items.DYES_YELLOW).build(output);

		sourceGristCost(BLACK_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_BLACK).build(output);
		sourceGristCost(BLUE_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_BLUE).build(output);
		sourceGristCost(BROWN_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_BROWN).build(output);
		sourceGristCost(CYAN_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_CYAN).build(output);
		sourceGristCost(GRAY_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_GRAY).build(output);
		sourceGristCost(GREEN_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_GREEN).build(output);
		sourceGristCost(LIGHT_BLUE_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_LIGHT_BLUE).build(output);
		sourceGristCost(LIGHT_GRAY_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_LIGHT_GRAY).build(output);
		sourceGristCost(LIME_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_LIME).build(output);
		sourceGristCost(MAGENTA_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_MAGENTA).build(output);
		sourceGristCost(ORANGE_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_ORANGE).build(output);
		sourceGristCost(PINK_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_PINK).build(output);
		sourceGristCost(PURPLE_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_PURPLE).build(output);
		sourceGristCost(RED_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_RED).build(output);
		sourceGristCost(WHITE_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_WHITE).build(output);
		sourceGristCost(YELLOW_HANGING_CANVAS_SIGN.get()).source(HANGING_CANVAS_SIGN.get()).source(Tags.Items.DYES_YELLOW).build(output);

		sourceGristCost(RICH_SOIL.get()).source(ORGANIC_COMPOST.get()).build(output);

		//
		// Combination Recipes
		//

		combination(WILD_CABBAGES.get()).or().input(Items.SHORT_GRASS).input(CABBAGE.get()).build(output);
		combination(WILD_TOMATOES.get()).or().input(Items.SHORT_GRASS).input(TOMATO.get()).build(output);
		combination(WILD_ONIONS.get()).or().input(Items.SHORT_GRASS).input(ONION.get()).build(output);
		combination(WILD_CARROTS.get()).or().input(Items.SHORT_GRASS).input(Items.CARROT).build(output);
		combination(WILD_POTATOES.get()).or().input(Items.SHORT_GRASS).input(Items.POTATO).build(output);
		combination(WILD_BEETROOTS.get()).or().input(Items.SHORT_GRASS).input(Items.BEETROOT).build(output);
		combination(SANDY_SHRUB.get()).or().input(Items.SHORT_GRASS).input(Items.DEAD_BUSH).build(output);
		combination(WILD_RICE.get()).or().input(Items.SHORT_GRASS).input(RICE.get()).build(output);
		combination(BROWN_MUSHROOM_COLONY.get()).or().input(Items.SHORT_GRASS).input(Items.BROWN_MUSHROOM).build(output);
		combination(RED_MUSHROOM_COLONY.get()).or().input(Items.SHORT_GRASS).input(Items.RED_MUSHROOM).build(output);
		combination(CABBAGE.get()).or().input(ItemTags.LEAVES).input(Items.APPLE).build(output);
		combination(ONION.get()).or().input(Items.BEETROOT).input(Items.POTATO).build(output);
		combination(TOMATO.get()).or().input(Items.POTATO).input(Tags.Items.DYES_RED).build(output);
		combination(ROTTEN_TOMATO.get()).or().input(TOMATO.get()).input(Items.ROTTEN_FLESH).build(output);
	}
}