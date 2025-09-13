package net.enderturret.minestuckcompat.data.recipe;

import static appeng.core.definitions.AEBlocks.*;
import static appeng.core.definitions.AEItems.*;
import static com.mraof.minestuck.api.alchemy.GristTypes.*;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.ApiStatus.Internal;

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

import appeng.core.definitions.AEParts;

@Internal
public final class Ae2Recipes extends AbstractRecipeProvider {

	public Ae2Recipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		final RecipeOutput output = new RenamingRecipeOutput(_recipeOutput, MinestuckCompat.MOD_ID, "ae2")
				.withConditions(new ConfigCondition("ae2"));

		// Materials

		gristCost(CERTUS_QUARTZ_CRYSTAL).grist(COBALT, 4).grist(QUARTZ, 4).build(output);
		sourceGristCost(FLUIX_CRYSTAL).grist(COBALT, 1).multiplier(0.5F).source(CERTUS_QUARTZ_CRYSTAL_CHARGED.get()).source(Items.REDSTONE).source(Items.QUARTZ).build(output);
		gristCost(SKY_STONE_BLOCK).grist(BUILD, 4).grist(TAR, 2).build(output);
		// Work around a Minestuck limitation/bug where the quartz glass recipe is not analyzed because its glass tag contains itself.
		gristCost(QUARTZ_GLASS).grist(BUILD, 1).grist(COBALT, 5).grist(QUARTZ, 5).grist(GARNET, 1).build(output);

		// Matter Condenser

		gristCost(MATTER_BALL).grist(BUILD, 256).build(output);
		gristCost(SINGULARITY).grist(BUILD, 256000).build(output);
		sourceGristCost(QUANTUM_ENTANGLED_SINGULARITY).multiplier(0.5F).source(SINGULARITY.get()).source(ENDER_DUST.get()).build(output);

		// Certus Quarts Buds / Budding Blocks

		gristCost(SMALL_QUARTZ_BUD).grist(COBALT, 3).grist(QUARTZ, 3).build(output);
		gristCost(MEDIUM_QUARTZ_BUD).grist(COBALT, 5).grist(QUARTZ, 5).build(output);
		gristCost(LARGE_QUARTZ_BUD).grist(COBALT, 7).grist(QUARTZ, 7).build(output);
		gristCost(QUARTZ_CLUSTER).grist(COBALT, 9).grist(QUARTZ, 9).build(output);

		gristCost(FLAWLESS_BUDDING_QUARTZ).grist(COBALT, 32).grist(QUARTZ, 32).build(output);
		gristCost(FLAWED_BUDDING_QUARTZ).grist(COBALT, 28).grist(QUARTZ, 28).build(output);
		gristCost(CHIPPED_BUDDING_QUARTZ).grist(COBALT, 24).grist(QUARTZ, 24).build(output);
		gristCost(DAMAGED_BUDDING_QUARTZ).grist(COBALT, 20).grist(QUARTZ, 20).build(output);

		// Patterns

		sourceGristCost(CRAFTING_PATTERN).source(BLANK_PATTERN.get()).build(output);
		sourceGristCost(PROCESSING_PATTERN).source(BLANK_PATTERN.get()).build(output);
		sourceGristCost(SMITHING_TABLE_PATTERN).source(BLANK_PATTERN.get()).build(output);
		sourceGristCost(STONECUTTING_PATTERN).source(BLANK_PATTERN.get()).build(output);

		// P2P Attunement

		sourceGristCost(AEParts.FE_P2P_TUNNEL).source(AEParts.ME_P2P_TUNNEL.get()).build(output);
		sourceGristCost(AEParts.FLUID_P2P_TUNNEL).source(AEParts.ME_P2P_TUNNEL.get()).build(output);
		sourceGristCost(AEParts.ITEM_P2P_TUNNEL).source(AEParts.ME_P2P_TUNNEL.get()).build(output);
		sourceGristCost(AEParts.LIGHT_P2P_TUNNEL).source(AEParts.ME_P2P_TUNNEL.get()).build(output);
		sourceGristCost(AEParts.REDSTONE_P2P_TUNNEL).source(AEParts.ME_P2P_TUNNEL.get()).build(output);

		// Miscellaneous

		sourceGristCost(MYSTERIOUS_CUBE).source(NOT_SO_MYSTERIOUS_CUBE.block().asItem()).build(output);
		gristCost(AEParts.CABLE_ANCHOR.get()).grist(BUILD, 2).build(output);
		sourceGristCost(FACADE).grist(BUILD, 2).source(AEParts.CABLE_ANCHOR.get()).build(output);
		gristCost(NAME_PRESS).grist(RUST, 9).build(output);

		//
		// Combination Recipes
		//

		combination(SKY_STONE_BLOCK).or().input(Items.BASALT).input(lookup("minestuck", "space_aspect_planks")).build(output);
		combination(MYSTERIOUS_CUBE).and().input(SKY_STONE_BLOCK).input(CERTUS_QUARTZ_CRYSTAL).build(output);
		combination(FLAWLESS_BUDDING_QUARTZ).or().input(MSItems.CRUXITE_BLOCK).input(ItemTags.SWORDS).build(output);
	}
}