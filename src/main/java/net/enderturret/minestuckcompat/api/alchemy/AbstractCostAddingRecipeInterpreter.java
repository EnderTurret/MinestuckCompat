package net.enderturret.minestuckcompat.api.alchemy;

import org.jetbrains.annotations.Nullable;

import com.mraof.minestuck.api.alchemy.GristSet;
import com.mraof.minestuck.api.alchemy.MutableGristSet;

public abstract class AbstractCostAddingRecipeInterpreter extends AbstractRecipeInterpreter {

	protected final GristSet.Immutable addedCost;

	protected AbstractCostAddingRecipeInterpreter(GristSet.Immutable addedCost) {
		this.addedCost = addedCost;
	}

	public GristSet.Immutable addedCost() {
		return addedCost;
	}

	@Override
	@Nullable
	protected GristSet finalizeGristCosts(@Nullable MutableGristSet totalCost, int resultCount) {
		if (totalCost != null)
			totalCost.add(addedCost);

		return super.finalizeGristCosts(totalCost, resultCount);
	}
}