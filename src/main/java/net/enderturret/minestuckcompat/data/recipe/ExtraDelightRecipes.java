package net.enderturret.minestuckcompat.data.recipe;

import static com.lance5057.extradelight.ExtraDelightItems.*;
import static com.mraof.minestuck.api.alchemy.GristTypes.*;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.lance5057.extradelight.ExtraDelightTags;
import com.lance5057.extradelight.data.recipebuilders.OvenRecipeBuilder;
import com.lance5057.extradelight.modules.Fermentation;
import com.lance5057.extradelight.modules.SummerCitrus;
import com.mraof.minestuck.item.MSItems;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import net.enderturret.minestuckcompat.ConfigCondition;
import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.data.util.AbstractRecipeProvider;
import net.enderturret.minestuckcompat.data.util.RenamingRecipeOutput;

@Internal
public final class ExtraDelightRecipes extends AbstractRecipeProvider {

	public ExtraDelightRecipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		final RecipeOutput output = new RenamingRecipeOutput(_recipeOutput, MinestuckCompat.MOD_ID, "extradelight")
				.withConditions(new ConfigCondition("extradelight"));

		containerGristCost(COCOA_BUTTER_FLUID_BUCKET).grist(AMBER, 20).grist(IODINE, 20 * 3).grist(TAR, 20).build(output);
		containerGristCost(YEAST).grist(AMBER, 6).grist(IODINE, 6).build(output);
		containerGristCost(COOKING_OIL).grist(AMBER, 4).build(output);
		containerGristCost(PEANUT_BUTTER_BOTTLE).grist(IODINE, 4).build(output);
		containerGristCost(MARSHMALLOW_FLUFF_BOTTLE).grist(AMBER, 3).grist(IODINE, 2).grist(COBALT, 1).grist(MERCURY, 1).build(output);
		gristCost(Fermentation.SALT).grist(CHALK, 2).build(output);
		gristCost(CORN_ON_COB).grist(AMBER, 2).grist(IODINE, 1).build(output);
		gristCost(CORN_HUSK).grist(AMBER, 2).build(output);
		gristCost(CORN_SILK).grist(IODINE, 1).build(output);
		containerGristCost(SummerCitrus.EGG_WHITE).grist(AMBER, 2).build(output);
		gristCost(SummerCitrus.EGG_YOLK).grist(AMBER, 3).build(output);
		containerGristCost(CURRY_POWDER).grist(IODINE, 4).grist(RUST, 4).grist(GARNET, 4).build(output);

		// Crops

		gristCost(GINGER).grist(AMBER, 2).grist(IODINE, 2).build(output);
		gristCost(PEANUTS_IN_SHELL).grist(AMBER, 1).grist(IODINE, 3).build(output);
		gristCost(CHILI).grist(AMBER, 1).grist(GARNET, 3).build(output);
		gristCost(MALLOW_ROOT).grist(AMBER, 2).grist(IODINE, 2).build(output);
		gristCost(GARLIC).grist(AMBER, 1).grist(IODINE, 1).grist(CHALK, 2).build(output);
		gristCost(Fermentation.CUCUMBER).grist(AMBER, 3).grist(IODINE, 1).build(output);
		gristCost(Fermentation.SOYBEAN_POD).grist(AMBER, 1).grist(IODINE, 3).build(output);
		gristCost(CORN_SEEDS).grist(AMBER, 2).grist(IODINE, 1).build(output);
		gristCost(UNSHUCKED_CORN).grist(AMBER, 4).grist(IODINE, 1).build(output);

		sourceGristCost(WILD_GINGER).grist(AMBER, 2).source(GINGER.asItem()).build(output);
		sourceGristCost(WILD_PEANUT_BLOCK).grist(AMBER, 2).source(PEANUTS_IN_SHELL.asItem()).build(output);
		sourceGristCost(WILD_CHILI_BLOCK).grist(AMBER, 2).source(CHILI.asItem()).build(output);
		sourceGristCost(WILD_MALLOW_ROOT_BLOCK).grist(AMBER, 2).source(MALLOW_ROOT.asItem()).build(output);
		sourceGristCost(WILD_GARLIC_BLOCK).grist(AMBER, 2).source(GARLIC.asItem()).build(output);
		sourceGristCost(Fermentation.WILD_CUCUMBER_ITEM).grist(AMBER, 2).source(Fermentation.CUCUMBER.asItem()).build(output);
		sourceGristCost(Fermentation.WILD_SOYBEAN_ITEM).grist(AMBER, 2).source(Fermentation.SOYBEAN_POD.asItem()).build(output);

