package net.enderturret.minestuckcompat.mixin.feature.outside_grist_costs;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.mraof.minestuck.api.alchemy.recipe.generator.GeneratedCostProvider;

import net.minecraft.world.item.Item;

@Mixin(targets = "com/mraof/minestuck/alchemy/recipe/generator/GristCostGenerator$GeneratorProcess")
public interface GeneratorProcessAccess {

	@Accessor("providersByItem")
	public Map<Item, List<GeneratedCostProvider>> minestuckcompat$getProvidersByItem();

	@Accessor("providers")
	public Set<GeneratedCostProvider> minestuckcompat$getProviders();
}