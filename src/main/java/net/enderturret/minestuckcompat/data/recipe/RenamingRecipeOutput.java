package net.enderturret.minestuckcompat.data.recipe;

import org.jetbrains.annotations.Nullable;

import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import net.neoforged.neoforge.common.conditions.ICondition;

public final class RenamingRecipeOutput implements RecipeOutput {

	private final RecipeOutput parent;
	private final String modId;
	private final String prefix;

	public RenamingRecipeOutput(RecipeOutput parent, String modId, String prefix) {
		this.parent = parent;
		this.modId = modId;
		this.prefix = prefix;
	}

	private ResourceLocation rename(ResourceLocation input) {
		final String path = input.getPath();
		return ResourceLocation.fromNamespaceAndPath(modId, path.contains("grist_costs") ? path.replace("grist_costs/", "grist_costs/" + prefix + "/") : path.replace("combinations/", "combinations/" + prefix + "/"));
	}

	@Override
	public void accept(ResourceLocation id, Recipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition... conditions) {
		parent.accept(rename(id), recipe, advancement, conditions);
	}

	@Override
	public void accept(ResourceLocation location, Recipe<?> recipe, AdvancementHolder advancement) {
		parent.accept(rename(location), recipe, advancement);
	}

	@Override
	public Builder advancement() {
		return parent.advancement();
	}
}