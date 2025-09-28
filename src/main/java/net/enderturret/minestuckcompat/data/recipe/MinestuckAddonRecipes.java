package net.enderturret.minestuckcompat.data.recipe;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.ApiStatus.Internal;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;

import net.enderturret.minestuckcompat.data.util.AbstractRecipeProvider;

@Internal
public final class MinestuckAddonRecipes extends AbstractRecipeProvider {

	public MinestuckAddonRecipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		//
		// Minestuck Extended/Expanded
		//

		remove("minecraft", "grist_costs/armadillo_scute");
		remove("minecraft", "grist_costs/axolotl_bucket");
		remove("minecraft", "grist_costs/breeze_rod");
		remove("minecraft", "grist_costs/dead_horn_coral");
		remove("minecraft", "grist_costs/dead_horn_coral_block");
		remove("minecraft", "grist_costs/dead_horn_coral_fan");
		remove("minecraft", "grist_costs/frogspawn");
		remove("minecraft", "grist_costs/heavy_core");
		remove("minecraft", "grist_costs/horn_coral");
		remove("minecraft", "grist_costs/horn_coral_block");
		remove("minecraft", "grist_costs/horn_coral_fan");
		remove("minecraft", "grist_costs/music_disc_creator");
		remove("minecraft", "grist_costs/music_disc_creator_music_box");
		remove("minecraft", "grist_costs/music_disc_otherside");
		remove("minecraft", "grist_costs/music_disc_precipice");
		remove("minecraft", "grist_costs/piglin_head");
		remove("minecraft", "grist_costs/tadpole_bucket");

		remove("create", "combinations/blaze_burner");
		remove("create", "combinations/zinc_ore");
		remove("create", "grist_costs/andesite_casing");
		remove("create", "grist_costs/asurine");
		remove("create", "grist_costs/blaze_burner");
		remove("create", "grist_costs/blaze_cake");
		remove("create", "grist_costs/blaze_cake_base");
		remove("create", "grist_costs/brass_casing");
		remove("create", "grist_costs/brass_sheet");
		remove("create", "grist_costs/cardboard");
		remove("create", "grist_costs/copper_casing");
		remove("create", "grist_costs/copper_sheet");
		remove("create", "grist_costs/crimsite");
		remove("create", "grist_costs/dough");
		remove("create", "grist_costs/electron_tube");
		remove("create", "grist_costs/golden_sheet");
		remove("create", "grist_costs/iron_sheet");
		remove("create", "grist_costs/limestone");
		remove("create", "grist_costs/ochrum");
		remove("create", "grist_costs/pulp");
		remove("create", "grist_costs/veridium");

		remove("farmersdelight", "grist_costs/apple_pie_slice");
		remove("farmersdelight", "grist_costs/bacon");
		remove("farmersdelight", "grist_costs/brown_mushroom_colony");
		remove("farmersdelight", "grist_costs/cabbage");
		remove("farmersdelight", "grist_costs/cabbage_leaf");
		remove("farmersdelight", "grist_costs/cabbage_seeds");
		remove("farmersdelight", "grist_costs/cake_slice");
		remove("farmersdelight", "grist_costs/chicken_cuts");
		remove("farmersdelight", "grist_costs/chocolate_pie_slice");
		remove("farmersdelight", "grist_costs/cod_slice");
		remove("farmersdelight", "grist_costs/ham");
		remove("farmersdelight", "grist_costs/honey_glazed_ham_block");
		remove("farmersdelight", "grist_costs/minced_beef");
		remove("farmersdelight", "grist_costs/mutton_chops");
		remove("farmersdelight", "grist_costs/onion");
		remove("farmersdelight", "grist_costs/pumpkin_slice");
		remove("farmersdelight", "grist_costs/raw_pasta");
		remove("farmersdelight", "grist_costs/red_mushroom_colony");
		remove("farmersdelight", "grist_costs/rice");
		remove("farmersdelight", "grist_costs/rice_roll_medley_block");
		remove("farmersdelight", "grist_costs/rotten_tomato");
		remove("farmersdelight", "grist_costs/salmon_slice");
		remove("farmersdelight", "grist_costs/sandy_shrub");
		remove("farmersdelight", "grist_costs/smoked_ham");
		remove("farmersdelight", "grist_costs/straw");
		remove("farmersdelight", "grist_costs/tomato");
		remove("farmersdelight", "grist_costs/tomato_seeds");
		remove("farmersdelight", "grist_costs/tree_bark");
		remove("farmersdelight", "grist_costs/wild_beetroots");
		remove("farmersdelight", "grist_costs/wild_cabbages");
		remove("farmersdelight", "grist_costs/wild_carrots");
		remove("farmersdelight", "grist_costs/wild_onions");
		remove("farmersdelight", "grist_costs/wild_potatoes");
		remove("farmersdelight", "grist_costs/wild_rice");
		remove("farmersdelight", "grist_costs/wild_tomatoes");
	}
}