package net.enderturret.minestuckcompat.perf;

import java.util.List;
import java.util.Map;

import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;

/**
 * Empty implementation of {@link com.mraof.minestuck.api.alchemy.GristSet.Immutable}.
 * @author EnderTurret
 * @see SmallImmutableGristSet
 */
public final class EmptyGristSet implements GristSet.Immutable {

	public static final EmptyGristSet INSTANCE = new EmptyGristSet();

	@Override
	public Map<GristType, Long> asMap() {
		return Map.of();
	}

	@Override
	public List<GristAmount> asAmounts() {
		return List.of(); // Base uses Steam#toList, so the contract implies immutability.
	}

	@Override
	public long getGrist(GristType type) {
		return 0;
	}

	@Override
	public boolean hasType(GristType type) {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}
}