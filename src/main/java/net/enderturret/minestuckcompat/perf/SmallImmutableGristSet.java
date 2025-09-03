package net.enderturret.minestuckcompat.perf;

import java.util.List;
import java.util.Map;

import com.mraof.minestuck.api.alchemy.DefaultImmutableGristSet;
import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;

/**
 * <p>
 * {@code SmallImmutableGristSet} is a more memory-efficient {@linkplain com.mraof.minestuck.api.alchemy.GristSet.Immutable immutable grist set}.
 * While {@linkplain DefaultImmutableGristSet the default} stores the grist data in a {@link Map}, this implementation uses an {@code int} array.
 * This ends up shrinking the memory footprint of an immutable grist set by around 3/4ths (i.e., a small immutable grist set is ~1/4 the size of a default one).
 * </p>
 * <p>
 * There is one caveat with this approach: grist values (of a specific type) are limited to {@link Integer#MAX_VALUE}.
 * Given the fact that this size is 2,147,483,647 however, I expect overflows will be unlikely.
 * (I don't think you can even have that much grist on you to begin with.)
 * </p>
 * <p>
 * There is no need to go out of your way to construct these; all the relevant code paths have already been altered to use this class.
 * Just call {@link GristSet#asImmutable()}, {@link GristSet#of(GristAmount...)}, or {@link DefaultImmutableGristSet#create(List)} as normal.
 * </p>
 * @author EnderTurret
 * @see SmallMutableGristSet
 */
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