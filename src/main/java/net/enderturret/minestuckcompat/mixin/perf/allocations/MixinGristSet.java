package net.enderturret.minestuckcompat.mixin.perf.allocations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.mraof.minestuck.api.alchemy.GristAmount;
import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.GristType;

@Mixin(GristSet.class)
public interface MixinGristSet {

	@Overwrite
	public default boolean isEmpty() {
		final GristSet self = (GristSet) this;

		for (Long l : self.asMap().values())
			if (l != 0)
				return false;

		return true;
	}

	@Overwrite
	public default double getValue() {
		final GristSet self = (GristSet) this;
		double ret = 0;

		for (Long l : self.asMap().values())
			ret += l;

		return ret;
	}

	@Overwrite
	public default List<GristAmount> asAmounts() {
		final GristSet self = (GristSet) this;
		final Map<GristType, Long> map = self.asMap();
		final List<GristAmount> list = new ArrayList<>(map.size());

		for (Map.Entry<GristType, Long> entry : map.entrySet())
			list.add(new GristAmount(entry.getKey(), entry.getValue()));

		return list;
	}
}