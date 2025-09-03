package net.enderturret.minestuckcompat;

import org.jetbrains.annotations.ApiStatus.Internal;

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
	public final BooleanValue debugDefaultInterpreterRecipeEligibility;

	private MinestuckCompatConfig(ModConfigSpec.Builder builder) {
		builder.push("grist");

		dumpGristlessItems = builder.comment(
				"Whether or not to print a list of all items that lack grist costs to the log.",
				"This can be useful when building compatibility data packs.",
				"",
				"Unlike Minestuck's config option, this lists *all* items, not just those which are ingredients."
				).define("dumpGristlessItems", false);

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

		builder.pop();
	}

	public static MinestuckCompatConfig common() {
		return COMMON_INSTANCE;
	}
}