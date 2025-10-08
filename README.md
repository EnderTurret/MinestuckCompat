# Minestuck Compat

A comprehensive Minestuck addon that provides grist costs and combination recipes for various large mods.
It also comes with a few other features.

Minestuck Compat has specific support for:
* [Applied Energistics 2](https://modrinth.com/mod/ae2)
* [Biomes O' Plenty](https://modrinth.com/mod/biomes-o-plenty)
* [Create](https://modrinth.com/mod/create)
* [Farmer's Delight](https://modrinth.com/mod/farmers-delight)
* [Immersive Engineering](https://modrinth.com/mod/immersiveengineering)
* [Mekanism](https://modrinth.com/mod/mekanism)
* [Rechiseled](https://modrinth.com/mod/rechiseled)
* [Supplementaries](https://modrinth.com/mod/supplementaries)

Minestuck Compat also has partial (or full) support for:
* [Applied Mekanistics](https://modrinth.com/mod/applied-mekanistics)
* [Builders Crafts & Additions 2](https://modrinth.com/mod/buildersaddition)
* [Create Ore Excavation](https://modrinth.com/mod/create-ore-excavation)
* [Dank Storage](https://www.curseforge.com/minecraft/mc-mods/dank-storage)
* [Expanded Storage](https://modrinth.com/mod/expanded-storage)
* [FramedBlocks](https://modrinth.com/mod/framedblocks)
* [GraveStone Mod](https://modrinth.com/mod/gravestone-mod)
* [Hostile Neural Networks](https://www.curseforge.com/minecraft/mc-mods/hostile-neural-networks)
* [Lootr](https://modrinth.com/mod/lootr)

Additionally, Minestuck Compat fills out some missing grist costs and combinations of both vanilla Minecraft as well as Minestuck itself.
In particular, Minestuck Compat adds:
* Grist costs for all vanilla content that lacks them
* Combination recipes for pottery sherds, armor trims, banner patterns
* Combination recipes for a few other odds and ends (e.g., sniffer egg, wind charge)
* Combination recipes for Minestuck's wood sets (saplings, stairs, fence gates, trapdoors)
* Combination recipes for deepslate ores (ingot AND deepslate)
* Corrected grist costs for suspicious sand and barriers (empty item tags)

Minestuck Compat also enhances Minestuck's JEI integration:
* Subtype interpreters were added for Minestuck content, allowing e.g. frogs to appear in the item list
* Recipe IDs can now be displayed for grist costs and combination recipes (press shift or turn on advanced tooltips to see them)
* Grist ingredients hide their ID when advanced tooltips is not on
* Grist types are aliased to "grist", allowing them to show when searching exactly that
* When Create is installed, "mysterious conversion" recipes are added for swapping weapons (e.g. lipstick chainsaws)

## Installation

Just drop Minestuck Compat into the mods folder.
It will automatically enable the relevant compatibility recipes, so further configuration isn't necessary.
However, if certain features aren't to your liking, the `minestuckcompat-common.toml` file contains config options for turning most of Minestuck Compat's features off.

### For mod and modpack developers

Minestuck Compat offers some optional features that can be turned on via the config.
These features all generally aid efforts at integrating Minestuck with other mods.
Modpack developers may find these useful for making other mods play nicely with Minestuck, and mod developers may find them useful for developing Minestuck addons.
See the following section for details on the offered features.

Mod/modpack developers may also ship an `assets/yournamespace/minestuckcompat/swapping_weapons.json` file to supplement Minestuck Compat's swapping weapon list with additional such weapons. (For example, many of Alchemy Expanded's firearms.)

## Debugging features

Minestuck Compat provides a few debugging features, mainly involving Minestuck's grist cost generator. They can all be found in Minestuck Compat's config.

#### dumpGristlessItems

This causes Minestuck Compat to dump a list of items that lack grist costs to the log.
This is *invaluable* for finding unalchemizable things to write grist cost recipes for!

While Minestuck already has a config option to list every *ingredient* that lacks a grist cost, this is not the same as listing every *item*.
Minestuck's option will only tell you about items that lack grist costs *that are also used in a recipe Minestuck knows about*.
Thus, Minestuck Compat's option is more useful for the goal of assigning every item a grist cost.

#### dumpUnhandledRecipeTypes

This causes Minestuck Compat to dump a list of unrecognized recipes to the log.
Specifically, these are all the recipes that Minestuck can't process because they have no associated recipe interpreter.
This can be useful for finding recipe types to add to the `grist_cost_generation_recipes.json` file, but in some cases it might be easier to manually assign grist costs instead (say, to avoid accidentally making vanilla items dirt cheap).

#### debugDefaultInterpreterRecipeEligibility

This causes Minestuck Compat to detect recipes that have been passed to the `minestuck:default` interpreter but that cannot actually be handled by it.
There are three conditions this option checks for:
* The recipe is marked special (`isSpecial()` returns `true`)
* The recipe reports having no ingredients
* The recipe reports having no result

In any of these cases, Minestuck Compat will print the offending recipe to the log, or rather its type and serializer as the recipe ID is lost at that stage.

#### checkConflictingCombinationRecipes

This causes Minestuck Compat to detect combination recipes (i.e. Punch Designix and Totem Lathe) that conflict with each other.
Specifically, if any two recipes "overlap" such that a particular combination of items could result in two different outputs, Minestuck Compat will log it.

Minestuck Compat will also identify self-referential recipes — ones where the output is *also* one of the inputs.

## Obtainability Analyzer

One of the major features of Minestuck Compat is the Obtainability Analyzer.
As the name suggests, it analyzes the obtainability of items — what items are obtainable and which aren't — and builds a list of unobtainable items.

The analyzer can be run with the `/minestuckcompat analyzer` command in an *integrated server (singleplayer)*.
It will then dump the file `analyzer_unobtainables.txt` into the game directory, which contains all the unobtainable items the analyzer identified.

### Why do this

The purpose of the analyzer is to identify what things cannot be obtained once in the Medium.
If a given item can *only* be obtained via say, overworld worldgen, then the item will be completely unobtainable if a player were to start in the Medium.
Moreover, a player that enters the Medium with no way back out would be in trouble if they hadn't collected at least one of the item.

However even if none of those are compelling arguments there's at least the fact that the analyzer can identify decent candidates for combination recipes.

### How it works

The analyzer starts by building a list of "roots" — items that are guaranteed to be obtainable.
This is read from the `minestuckcompat/obtainability_analyzer/roots.json` file.
The analyzer places these roots into a list of "newly obtainable" items.

Then for every "newly obtainable" item the analyzer checks every recipe that has a grist cost interpreter to see whether all of its ingredients are in the list of obtainable items. Recipes whose ingredients are all obtainable have their results added to the newly obtainable list and the process repeats.

Once the analyzer runs out of newly obtainable items it makes a list of every item that *isn't* in the obtainable list and dumps that to a file.

### Understood recipes

Since the analyzer piggybacks off of the grist cost generator, recipes that are understood by the grist cost generator will be understood by the analyzer.
On the other hand, the converse is also true; recipes not understood by the grist cost generator will not be understood by the analyzer.

However, the analyzer *does* come with some built-in understanding for select game mechanics.
In particular log/wood stripping, copper oxidizing and waxing, logs/leaves from saplings, and dead forms of coral.
For other things (such as the Immersive Engineering coke oven) "recipes" are hard-coded using the `minestuckcompat/obtainability_analyzer/recipes.json` file, which is where the analyzer can be taught about smithing upgrades, shulker box and toolbox dying, crops from seeds, and pretty much anything else.

## Interpreter types

Minestuck Compat supplements Minestuck's recipe interpreter types with additional ones designed for use with other mods.
Unless otherwise stated, each one may optionally have an `added_cost`.

### `minestuckcompat:simple`

The simple interpreter is a functional clone of Minestuck's `default` interpreter, but with some more configurability.
Here's a sample config:

```json
[
  {
    "interpreter": {
      "type": "minestuckcompat:simple",
      "ignore_special": false
    },
    "source": {
      "type": "recipe_type",
      "recipe_type": "create:item_application"
    }
  }
]
```

The simple interpreter can be configured to process "special" recipes (via the `ignore_special` property).
By default Minestuck's interpreters *ignore* special recipes, which means they're useless for recipes such as Create's (which are *all* special).
This allows one to bypass that limitation.

The simple interpreter can also be configured with an `added_cost` — see Minestuck's cooking interpreter for syntax.

### `minestuckcompat:ae2/charger` and `minestuckcompat:ae2/inscriber`

Interpreter types for Applied Energistics 2's Charger and Inscriber, respectively.

### `minestuckcompat:create/fluid_processing`

An interpreter type intended for Create's Compacting, Filling, and Mixing recipes.
It's generally the same as `minestuckcompat:simple` in behavior, but correctly handles *fluid ingredients*.
It may also be configured with a `heated_cost`, same as with `added_cost`.

Note: fluids must have their buckets assigned grist costs in order for this interpreter to work.

### `minestuckcompat:create/sequenced_assembly`

An interpreter type for Create's Sequenced Assembly recipes.
Here's a sample config:

```json
[
  {
    "interpreter": {
      "type": "minestuckcompat:create/sequenced_assembly",
      "press_cost": {
        "minestuck:mercury": 1
      }
    },
    "source": {
      "type": "recipe_type",
      "recipe_type": "create:sequenced_assembly"
    }
  }
]
```

This interpreter type can be configured with a `deploy_cost` and `press_cost`, both optional.

### `minestuckcompat:mekanism/item_chemical_to_item`

An interpreter type for Mekanism's Item Chemical to Item recipes.

This can be used for Mekanism's:
* Compressing recipes (in the Osmium Compressor)
* Injecting recipes (in the Chemical Injection Chamber)
* Purifying recipes (in the Purification Chamber)
* Infusing recipes (in the Metallurgic Infuser)
* Painting recipes
* Nucleosynthesizing recipes

### `minestuckcompat:mekanism/item_to_item`

An interpreter type for Mekanism's Item to Item recipes.

This can be used for Mekanism's:
* Crushing recipes
* Enriching recipes (in the Enrichment Chamber)
* Smelting recipes

## Conditional Grist Cost Generation Recipes

Minestuck Compat also enhances Minestuck's `grist_cost_generation_recipes.json` with support for [NeoForge's conditions](https://docs.neoforged.net/docs/1.21.1/resources/server/conditions).
This allows — among other things — loading "source entries" only when the requisite mod(s) are present.
Here's an example from Minestuck Compat's file:

```json5
[
  {
    "neoforge:conditions": [
      {
        "type": "neoforge:mod_loaded",
        "modid": "mekanism"
      }
    ],
    "interpreter": {
      "type": "minestuck:default"
    },
    "source": {
      "type": "recipe_serializer",
      "serializer": "mekanism:mek_data"
    }
  },
// ... other recipe interpreters ...
]
```

In this example, the `mod_loaded` condition prevents this entry from loading when Mekanism isn't present, thereby avoiding unnecessary errors in the logs about missing recipe types/serializers.

## Performance optimizations

Minestuck Compat (ironically) also applies a few optimizations to Minestuck's grist cost generation algorithm.
These are primarily just some good ol' allocation avoidance optimizations (theoretically saving CPU time but mostly my sanity), but also include smaller implementations of the backing grist cost data structure.
The latter optimization shrinks the memory footprint of a given grist cost by around 3/4ths.

## Compiling this mod

Run `gradlew.bat build` (on Windows) or `./gradlew build` (on Linux) to build the mod.
You will need to ensure the required dependencies are present in the `libs` folder.
See the `build.gradle` for a list of required mods.