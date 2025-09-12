package net.enderturret.minestuckcompat.data.recipe;

import static blusunrize.immersiveengineering.common.register.IEBlocks.MetalDecoration.*;
import static blusunrize.immersiveengineering.common.register.IEBlocks.Metals.*;
import static blusunrize.immersiveengineering.common.register.IEBlocks.StoneDecoration.*;
import static blusunrize.immersiveengineering.common.register.IEBlocks.WoodenDecoration.*;
import static blusunrize.immersiveengineering.common.register.IEItems.Ingredients.*;
import static blusunrize.immersiveengineering.common.register.IEItems.Metals.*;
import static blusunrize.immersiveengineering.common.register.IEItems.Misc.*;
import static blusunrize.immersiveengineering.common.register.IEItems.Tools.*;
import static blusunrize.immersiveengineering.common.register.IEItems.Weapons.*;
import static com.mraof.minestuck.api.alchemy.GristTypes.*;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.ApiStatus.Internal;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

import net.neoforged.neoforge.common.Tags;

import net.enderturret.minestuckcompat.ConfigCondition;
import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.api.data.AbstractRecipeProvider;
import net.enderturret.minestuckcompat.api.data.RenamingRecipeOutput;

import blusunrize.immersiveengineering.api.EnumMetals;
import blusunrize.immersiveengineering.api.Lib;
import blusunrize.immersiveengineering.api.shader.ShaderRegistry;
import blusunrize.immersiveengineering.common.blocks.wooden.TreatedWoodStyles;
import blusunrize.immersiveengineering.common.items.ShaderItem;

@Internal
public final class ImmersiveEngineeringRecipes extends AbstractRecipeProvider {

