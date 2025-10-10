package net.enderturret.minestuckcompat.alchemy.analysis;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.mraof.minestuck.api.alchemy.recipe.generator.LookupTracker;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public final class AnalyzingLookupTracker implements LookupTracker {

	private final Set<SimpleIngredient> ingredients = new LinkedHashSet<>();

	public Set<SimpleIngredient> getIngredients() {
		return Collections.unmodifiableSet(ingredients);
	}

	@Override
	public void report(Item item) {
		if (item == Items.AIR) return;
		ingredients.add(SimpleIngredient.of(item));
	}

	@Override
	public void report(Ingredient ingredient) {
		if (ingredient == Ingredient.EMPTY) return;
		ingredients.add(SimpleIngredient.of(ingredient));
	}

	public void report(TagKey<Item> tag) {
		ingredients.add(SimpleIngredient.of(tag));
	}

	public void report(List<Item> ingredient) {
		if (ingredient.isEmpty()) return;
		ingredients.add(new SimpleIngredient(List.copyOf(ingredient)));
	}
}