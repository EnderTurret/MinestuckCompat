package net.enderturret.minestuckcompat.perf;

import java.util.List;
import java.util.Map;

import com.mraof.minestuck.api.alchemy.DefaultImmutableGristSet;
import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;

/**
 * <p>
 * {@code SmallImmutableGristSet} is a more memory-efficient {@linkplain com.mraof.minestuck.api.alchemy.GristSet.Immutable immutable grist set}.
 * While {@linkplain DefaultImmutableGristSet the default} stores the grist data in a {@link Map}, this implementation uses a {@code short} array.
 * </p>
 * <p>
 * There is one caveat with this approach: grist values (of a specific type) are limited to {@link Short#MAX_VALUE}.
 * </p>
 * <p>
 * There is no need to go out of your way to construct these; all the relevant code paths have already been altered to use this class.
 * Just call {@link GristSet#asImmutable()}, {@link GristSet#of(GristAmount...)}, or {@link DefaultImmutableGristSet#create(List)} as normal.
 * </p>
 * @author EnderTurret
 * @see MutableIntGristSet
 */
final class ImmutableShortGristSet extends AbstractShortGristSet implements GristSet.Immutable {

	private ImmutableShortGristSet(short[] gristByType) {
		super(gristByType);
	}

	static GristSet.Immutable from(int[] gristByType) {
		final short[] gristByTypeShort = new short[gristByType.length];
		for (int i = 0; i < gristByType.length; i++) gristByTypeShort[i] = (short) gristByType[i];
		return new ImmutableShortGristSet(gristByTypeShort);
	}

	static boolean isSmall(int[] gristByType) {
		for (int grist : gristByType)
			if (grist > Short.MAX_VALUE || grist < Short.MIN_VALUE)
				return false;

		return true;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}