package net.enderturret.minestuckcompat.alchemy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.mojang.serialization.Codec;
import com.mraof.minestuck.alchemy.recipe.RegularCombinationRecipe;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedCostHandler.SourceEntry;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedGristCost;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.recipe.GristCostRecipe;
import com.mraof.minestuck.api.alchemy.recipe.combination.CombinationMode;
import com.mraof.minestuck.api.alchemy.recipe.combination.CombinationRecipe;
import com.mraof.minestuck.item.crafting.MSRecipeTypes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
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

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.conditions.ConditionalOps;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.MinestuckCompatConfig;
import net.enderturret.minestuckcompat.api.alchemy.RegisterGristCostProvidersEvent;
import net.enderturret.minestuckcompat.mixin.feature.outside_grist_costs.GeneratorProcessAccess;

@Internal
public final class MixinHooks {

	public static final Codec<Optional<SourceEntry>> CONDITIONAL_SOURCE_ENTRY = ConditionalOps.createConditionalCodec(SourceEntry.CODEC);
	public static final Codec<List<Optional<SourceEntry>>> CONDITIONAL_SOURCE_ENTRY_LIST = CONDITIONAL_SOURCE_ENTRY.listOf();

	public static Map<Item, GristSet.Immutable> generatedCosts;

	public static void generateAdditionalGristCosts(GeneratorProcessAccess access) {
		MinestuckCompat.LOGGER.info("Discovering additional grist cost providers!");
		NeoForge.EVENT_BUS.post(new RegisterGristCostProvidersEvent(generatedCosts, access));
	}

