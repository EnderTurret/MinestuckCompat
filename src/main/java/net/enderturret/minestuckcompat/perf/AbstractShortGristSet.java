package net.enderturret.minestuckcompat.perf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;
import com.mraof.minestuck.api.alchemy.MutableGristSet;

import net.enderturret.minestuckcompat.MinestuckCompat;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;

abstract sealed class AbstractShortGristSet implements GristSet permits ImmutableShortGristSet {

	final short[] gristByType;

	AbstractShortGristSet(short[] gristByType) {
		this.gristByType = gristByType;
	}

	@Override
	public Map<GristType, Long> asMap() {
		final Object2LongMap<GristType> ret = new Object2LongOpenHashMap<>();

		for (int i = 0; i < gristByType.length; i++) {
			if (gristByType[i] == 0) continue;
			final GristType type = GristTypeHelper.type(i);
			ret.put(type, gristByType[i]);
		}

		return ret;
	}

	@Override
	public List<GristAmount> asAmounts() {
		final List<GristAmount> ret = new ArrayList<>();

		for (int i = 0; i < gristByType.length; i++) {
			if (gristByType[i] == 0) continue;
			final GristType type = GristTypeHelper.type(i);
			ret.add(new GristAmount(type, gristByType[i]));
		}

		return ret;
	}

	@Override
	public long getGrist(GristType type) {
		return gristByType[GristTypeHelper.id(type)];
	}

	@Override
	public boolean hasType(GristType type) {
		return gristByType[GristTypeHelper.id(type)] != 0;
	}

	@Override
	public boolean isEmpty() {
		for (int i : gristByType)
			if (i != 0) return false;
		return true;
	}

	@Override
	public MutableGristSet mutableCopy() {
		final int[] gristByTypeInt = new int[gristByType.length];
		for (int i = 0; i < gristByType.length; i++) gristByTypeInt[i] = gristByType[i];
		return new MutableIntGristSet(gristByTypeInt);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append(" { ");

		boolean first = true;
		for (int i = 0; i < gristByType.length; i++) {
			if (gristByType[i] == 0) continue;
			if (first) first = false;
			else sb.append(", ");

			final GristType type = GristTypeHelper.type(i);
			sb.append(type.getId().getPath()).append(" x").append(gristByType[i]);
		}

		sb.append(" }");
		return sb.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof AbstractShortGristSet set)) return false;
		return getClass() == set.getClass() && equalContent(set);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(asAmounts().toArray());
	}
}