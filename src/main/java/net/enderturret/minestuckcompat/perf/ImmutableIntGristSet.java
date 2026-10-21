package net.enderturret.minestuckcompat.perf;

import java.util.List;
import java.util.Map;

import com.mraof.minestuck.api.alchemy.DefaultImmutableGristSet;
import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;

import net.enderturret.minestuckcompat.alchemy.MixinHooks;

/**
 * <p>
 * {@code ImmutableIntGristSet} is a more memory-efficient {@linkplain com.mraof.minestuck.api.alchemy.GristSet.Immutable immutable grist set}.
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
 * @see MutableIntGristSet
 */
public final class ImmutableIntGristSet extends AbstractIntGristSet implements GristSet.Immutable {

	private ImmutableIntGristSet(int[] gristByType) {
		super(gristByType);
	}

	static GristSet.Immutable from(int[] gristByType) {
		boolean empty = true;
		for (int i : gristByType)
			if (i != 0) {
				empty = false;
				break;
			}

		return empty ? EmptyGristSet.INSTANCE : smallImmutable(gristByType);
	}

	private static GristSet.Immutable smallImmutable(int[] gristByType) {
		/*
		if (!MinestuckCompatConfig.common().shrinkGristSets.getAsBoolean()) {
			final Map<GristType, Long> types = new LinkedHashMap<>();
			for (int i = 0; i < gristByType.length; i++)
				if (gristByType[i] != 0)
					types.put(GristTypeHelper.type(i), (long) gristByType[i]);

			return new DefaultImmutableGristSet(types);
		}
		*/

		if (/* MinestuckCompatConfig.common().shortGristSets.getAsBoolean() && */ ImmutableShortGristSet.isSmall(gristByType))
			return MixinHooks.intern(ImmutableShortGristSet.from(gristByType));

		return MixinHooks.intern(new ImmutableIntGristSet(gristByType));
	}

	/**
	 * Creates an {@linkplain com.mraof.minestuck.api.alchemy.GristSet.Immutable immutable grist set} from the specified grist set.
	 * @param set The grist set to create the immutable one from.
	 * @return The created grist set.
	 */
	public static GristSet.Immutable create(GristSet set) {
		final int[] gristByType = gristByType(set, true);
		return gristByType == null ? EmptyGristSet.INSTANCE : smallImmutable(gristByType);
	}

	/**
	 * Creates an {@linkplain com.mraof.minestuck.api.alchemy.GristSet.Immutable immutable grist set} from the specified map.
	 * @param map The map to create the grist set from.
	 * @return The created grist set.
	 */
	public static GristSet.Immutable create(Map<GristType, Long> map) {
		final int[] gristByType = gristByType(map, true);
		return gristByType == null ? EmptyGristSet.INSTANCE : smallImmutable(gristByType);
	}

	/**
	 * Creates an {@linkplain com.mraof.minestuck.api.alchemy.GristSet.Immutable immutable grist set} from the specified list.
	 * @param list The list to create the grist set from.
	 * @return The created grist set.
	 */
	public static GristSet.Immutable create(List<GristAmount> list) {
		if (list.isEmpty()) return EmptyGristSet.INSTANCE;
		if (list.size() == 1 /* && MinestuckCompatConfig.common().shrinkGristSets.getAsBoolean() */)
			return MixinHooks.intern(list.get(0));
		return smallImmutable(gristByType(list));
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}