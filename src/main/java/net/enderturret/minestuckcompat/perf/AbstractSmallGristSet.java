package net.enderturret.minestuckcompat.perf;

import java.util.ArrayList;
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

abstract sealed class AbstractSmallGristSet implements GristSet permits SmallImmutableGristSet, SmallMutableGristSet {

	final int[] gristByType;

	AbstractSmallGristSet(int[] gristByType) {
		this.gristByType = gristByType;
	}

	@Nullable
	static int[] gristByType(GristSet set, boolean emptyToNull) {
		final int[] gristByType = new int[GristTypeHelper.size()];
		boolean empty = true;
		for (int i = 0; i < gristByType.length; i++) {
			final GristType type = GristTypeHelper.type(i);
			if (set.hasType(type)) {
				empty = false;
				gristByType[i] = checkCast(set.getGrist(type));
			}
		}

		return empty && emptyToNull ? null : gristByType;
	}

	@Nullable
	static int[] gristByType(Map<GristType, Long> map, boolean emptyToNull) {
		final int[] gristByType = new int[GristTypeHelper.size()];
		boolean empty = true;
		for (int i = 0; i < gristByType.length; i++) {
			final GristType type = GristTypeHelper.type(i);
			if (map.containsKey(type)) {
				empty = false;
				gristByType[i] = checkCast(map.get(type));
			}
		}

		return empty && emptyToNull ? null : gristByType;
	}

	static int[] gristByType(List<GristAmount> list) {
		final int[] gristByType = new int[GristTypeHelper.size()];
		for (GristAmount amt : list) {
			final int index = GristTypeHelper.id(amt.type());
			gristByType[index] = checkCast(amt.amount());
		}

		return gristByType;
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
		return new SmallMutableGristSet(gristByType.clone());
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

	private static int checkCast(long cost) {
		if (cost > Integer.MAX_VALUE) MinestuckCompat.LOGGER.warn("Grist cost is larger than max allowed ({}): {}", Integer.MAX_VALUE, cost, new Throwable("stacktrace"));
		return (int) cost;
	}
}