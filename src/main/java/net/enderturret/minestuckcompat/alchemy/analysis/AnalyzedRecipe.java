package net.enderturret.minestuckcompat.alchemy.analysis;

import java.util.Arrays;
import java.util.List;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

record AnalyzedRecipe(List<SimpleIngredient> inputs, List<Item> outputs) {

	static AnalyzedRecipe fromCodec(List<SimpleIngredient> inputs, List<Item> outputs) {
		if (inputs.contains(SimpleIngredient.INVALID))
			return new AnalyzedRecipe(List.of(), List.of());

		if (outputs.contains(Items.AIR))
			outputs = outputs.stream().filter(item -> item != Items.AIR).toList();

		return new AnalyzedRecipe(inputs, outputs);
	}

	static AnalyzedRecipe singleInput(ItemLike input, ItemLike... outputs) {
		return new AnalyzedRecipe(List.of(SimpleIngredient.of(input)), Arrays.stream(outputs).map(ItemLike::asItem).toList());
	}

	static AnalyzedRecipe twoInputs(ItemLike input1, ItemLike input2, ItemLike... outputs) {
		return new AnalyzedRecipe(List.of(SimpleIngredient.of(input1), SimpleIngredient.of(input2)), Arrays.stream(outputs).map(ItemLike::asItem).toList());
	}

	@Override
	public final String toString() {
		return inputs + " ==> " + outputs;
	}
}