package net.enderturret.minestuckcompat.data.recipe;

import static com.mraof.minestuck.api.alchemy.GristTypes.*;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.mraof.minestuck.item.MSItems;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.enderturret.minestuckcompat.ConfigCondition;
import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.data.util.AbstractRecipeProvider;
import net.enderturret.minestuckcompat.data.util.RenamingRecipeOutput;

@Internal
public final class LunarNetherRecipes extends AbstractRecipeProvider {

	public LunarNetherRecipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		final RecipeOutput output = new RenamingRecipeOutput(_recipeOutput, MinestuckCompat.MOD_ID, "lunarnether")
				.withConditions(new ConfigCondition("lunarnether"));

		// Based on Sand.
		gristCost(l("lunar_dust")).grist(BUILD, 1).grist(MERCURY, 1).build(output);
		// Based on Cobblestone/Stone.
		gristCost(l("lunar_stone")).grist(BUILD, 2).grist(MERCURY, 1).build(output);
		// Somewhat based on Smooth Basalt and Obsidian.
		gristCost(l("astralith")).grist(BUILD, 6).grist(DIAMOND, 1).grist(QUARTZ, 1).build(output);

		// Loosely based on Iron, plus mercury grist because why not I guess.
		gristCost(l("titanium_ingot")).grist(RUST, 9).grist(MERCURY, 9).build(output);
		gristCost(l("raw_ilmenite")).grist(RUST, 1).grist(MERCURY, 1).build(output);
		gristCost(l("ilmenite_ore")).grist(RUST, 1 * 3).grist(MERCURY, 1 * 3).build(output);

		combination(l("ilmenite_ore")).and().namedInput(l("titanium_ingot")).input(l("lunar_stone")).build(output);
		combination(l("ilmenite_ore")).and().namedInput(Items.IRON_INGOT).input(l("lunar_stone")).build(output);

		// I can't decide whether these should be Moonstone or Stone. Moonstone is probably fine.
		combination(l("titanium_block")).or().input(l("titanium_ingot")).input(l("lunar_stone")).build(output);
		combination(l("raw_ilmenite_block")).and().input(l("raw_ilmenite")).input(l("lunar_stone")).build(output);

		combination(l("lunar_dust")).or().input(Items.SAND).input(MSItems.MOON_CAKE).build(output);
		combination(l("lunar_stone")).and().namedInput(l("lunar_dust")).input(Items.STONE).build(output);
		combination(l("lunar_stone")).or().namedInput(MSItems.MOON_CAKE).input(Items.STONE).build(output);
		combination(l("astralith")).and().input(Items.SMOOTH_BASALT).input(l("lunar_stone")).build(output);

		combination(l("titanium_trapdoor")).or().input(l("titanium_door")).input(l("cut_titanium_slab")).build(output);
	}

	private static Item l(String id) {
		return lookup("lunarnether", id);
	}
}