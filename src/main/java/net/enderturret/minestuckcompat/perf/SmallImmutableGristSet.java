package net.enderturret.minestuckcompat.perf;

import java.util.List;
import java.util.Map;

import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;

public final class SmallImmutableGristSet extends AbstractSmallGristSet implements GristSet.Immutable {

	private SmallImmutableGristSet(int[] gristByType) {
		super(gristByType);
	}

	static GristSet.Immutable from(int[] gristByType) {
		boolean empty = true;
		for (int i : gristByType)
			if (i != 0) {
				empty = false;
				break;
			}

		return empty ? EmptyGristSet.INSTANCE : new SmallImmutableGristSet(gristByType);
	}

	public static GristSet.Immutable create(GristSet set) {
		final int[] gristByType = gristByType(set, true);
		return gristByType == null ? EmptyGristSet.INSTANCE : new SmallImmutableGristSet(gristByType);
	}

	public static GristSet.Immutable create(Map<GristType, Long> map) {
		final int[] gristByType = gristByType(map, true);
		return gristByType == null ? EmptyGristSet.INSTANCE : new SmallImmutableGristSet(gristByType);
	}

	public static GristSet.Immutable create(List<GristAmount> list) {
		if (list.isEmpty()) return EmptyGristSet.INSTANCE;
		if (list.size() == 1) return list.get(0);
		return new SmallImmutableGristSet(gristByType(list));
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}