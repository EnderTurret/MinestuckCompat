package net.enderturret.minestuckcompat.alchemy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedCostHandler.SourceEntry;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.recipe.GristCostRecipe;
import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratedCostProvider;
import com.mraof.minestuck.api.alchemy.recipe.generator.GristCostResult;
import com.mraof.minestuck.item.crafting.MSRecipeTypes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.GameMasterBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.MapExtendingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;

import net.neoforged.fml.ModList;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.MinestuckCompatConfig;
import net.enderturret.minestuckcompat.alchemy.rechiseled.RechiseledGristCosts;
import net.enderturret.minestuckcompat.mixin.GeneratorProcessAccess;

@Internal
public final class MixinHooks {

	public static Map<Item, GristSet.Immutable> generatedCosts;

	public static void generateAdditionalGristCosts(GeneratorProcessAccess access) {
		MinestuckCompat.LOGGER.info("Discovering additional grist costs!");

		final BiConsumer<Item, GeneratedCostProvider> registrar = (item, provider) -> {
			access.minestuckcompat$getProvidersByItem().computeIfAbsent(item, k -> new ArrayList<>()).add(provider);
			access.minestuckcompat$getProviders().add(provider);
		};

		final BiConsumer<Item, @Nullable GristCostResult> callback = (item, result) -> {
			if (result == null) return;
			generatedCosts.putIfAbsent(item, result.cost().asImmutable());
		};

		if (ModList.get().isLoaded("rechiseled"))
			RechiseledGristCosts.generateAdditionalGristCosts(registrar, callback);
	}

	@SuppressWarnings("deprecation")
	public static void checkItemsWithoutGristCost(RecipeManager recipeManager) {
		if (!MinestuckCompatConfig.common().dumpGristlessItems.getAsBoolean()) return;

		final Set<String> badVanillaItems = Set.of(
				// Technical items
				"air", "filled_map",
				// Cheat/debug items
				"command_block_minecart", "debug_stick", "spawner", "trial_spawner", "structure_void",
				// Unobtainable items
				"bedrock", "light", "knowledge_book", "petrified_oak_slab", "reinforced_deepslate", "end_portal_frame", "frogspawn", "budding_amethyst",
				"vault",
				// Not traditionally obtainable
				"farmland", "dirt_path", "chorus_plant", "infested_chiseled_stone_bricks",
				"infested_cracked_stone_bricks", "infested_deepslate", "infested_mossy_stone_bricks", "infested_stone", "infested_stone_bricks",
				// Items that would be useless alchemized
				"axolotl_bucket", "tadpole_bucket", "potion", "splash_potion", "lingering_potion", "suspicious_stew", "tipped_arrow", "written_book"
				);

		final Set<String> badMekanismItems = Set.of(
				"bounding_block", "creative_bin", "creative_chemical_tank", "creative_energy_cube", "creative_fluid_tank"
				);

		final Set<String> badAe2Items = Set.of(
				"debug_card", "debug_cube_gen", "debug_energy_gen", "debug_eraser", "debug_item_gen", "debug_meteorite_placer", "debug_phantom_node",
				"debug_replicator_card", "cable_bus", "matrix_frame", "paint", "wrapped_generic_stack", "missing_content",
				"creative_energy_cell", "creative_storage_cell"
				);

		final Set<String> badCreateItems = Set.of(
				// Technical items
				"andesite_encased_cogwheel", "andesite_encased_large_cogwheel", "andesite_encased_shaft",
				"brass_encased_cogwheel", "brass_encased_large_cogwheel", "brass_encased_shaft",
				"chest_minecart_contraption", "furnace_minecart_contraption", "minecart_contraption",
				"elevator_contact", "copper_backtank_placeable", "netherite_backtank_placeable", "shopping_list", "schematic",
				// Cheat items
				"creative_blaze_cake", "creative_crate", "creative_fluid_tank", "creative_motor", "handheld_worldshaper",
				// Unobtainable items
				"chromatic_compound", "refined_radiance", "refined_radiance_casing", "shadow_steel", "shadow_steel_casing",
				// Obtainable technical items
				"cardboard_package_10x12", "cardboard_package_10x8", "cardboard_package_12x10", "cardboard_package_12x12",
				"rare_creeper_package", "rare_darcy_package", "rare_evan_package", "rare_jinx_package", "rare_kryppers_package",
				"rare_simi_package", "rare_starlotte_package", "rare_thunder_package", "rare_up_package", "rare_vector_package",
				// We already provide grist costs for these based on their parent metals.
				"crushed_raw_aluminum", "crushed_raw_nickel", "crushed_raw_platinum", "crushed_raw_quicksilver", "crushed_raw_silver"
				);

		final var sherds = BuiltInRegistries.ITEM.getTag(ItemTags.DECORATED_POT_SHERDS).orElseThrow();

		final List<ResourceLocation> items = new ArrayList<>();
		final List<ResourceLocation> collectibles = new ArrayList<>();

		for (Item item : BuiltInRegistries.ITEM) {
			if (item instanceof GameMasterBlockItem || item instanceof SpawnEggItem) continue;

			final ResourceLocation id = item.builtInRegistryHolder().getKey().location();

			if ("minestuck".equals(id.getNamespace())) continue;
			if ("minecraft".equals(id.getNamespace()) && badVanillaItems.contains(id.getPath())) continue;
			if ("mekanism".equals(id.getNamespace()) && badMekanismItems.contains(id.getPath())) continue;
			if ("ae2".equals(id.getNamespace()) && badAe2Items.contains(id.getPath())) continue;
			if ("create".equals(id.getNamespace()) && badCreateItems.contains(id.getPath())) continue;
			if ("guideme".equals(id.getNamespace())) continue;

			if (!hasGristCost(item.getDefaultInstance(), recipeManager))
				(item instanceof SmithingTemplateItem || sherds.contains(item.builtInRegistryHolder()) ? collectibles : items)
				.add(id);
		}

		if (!items.isEmpty()) {
			items.sort(ResourceLocation::compareNamespaced);
			MinestuckCompat.LOGGER.info("Items without grist costs:\n{}", items.stream().map(ResourceLocation::toString).collect(Collectors.joining("\n")));
		}

		if (!collectibles.isEmpty()) {
			collectibles.sort(ResourceLocation::compareNamespaced);
			MinestuckCompat.LOGGER.info("Collectibles without grist costs:\n{}", collectibles.stream().map(ResourceLocation::toString).collect(Collectors.joining("\n")));
		}
	}

