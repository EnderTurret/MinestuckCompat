package net.enderturret.minestuckcompat.perf;

import java.util.Arrays;

import com.mraof.minestuck.api.alchemy.GristType;
import com.mraof.minestuck.api.alchemy.GristTypes;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public final class GristTypeHelper {

	private static GristType[] orderedTypes = null;
	private static final Object2IntMap<GristType> IDS_BY_TYPE = new Object2IntOpenHashMap<>();

	private static void check() {
		if (orderedTypes == null) {
			final GristType[] types = new GristType[GristTypes.REGISTRY.size()];
			for (GristType type : GristTypes.REGISTRY)
				types[GristTypes.REGISTRY.getId(type)] = type;

			Arrays.sort(types);

			orderedTypes = types;
			for (int i = 0; i < types.length; i++)
				IDS_BY_TYPE.put(types[i], i);
		}
	}

	public static int size() {
		check();
		return orderedTypes.length;
	}

	public static int id(GristType type) {
		//check();
		return IDS_BY_TYPE.getInt(type);
	}

	public static GristType type(int id) {
		//check();
		return orderedTypes[id];
	}
}