package net.enderturret.minestuckcompat.data.recipe;

import static com.mraof.minestuck.api.alchemy.GristTypes.*;
import static com.mraof.minestuck.block.AspectTreeBlocks.*;
import static com.mraof.minestuck.item.MSItems.*;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.mraof.minestuck.api.alchemy.GristTypes;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;

import net.enderturret.minestuckcompat.ConfigCondition;
import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.data.util.AbstractRecipeProvider;
import net.enderturret.minestuckcompat.data.util.RenamingRecipeOutput;

@Internal
public final class MinestuckRecipes extends AbstractRecipeProvider {

	public MinestuckRecipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		final RecipeOutput output = new RenamingRecipeOutput(_recipeOutput, MinestuckCompat.MOD_ID, "minestuck").withConditions(new ConfigCondition("minestuck"));

		//
		// Try to fix empty tag grist costs.
		//

		{
			final RecipeOutput output2 = new RenamingRecipeOutput(_recipeOutput, "minestuck", null);
			sourceGristCost(c("ores/aluminium")).grist(BUILD, 4).source(c("raw_materials/aluminum")).build(output2.withConditions(not(tagEmpty(c("ores/aluminium")))));
			sourceGristCost(c("ores/aluminum")).grist(BUILD, 4).source(c("raw_materials/aluminum")).build(output2.withConditions(not(tagEmpty(c("ores/aluminum")))));
			sourceGristCost(c("ores/ardite")).grist(BUILD, 4).source(c("raw_materials/ardite")).build(output2.withConditions(not(tagEmpty(c("ores/ardite")))));
			sourceGristCost(c("ores/cobalt")).grist(BUILD, 4).source(c("raw_materials/cobalt")).build(output2.withConditions(not(tagEmpty(c("ores/cobalt")))));
			sourceGristCost(c("ores/galena")).grist(BUILD, 4).source(c("raw_materials/lead")).build(output2.withConditions(not(tagEmpty(c("ores/galena")))));
			sourceGristCost(c("ores/lead")).grist(BUILD, 4).source(c("raw_materials/lead")).build(output2.withConditions(not(tagEmpty(c("ores/lead")))));
			sourceGristCost(c("ores/nickel")).grist(BUILD, 4).source(c("raw_materials/nickel")).build(output2.withConditions(not(tagEmpty(c("ores/nickel")))));
			sourceGristCost(c("ores/silver")).grist(BUILD, 4).source(c("raw_materials/silver")).build(output2.withConditions(not(tagEmpty(c("ores/silver")))));
			sourceGristCost(c("ores/tin")).grist(BUILD, 4).source(c("raw_materials/tin")).build(output2.withConditions(not(tagEmpty(c("ores/tin")))));
			sourceGristCost(c("ores/zinc")).grist(BUILD, 4).source(c("raw_materials/zinc")).build(output2.withConditions(not(tagEmpty(c("ores/zinc")))));

			sourceGristCost(c("ingots/aluminium")).source(c("ingots/aluminum")).build(output2.withConditions(not(tagEmpty(c("ingots/aluminium")))));
			sourceGristCost(c("ingots/aluminum")).source(c("raw_materials/aluminum")).build(output2.withConditions(not(tagEmpty(c("ingots/aluminum")))));
			sourceGristCost(c("ingots/ardite")).source(c("raw_materials/ardite")).build(output2.withConditions(not(tagEmpty(c("ingots/ardite")))));
			gristCost(c("ingots/brass")).grist(CAULK, 8).grist(RUST, 12).build(output2.withConditions(not(tagEmpty(c("ingots/brass")))));
			sourceGristCost(c("ingots/cobalt")).source(c("raw_materials/cobalt")).build(output2.withConditions(not(tagEmpty(c("ingots/cobalt")))));
			gristCost(c("ingots/electrum")).grist(GOLD, 6).grist(MERCURY, 6).grist(RUST, 10).build(output2.withConditions(not(tagEmpty(c("ingots/electrum")))));
			gristCost(c("ingots/invar")).grist(RUST, 12).grist(SULFUR, 5).build(output2.withConditions(not(tagEmpty(c("ingots/invar")))));
			sourceGristCost(c("ingots/lead")).source(c("raw_materials/lead")).build(output2.withConditions(not(tagEmpty(c("ingots/lead")))));
			sourceGristCost(c("ingots/nickel")).source(c("raw_materials/nickel")).build(output2.withConditions(not(tagEmpty(c("ingots/nickel")))));
			gristCost(c("ingots/red_alloy")).grist(GARNET, 32).grist(RUST, 18).build(output2.withConditions(not(tagEmpty(c("ingots/red_alloy")))));
			sourceGristCost(c("ingots/silver")).source(c("raw_materials/silver")).build(output2.withConditions(not(tagEmpty(c("ingots/silver")))));
			sourceGristCost(c("ingots/tin")).source(c("raw_materials/tin")).build(output2.withConditions(not(tagEmpty(c("ingots/tin")))));
			gristCost(c("ingots/uranium")).grist(URANIUM, 8).build(output2.withConditions(not(tagEmpty(c("ingots/uranium")))));
			sourceGristCost(c("ingots/zinc")).source(c("raw_materials/zinc")).build(output2.withConditions(not(tagEmpty(c("ingots/zinc")))));

			sourceGristCost(c("raw_materials/aluminium")).source(c("raw_materials/aluminum")).build(output2.withConditions(not(tagEmpty(c("raw_materials/aluminium")))));
			gristCost(c("raw_materials/aluminum")).grist(GristTypes.CHALK, 6).grist(RUST, 12).build(output2.withConditions(not(tagEmpty(c("raw_materials/aluminum")))));
			gristCost(c("raw_materials/ardite")).grist(GARNET, 12).grist(SULFUR, 8).build(output2.withConditions(not(tagEmpty(c("raw_materials/ardite")))));
			gristCost(c("raw_materials/cobalt")).grist(COBALT, 18).build(output2.withConditions(not(tagEmpty(c("raw_materials/cobalt")))));
			gristCost(c("raw_materials/lead")).grist(COBALT, 4).grist(RUST, 12).grist(SHALE, 4).build(output2.withConditions(not(tagEmpty(c("raw_materials/lead")))));
			gristCost(c("raw_materials/nickel")).grist(RUST, 12).grist(SULFUR, 8).build(output2.withConditions(not(tagEmpty(c("raw_materials/nickel")))));
			gristCost(c("raw_materials/silver")).grist(MERCURY, 8).grist(RUST, 12).build(output2.withConditions(not(tagEmpty(c("raw_materials/silver")))));
			gristCost(c("raw_materials/tin")).grist(CAULK, 8).grist(RUST, 12).build(output2.withConditions(not(tagEmpty(c("raw_materials/tin")))));
			gristCost(c("raw_materials/zinc")).grist(RUST, 12).grist(SHALE, 4).build(output2.withConditions(not(tagEmpty(c("raw_materials/zinc")))));

			// Fix sand also including suspicious sand.
			gristCost(tag(MinestuckCompat.MOD_ID, "unsuspicious_sand")).grist(BUILD, 1).build(output2, ResourceLocation.fromNamespaceAndPath("minestuck", "sand_tag"));
		}

