package net.enderturret.minestuckcompat.alchemy.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

record SimpleIngredient(List<Item> items) {

	static final SimpleIngredient INVALID = new SimpleIngredient(List.of(Items.AIR));

	static SimpleIngredient of(ItemLike item) {
		return new SimpleIngredient(List.of(item.asItem()));
	}

	static SimpleIngredient of(TagKey<Item> tag) {
		return new SimpleIngredient(BuiltInRegistries.ITEM.getTag(tag)
				.map(named -> named.stream().map(Holder::value).toList())
				.orElse(List.of()));
	}

	static SimpleIngredient of(Ingredient ing) {
		final List<Item> items = new ArrayList<>(1);

		if (ing.isCustom())
			for (ItemStack item : ing.getItems())
				items.add(item.getItem());
		else
			for (Ingredient.Value val : ing.getValues()) {
				if (val instanceof Ingredient.ItemValue item)
					items.add(item.item().getItem());
				else if (val instanceof Ingredient.TagValue tag)
					items.addAll(of(tag.tag()).items);
			}

		return new SimpleIngredient(List.copyOf(items));
	}

	@SuppressWarnings("deprecation")
	@Override
	public final String toString() {
		return "[" + items.stream().map(item -> item.builtInRegistryHolder().getRegisteredName()).collect(Collectors.joining(", ")) + "]";
	}
}