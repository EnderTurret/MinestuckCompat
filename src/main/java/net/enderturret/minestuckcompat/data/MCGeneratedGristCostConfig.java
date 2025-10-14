package net.enderturret.minestuckcompat.data;

import static com.mraof.minestuck.api.alchemy.GristSet.EMPTY;
import static com.mraof.minestuck.api.alchemy.GristTypes.*;

import java.util.function.Supplier;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.DefaultInterpreter;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;
import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;
import com.mraof.minestuck.data.GeneratedGristCostConfigProvider;
import com.simibubi.create.AllRecipeTypes;

import net.minecraft.data.PackOutput;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.alchemy.ae2.ChargerInterpreter;
import net.enderturret.minestuckcompat.alchemy.ae2.InscriberInterpreter;
import net.enderturret.minestuckcompat.alchemy.create.FluidProcessingInterpreter;
import net.enderturret.minestuckcompat.alchemy.create.SequencedAssemblyInterpreter;
import net.enderturret.minestuckcompat.alchemy.ie.AlloySmelterInterpreter;
import net.enderturret.minestuckcompat.alchemy.ie.CrusherInterpreter;
import net.enderturret.minestuckcompat.alchemy.ie.IEShapedInterpreter;
import net.enderturret.minestuckcompat.alchemy.ie.MetalPressInterpreter;
import net.enderturret.minestuckcompat.alchemy.ie.MultiblockInterpreter;
import net.enderturret.minestuckcompat.alchemy.mekanism.Item2ItemInterpreter;
import net.enderturret.minestuckcompat.alchemy.mekanism.ItemChemical2ItemInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.SimpleRecipeInterpreter;
import net.enderturret.minestuckcompat.api.data.IGeneratedGristCostConfigProviderExtensions;
import net.enderturret.minestuckcompat.perf.SmallImmutableGristSet;

import appeng.recipes.AERecipeTypes;
import blusunrize.immersiveengineering.api.crafting.IERecipeTypes;
import blusunrize.immersiveengineering.common.util.RecipeSerializers;
import mekanism.api.recipes.MekanismRecipeTypes;
import mekanism.common.registries.MekanismRecipeSerializersInternal;
import mekanism.tools.common.registries.ToolsRecipeSerializers;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

@Internal
public final class MCGeneratedGristCostConfig extends GeneratedGristCostConfigProvider implements IGeneratedGristCostConfigProviderExtensions {

	public MCGeneratedGristCostConfig(PackOutput output) {
		super(output, MinestuckCompat.MOD_ID);
	}

