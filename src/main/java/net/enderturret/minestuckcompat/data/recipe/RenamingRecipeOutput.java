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

	@Override
	public void accept(ResourceLocation id, Recipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition... conditions) {
		id = ResourceLocation.fromNamespaceAndPath(modId, id.getPath().replace("grist_costs/", "grist_costs/" + prefix + "/"));
		parent.accept(id, recipe, advancement, conditions);
	}

	@Override
	public void accept(ResourceLocation location, Recipe<?> recipe, AdvancementHolder advancement) {
		location = ResourceLocation.fromNamespaceAndPath(modId, location.getPath().replace("grist_costs/", "grist_costs/" + prefix + "/"));
		parent.accept(location, recipe, advancement);
	}

	@Override
	public Builder advancement() {
		return parent.advancement();
	}
}