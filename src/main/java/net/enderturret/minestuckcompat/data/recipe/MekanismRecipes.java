package net.enderturret.minestuckcompat.data.recipe;

import static com.mraof.minestuck.api.alchemy.GristTypes.*;
import static mekanism.common.registries.MekanismItems.*;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.mraof.minestuck.item.MSItems;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.neoforged.neoforge.common.Tags;

import net.enderturret.minestuckcompat.ConfigCondition;
import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.api.data.AbstractRecipeProvider;
import net.enderturret.minestuckcompat.api.data.RenamingRecipeOutput;

import mekanism.common.registries.MekanismBlocks;

@Internal
public final class MekanismRecipes extends AbstractRecipeProvider {

	public MekanismRecipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		final RecipeOutput output = new RenamingRecipeOutput(_recipeOutput, MinestuckCompat.MOD_ID, "mekanism")
				.withConditions(new ConfigCondition("mekanism"));

		// Ores

		gristCost(c("gems/fluorite")).grist(AMETHYST, 3).grist(QUARTZ, 6).build(output);
		sourceGristCost(c("ingots/osmium")).source(c("raw_materials/osmium")).build(output);
		sourceGristCost(c("ingots/uranium")).source(c("raw_materials/uranium")).build(output);

		sourceGristCost(c("ores/fluorite")).grist(BUILD, 4).multiplier(4).source(c("gems/fluorite")).build(output);
		sourceGristCost(c("ores/osmium")).grist(BUILD, 4).source(c("raw_materials/osmium")).build(output);
		sourceGristCost(c("ores/uranium")).grist(BUILD, 4).source(c("raw_materials/uranium")).build(output);

		gristCost(c("raw_materials/osmium")).grist(RUST, 12).grist(COBALT, 4).build(output);
		gristCost(c("raw_materials/uranium")).grist(URANIUM, 3).build(output);

		sourceGristCost(mek("crystal_iron")).grist(QUARTZ, 1).multiplier(0.2F).source(Items.IRON_INGOT).build(output);
		sourceGristCost(mek("crystal_gold")).grist(QUARTZ, 1).multiplier(0.2F).source(Items.GOLD_INGOT).build(output);
		sourceGristCost(mek("crystal_osmium")).grist(QUARTZ, 1).multiplier(0.2F).source(mek("ingot_osmium")).build(output);
		sourceGristCost(mek("crystal_copper")).grist(QUARTZ, 1).multiplier(0.2F).source(Items.COPPER_INGOT).build(output);
		sourceGristCost(mek("crystal_tin")).grist(QUARTZ, 1).multiplier(0.2F).source(mek("ingot_tin")).build(output);
		sourceGristCost(mek("crystal_lead")).grist(QUARTZ, 1).multiplier(0.2F).source(mek("ingot_lead")).build(output);
		sourceGristCost(mek("crystal_uranium")).grist(QUARTZ, 1).multiplier(0.2F).source(mek("ingot_uranium")).build(output);

		// Materials

		gristCost(SALT).grist(CHALK, 2).build(output);
		sourceGristCost(MekanismBlocks.SALT_BLOCK).grist(BUILD, 4).multiplier(4).source(SALT.asItem()).build(output);
		sourceGristCost(SAWDUST).grist(MERCURY, 1).source(Items.STICK).build(output);

		gristCost(SUBSTRATE).grist(MERCURY, 2).grist(BUILD, 2).grist(COBALT, 1).build(output);
		gristCost(HDPE_PELLET).grist(CHALK, 5).grist(MERCURY, 2).grist(BUILD, 2).grist(COBALT, 1).build(output);

		gristCost(POLONIUM_PELLET).grist(URANIUM, 150).grist(COBALT, 106).grist(BUILD, 100).grist(QUARTZ, 36).grist(SULFUR, 30).grist(AMETHYST, 18).grist(MERCURY, 4).build(output);
		sourceGristCost(PLUTONIUM_PELLET).source(POLONIUM_PELLET.asItem()).build(output);
		sourceGristCost(REPROCESSED_FISSILE_FRAGMENT).multiplier(0.25F).source(PLUTONIUM_PELLET.asItem()).build(output);
		gristCost(ANTIMATTER_PELLET).grist(URANIUM, 400).grist(ZILLIUM, 20).build(output);

		gristCost(LITHIUM_DUST).grist(IODINE, 12).build(output);
		gristCost(SULFUR_DUST).grist(SULFUR, 6).build(output);

		// Buckets