	@Override
	protected void addEntries() {
		// Mekanism
		serializer(MekanismRecipeSerializersInternal.MEK_DATA.get());
		type(MekanismRecipeTypes.TYPE_COMPRESSING.get(), new ItemChemical2ItemInterpreter(EMPTY));
		type(MekanismRecipeTypes.TYPE_INJECTING.get(), new ItemChemical2ItemInterpreter(EMPTY));
		type(MekanismRecipeTypes.TYPE_PURIFYING.get(), new ItemChemical2ItemInterpreter(EMPTY));
		type(MekanismRecipeTypes.TYPE_METALLURGIC_INFUSING.get(), new ItemChemical2ItemInterpreter(EMPTY));
		type(MekanismRecipeTypes.TYPE_NUCLEOSYNTHESIZING.get(), new ItemChemical2ItemInterpreter(EMPTY));
		type(MekanismRecipeTypes.TYPE_ENRICHING.get(), new Item2ItemInterpreter(grist(COBALT, 10)));
		type(MekanismRecipeTypes.TYPE_CRUSHING.get(), new Item2ItemInterpreter(grist(MERCURY, 1)));
		// Mekanism Tools
		serializer(ToolsRecipeSerializers.PAXEL.get());

		// Applied Energistics 2
		type(AERecipeTypes.CHARGER, new ChargerInterpreter(grist(URANIUM, 1)));
		type(AERecipeTypes.INSCRIBER, new InscriberInterpreter(grist(GARNET, 1)));

		// Create
		type(AllRecipeTypes.ITEM_APPLICATION.getType(), new SimpleRecipeInterpreter(false));
		type(AllRecipeTypes.PRESSING.getType(), new SimpleRecipeInterpreter(false));
		type(AllRecipeTypes.MECHANICAL_CRAFTING.getType(), new SimpleRecipeInterpreter(false));
		type(AllRecipeTypes.SANDPAPER_POLISHING.getType(), new SimpleRecipeInterpreter(false, grist(MERCURY, 1)));
		type(AllRecipeTypes.SEQUENCED_ASSEMBLY.getType(), new SequencedAssemblyInterpreter(EMPTY, grist(BUILD, 1), grist(MERCURY, 1), EMPTY));
		type(AllRecipeTypes.HAUNTING.getType(), new SimpleRecipeInterpreter(false, grist(SHALE, 1)));
		type(AllRecipeTypes.SPLASHING.getType(), new SimpleRecipeInterpreter(false, grist(COBALT, 1)));
		type(AllRecipeTypes.MILLING.getType(), new SimpleRecipeInterpreter(false, grist(MERCURY, 1)));
		type(AllRecipeTypes.CRUSHING.getType(), new SimpleRecipeInterpreter(false, grist(MERCURY, 1)));
		type(AllRecipeTypes.DEPLOYING.getType(), new SimpleRecipeInterpreter(false, grist(BUILD, 1)));
		type(AllRecipeTypes.MIXING.getType(), new FluidProcessingInterpreter(EMPTY, grist(TAR, 1)));
		type(AllRecipeTypes.COMPACTING.getType(), new FluidProcessingInterpreter(grist(MERCURY, 1), grist(TAR, 1)));
		type(AllRecipeTypes.FILLING.getType(), new FluidProcessingInterpreter(EMPTY, EMPTY));

		// Farmers Delight
		type(ModRecipeTypes.COOKING.get());
		type(ModRecipeTypes.CUTTING.get(), new SimpleRecipeInterpreter(false, grist(RUST, 1)));

		// Immersive Engineering
		type(IERecipeTypes.BLUEPRINT.get(), new MultiblockInterpreter(EMPTY));
		serializer(RecipeSerializers.TURN_AND_COPY_SERIALIZER.get(), new IEShapedInterpreter(EMPTY));
		serializer(RecipeSerializers.IE_SHAPED_SERIALIZER.get(), new IEShapedInterpreter(EMPTY));
		type(IERecipeTypes.METAL_PRESS.get(), new MetalPressInterpreter(grist(RUST, 1)));
		type(IERecipeTypes.CRUSHER.get(), new CrusherInterpreter(grist(MERCURY, 1)));
		type(IERecipeTypes.ALLOY.get(), new AlloySmelterInterpreter(grist(TAR, 1)));

		// GTCEu
		/*
		serializer(GTRecipeSerializers.CRAFTING_SHAPED_ENERGY_TRANSFER.get(), DefaultInterpreter.INSTANCE);
		serializer(GTRecipeSerializers.CRAFTING_SHAPED_STRICT.get(), DefaultInterpreter.INSTANCE);
		type(GTRecipeTypes.ALLOY_SMELTER_RECIPES, new GTRecipeInterpreter(grist(TAR, 1)));
		type(GTRecipeTypes.ARC_FURNACE_RECIPES, new GTRecipeInterpreter(grist(TAR, 1)));
		type(GTRecipeTypes.ASSEMBLER_RECIPES, new GTRecipeInterpreter(EMPTY));
		type(GTRecipeTypes.ASSEMBLY_LINE_RECIPES, new GTRecipeInterpreter(EMPTY));
		type(GTRecipeTypes.AUTOCLAVE_RECIPES, new GTRecipeInterpreter(EMPTY));
		type(GTRecipeTypes.BENDER_RECIPES, new GTRecipeInterpreter(grist(SHALE, 1)));
		type(GTRecipeTypes.BREWING_RECIPES, new GTRecipeInterpreter(grist(AMBER, 1)));
		type(GTRecipeTypes.CANNER_RECIPES, new GTRecipeInterpreter(grist(RUST, 1)));
		type(GTRecipeTypes.CENTRIFUGE_RECIPES, new GTRecipeInterpreter(grist(URANIUM, 1)));
		type(GTRecipeTypes.CHEMICAL_BATH_RECIPES, new GTRecipeInterpreter(grist(COBALT, 1)));
		type(GTRecipeTypes.CHEMICAL_RECIPES, new GTRecipeInterpreter(grist(CAULK, 1)));
		type(GTRecipeTypes.CIRCUIT_ASSEMBLER_RECIPES, new GTRecipeInterpreter(EMPTY));
		type(GTRecipeTypes.COKE_OVEN_RECIPES, new GTRecipeInterpreter(grist(TAR, 4)));
		type(GTRecipeTypes.COMPRESSOR_RECIPES, new GTRecipeInterpreter(grist(MERCURY, 1)));
		type(GTRecipeTypes.CUTTER_RECIPES, new GTRecipeInterpreter(grist(RUST, 1)));
		type(GTRecipeTypes.BLAST_RECIPES, new GTRecipeInterpreter(grist(TAR, 1)));
		type(GTRecipeTypes.EXTRACTOR_RECIPES, new GTRecipeInterpreter(grist(SHALE, 1)));
		type(GTRecipeTypes.EXTRUDER_RECIPES, new GTRecipeInterpreter(grist(SHALE, 1)));
		type(GTRecipeTypes.FLUID_SOLIDFICATION_RECIPES, new GTRecipeInterpreter(grist(BUILD, 1)));
		type(GTRecipeTypes.FORGE_HAMMER_RECIPES, new GTRecipeInterpreter(grist(MERCURY, 1)));
		type(GTRecipeTypes.LARGE_CHEMICAL_RECIPES, new GTRecipeInterpreter(grist(CAULK, 2)));
		type(GTRecipeTypes.LASER_ENGRAVER_RECIPES, new GTRecipeInterpreter(grist(RUBY, 1)));
		type(GTRecipeTypes.LATHE_RECIPES, new GTRecipeInterpreter(grist(MERCURY, 1)));
		type(GTRecipeTypes.MACERATOR_RECIPES, new GTRecipeInterpreter(grist(MERCURY, 1)));
		type(GTRecipeTypes.MIXER_RECIPES, new GTRecipeInterpreter(grist(COBALT, 1)));
		type(GTRecipeTypes.ORE_WASHER_RECIPES, new GTRecipeInterpreter(grist(COBALT, 1)));
		type(GTRecipeTypes.PACKER_RECIPES, new GTRecipeInterpreter(grist(MERCURY, 1)));
		type(GTRecipeTypes.WIREMILL_RECIPES, new GTRecipeInterpreter(grist(SHALE, 1)));
		*/
	}

