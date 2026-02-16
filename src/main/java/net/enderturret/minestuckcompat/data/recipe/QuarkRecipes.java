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

		gristCost(q("red_corundum")).grist(GARNET, 4).grist(AMETHYST, 12).build(output);
		gristCost(q("red_corundum_cluster")).grist(GARNET, 3).grist(AMETHYST, 9).build(output);
		gristCost(q("orange_corundum")).grist(IODINE, 4).grist(AMETHYST, 12).build(output);
		gristCost(q("orange_corundum_cluster")).grist(IODINE, 3).grist(AMETHYST, 9).build(output);
		gristCost(q("yellow_corundum")).grist(SULFUR, 4).grist(AMETHYST, 12).build(output);
		gristCost(q("yellow_corundum_cluster")).grist(SULFUR, 3).grist(AMETHYST, 9).build(output);
		gristCost(q("green_corundum")).grist(URANIUM, 4).grist(AMETHYST, 12).build(output);
		gristCost(q("green_corundum_cluster")).grist(URANIUM, 3).grist(AMETHYST, 9).build(output);
		gristCost(q("blue_corundum")).grist(BUILD, 4).grist(AMETHYST, 12).build(output);
		gristCost(q("blue_corundum_cluster")).grist(BUILD, 3).grist(AMETHYST, 9).build(output);
		gristCost(q("indigo_corundum")).grist(COBALT, 4).grist(AMETHYST, 12).build(output);
		gristCost(q("indigo_corundum_cluster")).grist(COBALT, 3).grist(AMETHYST, 9).build(output);
		gristCost(q("violet_corundum")).grist(MARBLE, 4).grist(AMETHYST, 12).build(output);
		gristCost(q("violet_corundum_cluster")).grist(MARBLE, 3).grist(AMETHYST, 9).build(output);
		gristCost(q("white_corundum")).grist(CHALK, 4).grist(AMETHYST, 12).build(output);
		gristCost(q("white_corundum_cluster")).grist(CHALK, 3).grist(AMETHYST, 9).build(output);
		gristCost(q("black_corundum")).grist(TAR, 4).grist(AMETHYST, 12).build(output);
		gristCost(q("black_corundum_cluster")).grist(TAR, 3).grist(AMETHYST, 9).build(output);

		// Glass shards
		// So we have two options for these:
		// a) make them all 1/4 their corresponding glass (which in practice makes them equal to the blocks in terms of grist)
		// b) make them all cost 1 build (which loses the dye component)
		// Neither of these are great options. Either you can easily duplicate grist (block->shard) or you can easily obtain colored glass blocks (shard->block).
		// Let's go with option b) as the lesser evil.

		gristCost(tag("quark", "shards")).grist(BUILD, 1).build(output);

		gristCost(q("dirty_glass")).grist(BUILD, 1).grist(RUST, 1).build(output);

		// Stone types

		gristCost(q("jasper")).grist(BUILD, 2).grist(RUST, 1).build(output);
		gristCost(q("limestone")).grist(BUILD, 2).grist(CHALK, 1).build(output);
		gristCost(q("shale")).grist(BUILD, 2).grist(SHALE, 1).build(output);
		gristCost(q("permafrost")).grist(BUILD, 2).grist(COBALT, 2).build(output); // Is permafrost even a stone?

		gristCost(q("myalite")).grist(BUILD, 4).grist(SHALE, 3).build(output); // Based on the cost for End Stone.
		gristCost(q("dusky_myalite")).grist(BUILD, 4).grist(SHALE, 3).grist(TAR, 2).build(output);
		gristCost(q("myalite_crystal")).grist(SHALE, 3).grist(AMETHYST, 9).build(output); // Based on the cost for Amethyst Blocks.

		// Parrot eggs

		gristCost(q("egg_parrot_blue")).grist(AMBER, 5).build(output);
		gristCost(q("egg_parrot_gray")).grist(AMBER, 5).build(output);
		gristCost(q("egg_parrot_green")).grist(AMBER, 5).build(output);
		gristCost(q("egg_parrot_red_blue")).grist(AMBER, 5).build(output);
		gristCost(q("egg_parrot_yellow_blue")).grist(AMBER, 5).build(output);
	}

	private static Item q(String id) {
		return lookup("quark", id);
	}
}