		//
		// Combination Recipes
		//

		saplingCombinations(output, BLOOD_ASPECT_SAPLING, BLOOD_ASPECT_LOG, BLOOD_ASPECT_LEAVES);
		saplingCombinations(output, BREATH_ASPECT_SAPLING, BREATH_ASPECT_LOG, BREATH_ASPECT_LEAVES);
		saplingCombinations(output, DOOM_ASPECT_SAPLING, DOOM_ASPECT_LOG, DOOM_ASPECT_LEAVES);
		saplingCombinations(output, HEART_ASPECT_SAPLING, HEART_ASPECT_LOG, HEART_ASPECT_LEAVES);
		saplingCombinations(output, HOPE_ASPECT_SAPLING, HOPE_ASPECT_LOG, HOPE_ASPECT_LEAVES);
		saplingCombinations(output, LIFE_ASPECT_SAPLING, LIFE_ASPECT_LOG, LIFE_ASPECT_LEAVES);
		saplingCombinations(output, LIGHT_ASPECT_SAPLING, LIGHT_ASPECT_LOG, LIGHT_ASPECT_LEAVES);
		saplingCombinations(output, MIND_ASPECT_SAPLING, MIND_ASPECT_LOG, MIND_ASPECT_LEAVES);
		saplingCombinations(output, RAGE_ASPECT_SAPLING, RAGE_ASPECT_LOG, RAGE_ASPECT_LEAVES);
		saplingCombinations(output, SPACE_ASPECT_SAPLING, SPACE_ASPECT_LOG, SPACE_ASPECT_LEAVES);
		saplingCombinations(output, TIME_ASPECT_SAPLING, TIME_ASPECT_LOG, TIME_ASPECT_LEAVES);
		saplingCombinations(output, VOID_ASPECT_SAPLING, VOID_ASPECT_LOG, VOID_ASPECT_LEAVES);
		saplingCombinations(output, FROST_SAPLING, FROST_LOG, FROST_LEAVES);
		saplingCombinations(output, RAINBOW_SAPLING, RAINBOW_LOG, RAINBOW_LEAVES, false);
		saplingCombinations(output, END_SAPLING, END_LOG, END_LEAVES, false);
		saplingCombinations(output, SHADEWOOD_SAPLING, SHADEWOOD_LOG, SHADEWOOD_LEAVES, false);

