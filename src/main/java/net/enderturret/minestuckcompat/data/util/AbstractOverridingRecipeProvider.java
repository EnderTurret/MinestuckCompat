package net.enderturret.minestuckcompat.data.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;

import net.neoforged.neoforge.common.conditions.IConditionBuilder;

public abstract class AbstractOverridingRecipeProvider extends RecipeProvider implements IConditionBuilder {

	public AbstractOverridingRecipeProvider(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries);
	}

	@Override
	protected abstract void buildRecipes(RecipeOutput recipeOutput);

	protected void remove(ResourceLocation name) {
		extraFutures.add(DataProvider.saveStable(cachedOutput, NO_RECIPE, recipePathProvider.json(name)));
	}

	private final List<CompletableFuture<?>> extraFutures = new ArrayList<>();
	private CachedOutput cachedOutput;
	private HolderLookup.Provider registries;

	@Override
	protected CompletableFuture<?> run(CachedOutput output, HolderLookup.Provider registries) {
		cachedOutput = output;
		this.registries = registries;

		extraFutures.add(0, super.run(output, registries));

		cachedOutput = null;
		this.registries = null;

		return CompletableFuture.allOf(extraFutures.toArray(CompletableFuture[]::new));
	}

	private static final JsonObject NO_RECIPE;

	static {
		// {"neoforge:conditions":[{"type":"neoforge:false"}]}"
		NO_RECIPE = new JsonObject();

		final JsonArray cons = new JsonArray();
		NO_RECIPE.add("neoforge:conditions", cons);

		final JsonObject con = new JsonObject();
		con.addProperty("type", "neoforge:false");
		cons.add(con);
	}
}