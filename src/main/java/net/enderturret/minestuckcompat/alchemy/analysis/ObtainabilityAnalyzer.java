package net.enderturret.minestuckcompat.alchemy.analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.mraof.minestuck.alchemy.recipe.RegularCombinationRecipe;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedCostHandler;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeGeneratedCostHandler.SourceEntry;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.alchemy.MixinHooks;
import net.enderturret.minestuckcompat.api.alchemy.AnalyzableRecipeInterpreter;

public final class ObtainabilityAnalyzer {

	private final RecipeManager recipeManager;
	private final ResourceManager resourceManager;
	private final Map<Recipe<?>, RecipeInterpreter> interpretersByRecipe;

	public ObtainabilityAnalyzer(RecipeManager recipeManager, ResourceManager resourceManager) {
		this.recipeManager = recipeManager;
		this.resourceManager = resourceManager;

		interpretersByRecipe = readInterpreters(recipeManager, resourceManager);
	}

	@SuppressWarnings("deprecation")
	public List<Item> check() {
		final Set<Item> roots = defineDefaultRoots();

		if (roots.isEmpty()) {
			MinestuckCompat.LOGGER.warn("Attempted to perform obtainability analysis with 0 roots!");
			return List.of();
		}

		MinestuckCompat.LOGGER.info("Beginning obtainability analysis with {} roots!", roots.size());

		final Map<Item, List<AnalyzedRecipe>> relevantRecipes = buildRelevantRecipes();

		MinestuckCompat.LOGGER.info("Built relevant recipes for {} ingredients!", relevantRecipes.size());

		final long start = System.currentTimeMillis();

		final Set<Item> obtainable = new HashSet<>(roots);
		final List<Item> newlyObtainable = new ArrayList<>(roots);

		while (!newlyObtainable.isEmpty()) {
			final Item item = newlyObtainable.removeFirst();

			final List<AnalyzedRecipe> recipes = relevantRecipes.getOrDefault(item, List.of());
			parent:
			for (Iterator<AnalyzedRecipe> it = recipes.iterator(); it.hasNext(); ) {
				final AnalyzedRecipe recipe = it.next();

				for (SimpleIngredient input : recipe.inputs()) {
					boolean matches = false;
					for (Item i : input.items())
						if (obtainable.contains(i))
							matches = true;

					if (!matches)
						continue parent;
				}

				for (Item out : recipe.outputs())
					if (obtainable.add(out))
						newlyObtainable.add(out);

				it.remove();
			}
		}

		final Predicate<Item> filter = MixinHooks.getUnobtainableItemPredicate();

		final List<Item> unobtainable = BuiltInRegistries.ITEM.stream()
				.filter(item -> {
					if (item instanceof MobBucketItem || filter.test(item)) return false;

					final var holder = item.builtInRegistryHolder();
					final String domain = holder.getKey().location().getNamespace();

					if ("rechiseled".equals(domain)) return false;

					return !obtainable.contains(item);
				})
				.sorted(Comparator.comparing(item -> item.builtInRegistryHolder().getKey().location(), ResourceLocation::compareNamespaced))
				.toList();

		final long end = System.currentTimeMillis();
		MinestuckCompat.LOGGER.info("Analysis completed with {} obtainable items! Took {} s.", obtainable.size(), "%.3f".formatted((end - start) / 1000D));

		if (!unobtainable.isEmpty())
			dumpUnobtainables(relevantRecipes, obtainable, unobtainable);

		return unobtainable;
	}

	private Set<Item> defineDefaultRoots() {
		return BuiltinRecipeList.readRoots(resourceManager);
	}

	@SuppressWarnings("deprecation")
	private static void dumpUnobtainables(Map<Item, List<AnalyzedRecipe>> relevantRecipes, Set<Item> obtainable, List<Item> unobtainable) {
		try {
			Files.writeString(Paths.get("analyzer_unobtainables.txt"), unobtainable.stream()
					.map(item -> item.builtInRegistryHolder().getRegisteredName())
					.collect(Collectors.joining("\n")));
		} catch (IOException e) {
			MinestuckCompat.LOGGER.warn("Failed to write analyzer output:", e);
		}
	}

	private static Map<Recipe<?>, RecipeInterpreter> readInterpreters(RecipeManager recipeManager, ResourceManager resourceManager) {
		final List<SourceEntry> sources = new ArrayList<>();
		final ConditionalOps<JsonElement> ops = new ConditionalOps<>(RegistryOps.create(JsonOps.INSTANCE, VanillaRegistries.createLookup()), ICondition.IContext.EMPTY);

		sources.addAll(BuiltinRecipeList.parseResource(resourceManager, "minestuck", RecipeGeneratedCostHandler.PATH,
				json -> MixinHooks.CONDITIONAL_SOURCE_ENTRY_LIST.parse(ops, json)
				.getOrThrow(RuntimeException::new)
				.stream()
				.filter(Optional::isPresent)
				.map(Optional::get)
				.toList()));

		final Map<Recipe<?>, RecipeInterpreter> ret = new HashMap<>();

		for (SourceEntry source : sources)
			for (RecipeHolder<?> recipe : source.source().findRecipes(recipeManager))
				ret.put(recipe.value(), source.interpreter());

		return ret;
	}

	@Nullable
	public RecipeInterpreter findInterpreter(Recipe<?> recipe) {
		if (recipe instanceof RegularCombinationRecipe) return CombinationRecipeInterpreter.INSTANCE;
		return interpretersByRecipe.get(recipe);
	}

	private Map<Item, List<AnalyzedRecipe>> buildRelevantRecipes() {
		final Map<Item, List<AnalyzedRecipe>> ret = new HashMap<>();

		for (RecipeHolder<?> recipe : recipeManager.getRecipes()) {
			final RecipeInterpreter interpreter = findInterpreter(recipe.value());
			if (interpreter == null) continue;

			final List<Item> outputs = interpreter.getOutputItems(recipe.value());

			final List<SimpleIngredient> inputs = getInputs(recipe.value(), interpreter);

			final AnalyzedRecipe analyzed = new AnalyzedRecipe(inputs, outputs);
			final Set<Item> uniqueInputs = new HashSet<>();

			for (SimpleIngredient input : inputs)
				for (Item item : input.items())
					if (uniqueInputs.add(item))
						ret.computeIfAbsent(item, k -> new ArrayList<>()).add(analyzed);
		}

		for (AnalyzedRecipe recipe : BuiltinRecipeList.scanRecipes(resourceManager))
			for (SimpleIngredient input : recipe.inputs())
				for (Item item : input.items())
					ret.computeIfAbsent(item, k -> new ArrayList<>()).add(recipe);

		return ret;
	}

	private static List<SimpleIngredient> getInputs(Recipe<?> recipe, RecipeInterpreter interpreter) {
		final Set<SimpleIngredient> ret = new LinkedHashSet<>();

		final LookupTracker tracker = new LookupTracker() {
			@Override
			public void report(Item item) {
				if (item == Items.AIR) return;
				ret.add(SimpleIngredient.of(item));
			}
			@Override
			public void report(Ingredient ingredient) {
				if (ingredient == Ingredient.EMPTY) return;
				ret.add(SimpleIngredient.of(ingredient));
			}
		};

		interpreter.reportPreliminaryLookups(recipe, tracker);
		if (interpreter instanceof AnalyzableRecipeInterpreter analyzable)
			analyzable.reportCraftingStation(recipe, tracker);

		return List.copyOf(ret);
	}
}