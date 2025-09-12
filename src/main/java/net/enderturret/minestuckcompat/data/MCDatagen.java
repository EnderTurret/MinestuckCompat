package net.enderturret.minestuckcompat.data;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.jetbrains.annotations.ApiStatus.Internal;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataProvider.Factory;
import net.minecraft.data.PackOutput;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.api.data.WrappedDataProvider;
import net.enderturret.minestuckcompat.data.recipe.Ae2Recipes;
import net.enderturret.minestuckcompat.data.recipe.BiomesOPlentyRecipes;
import net.enderturret.minestuckcompat.data.recipe.CreateRecipes;
import net.enderturret.minestuckcompat.data.recipe.FarmersDelightRecipes;
import net.enderturret.minestuckcompat.data.recipe.ImmersiveEngineeringRecipes;
import net.enderturret.minestuckcompat.data.recipe.MekanismRecipes;
import net.enderturret.minestuckcompat.data.recipe.MinestuckRecipes;
import net.enderturret.minestuckcompat.data.recipe.VanillaRecipes;

@Internal
@EventBusSubscriber(modid = MinestuckCompat.MOD_ID)
public final class MCDatagen {

	@SubscribeEvent
	static void gatherData(GatherDataEvent e) {
		final ExistingFileHelper files = e.getExistingFileHelper();
		final CompletableFuture<HolderLookup.Provider> lookup = e.getLookupProvider();
		final boolean server = e.includeServer();

		e.getGenerator().addProvider(server, (Factory<MCGeneratedGristCostConfig>) MCGeneratedGristCostConfig::new);

		e.getGenerator().addProvider(server, wrap(lookup, VanillaRecipes::new, "Vanilla Recipes"));
		e.getGenerator().addProvider(server, wrap(lookup, MinestuckRecipes::new, "Minestuck Recipes"));

		if (ModList.get().isLoaded("ae2"))
			e.getGenerator().addProvider(server, wrap(lookup, Ae2Recipes::new, "AE2 Recipes"));

		if (ModList.get().isLoaded("biomesoplenty"))
			e.getGenerator().addProvider(server, wrap(lookup, BiomesOPlentyRecipes::new, "Biomes O' Plenty Recipes"));

		if (ModList.get().isLoaded("create"))
			e.getGenerator().addProvider(server, wrap(lookup, CreateRecipes::new, "Create Recipes"));

		if (ModList.get().isLoaded("farmersdelight"))
			e.getGenerator().addProvider(server, wrap(lookup, FarmersDelightRecipes::new, "Farmers Delight Recipes"));

		if (ModList.get().isLoaded("immersiveengineering"))
			e.getGenerator().addProvider(server, wrap(lookup, ImmersiveEngineeringRecipes::new, "Immersive Engineering Recipes"));

		if (ModList.get().isLoaded("mekanism"))
			e.getGenerator().addProvider(server, wrap(lookup, MekanismRecipes::new, "Mekanism Recipes"));
	}

	private static Factory<WrappedDataProvider> wrap(CompletableFuture<HolderLookup.Provider> lookup, BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, DataProvider> provider, String name) {
		return output -> new WrappedDataProvider(provider.apply(output, lookup), name);
	}
}