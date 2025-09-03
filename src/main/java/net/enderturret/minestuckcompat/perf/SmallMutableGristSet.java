package net.enderturret.minestuckcompat.perf;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;
import com.mraof.minestuck.api.alchemy.GristTypes;
import com.mraof.minestuck.api.alchemy.MutableGristSet;

/**
 * <p>
 * {@code SmallMutableGristSet} is the mutable version of {@link SmallImmutableGristSet}.
 * </p>
 * <p>
 * The default mutable grist set is somehow more expensive than the immutable version,
 * with the fact that it uses a {@link TreeMap}(!) to store the grist types. Instead,
 * both small implementations are already ordered correctly (without needing a {@code TreeMap}).
 * </p>
 * <p>
 * There is no need to go out of your way to construct these; all the relevant code paths have already been altered to use this class.
 * Just call {@link MutableGristSet#newDefault()} or {@link GristSet#mutableCopy()} as normal.
 * </p>
 * @author EnderTurret
 * @see SmallImmutableGristSet
 */
public final class SmallMutableGristSet extends AbstractSmallGristSet implements MutableGristSet {

	SmallMutableGristSet(int[] gristByType) {
		super(gristByType);
	}

	public SmallMutableGristSet() {
		this(new int[GristTypes.REGISTRY.size()]);
	}

	public static MutableGristSet shrink(GristSet set) {
		final int[] gristByType = gristByType(set, false);
		return new SmallMutableGristSet(gristByType);
	}

	public static MutableGristSet shrink(List<GristAmount> list) {
		return new SmallMutableGristSet(gristByType(list));
	}

	@Override
	public MutableGristSet set(GristType type, long amount) {
		gristByType[GristTypeHelper.id(type)] = (int) amount;
		return this;
	}

	@Override
	public MutableGristSet add(GristType type, long amount) {
		gristByType[GristTypeHelper.id(type)] += (int) amount;
		return this;
	}

	@Override
	public MutableGristSet add(GristSet set) {
		if (set instanceof SmallImmutableGristSet smol) {
			for (int i = 0; i < gristByType.length; i++)
				gristByType[i] += smol.gristByType[i];

			return this;
		}

		for (Map.Entry<GristType, Long> entry : set.asMap().entrySet())
			add(entry.getKey(), entry.getValue());

		return this;
	}

	@Override
	public MutableGristSet scale(float scale, boolean roundDown) {
		for (int i = 0; i < gristByType.length; i++) {
			final int amount = gristByType[i];
			if (amount == 0) continue;

			gristByType[i] = roundDown ? (int) (amount * scale) : roundToNonZero(amount * scale);
		}

		return this;
	}

	@Override
	public Immutable asImmutable() {
		return SmallImmutableGristSet.from(gristByType.clone());
	}

	private static int roundToNonZero(float value) {
		if (value < 0) return Math.min(-1, Math.round(value));
		return Math.max(1, Math.round(value));
	}
}