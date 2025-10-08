package net.enderturret.minestuckcompat.alchemy.analysis;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mraof.minestuck.block.plant.EndSaplingBlock;
import com.mraof.minestuck.block.plant.StrippableFlammableLogBlock;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.CoralFanBlock;
import net.minecraft.world.level.block.CoralPlantBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.alchemy.ClientDataManager;

@Internal
public final class BuiltinRecipeList {

	private static final Codec<SimpleIngredient> INGREDIENT_CODEC = ExtraCodecs.TAG_OR_ELEMENT_ID.flatXmap(
			BuiltinRecipeList::parseIngredient,
			ing -> DataResult.error(() -> "Cannot serialize SimpleIngredient"));

	@SuppressWarnings("deprecation")
	private static final Codec<Item> LENIENT_ITEM_CODEC = ResourceLocation.CODEC.xmap(
			id -> {
				final Optional<Item> optional = BuiltInRegistries.ITEM.getOptional(id);
				if (!optional.isPresent()) {
					maybeWarn(id);
					return Items.AIR;
				}

				return optional.get();
			},
			item -> item.builtInRegistryHolder().getKey().location());

	private static final Codec<AnalyzedRecipe> RECIPE_CODEC = RecordCodecBuilder.create(builder -> builder.group(
			INGREDIENT_CODEC.listOf().fieldOf("inputs").forGetter(AnalyzedRecipe::inputs),
			LENIENT_ITEM_CODEC.listOf().fieldOf("outputs").forGetter(AnalyzedRecipe::outputs)
			).apply(builder, AnalyzedRecipe::fromCodec));

	private static final Codec<List<AnalyzedRecipe>> CODEC = RECIPE_CODEC.listOf();

	static Set<Item> readRoots(ResourceManager resourceManager) {
		return Set.copyOf(parseResource(resourceManager, "minestuckcompat/obtainability_analyzer", "minestuckcompat/obtainability_analyzer/roots.json", json -> {
			final Set<Item> set = new HashSet<>();

			for (JsonElement elem : json.getAsJsonObject().asMap().values())
				for (JsonElement id : elem.getAsJsonArray())
					set.addAll(INGREDIENT_CODEC.parse(JsonOps.INSTANCE, id).getOrThrow().items());

			return set;
		}));
	}

	static List<AnalyzedRecipe> scanRecipes(ResourceManager resourceManager) {
		final List<AnalyzedRecipe> ret = new ArrayList<>();

		ret.addAll(parseResource(resourceManager, "minestuckcompat/obtainability_analyzer", "minestuckcompat/obtainability_analyzer/recipes.json",
				elem -> CODEC.parse(JsonOps.INSTANCE, elem).getOrThrow()));

		// Remove any invalid recipes.
		ret.removeIf(recipe -> recipe.inputs().isEmpty() && recipe.outputs().isEmpty());

		detectRecipesFromRuntime(ret);

		return ret;
	}

	public static <T> List<T> parseResource(ResourceManager resourceManager, String folder, String path, Function<JsonElement, Collection<T>> codec) {
		final List<T> ret = new ArrayList<>();

		for (var entry : resourceManager.listResources(folder, rl -> rl.getPath().equals(path)).entrySet()) {
			try (BufferedReader br = entry.getValue().openAsReader()) {
				final JsonElement elem = JsonParser.parseReader(br);
				ret.addAll(codec.apply(elem));
			} catch (Exception e) {
				MinestuckCompat.LOGGER.warn("Exception reading {}:", entry.getKey(), e);
			}
		}

		return ret;
	}

	private static final Field CORAL_DEADBLOCK = ObfuscationReflectionHelper.findField(CoralBlock.class, "deadBlock");
	private static final Field CORALFAN_DEADBLOCK = ObfuscationReflectionHelper.findField(CoralFanBlock.class, "deadBlock");
	private static final Field CORALPLANT_DEADBLOCK = ObfuscationReflectionHelper.findField(CoralPlantBlock.class, "deadBlock");
	private static final Field STRIPPABLEFLAMMABLELOG_STRIPPEDSTATE = ObfuscationReflectionHelper.findField(StrippableFlammableLogBlock.class, "strippedState");