	@SuppressWarnings("deprecation")
	public static void checkItemsWithoutGristCost(RecipeManager recipeManager) {
		if (MinestuckCompatConfig.common().checkConflictingCombinationRecipes.getAsBoolean())
			checkDuplicateCombinations(recipeManager);

		if (!MinestuckCompatConfig.common().dumpGristlessItems.getAsBoolean()) return;

		final TagKey<Item> technicalItemsTag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MinestuckCompat.MOD_ID, "technical_items"));
		final TagKey<Item> unobtainableItemsTag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MinestuckCompat.MOD_ID, "unobtainable_items"));

		final var sherds = BuiltInRegistries.ITEM.getTag(ItemTags.DECORATED_POT_SHERDS).orElseThrow();
		final var technicalItems = BuiltInRegistries.ITEM.getTag(technicalItemsTag).orElseThrow();
		final var unobtainableItems = BuiltInRegistries.ITEM.getTag(unobtainableItemsTag).orElseThrow();

		final List<ResourceLocation> items = new ArrayList<>();
		final List<ResourceLocation> collectibles = new ArrayList<>();
		final Map<ResourceLocation, List<ResourceLocation>> multiSources = new TreeMap<>(ResourceLocation::compareNamespaced);

		for (Item item : BuiltInRegistries.ITEM) {
			if (item instanceof GameMasterBlockItem || item instanceof SpawnEggItem) continue;

			final ResourceLocation id = item.builtInRegistryHolder().getKey().location();

			if (technicalItems.contains(item.builtInRegistryHolder())) continue;
			if (unobtainableItems.contains(item.builtInRegistryHolder())) continue;

			final List<RecipeHolder<GristCostRecipe>> recipes = hasGristCost(item.getDefaultInstance(), recipeManager);
			if (recipes.isEmpty())
				(item instanceof SmithingTemplateItem || sherds.contains(item.builtInRegistryHolder()) ? collectibles : items)
				.add(id);

			if (recipes.size() > 1) {
				// Remove the generated recipe, if present.
				recipes.removeIf(holder -> holder.value() instanceof RecipeGeneratedGristCost);
				if (recipes.size() > 1)
					multiSources.put(id, recipes.stream().map(RecipeHolder::id).sorted(ResourceLocation::compareNamespaced).toList());
			}
		}

		if (!items.isEmpty()) {
			items.sort(ResourceLocation::compareNamespaced);
			MinestuckCompat.LOGGER.info("Items without grist costs:\n{}", items.stream().map(ResourceLocation::toString).collect(Collectors.joining("\n")));
		}

		if (!collectibles.isEmpty()) {
			collectibles.sort(ResourceLocation::compareNamespaced);
			MinestuckCompat.LOGGER.info("Collectibles without grist costs:\n{}", collectibles.stream().map(ResourceLocation::toString).collect(Collectors.joining("\n")));
		}

		if (!multiSources.isEmpty())
			MinestuckCompat.LOGGER.info("Items with multiple non-generated grist costs:\n{}", multiSources.entrySet().stream()
					.map(MixinHooks::formatRecipeList)
					.collect(Collectors.joining("\n")));
	}

	private static String formatRecipeList(Map.Entry<ResourceLocation, List<ResourceLocation>> entry) {
		final String id = entry.getKey().toString();
		final StringBuilder ret = new StringBuilder(id);

		for (ResourceLocation rl : entry.getValue())
			ret.append('\n').append("  ==> ").append(rl.toString());

		return ret.toString();
	}

	public static void checkRecipesWithoutInterpreters(RecipeManager recipeManager, List<SourceEntry> sources) {
		if (!MinestuckCompatConfig.common().dumpUnhandledRecipeTypes.getAsBoolean()) return;

		final Set<RecipeHolder<?>> recipes = new HashSet<>(recipeManager.getRecipes());

		final Set<String> impossibleRecipes = Set.of(
				"mekanism:chemical_conversion", "mekanism:chemical_infusing",
				"mekanism:dissolution", "mekanism:evaporating", "mekanism:oxidizing", "mekanism:pigment_extracting",
				"mekanism:rotary", "mekanism:washing", "mekanism:centrifuging", "mekanism:separating",
				"mekanism:pigment_mixing", "mekanism:energy_conversion", "mekanism:activating",
				// No point supporting this one (only mirrors vanilla recipes):
				"mekanism:painting",

				"ae2:entropy", "ae2:matter_cannon",

				"create:emptying",

				"immersiveengineering:thermoelectric_source", "immersiveengineering:mineral_mix",
				"immersiveengineering:generator_fuel", "immersiveengineering:fertilizer", "immersiveengineering:cloche",
				"immersiveengineering:blast_furnace_fuel", "immersiveengineering:windmill_biome",

				// Item ==> Liquid
				"immersiveengineering:squeezer", "immersiveengineering:fermenter",
				// Liquid + Item ==> Liquid
				"immersiveengineering:refinery", "immersiveengineering:mixer"
				);

		final Set<String> unsupportedRecipes = Set.of(
				"minecraft:smithing",
				// Mirrors of smelting:
				"minecraft:blasting", "minecraft:smoking", "minecraft:campfire_cooking",
				// Likely to significantly alter grist costs:
				"mekanism:sawing", "mekanism:combining",
				"immersiveengineering:sawmill",
				// Can't be bothered:
				"mekanism:crystallizing", "mekanism:reaction",
				"ae2:transform");

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

	private static void checkDuplicateCombinations(RecipeManager recipeManager) {
		final List<RecipeHolder<CombinationRecipe>> list = recipeManager.getAllRecipesFor(MSRecipeTypes.COMBINATION_TYPE.get());

		record Combination(Item a, Item b, boolean and) {
			@SuppressWarnings("deprecation")
			Combination {
				if (a.builtInRegistryHolder().getRegisteredName().compareTo(b.builtInRegistryHolder().getRegisteredName()) < 0) {
					final Item temp = a;
					a = b;
					b = temp;
				}
			}
		}

		final Map<Combination, ResourceLocation> usedCombinations = new HashMap<>();

		for (RecipeHolder<CombinationRecipe> holder : list) {
			final RegularCombinationRecipe recipe = (RegularCombinationRecipe) holder.value();
			for (ItemStack stack1 : recipe.input1().getItems()) {
				if (stack1.getItemHolder().getRegisteredName().contains("paxel")) continue;
				for (ItemStack stack2 : recipe.input2().getItems()) {
					if (stack2.getItemHolder().getRegisteredName().contains("paxel")) continue;

					final Combination combo = new Combination(stack1.getItem(), stack2.getItem(), recipe.mode() == CombinationMode.AND);
					ResourceLocation conflict;
					if ((conflict = usedCombinations.put(combo, holder.id())) != null)
						MinestuckCompat.LOGGER.warn("Combination recipe {} conflicts with {}!", holder.id(), conflict);
				}
			}
		}
	}

	private static List<RecipeHolder<GristCostRecipe>> hasGristCost(ItemStack item, RecipeManager recipeManager) {
		final List<RecipeHolder<GristCostRecipe>> list = recipeManager.getAllRecipesFor(GristCostRecipe.RECIPE_TYPE.get());
		final SingleRecipeInput input = new SingleRecipeInput(item);

		final List<RecipeHolder<GristCostRecipe>> ret = new ArrayList<>(1);
		for (RecipeHolder<GristCostRecipe> elem : list)
			if (elem.value().matches(input, null))
				ret.add(elem);

		return ret;
	}
}