	@Override
	public JsonElement modify(JsonElement root) {
		final JsonArray rootA = root.getAsJsonArray();
		rootA.add(rawType("buildersaddition2:carpenter", DefaultInterpreter.INSTANCE));
		rootA.add(rawSerializer("dankstorage:upgrade", DefaultInterpreter.INSTANCE));

		for (JsonElement child : rootA)
			if (child instanceof JsonObject obj) {
				final JsonObject source = obj.getAsJsonObject("source");
				final String type = (source.has("recipe_type") ? source.get("recipe_type") : source.get("serializer")).getAsString();
				addModLoadedCondition(obj, type.substring(0, type.indexOf(':')));
			}

		return root;
	}

	private static JsonObject rawSerializer(String id, RecipeInterpreter interpreter) {
		final JsonObject ret = new JsonObject();

		ret.add("interpreter", encodeInterpreter(interpreter));

		final JsonObject source = new JsonObject();
		source.addProperty("type", "recipe_serializer");
		source.addProperty("serializer", id);
		ret.add("source", source);

		return ret;
	}

	private static JsonObject rawType(String id, RecipeInterpreter interpreter) {
		final JsonObject ret = new JsonObject();

		ret.add("interpreter", encodeInterpreter(interpreter));

		final JsonObject source = new JsonObject();
		source.addProperty("type", "recipe_type");
		source.addProperty("recipe_type", id);
		ret.add("source", source);

		return ret;
	}

	private static JsonElement encodeInterpreter(RecipeInterpreter interpreter) {
		final DataResult<JsonElement> interp = RecipeInterpreter.DISPATCH_CODEC.encodeStart(JsonOps.INSTANCE, interpreter);
		return interp.getOrThrow();
	}

	private static void addModLoadedCondition(JsonObject obj, String modId) {
		final JsonArray neoforgeConditions = new JsonArray();

		{
			final JsonObject condition = new JsonObject();
			condition.addProperty("type", "neoforge:mod_loaded");
			condition.addProperty("modid", modId);
			neoforgeConditions.add(condition);
		}

		{
			final JsonObject condition = new JsonObject();
			condition.addProperty("type", "minestuckcompat:config");
			condition.addProperty("modid", "mekanismtools".equals(modId) ? "mekanism" : modId);
			neoforgeConditions.add(condition);
		}

		// Make sure the conditions are the first element, for readability.
		final JsonObject interpreter = obj.getAsJsonObject("interpreter");
		final JsonObject source = obj.getAsJsonObject("source");
		obj.remove("interpreter");
		obj.remove("source");
		obj.add("neoforge:conditions", neoforgeConditions);
		obj.add("interpreter", interpreter);
		obj.add("source", source);
	}

	private static GristSet.Immutable grist(Supplier<GristType> type, int count) {
		return SmallImmutableGristSet.create(new GristAmount(type.get(), count));
	}
}