		gristCost(SummerCitrus.LEMON).grist(AMBER, 4).build(output);
		gristCost(SummerCitrus.LIME).grist(AMBER, 3).grist(CAULK, 1).build(output);
		gristCost(SummerCitrus.ORANGE).grist(AMBER, 3).grist(IODINE, 1).build(output);
		gristCost(SummerCitrus.GRAPEFRUIT).grist(AMBER, 3).grist(SULFUR, 1).build(output);
		gristCost(COFFEE_CHERRIES).grist(AMBER, 2).grist(GARNET, 2).build(output);
		gristCost(MINT).grist(AMBER, 3).grist(IODINE, 1).build(output);
		gristCost(HAZELNUTS_IN_SHELL).grist(AMBER, 1).grist(IODINE, 3).build(output);

		containerGristCost(Fermentation.PICKLE_JUICE).grist(AMBER, 3).grist(IODINE, 2).build(output);
		containerGristCost(SummerCitrus.LEMON_JUICE).grist(AMBER, 3).build(output);
		containerGristCost(SummerCitrus.LIME_JUICE).grist(AMBER, 2).grist(CAULK, 1).build(output);
		containerGristCost(SummerCitrus.ORANGE_JUICE).grist(AMBER, 2).grist(IODINE, 1).build(output);
		containerGristCost(SummerCitrus.GRAPEFRUIT_JUICE).grist(AMBER, 2).grist(SULFUR, 1).build(output);

		sourceGristCost(SummerCitrus.BAKED_ALASKA_ITEM).grist(TAR, 4).source(SummerCitrus.RAW_BAKED_ALASKA_ITEM.asItem()).build(output);
		sourceGristCost(TARTE_TATIN).source(TARTE_TATIN_IN_PAN.asItem()).build(output);

		// For whatever reason, these aren't in the leaves item tag.
		gristCost(APPLE_LEAVES).grist(BUILD, 1).build(output);
		gristCost(CINNAMON_LEAVES).grist(BUILD, 1).build(output);
		gristCost(HAZELNUT_LEAVES).grist(BUILD, 1).build(output);
		gristCost(SummerCitrus.LEMON_LEAVES_ITEM).grist(BUILD, 1).build(output);
		gristCost(SummerCitrus.LIME_LEAVES_ITEM).grist(BUILD, 1).build(output);
		gristCost(SummerCitrus.ORANGE_LEAVES_ITEM).grist(BUILD, 1).build(output);
		gristCost(SummerCitrus.GRAPEFRUIT_LEAVES_ITEM).grist(BUILD, 1).build(output);

		gristCost(HAZELNUT_PETAL_LITTER_ITEM).grist(CHALK, 1).grist(IODINE, 1).build(output);
		gristCost(APPLE_PETAL_LITTER_ITEM).grist(CHALK, 1).grist(IODINE, 1).build(output);
		gristCost(SummerCitrus.LEMON_PETAL_LITTER_ITEM).grist(CHALK, 1).grist(IODINE, 1).build(output);
		gristCost(SummerCitrus.LIME_PETAL_LITTER_ITEM).grist(CHALK, 1).grist(IODINE, 1).build(output);
		gristCost(SummerCitrus.ORANGE_PETAL_LITTER_ITEM).grist(CHALK, 1).grist(IODINE, 1).build(output);
		gristCost(SummerCitrus.GRAPEFRUIT_PETAL_LITTER_ITEM).grist(CHALK, 1).grist(IODINE, 1).build(output);

		// Miscellaneous
		sourceGristCost(PESTLE_AMETHYST).source(Items.AMETHYST_SHARD).source(Items.STICK).build(output);
		sourceGristCost(PESTLE_GILDED_BLACKSTONE).source(Items.GILDED_BLACKSTONE).source(Items.STICK).build(output);

		//
		// Combination Recipes
		//

		combination(WILD_GINGER).or().input(Items.SHORT_GRASS).input(GINGER).build(output);
		combination(WILD_PEANUT_BLOCK).or().input(Items.SHORT_GRASS).input(PEANUTS_IN_SHELL).build(output);
		combination(WILD_CHILI_BLOCK).or().input(Items.SHORT_GRASS).input(CHILI).build(output);
		combination(WILD_MALLOW_ROOT_BLOCK).or().input(Items.SHORT_GRASS).input(MALLOW_ROOT).build(output);
		combination(WILD_GARLIC_BLOCK).or().input(Items.SHORT_GRASS).input(GARLIC).build(output);
		combination(Fermentation.WILD_CUCUMBER_ITEM).or().input(Items.SHORT_GRASS).input(Fermentation.CUCUMBER).build(output);
		combination(Fermentation.WILD_SOYBEAN_ITEM).or().input(Items.SHORT_GRASS).input(Fermentation.SOYBEAN_POD).build(output);

