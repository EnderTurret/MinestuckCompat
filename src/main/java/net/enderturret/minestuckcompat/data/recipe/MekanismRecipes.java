package net.enderturret.minestuckcompat.data.recipe;

import static com.mraof.minestuck.api.alchemy.GristTypes.*;
import static mekanism.common.registries.MekanismItems.*;

import java.util.concurrent.CompletableFuture;

import com.mraof.minestuck.item.MSItems;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

import net.neoforged.neoforge.common.Tags;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.api.data.AbstractRecipeProvider;
import net.enderturret.minestuckcompat.api.data.RenamingRecipeOutput;

import mekanism.common.registries.MekanismBlocks;

public final class MekanismRecipes extends AbstractRecipeProvider {

	public MekanismRecipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		final RecipeOutput output = new RenamingRecipeOutput(_recipeOutput, MinestuckCompat.MOD_ID, "mekanism")
				.withConditions(modLoaded("mekanism"));

		gristCost(c("gems/fluorite")).grist(AMETHYST, 3).grist(QUARTZ, 6).build(output);
		sourceGristCost(c("ingots/osmium")).source(c("raw_materials/osmium")).build(output);
		sourceGristCost(c("ingots/uranium")).source(c("raw_materials/uranium")).build(output);

		sourceGristCost(c("ores/fluorite")).grist(BUILD, 4).multiplier(4).source(c("gems/fluorite")).build(output);
		sourceGristCost(c("ores/osmium")).grist(BUILD, 4).source(c("raw_materials/osmium")).build(output);
		sourceGristCost(c("ores/uranium")).grist(BUILD, 4).source(c("raw_materials/uranium")).build(output);

		gristCost(c("raw_materials/osmium")).grist(RUST, 12).grist(COBALT, 4).build(output);
		gristCost(c("raw_materials/uranium")).grist(URANIUM, 3).build(output);

		gristCost(ANTIMATTER_PELLET).grist(URANIUM, 400).grist(ZILLIUM, 20).build(output);
		gristCost(HDPE_PELLET).grist(CHALK, 5).grist(MERCURY, 2).grist(BUILD, 2).grist(COBALT, 1).build(output);
		gristCost(LITHIUM_DUST).grist(IODINE, 12).build(output);
		gristCost(POLONIUM_PELLET).grist(URANIUM, 150).grist(COBALT, 106).grist(BUILD, 100).grist(QUARTZ, 36).grist(SULFUR, 30).grist(AMETHYST, 18).grist(MERCURY, 4).build(output);
		sourceGristCost(PLUTONIUM_PELLET).source(POLONIUM_PELLET.asItem()).build(output);
		sourceGristCost(REPROCESSED_FISSILE_FRAGMENT).multiplier(0.25F).source(PLUTONIUM_PELLET.asItem()).build(output);
		gristCost(SALT).grist(CHALK, 2).build(output);
		sourceGristCost(MekanismBlocks.SALT_BLOCK).grist(BUILD, 4).multiplier(4).source(SALT.asItem()).build(output);
		sourceGristCost(SAWDUST).grist(MERCURY, 1).source(Items.STICK).build(output);
		gristCost(SUBSTRATE).grist(MERCURY, 2).grist(BUILD, 2).grist(COBALT, 1).build(output);
		gristCost(SULFUR_DUST).grist(SULFUR, 6).build(output);

		// Buckets

		sourceGristCost(lookup("mekanism", "chlorine_bucket")).grist(AMBER, 16).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "nutritional_paste_bucket")).grist(AMBER, 80).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "sodium_bucket")).grist(CAULK, 16).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "superheated_sodium_bucket")).grist(CAULK, 16).grist(SULFUR, 4).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "hydrogen_chloride_bucket")).grist(CHALK, 16).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "oxygen_bucket")).grist(COBALT, 4).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "heavy_water_bucket")).grist(COBALT, 40).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "lithium_bucket")).grist(IODINE, 16).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "hydrogen_bucket")).grist(MERCURY, 4).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "steam_bucket")).grist(MERCURY, 4).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "ethene_bucket")).grist(SHALE, 16).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "brine_bucket")).grist(SULFUR, 16).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "sulfur_dioxide_bucket")).grist(SULFUR, 16).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "sulfur_trioxide_bucket")).grist(SULFUR, 16).grist(COBALT, 4).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "sulfuric_acid_bucket")).grist(SULFUR, 16).grist(COBALT, 4).grist(MERCURY, 4).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "hydrofluoric_acid_bucket")).grist(SULFUR, 16).grist(IODINE, 16).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "uranium_oxide_bucket")).grist(URANIUM, 16).source(Items.BUCKET).build(output);
		sourceGristCost(lookup("mekanism", "uranium_hexafluoride_bucket")).grist(URANIUM, 16).grist(SULFUR, 16).grist(IODINE, 16).source(Items.BUCKET).build(output);

		//
		// Combination Recipes
		//

		oreCombinations(output, lookup("mekanism", "ingot_lead"), lookup("mekanism", "block_lead"), lookup("mekanism", "lead_ore"));
		combination(lookup("mekanism", "ingot_lead")).and().input(Items.IRON_INGOT).input(Tags.Items.DYES_GRAY).build(output);
		oreCombinations(output, lookup("mekanism", "ingot_osmium"), lookup("mekanism", "block_osmium"), lookup("mekanism", "osmium_ore"));
		combination(lookup("mekanism", "ingot_osmium")).and().input(Items.IRON_INGOT).input(MSItems.RAW_CRUXITE).build(output);
		oreCombinations(output, lookup("mekanism", "ingot_tin"), lookup("mekanism", "block_tin"), lookup("mekanism", "tin_ore"));
		combination(lookup("mekanism", "ingot_tin")).or().input(Items.COPPER_INGOT).input(MSItems.CHALK).build(output);
		oreCombinations(output, lookup("mekanism", "ingot_uranium"), lookup("mekanism", "block_uranium"), lookup("mekanism", "uranium_ore"));
		combination(lookup("mekanism", "ingot_uranium")).and().input(lookup("mekanism", "ingot_lead")).input(Items.FERMENTED_SPIDER_EYE).build(output);
	}
}