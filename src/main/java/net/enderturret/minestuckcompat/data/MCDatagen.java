package net.enderturret.minestuckcompat.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider.Factory;

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
import net.enderturret.minestuckcompat.data.recipe.MekanismRecipes;
import net.enderturret.minestuckcompat.data.recipe.MinestuckRecipes;
import net.enderturret.minestuckcompat.data.recipe.VanillaRecipes;

@EventBusSubscriber(modid = MinestuckCompat.MOD_ID)
public final class MCDatagen {

	@SubscribeEvent
	static void gatherData(GatherDataEvent e) {
		final ExistingFileHelper files = e.getExistingFileHelper();
		final CompletableFuture<HolderLookup.Provider> lookup = e.getLookupProvider();
		final boolean server = e.includeServer();

		e.getGenerator().addProvider(server, (Factory<MCGeneratedGristCostConfig>) MCGeneratedGristCostConfig::new);

		e.getGenerator().addProvider(server, (Factory<WrappedDataProvider>) output -> new WrappedDataProvider(new VanillaRecipes(output, lookup), "Vanilla Recipes"));
		e.getGenerator().addProvider(server, (Factory<WrappedDataProvider>) output -> new WrappedDataProvider(new MinestuckRecipes(output, lookup), "Minestuck Recipes"));

		if (ModList.get().isLoaded("mekanism"))
			e.getGenerator().addProvider(server, (Factory<WrappedDataProvider>) output -> new WrappedDataProvider(new MekanismRecipes(output, lookup), "Mekanism Recipes"));

		if (ModList.get().isLoaded("ae2"))
			e.getGenerator().addProvider(server, (Factory<WrappedDataProvider>) output -> new WrappedDataProvider(new Ae2Recipes(output, lookup), "AE2 Recipes"));

		if (ModList.get().isLoaded("create"))
			e.getGenerator().addProvider(server, (Factory<WrappedDataProvider>) output -> new WrappedDataProvider(new CreateRecipes(output, lookup), "Create Recipes"));

		if (ModList.get().isLoaded("biomesoplenty"))
			e.getGenerator().addProvider(server, (Factory<WrappedDataProvider>) output -> new WrappedDataProvider(new BiomesOPlentyRecipes(output, lookup), "Biomes O' Plenty Recipes"));
	}
}