		saplingCombinations(output, APPLE_SAPLING, FRUIT_LOG, APPLE_LEAVES);
		saplingCombinations(output, CINNAMON_SAPLING, CINNAMON_LOG, CINNAMON_LEAVES);
		saplingCombinations(output, HAZELNUT_SAPLING, FRUIT_LOG, HAZELNUT_LEAVES);
		saplingCombinations(output, SummerCitrus.LEMON_SAPLING_ITEM, FRUIT_LOG, SummerCitrus.LEMON_LEAVES);
		saplingCombinations(output, SummerCitrus.LIME_SAPLING_ITEM, FRUIT_LOG, SummerCitrus.LIME_LEAVES);
		saplingCombinations(output, SummerCitrus.ORANGE_SAPLING_ITEM, FRUIT_LOG, SummerCitrus.ORANGE_LEAVES);
		saplingCombinations(output, SummerCitrus.GRAPEFRUIT_SAPLING_ITEM, FRUIT_LOG, SummerCitrus.GRAPEFRUIT_LEAVES);

		combination(APPLE_SAPLING).or().namedInput(ItemTags.SAPLINGS).input(Items.APPLE).build(output);
		combination(CINNAMON_SAPLING).or().namedInput(ItemTags.SAPLINGS).input(CINNAMON_BARK).build(output);
		combination(HAZELNUT_SAPLING).or().namedInput(ItemTags.SAPLINGS).input(HAZELNUTS_IN_SHELL).build(output);
		combination(SummerCitrus.LEMON_SAPLING_ITEM).or().namedInput(ItemTags.SAPLINGS).input(SummerCitrus.LEMON).build(output);
		combination(SummerCitrus.LIME_SAPLING_ITEM).or().namedInput(ItemTags.SAPLINGS).input(SummerCitrus.LIME).build(output);
		combination(SummerCitrus.ORANGE_SAPLING_ITEM).or().namedInput(ItemTags.SAPLINGS).input(SummerCitrus.ORANGE).build(output);
		combination(SummerCitrus.GRAPEFRUIT_SAPLING_ITEM).or().namedInput(ItemTags.SAPLINGS).input(SummerCitrus.GRAPEFRUIT).build(output);

		//
		// Oven Recipes
		//

		cake(output, MSItems.APPLE_CAKE, Items.APPLE);
		cake(output, MSItems.BLUE_CAKE, MSItems.GLOWING_MUSHROOM);
		cake(output, MSItems.COLD_CAKE, Items.BLUE_ICE);
		cake(output, MSItems.RED_CAKE, Items.MELON_SLICE);
		cake(output, MSItems.HOT_CAKE, Items.LAVA_BUCKET);
		cake(output, MSItems.FUCHSIA_CAKE, Ingredient.of(ItemTags.FISHES), Ingredient.of(ItemTags.FISHES));
		cake(output, MSItems.NEGATIVE_CAKE, Items.FERMENTED_SPIDER_EYE);
		cake(output, MSItems.CARROT_CAKE, Items.CARROT);
		cake(output, MSItems.CHOCOLATEY_CAKE, MSItems.CHOCOLATE_BEETLE);
		cake(output, MSItems.MOON_CAKE, Ingredient.of(MSItems.SBURB_CODE), Ingredient.of(MSItems.GRIMOIRE));

		oven(MSItems.REVERSE_CAKE, SQUARE_PAN, false)
		.addIngredient(Items.EGG).addIngredient(ExtraDelightTags.SWEETENER, 2)
		.addIngredient(c("foods/milk"), 3).addIngredient(c("flour"), 3)
		.save(output, ResourceLocation.fromNamespaceAndPath(MinestuckCompat.MOD_ID, "reverse_cake"));
	}

	private static void cake(RecipeOutput recipeOutput, ItemLike cake, ItemLike special) {
		cake(recipeOutput, cake, Ingredient.of(special), Ingredient.of(special));
	}

	@SuppressWarnings("deprecation")
	private static void cake(RecipeOutput recipeOutput, ItemLike cake, Ingredient special1, Ingredient special2) {
		oven(cake, SQUARE_PAN, false)
		.addIngredient(c("flour"), 3).addIngredient(special1).addIngredient(c("foods/milk")).addIngredient(special2)
		.addIngredient(ExtraDelightTags.SWEETENER).addIngredient(Items.EGG).addIngredient(ExtraDelightTags.SWEETENER)
		.save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MinestuckCompat.MOD_ID, cake.asItem().builtInRegistryHolder().getKey().location().getPath()));
	}

	private static OvenRecipeBuilder oven(ItemLike output, ItemLike container, boolean consumeContainer) {
		return OvenRecipeBuilder.OvenRecipe(new ItemStack(output), 800, 1, new ItemStack(container), consumeContainer);
	}
}