	public static void checkRecipesWithoutInterpreters(RecipeManager recipeManager, List<SourceEntry> sources) {
		if (!MinestuckCompatConfig.common().dumpUnhandledRecipeTypes.getAsBoolean()) return;

		final Set<RecipeHolder<?>> recipes = new HashSet<>(recipeManager.getRecipes());

		final Set<String> impossibleRecipes = Set.of("mekanism:chemical_conversion", "mekanism:chemical_infusing",
				"mekanism:dissolution", "mekanism:evaporating", "mekanism:oxidizing", "mekanism:pigment_extracting",
				"mekanism:rotary", "mekanism:washing", "mekanism:centrifuging", "mekanism:separating",
				"mekanism:pigment_mixing", "mekanism:energy_conversion",
				// No point supporting this one (only mirrors vanilla recipes):
				"mekanism:painting",
				"ae2:entropy", "ae2:matter_cannon");

		final Set<String> unsupportedRecipes = Set.of(
				// Mirrors of smelting:
				"minecraft:blasting", "minecraft:smoking", "minecraft:campfire_cooking",
				// Likely to significantly alter grist costs:
				"mekanism:sawing", "mekanism:combining");

		recipes.removeIf(recipe -> {
			// Skip grist/alchemization recipes.
			if (recipe.value().getType() == MSRecipeTypes.GRIST_COST_TYPE.get() || recipe.value().getType() == MSRecipeTypes.COMBINATION_TYPE.get())
				return true;

			// Skip "special" opaque recipes.
			if (recipe.value() instanceof CustomRecipe || recipe.value() instanceof MapExtendingRecipe)
				return true;

			final ResourceLocation typeId = BuiltInRegistries.RECIPE_TYPE.getKey(recipe.value().getType());

			// Skip recipes we cannot support, to avoid cluttering the logs.
			if (typeId != null) {
				// Hide "runtime generated" recipes, since there tends to be hundreds if not thousands of them.
				if ("create".equals(typeId.getNamespace()) && recipe.id().getPath().contains("runtime_generated/")) return true;

				final String str = typeId.toString();
				if (impossibleRecipes.contains(str)) return true;
				if (MinestuckCompatConfig.common().dumpUnhandledRecipeTypesFiltering.get() && unsupportedRecipes.contains(str)) return true;
			}

			return false;
		});

		for (SourceEntry source : sources)
			recipes.removeAll(source.source().findRecipes(recipeManager));

		if (recipes.isEmpty()) return;

		record Line(String id, String typeId, String serializerId) {}

		final List<Line> lines = recipes.stream()
				// Order by recipe type and then recipe ID
				.sorted(Comparator.<RecipeHolder<?>, ResourceLocation>comparing(r -> BuiltInRegistries.RECIPE_TYPE.getKey(r.value().getType()), ResourceLocation::compareNamespaced)
						.thenComparing(Comparator.comparing(RecipeHolder::id, ResourceLocation::compareNamespaced)))
				.map(recipe -> new Line(recipe.id().toString(),
						BuiltInRegistries.RECIPE_TYPE.getKey(recipe.value().getType()).toString(),
						BuiltInRegistries.RECIPE_SERIALIZER.getKey(recipe.value().getSerializer()).toString()))
				.collect(Collectors.toList());

		lines.add(0, new Line("ID", "Recipe Type", "Recipe Serializer"));

		int idWidth = 0;
		int typeWidth = 0;

		for (Line line : lines) {
			if (line.id.length() > idWidth)
				idWidth = line.id.length();
			if (line.typeId.length() > typeWidth)
				typeWidth = line.typeId.length();
		}

		final int _idWidth = idWidth + 5;
		final int _typeWidth = typeWidth + 5;

		MinestuckCompat.LOGGER.info("Unhandled recipes:\n{}", lines.stream()
				.map(line -> line.id + " ".repeat(_idWidth - line.id.length()) + line.typeId + " ".repeat(_typeWidth - line.typeId.length()) + line.serializerId)
				.collect(Collectors.joining("\n")));
	}

	private static boolean hasGristCost(ItemStack item, RecipeManager recipeManager) {
		final List<RecipeHolder<GristCostRecipe>> list = recipeManager.getAllRecipesFor(GristCostRecipe.RECIPE_TYPE.get());
		final SingleRecipeInput input = new SingleRecipeInput(item);

		for (RecipeHolder<GristCostRecipe> elem : list)
			if (elem.value().matches(input, null))
				return true;

		return false;
	}
}