		woodCombinations(output, BLOOD_ASPECT_PLANKS, BLOOD_ASPECT_SLAB, BLOOD_ASPECT_STAIRS, BLOOD_ASPECT_DOOR, BLOOD_ASPECT_FENCE, BLOOD_ASPECT_FENCE_GATE, BLOOD_ASPECT_TRAPDOOR);
		woodCombinations(output, BREATH_ASPECT_PLANKS, BREATH_ASPECT_SLAB, BREATH_ASPECT_STAIRS, BREATH_ASPECT_DOOR, BREATH_ASPECT_FENCE, BREATH_ASPECT_FENCE_GATE, BREATH_ASPECT_TRAPDOOR);
		woodCombinations(output, DOOM_ASPECT_PLANKS, DOOM_ASPECT_SLAB, DOOM_ASPECT_STAIRS, DOOM_ASPECT_DOOR, DOOM_ASPECT_FENCE, DOOM_ASPECT_FENCE_GATE, DOOM_ASPECT_TRAPDOOR);
		woodCombinations(output, HEART_ASPECT_PLANKS, HEART_ASPECT_SLAB, HEART_ASPECT_STAIRS, HEART_ASPECT_DOOR, HEART_ASPECT_FENCE, HEART_ASPECT_FENCE_GATE, HEART_ASPECT_TRAPDOOR);
		woodCombinations(output, HOPE_ASPECT_PLANKS, HOPE_ASPECT_SLAB, HOPE_ASPECT_STAIRS, HOPE_ASPECT_DOOR, HOPE_ASPECT_FENCE, HOPE_ASPECT_FENCE_GATE, HOPE_ASPECT_TRAPDOOR);
		woodCombinations(output, LIFE_ASPECT_PLANKS, LIFE_ASPECT_SLAB, LIFE_ASPECT_STAIRS, LIFE_ASPECT_DOOR, LIFE_ASPECT_FENCE, LIFE_ASPECT_FENCE_GATE, LIFE_ASPECT_TRAPDOOR);
		woodCombinations(output, LIGHT_ASPECT_PLANKS, LIGHT_ASPECT_SLAB, LIGHT_ASPECT_STAIRS, LIGHT_ASPECT_DOOR, LIGHT_ASPECT_FENCE, LIGHT_ASPECT_FENCE_GATE, LIGHT_ASPECT_TRAPDOOR);
		woodCombinations(output, MIND_ASPECT_PLANKS, MIND_ASPECT_SLAB, MIND_ASPECT_STAIRS, MIND_ASPECT_DOOR, MIND_ASPECT_FENCE, MIND_ASPECT_FENCE_GATE, MIND_ASPECT_TRAPDOOR);
		woodCombinations(output, RAGE_ASPECT_PLANKS, RAGE_ASPECT_SLAB, RAGE_ASPECT_STAIRS, RAGE_ASPECT_DOOR, RAGE_ASPECT_FENCE, RAGE_ASPECT_FENCE_GATE, RAGE_ASPECT_TRAPDOOR);
		woodCombinations(output, SPACE_ASPECT_PLANKS, SPACE_ASPECT_SLAB, SPACE_ASPECT_STAIRS, SPACE_ASPECT_DOOR, SPACE_ASPECT_FENCE, SPACE_ASPECT_FENCE_GATE, SPACE_ASPECT_TRAPDOOR);
		woodCombinations(output, TIME_ASPECT_PLANKS, TIME_ASPECT_SLAB, TIME_ASPECT_STAIRS, TIME_ASPECT_DOOR, TIME_ASPECT_FENCE, TIME_ASPECT_FENCE_GATE, TIME_ASPECT_TRAPDOOR);
		woodCombinations(output, VOID_ASPECT_PLANKS, VOID_ASPECT_SLAB, VOID_ASPECT_STAIRS, VOID_ASPECT_DOOR, VOID_ASPECT_FENCE, VOID_ASPECT_FENCE_GATE, VOID_ASPECT_TRAPDOOR);
		woodCombinations(output, FROST_PLANKS, FROST_SLAB, FROST_STAIRS, FROST_DOOR, FROST_FENCE, FROST_FENCE_GATE, FROST_TRAPDOOR);
		woodCombinations(output, RAINBOW_PLANKS, RAINBOW_SLAB, RAINBOW_STAIRS, RAINBOW_DOOR, RAINBOW_FENCE, RAINBOW_FENCE_GATE, RAINBOW_TRAPDOOR);
		woodCombinations(output, END_PLANKS, END_SLAB, END_STAIRS, END_DOOR, END_FENCE, END_FENCE_GATE, END_TRAPDOOR);
		woodCombinations(output, SHADEWOOD_PLANKS, SHADEWOOD_SLAB, SHADEWOOD_STAIRS, SHADEWOOD_DOOR, SHADEWOOD_FENCE, SHADEWOOD_FENCE_GATE, SHADEWOOD_TRAPDOOR);
	}
}