	private static void detectRecipesFromRuntime(List<AnalyzedRecipe> recipes) {
		for (Holder<Block> holder : BuiltInRegistries.BLOCK.getTag(BlockTags.LOGS).get()) {
			final BlockState stripped = AxeItem.getAxeStrippingState(holder.value().defaultBlockState());
			if (stripped != null)
				recipes.add(AnalyzedRecipe.singleInput(holder.value(), stripped.getBlock()));
		}

		for (var entry : BuiltInRegistries.BLOCK.getDataMap(NeoForgeDataMaps.OXIDIZABLES).entrySet()) {
			final Block from = BuiltInRegistries.BLOCK.get(entry.getKey());
			recipes.add(AnalyzedRecipe.singleInput(from, entry.getValue().nextOxidationStage()));
		}

		for (var entry : BuiltInRegistries.BLOCK.getDataMap(NeoForgeDataMaps.WAXABLES).entrySet()) {
			final Block from = BuiltInRegistries.BLOCK.get(entry.getKey());
			recipes.add(AnalyzedRecipe.twoInputs(from, Items.HONEYCOMB, entry.getValue().waxed()));
		}

		for (var entry : ClientDataManager.SWAPPING_WEAPONS.entrySet()) {
			recipes.add(AnalyzedRecipe.singleInput(entry.getKey(), entry.getValue()));
			recipes.add(AnalyzedRecipe.singleInput(entry.getValue(), entry.getKey()));
		}

		try {
			for (Block block : BuiltInRegistries.BLOCK) {
				if (block instanceof SaplingBlock || block instanceof EndSaplingBlock) {
					@SuppressWarnings("deprecation")
					final ResourceLocation id = block.builtInRegistryHolder().getKey().location();
					if (id.getPath().endsWith("_sapling")) {
						final String base = id.getPath().substring(0, id.getPath().length() - "_sapling".length());

						final Optional<Block> log = BuiltInRegistries.BLOCK.getOptional(id.withPath(base + "_log"));
						if (log.isPresent()) recipes.add(AnalyzedRecipe.singleInput(block, log.get()));

						final Optional<Block> leaves = BuiltInRegistries.BLOCK.getOptional(id.withPath(base + "_leaves"));
						if (leaves.isPresent()) recipes.add(AnalyzedRecipe.twoInputs(block, Items.SHEARS, leaves.get()));
					}
				}

				else if (block instanceof CoralBlock)
					recipes.add(AnalyzedRecipe.singleInput(block, (Block) CORAL_DEADBLOCK.get(block)));
				else if (block instanceof CoralFanBlock)
					recipes.add(AnalyzedRecipe.singleInput(block, (Block) CORALFAN_DEADBLOCK.get(block)));
				else if (block instanceof CoralPlantBlock)
					recipes.add(AnalyzedRecipe.singleInput(block, (Block) CORALPLANT_DEADBLOCK.get(block)));
				else if (block instanceof StrippableFlammableLogBlock) {
					@SuppressWarnings("unchecked")
					final Supplier<BlockState> stripped = (Supplier<BlockState>) STRIPPABLEFLAMMABLELOG_STRIPPEDSTATE.get(block);
					recipes.add(AnalyzedRecipe.singleInput(block, stripped.get().getBlock()));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static DataResult<SimpleIngredient> parseIngredient(ExtraCodecs.TagOrElementLocation loc) {
		if (loc.tag())
			return DataResult.success(SimpleIngredient.of(TagKey.create(Registries.ITEM, loc.id())));

		final Optional<Item> item = BuiltInRegistries.ITEM.getOptional(loc.id());
		if (!item.isPresent()) {
			maybeWarn(loc.id());
			return DataResult.success(SimpleIngredient.INVALID);
		}

		return DataResult.success(SimpleIngredient.of(item.get()));
	}

	private static void maybeWarn(ResourceLocation id) {
		final String domain = id.getNamespace();
		if (BuiltInRegistries.ITEM.holders().anyMatch(holder -> domain.equals(holder.getKey().location().getNamespace())))
			MinestuckCompat.LOGGER.warn("Unknown item ID in analyzer recipes: {}", id);
	}
}