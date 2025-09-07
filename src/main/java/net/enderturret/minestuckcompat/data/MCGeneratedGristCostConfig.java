package net.enderturret.minestuckcompat.data;

import java.util.function.Supplier;

import org.jetbrains.annotations.ApiStatus.Internal;

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
import net.enderturret.minestuckcompat.alchemy.ae2.ChargerInterpreter;
import net.enderturret.minestuckcompat.alchemy.ae2.InscriberInterpreter;
import net.enderturret.minestuckcompat.alchemy.create.SequencedAssemblyInterpreter;
import net.enderturret.minestuckcompat.alchemy.mekanism.Item2ItemInterpreter;
import net.enderturret.minestuckcompat.alchemy.mekanism.ItemChemical2ItemInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.SimpleRecipeInterpreter;
import net.enderturret.minestuckcompat.api.data.IGeneratedGristCostConfigProviderExtensions;
import net.enderturret.minestuckcompat.perf.SmallImmutableGristSet;

import appeng.recipes.AERecipeTypes;
import mekanism.api.recipes.MekanismRecipeTypes;
import mekanism.common.registries.MekanismRecipeSerializersInternal;
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
		type(MekanismRecipeTypes.TYPE_COMPRESSING.get(), new ItemChemical2ItemInterpreter(GristSet.EMPTY));
		type(MekanismRecipeTypes.TYPE_INJECTING.get(), new ItemChemical2ItemInterpreter(GristSet.EMPTY));
		type(MekanismRecipeTypes.TYPE_PURIFYING.get(), new ItemChemical2ItemInterpreter(GristSet.EMPTY));
		type(MekanismRecipeTypes.TYPE_METALLURGIC_INFUSING.get(), new ItemChemical2ItemInterpreter(GristSet.EMPTY));
		type(MekanismRecipeTypes.TYPE_NUCLEOSYNTHESIZING.get(), new ItemChemical2ItemInterpreter(GristSet.EMPTY));
		type(MekanismRecipeTypes.TYPE_ENRICHING.get(), new Item2ItemInterpreter(grist(GristTypes.COBALT, 10)));
		type(MekanismRecipeTypes.TYPE_CRUSHING.get(), new Item2ItemInterpreter(grist(GristTypes.MERCURY, 1)));

		// Applied Energistics 2
		type(AERecipeTypes.CHARGER, new ChargerInterpreter(grist(GristTypes.URANIUM, 1)));
		type(AERecipeTypes.INSCRIBER, new InscriberInterpreter(grist(GristTypes.GARNET, 1)));

		// Create
		type(AllRecipeTypes.ITEM_APPLICATION.getType(), new SimpleRecipeInterpreter(false));
		type(AllRecipeTypes.PRESSING.getType(), new SimpleRecipeInterpreter(false));
		type(AllRecipeTypes.SANDPAPER_POLISHING.getType(), new SimpleRecipeInterpreter(false, grist(GristTypes.MERCURY, 1)));
		type(AllRecipeTypes.SEQUENCED_ASSEMBLY.getType(), new SequencedAssemblyInterpreter(GristSet.EMPTY, grist(GristTypes.MERCURY, 1)));

		// Farmers Delight
		type(ModRecipeTypes.COOKING.get());
		type(ModRecipeTypes.CUTTING.get(), new SimpleRecipeInterpreter(false, grist(GristTypes.RUST, 1)));
	}

	@Override
	public JsonElement modify(JsonElement root) {
		final JsonArray rootA = root.getAsJsonArray();
		for (JsonElement child : rootA)
			if (child instanceof JsonObject obj) {
				final JsonObject source = obj.getAsJsonObject("source");
				final String type = (source.has("recipe_type") ? source.get("recipe_type") : source.get("serializer")).getAsString();
				addModLoadedCondition(obj, type.substring(0, type.indexOf(':')));
			}

		return root;
	}

	private static void addModLoadedCondition(JsonObject obj, String modId) {
		final JsonObject interpreter = obj.getAsJsonObject("interpreter");
		final JsonObject source = obj.getAsJsonObject("source");

		final JsonArray neoforgeConditions = new JsonArray();
		final JsonObject condition = new JsonObject();
		condition.addProperty("type", "neoforge:mod_loaded");
		condition.addProperty("modid", modId);
		neoforgeConditions.add(condition);

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