		sourceGristCost(mek("chlorine_bucket")).grist(AMBER, 16).source(Items.BUCKET).build(output);
		sourceGristCost(mek("nutritional_paste_bucket")).grist(AMBER, 80).source(Items.BUCKET).build(output);
		sourceGristCost(mek("sodium_bucket")).grist(CAULK, 16).source(Items.BUCKET).build(output);
		sourceGristCost(mek("superheated_sodium_bucket")).grist(CAULK, 16).grist(SULFUR, 4).source(Items.BUCKET).build(output);
		sourceGristCost(mek("hydrogen_chloride_bucket")).grist(CHALK, 16).source(Items.BUCKET).build(output);
		sourceGristCost(mek("oxygen_bucket")).grist(COBALT, 4).source(Items.BUCKET).build(output);
		sourceGristCost(mek("heavy_water_bucket")).grist(COBALT, 40).source(Items.BUCKET).build(output);
		sourceGristCost(mek("lithium_bucket")).grist(IODINE, 16).source(Items.BUCKET).build(output);
		sourceGristCost(mek("hydrogen_bucket")).grist(MERCURY, 4).source(Items.BUCKET).build(output);
		sourceGristCost(mek("steam_bucket")).grist(MERCURY, 4).source(Items.BUCKET).build(output);
		sourceGristCost(mek("ethene_bucket")).grist(SHALE, 16).source(Items.BUCKET).build(output);
		sourceGristCost(mek("brine_bucket")).grist(SULFUR, 16).source(Items.BUCKET).build(output);
		sourceGristCost(mek("sulfur_dioxide_bucket")).grist(SULFUR, 16).source(Items.BUCKET).build(output);
		sourceGristCost(mek("sulfur_trioxide_bucket")).grist(SULFUR, 16).grist(COBALT, 4).source(Items.BUCKET).build(output);
		sourceGristCost(mek("sulfuric_acid_bucket")).grist(SULFUR, 16).grist(COBALT, 4).grist(MERCURY, 4).source(Items.BUCKET).build(output);
		sourceGristCost(mek("hydrofluoric_acid_bucket")).grist(SULFUR, 16).grist(IODINE, 16).source(Items.BUCKET).build(output);
		sourceGristCost(mek("uranium_oxide_bucket")).grist(URANIUM, 16).source(Items.BUCKET).build(output);
		sourceGristCost(mek("uranium_hexafluoride_bucket")).grist(URANIUM, 16).grist(SULFUR, 16).grist(IODINE, 16).source(Items.BUCKET).build(output);

		//
		// Combination Recipes
		//

		oreCombinations(output, mek("ingot_lead"), mek("block_lead"), mek("lead_ore"), mek("deepslate_lead_ore"), mek("raw_lead"), mek("block_raw_lead"));
		combination(mek("ingot_lead")).or().input(Items.IRON_INGOT).input(Items.SPIDER_EYE).build(output);
		oreCombinations(output, mek("ingot_osmium"), mek("block_osmium"), mek("osmium_ore"), mek("deepslate_osmium_ore"), mek("raw_osmium"), mek("block_raw_osmium"));
		combination(mek("ingot_osmium")).and().input(Items.IRON_INGOT).input(MSItems.RAW_CRUXITE).build(output);
		oreCombinations(output, mek("ingot_tin"), mek("block_tin"), mek("tin_ore"), mek("deepslate_tin_ore"), mek("raw_tin"), mek("block_raw_tin"));
		combination(mek("ingot_tin")).or().input(Items.COPPER_INGOT).input(MSItems.CHALK).build(output);
		oreCombinations(output, mek("ingot_uranium"), mek("block_uranium"), mek("uranium_ore"), mek("deepslate_uranium_ore"), mek("raw_uranium"), mek("block_raw_uranium"));
		combination(mek("ingot_uranium")).or().input(mek("ingot_lead")).input(Items.FERMENTED_SPIDER_EYE).build(output);

		combination(mek("crystal_iron")).and().input(Items.QUARTZ).input(Items.IRON_INGOT).build(output);
		combination(mek("crystal_gold")).and().input(Items.QUARTZ).input(Items.GOLD_INGOT).build(output);
		combination(mek("crystal_osmium")).and().input(Items.QUARTZ).input(c("ingots/osmium")).build(output);
		combination(mek("crystal_copper")).and().input(Items.QUARTZ).input(Items.COPPER_INGOT).build(output);
		combination(mek("crystal_tin")).and().input(Items.QUARTZ).input(c("ingots/tin")).build(output);
		combination(mek("crystal_lead")).and().input(Items.QUARTZ).input(c("ingots/lead")).build(output);
		combination(mek("crystal_uranium")).and().input(Items.QUARTZ).input(c("ingots/uranium")).build(output);

		combination(mek("dust_iron")).and().input(Items.REDSTONE).input(Items.IRON_INGOT).build(output);
		combination(mek("dust_gold")).and().input(Items.REDSTONE).input(Items.GOLD_INGOT).build(output);
		combination(mek("dust_osmium")).and().input(Items.REDSTONE).input(c("ingots/osmium")).build(output);
		combination(mek("dust_copper")).and().input(Items.REDSTONE).input(Items.COPPER_INGOT).build(output);
		combination(mek("dust_tin")).and().input(Items.REDSTONE).input(c("ingots/tin")).build(output);
		combination(mek("dust_lead")).and().input(Items.REDSTONE).input(c("ingots/lead")).build(output);
		combination(mek("dust_uranium")).and().input(Items.REDSTONE).input(c("ingots/uranium")).build(output);
		combination(mek("dust_steel")).and().input(Items.REDSTONE).input(c("ingots/steel")).build(output);

		combination(mek("shard_iron")).and().input(Items.AMETHYST_SHARD).input(Items.IRON_INGOT).build(output);
		combination(mek("shard_gold")).and().input(Items.AMETHYST_SHARD).input(Items.GOLD_INGOT).build(output);
		combination(mek("shard_osmium")).and().input(Items.AMETHYST_SHARD).input(c("ingots/osmium")).build(output);
		combination(mek("shard_copper")).and().input(Items.AMETHYST_SHARD).input(Items.COPPER_INGOT).build(output);
		combination(mek("shard_tin")).and().input(Items.AMETHYST_SHARD).input(c("ingots/tin")).build(output);
		combination(mek("shard_lead")).and().input(Items.AMETHYST_SHARD).input(c("ingots/lead")).build(output);
		combination(mek("shard_uranium")).and().input(Items.AMETHYST_SHARD).input(c("ingots/uranium")).build(output);
	}

	private static Item mek(String id) {
		return lookup("mekanism", id);
	}
}