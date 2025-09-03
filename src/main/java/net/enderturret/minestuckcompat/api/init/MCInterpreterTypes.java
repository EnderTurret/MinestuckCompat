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
	public static final Holder<MapCodec<? extends RecipeInterpreter>> AE2_CHARGER;
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> AE2_INSCRIBER;

	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> CREATE_SEQUENCED_ASSEMBLY;

	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> MEKANISM_ITEM_CHEMICAL_TO_ITEM;
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> MEKANISM_ITEM_TO_ITEM;

	static {
		SIMPLE = REGISTRY.register("simple", () -> SimpleRecipeInterpreter.CODEC);

		if (ModList.get().isLoaded("ae2")) {
			AE2_CHARGER = REGISTRY.register("ae2/charger", () -> ChargerInterpreter.CODEC);
			AE2_INSCRIBER = REGISTRY.register("ae2/inscriber", () -> InscriberInterpreter.CODEC);
		} else {
			AE2_CHARGER = null;
			AE2_INSCRIBER = null;
		}

		if (ModList.get().isLoaded("create"))
			CREATE_SEQUENCED_ASSEMBLY = REGISTRY.register("create/sequenced_assembly", () -> SequencedAssemblyInterpreter.CODEC);
		else
			CREATE_SEQUENCED_ASSEMBLY = null;

		if (ModList.get().isLoaded("mekanism")) {
			MEKANISM_ITEM_CHEMICAL_TO_ITEM = REGISTRY.register("mekanism/item_chemical_to_item", () -> ItemChemical2ItemInterpreter.CODEC);
			MEKANISM_ITEM_TO_ITEM = REGISTRY.register("mekanism/item_to_item", () -> Item2ItemInterpreter.CODEC);
		} else {
			MEKANISM_ITEM_CHEMICAL_TO_ITEM = null;
			MEKANISM_ITEM_TO_ITEM = null;
		}
	}
}