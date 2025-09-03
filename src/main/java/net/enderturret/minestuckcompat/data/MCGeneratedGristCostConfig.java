package net.enderturret.minestuckcompat.data;

import java.util.function.Supplier;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;
import com.mraof.minestuck.api.alchemy.GristTypes;
import com.mraof.minestuck.data.GeneratedGristCostConfigProvider;
import com.simibubi.create.AllRecipeTypes;

import net.minecraft.data.PackOutput;

import net.neoforged.fml.ModList;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.alchemy.SimpleRecipeInterpreter;
import net.enderturret.minestuckcompat.alchemy.ae2.ChargerInterpreter;
import net.enderturret.minestuckcompat.alchemy.ae2.InscriberInterpreter;
import net.enderturret.minestuckcompat.alchemy.create.SequencedAssemblyInterpreter;
import net.enderturret.minestuckcompat.alchemy.mekanism.Item2ItemInterpreter;
import net.enderturret.minestuckcompat.alchemy.mekanism.ItemChemical2ItemInterpreter;
import net.enderturret.minestuckcompat.perf.SmallImmutableGristSet;

import appeng.recipes.AERecipeTypes;
import mekanism.api.recipes.MekanismRecipeTypes;
import mekanism.common.registries.MekanismRecipeSerializersInternal;

public final class MCGeneratedGristCostConfig extends GeneratedGristCostConfigProvider implements IGeneratedGristCostConfigProviderExtensions {

	public MCGeneratedGristCostConfig(PackOutput output) {
		super(output, MinestuckCompat.MOD_ID);
	}

	@Override
	protected void addEntries() {
		if (ModList.get().isLoaded("mekanism")) {
			serializer(MekanismRecipeSerializersInternal.MEK_DATA.get());
			type(MekanismRecipeTypes.TYPE_COMPRESSING.get(), ItemChemical2ItemInterpreter.INSTANCE);
			type(MekanismRecipeTypes.TYPE_INJECTING.get(), ItemChemical2ItemInterpreter.INSTANCE);
			type(MekanismRecipeTypes.TYPE_PURIFYING.get(), ItemChemical2ItemInterpreter.INSTANCE);
			type(MekanismRecipeTypes.TYPE_METALLURGIC_INFUSING.get(), ItemChemical2ItemInterpreter.INSTANCE);
			type(MekanismRecipeTypes.TYPE_NUCLEOSYNTHESIZING.get(), ItemChemical2ItemInterpreter.INSTANCE);
			type(MekanismRecipeTypes.TYPE_ENRICHING.get(), new Item2ItemInterpreter(grist(GristTypes.COBALT, 10)));
			type(MekanismRecipeTypes.TYPE_CRUSHING.get(), new Item2ItemInterpreter(grist(GristTypes.MERCURY, 1)));
		}

		if (ModList.get().isLoaded("ae2")) {
			type(AERecipeTypes.CHARGER, new ChargerInterpreter(grist(GristTypes.URANIUM, 1)));
			type(AERecipeTypes.INSCRIBER, new InscriberInterpreter(grist(GristTypes.GARNET, 1)));
		}

		if (ModList.get().isLoaded("create")) {
			type(AllRecipeTypes.ITEM_APPLICATION.getType(), new SimpleRecipeInterpreter(false));
			type(AllRecipeTypes.PRESSING.getType(), new SimpleRecipeInterpreter(false));
			type(AllRecipeTypes.SANDPAPER_POLISHING.getType(), new SimpleRecipeInterpreter(false, grist(GristTypes.MERCURY, 1)));
			type(AllRecipeTypes.SEQUENCED_ASSEMBLY.getType(), new SequencedAssemblyInterpreter(GristSet.EMPTY, grist(GristTypes.MERCURY, 1)));
		}
	}

	@Override
	public JsonElement modify(JsonElement root) {
		final JsonArray array = (JsonArray) root;
		for (JsonElement child : array)
			if (child instanceof JsonObject obj) {
				final JsonObject interpreter = obj.getAsJsonObject("interpreter");
				final JsonObject source = obj.getAsJsonObject("source");
				final String type = (source.has("recipe_type") ? source.get("recipe_type") : source.get("serializer")).getAsString();
				final String namespace = type.substring(0, type.indexOf(':'));

				final JsonArray neoforgeConditions = new JsonArray();
				final JsonObject condition = new JsonObject();
				condition.addProperty("type", "neoforge:mod_loaded");
				condition.addProperty("modid", namespace);
				neoforgeConditions.add(condition);

				obj.remove("interpreter");
				obj.remove("source");
				obj.add("neoforge:conditions", neoforgeConditions);
				obj.add("interpreter", interpreter);
				obj.add("source", source);
			}

		return root;
	}

	private static GristSet.Immutable grist(Supplier<GristType> type, int count) {
		return SmallImmutableGristSet.create(new GristAmount(type.get(), count));
	}
}