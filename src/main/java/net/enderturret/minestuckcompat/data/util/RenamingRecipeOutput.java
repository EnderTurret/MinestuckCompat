package net.enderturret.minestuckcompat.data.util;

import org.jetbrains.annotations.Nullable;

import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import net.neoforged.neoforge.common.conditions.ICondition;

/**
 * A {@link RecipeOutput} wrapper that moves generated recipes into subfolders.
 * @author EnderTurret
 */
public final class RenamingRecipeOutput implements RecipeOutput {

	private final RecipeOutput parent;
	private final String modId;
	@Nullable
	private final String prefix;

	public RenamingRecipeOutput(RecipeOutput parent, String modId, @Nullable String prefix) {
		this.parent = parent;
		this.modId = modId;
		this.prefix = prefix;
	}

	private ResourceLocation rename(ResourceLocation input) {
		String path = input.getPath();

		if (prefix != null) {
			if (path.contains("grist_costs"))
				path = path.replace("grist_costs/", "grist_costs/" + prefix + "/");
			else if (path.contains("combinations"))
				path = path.replace("combinations/", "combinations/" + prefix + "/");
			else if (path.contains("cooking/oven"))
				path = path.replace("cooking/oven/", prefix + "/cooking/oven/");
		}

		return ResourceLocation.fromNamespaceAndPath(modId, path);
	}

	@Override
	public void accept(ResourceLocation id, Recipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition... conditions) {
		parent.accept(rename(id), recipe, null, conditions);
	}

	@Override
	public void accept(ResourceLocation location, Recipe<?> recipe, AdvancementHolder advancement) {
		parent.accept(rename(location), recipe, null);
	}

	@Override
	public Builder advancement() {
		return parent.advancement();
	}
}