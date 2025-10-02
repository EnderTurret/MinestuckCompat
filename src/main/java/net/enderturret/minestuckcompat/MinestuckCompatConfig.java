package net.enderturret.minestuckcompat;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.ApiStatus.Internal;

import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;

@Internal
public final class MinestuckCompatConfig {

	static final ModConfigSpec COMMON_SPEC;
	private static final MinestuckCompatConfig COMMON_INSTANCE;

	static {
		final var pair = new ModConfigSpec.Builder().configure(MinestuckCompatConfig::new);
		COMMON_SPEC = pair.getRight();
		COMMON_INSTANCE = pair.getLeft();
	}

	public final BooleanValue dumpGristlessItems;
	public final BooleanValue dumpUnhandledRecipeTypes;
	public final BooleanValue dumpUnhandledRecipeTypesFiltering;
	public final BooleanValue checkConflictingCombinationRecipes;
	public final BooleanValue debugDefaultInterpreterRecipeEligibility;

	private final Map<String, BooleanValue> enabledRecipes = new HashMap<>();

	private MinestuckCompatConfig(ModConfigSpec.Builder builder) {
		builder.comment("Settings related to grist costs.").push("grist");

		dumpGristlessItems = builder.comment(
				"Whether or not to print a list of all items that lack grist costs to the log.",
				"This can be useful when building compatibility data packs.",
				"",
				"Unlike Minestuck's config option, this lists *all* items, not just those which are ingredients."
				).define("dumpGristlessItems", !FMLEnvironment.production);

		dumpUnhandledRecipeTypes = builder.comment(
				"Whether or not to print a list of all recipe types that lack interpreters to the log.",
				"This can be useful when building compatibility data packs."
				).define("dumpUnhandledRecipeTypes", false);

		dumpUnhandledRecipeTypesFiltering = builder.comment(
				"Whether or not to filter some (intentionally unsupported) recipe types out of the recipe type dump.",
				"Certain recipe types, like Mekanism's chemical-only recipe types, will always be filtered out,",
				"as they cannot realistically be supported due to limitations of Minestuck's grist cost system."
				).define("dumpUnhandledRecipeTypesFiltering", true);

		debugDefaultInterpreterRecipeEligibility = builder.comment(
				"Whether or not to print information about unhandled recipes that go through the 'minestuck:default'",
				"interpreter.",
				"",
				"These usually indicate recipe serializers that need special handling.",
				"For example because they report that they're special (i.e., hidden from the recipe book),",
				"or because they report no ingredients or results via the vanilla codepath (which means the",
				"recipe's internal representation may be considerably more advanced or abstracted).",
				"",
				"For the former (isSpecial() == true), see Create's recipes as an example.",
				"For the latter (ingredients and results are empty), see Mekanism's recipes as an example.",
				"",
				"In the case that the recipe is \"special\" but otherwise eligible, Minestuck Compat provides",
				"the \"minestuckcompat:simple\" interpreter, which can be instructed to allow \"special\"",
				"recipes by adding `\"ignore_simple\": false` to its definition. The interpreter is otherwise",
				"a one-to-one clone of Minestuck's default interpreter (besides allowing an added cost)."
				).define("debugDefaultInterpreterRecipeEligibility", false);

		builder.comment("Settings for turning on/off grist cost integration for supported mods.").push("integration");

		defineEnabledRecipes(builder, "ae2", "Applied Energistics 2");
		defineEnabledRecipes(builder, "biomesoplenty", "Biomes O' Plenty");
		defineEnabledRecipes(builder, "create", "Create");
		defineEnabledRecipes(builder, "farmersdelight", "Farmer's Delight");
		defineEnabledRecipes(builder, "immersiveengineering", "Immersive Engineering");
		defineEnabledRecipes(builder, "mekanism", "Mekanism");
		defineEnabledRecipes(builder, "minestuck", "Minestuck");
		defineEnabledRecipes(builder, "minecraft", "Minecraft");

		defineEnabledRecipes(builder, "appmek", "Applied Mekanistics");
		defineEnabledRecipes(builder, "buildersaddition2", "Builders Crafts & Additions 2");
		defineEnabledRecipes(builder, "createoreexcavation", "Create Ore Excavation");
		defineEnabledRecipes(builder, "dankstorage", "Dank Storage");
		defineEnabledRecipes(builder, "expandedstorage", "Expanded Storage");
		defineEnabledRecipes(builder, "framedblocks", "FramedBlocks");
		defineEnabledRecipes(builder, "gravestone", "GraveStone Mod");
		defineEnabledRecipes(builder, "hostilenetworks", "Hostile Neural Networks");
		defineEnabledRecipes(builder, "lootr", "Lootr");
		defineEnabledRecipes(builder, "supplementaries", "Supplementaries");

		builder.pop(2);

		checkConflictingCombinationRecipes = builder.comment(
				"Whether or not to check for overlapping punch designix and totem lathe (combination) recipes."
				).define("checkConflictingCombinationRecipes", false);
	}

	private void defineEnabledRecipes(ModConfigSpec.Builder builder, String modId, String name) {
		enabledRecipes.put(modId, builder
				.comment("Whether or not to enable Minestuck Compat's grist cost and combination recipes for " + name + ".")
				.define(name, true));
	}

	public boolean isModEnabled(String modId) {
		final BooleanValue val = enabledRecipes.get(modId);
		if (val == null) {
			MinestuckCompat.LOGGER.warn("Unknown mod ID {} in config query", modId);
			return false;
		}

		return val.getAsBoolean();
	}

	public static MinestuckCompatConfig common() {
		return COMMON_INSTANCE;
	}
}