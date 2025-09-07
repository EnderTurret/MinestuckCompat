# Minestuck Compat

A comprehensive Minestuck addon that provides grist costs and combination recipes for various large mods.

Minestuck Compat has specific support for:
* [Applied Energistics 2](https://modrinth.com/mod/ae2)
* [Biomes O' Plenty](https://modrinth.com/mod/biomes-o-plenty)
* [Create](https://modrinth.com/mod/create)
* [Farmer's Delight](https://modrinth.com/mod/farmers-delight)
* [Mekanism](https://modrinth.com/mod/mekanism)
* [Rechiseled](https://modrinth.com/mod/rechiseled)

Additionally, Minestuck Compat fills out some missing grist costs and combinations of both vanilla Minecraft as well as Minestuck itself. In particular, Minestuck Compat adds:
* Grist costs for all vanilla content that lacks them
* Combination recipes for pottery sherds
* Combination recipes for armor trims
* Combination recipes for banner patterns
* Combination recipes for a few other odds and ends (e.g., sniffer egg, wind charge)
* Combination recipes for Minestuck's wood sets (saplings, stairs, fence gates, trapdoors)

## Debugging Features

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

## Interpreter Types

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

The simple interpreter can be configured to process "special" recipes (via the `ignore_special` property). By default Minestuck's interpreters *ignore* special recipes, which means they're useless for recipes such as Create's (which are *all* special). This allows one to bypass that limitation.

The simple interpreter can also be configured with an `added_cost` — see Minestuck's cooking interpreter for syntax.

### `minestuckcompat:ae2/charger`

An interpreter type for Applied Energistics 2's Charger.

### `minestuckcompat:ae2/inscriber`

An interpreter type for Applied Energistics 2's Inscriber.

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

This interpreter type can be configured with a `deploy_cost` and `press_cost`, both optional. There is no `added_cost` field — use the other two instead.

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

Minestuck Compat also enhances Minestuck's `grist_cost_generation_recipes.json` with support for [NeoForge's conditions](https://docs.neoforged.net/docs/1.21.1/resources/server/conditions). This allows — among other things — loading "source entries" only when the requisite mod(s) are present. Here's an example from Minestuck Compat's file:

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

## Performance Optimizations

Minestuck Compat (ironically) also applies a few optimizations to Minestuck's grist cost generation algorithm. These are primarily just some good ol' allocation avoidance optimizations (theoretically saving CPU time but mostly my sanity), but also include smaller implementations of the backing grist cost data structure. The latter optimization shrinks the memory footprint of a given grist cost by around 3/4ths.