	public ImmersiveEngineeringRecipes(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput _recipeOutput) {
		final RecipeOutput output = new RenamingRecipeOutput(_recipeOutput, MinestuckCompat.MOD_ID, "immersiveengineering")
				.withConditions(modLoaded("immersiveengineering"), new ConfigCondition("immersiveengineering"));
		final String modId = "immersiveengineering";

		gristCost(HEMP_FIBER).grist(AMBER, 2).grist(CAULK, 2).build(output);
		gristCost(HEMP_SEEDS).grist(AMBER, 1).grist(CAULK, 1).build(output);

		gristCost(TREATED_WOOD.get(TreatedWoodStyles.HORIZONTAL)).grist(BUILD, 2).grist(TAR, 2).build(output);
		sourceGristCost(COAL_COKE).multiplier(2).source(Items.COAL).build(output);
		sourceGristCost(INGOTS.get(EnumMetals.STEEL)).source(Items.IRON_INGOT).source(COAL_COKE.asItem()).build(output);
		sourceGristCost(SLAG).grist(IODINE, 1).grist(RUST, 3).source(COAL_COKE.asItem()).build(output);
		gristCost(DUST_SALTPETER).grist(CHALK, 2).build(output);
		gristCost(DUST_SULFUR).grist(SULFUR, 6).build(output);
		gristCost(DUST_WOOD).grist(BUILD, 1).grist(MERCURY, 1).build(output);
		sourceGristCost(DUST_HOP_GRAPHITE).grist(MERCURY, 1).multiplier(8).source(DUST_COKE.asItem()).build(output);
		sourceGristCost(CLINKER_BRICK).grist(TAR, 2).source(Items.BRICKS).build(output);

		sourceGristCost(lookup(modId, "redstone_acid_bucket")).grist(SULFUR, 4).grist(RUST, 27).multiplier(4).source(Items.REDSTONE).build(output);
		sourceGristCost(lookup(modId, "creosote_bucket")).grist(TAR, 16).source(Items.BUCKET).build(output);
		sourceGristCost(lookup(modId, "plantoil_bucket")).grist(AMBER, 8).source(Items.BUCKET).build(output);
		sourceGristCost(lookup(modId, "concrete_bucket")).source(CONCRETE.asItem()).source(Items.BUCKET).build(output);
		sourceGristCost(lookup(modId, "ethanol_bucket")).grist(AMBER, 24).grist(SHALE, 24).source(Items.BUCKET).build(output);
		sourceGristCost(lookup(modId, "biodiesel_bucket")).grist(AMBER, 1984).grist(SHALE, 1488).grist(CHALK, 124).source(Items.BUCKET).build(output);
		sourceGristCost(lookup(modId, "high_power_biodiesel_bucket")).grist(COBALT, 4).source(lookup(modId, "biodiesel_bucket")).source(Items.BLAZE_POWDER).build(output);
		sourceGristCost(lookup(modId, "herbicide_bucket")).grist(SHALE, 14).grist(MERCURY, 2).grist(SULFUR, 12).source(lookup(modId, "ethanol_bucket")).build(output);
		sourceGristCost(lookup(modId, "acetaldehyde_bucket")).grist(MERCURY, 1000).grist(RUST, 1500).source(lookup(modId, "ethanol_bucket")).build(output);
		sourceGristCost(lookup(modId, "phenolic_resin_bucket")).grist(TAR, 16).grist(MERCURY, 1500).grist(RUST, 2250).grist(AMBER, 36).grist(SHALE, 36).source(Items.BUCKET).build(output);

		gristCost(DUROPLAST).grist(TAR, 16).grist(MERCURY, 1500).grist(RUST, 2250).grist(AMBER, 36).grist(SHALE, 36).build(output);
		sourceGristCost(DUROPLAST_PLATE).multiplier(0.25F).source(DUROPLAST.asItem()).build(output);
		sourceGristCost(GRINDINGDISK).grist(BUILD, 12).grist(AMBER, 16).grist(CAULK, 16).grist(CHALK, 18).grist(MERCURY, 6).grist(RUST, 36).multiplier(0.5F).source(DUROPLAST.asItem()).build(output);
		sourceGristCost(PLATE_HOP_GRAPHITE).grist(TAR, 4).source(DUST_HOP_GRAPHITE.asItem()).build(output);
		sourceGristCost(GRAPHITE_ELECTRODE).grist(TAR, 16).multiplier(4).source(DUST_HOP_GRAPHITE.asItem()).build(output);

		// I really can't be bothered to add interpreters for these single recipes.
		sourceGristCost(REVOLVER).source(GUNPART_HAMMER.asItem()).source(WOODEN_GRIP.asItem()).source(GUNPART_DRUM.asItem()).source(COMPONENT_STEEL.asItem()).source(GUNPART_BARREL.asItem()).build(output);
		sourceGristCost(SURVEY_TOOLS).grist(BUILD, 3).grist(AMBER, 16 * 3).grist(CAULK, 16 * 3).source(Items.WRITABLE_BOOK).source(Items.GLASS_BOTTLE).source(HAMMER.asItem()).build(output);
		sourceGristCost(MAINTENANCE_KIT).grist(BUILD, 3).grist(AMBER, 16 * 3).grist(CAULK, 16 * 3).source(SCREWDRIVER.asItem()).source(WIRECUTTER.asItem()).build(output);

		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.BLACK).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_BLACK).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.BLUE).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_BLUE).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.BROWN).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_BROWN).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.CYAN).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_CYAN).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.GRAY).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_GRAY).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.GREEN).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_GREEN).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.LIGHT_BLUE).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_LIGHT_BLUE).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.LIGHT_GRAY).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_LIGHT_GRAY).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.LIME).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_LIME).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.MAGENTA).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_MAGENTA).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.ORANGE).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_ORANGE).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.PINK).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_PINK).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.PURPLE).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_PURPLE).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.RED).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_RED).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.WHITE).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_WHITE).build(output);
		sourceGristCost(COLORED_SHEETMETAL.get(DyeColor.YELLOW).get()).source(SHEETMETAL.get(EnumMetals.IRON).get().asItem()).source(Tags.Items.DYES_YELLOW).build(output);

		// Shaders

		gristCost(SHADER_BAG.get(Rarity.COMMON)).grist(BUILD, 16).grist(MARBLE, 4).build(output);
		gristCost(SHADER_BAG.get(Rarity.UNCOMMON)).grist(BUILD, 16).grist(QUARTZ, 4).build(output);
		gristCost(SHADER_BAG.get(Rarity.RARE)).grist(BUILD, 16).grist(RUBY, 4).build(output);
		gristCost(SHADER_BAG.get(Rarity.EPIC)).grist(BUILD, 16).grist(DIAMOND, 4).build(output);
		gristCost(SHADER_BAG.get(Lib.RARITY_MASTERWORK.getValue())).grist(BUILD, 16).grist(ZILLIUM, 4).build(output);

		for (var shader : SHADERS.entrySet()) {
			final ShaderRegistry.ShaderRegistryEntry entry = ShaderRegistry.shaderRegistry.get(shader.getKey());
			final ShaderItem item = shader.getValue().get();
			switch (entry.rarity) {
				case COMMON -> gristCost(item).grist(BUILD, 16).grist(MARBLE, 4).build(output);
				case UNCOMMON -> gristCost(item).grist(BUILD, 16).grist(QUARTZ, 4).build(output);
				case RARE -> gristCost(item).grist(BUILD, 16).grist(RUBY, 4).build(output);
				case EPIC -> gristCost(item).grist(BUILD, 16).grist(DIAMOND, 4).build(output);
				default -> {
					if (entry.rarity == Lib.RARITY_MASTERWORK.getValue())
						gristCost(item).grist(BUILD, 16).grist(ZILLIUM, 4).build(output);
					else
						MinestuckCompat.LOGGER.error("Unknown rarity {}", entry.rarity);
				}
			}
		}

		//
		// Combination Recipes
		//

	}
}