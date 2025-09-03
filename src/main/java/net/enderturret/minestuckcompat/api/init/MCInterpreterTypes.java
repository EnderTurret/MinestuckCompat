package net.enderturret.minestuckcompat.api.init;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.InterpreterTypes;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;

import net.minecraft.core.Holder;

import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.alchemy.ae2.ChargerInterpreter;
import net.enderturret.minestuckcompat.alchemy.ae2.InscriberInterpreter;
import net.enderturret.minestuckcompat.alchemy.create.SequencedAssemblyInterpreter;
import net.enderturret.minestuckcompat.alchemy.mekanism.Item2ItemInterpreter;
import net.enderturret.minestuckcompat.alchemy.mekanism.ItemChemical2ItemInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.SimpleRecipeInterpreter;

/**
 * MinestuckCompat's interpreter types.
 * @author EnderTurret
 */
public final class MCInterpreterTypes {

	@Internal
	public static final DeferredRegister<MapCodec<? extends RecipeInterpreter>> REGISTRY = DeferredRegister.create(InterpreterTypes.REGISTRY, MinestuckCompat.MOD_ID);

	public static final Holder<MapCodec<? extends RecipeInterpreter>> SIMPLE;

	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> ITEM_CHEMICAL_TO_ITEM;
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> ITEM_TO_ITEM;

	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> CHARGER;
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> INSCRIBER;

	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> SEQUENCED_ASSEMBLY;

	static {
		SIMPLE = REGISTRY.register("simple", () -> SimpleRecipeInterpreter.CODEC);

		if (ModList.get().isLoaded("mekanism")) {
			ITEM_CHEMICAL_TO_ITEM = REGISTRY.register("item_chemical_to_item", () -> ItemChemical2ItemInterpreter.CODEC);
			ITEM_TO_ITEM = REGISTRY.register("item_to_item", () -> Item2ItemInterpreter.CODEC);
		} else {
			ITEM_CHEMICAL_TO_ITEM = null;
			ITEM_TO_ITEM = null;
		}

		if (ModList.get().isLoaded("ae2")) {
			CHARGER = REGISTRY.register("charger", () -> ChargerInterpreter.CODEC);
			INSCRIBER = REGISTRY.register("inscriber", () -> InscriberInterpreter.CODEC);
		} else {
			CHARGER = null;
			INSCRIBER = null;
		}

		if (ModList.get().isLoaded("create")) {
			SEQUENCED_ASSEMBLY = REGISTRY.register("sequenced_assembly", () -> SequencedAssemblyInterpreter.CODEC);
		} else {
			SEQUENCED_ASSEMBLY = null;
		}
	}
}