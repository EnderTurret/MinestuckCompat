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
import net.enderturret.minestuckcompat.alchemy.create.FluidProcessingInterpreter;
import net.enderturret.minestuckcompat.alchemy.create.SequencedAssemblyInterpreter;
import net.enderturret.minestuckcompat.alchemy.extradelight.ExtraDelightFluidInterpreter;
import net.enderturret.minestuckcompat.alchemy.extradelight.FeastInterpreter;
import net.enderturret.minestuckcompat.alchemy.extradelight.FermentingInterpreter;
import net.enderturret.minestuckcompat.alchemy.extradelight.OvenInterpreter;
import net.enderturret.minestuckcompat.alchemy.extradelight.ToolOnBlockInterpreter;
import net.enderturret.minestuckcompat.alchemy.farmersdelight.CookingInterpreter;
import net.enderturret.minestuckcompat.alchemy.farmersdelight.CuttingBoardInterpreter;
import net.enderturret.minestuckcompat.alchemy.ie.AlloySmelterInterpreter;
import net.enderturret.minestuckcompat.alchemy.ie.CrusherInterpreter;
import net.enderturret.minestuckcompat.alchemy.ie.IEShapedInterpreter;
import net.enderturret.minestuckcompat.alchemy.ie.MetalPressInterpreter;
import net.enderturret.minestuckcompat.alchemy.ie.MultiblockInterpreter;
import net.enderturret.minestuckcompat.alchemy.mekanism.Item2ItemInterpreter;
import net.enderturret.minestuckcompat.alchemy.mekanism.ItemChemical2ItemInterpreter;
import net.enderturret.minestuckcompat.api.alchemy.SimpleRecipeInterpreter;

/**
 * Minestuck Compat's interpreter types.
 * @author EnderTurret
 */
public final class MCInterpreterTypes {

	/**
	 * Minestuck Compat's {@link DeferredRegister}.
	 */
	@Internal
	public static final DeferredRegister<MapCodec<? extends RecipeInterpreter>> REGISTRY = DeferredRegister.create(InterpreterTypes.REGISTRY, MinestuckCompat.MOD_ID);

	/**
	 * The codec for {@link SimpleRecipeInterpreter}.
	 */
	public static final Holder<MapCodec<? extends RecipeInterpreter>> SIMPLE;

	// ===== Applied Energistics 2 =====

	/**
	 * The codec for {@link ChargerInterpreter}. Will be {@code null} if AE2 is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> AE2_CHARGER;

	/**
	 * The codec for {@link InscriberInterpreter}. Will be {@code null} if AE2 is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> AE2_INSCRIBER;

	// ===== Create =====

	/**
	 * The codec for {@link FluidProcessingInterpreter}. Will be {@code null} if Create is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> CREATE_FLUID_PROCESSING;

	/**
	 * The codec for {@link SequencedAssemblyInterpreter}. Will be {@code null} if Create is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> CREATE_SEQUENCED_ASSEMBLY;

	// ===== ExtraDelight =====

	/**
	 * The codec for {@link FeastInterpreter}. Will be {@code null} if ExtraDelight is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> EXTRADELIGHT_FEAST;

	/**
	 * The codec for {@link FermentingInterpreter}. Will be {@code null} if ExtraDelight is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> EXTRADELIGHT_FERMENTING;

	/**
	 * The codec for {@link ExtraDelightFluidInterpreter}. Will be {@code null} if ExtraDelight is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> EXTRADELIGHT_FLUID_RECIPE;

	/**
	 * The codec for {@link OvenInterpreter}. Will be {@code null} if ExtraDelight is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> EXTRADELIGHT_OVEN;

	/**
	 * The codec for {@link ToolOnBlockInterpreter}. Will be {@code null} if ExtraDelight is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> EXTRADELIGHT_TOOL_ON_BLOCK;

	// ===== Farmers Delight =====

	/**
	 * The codec for {@link CookingInterpreter}. Will be {@code null} if Farmers Delight is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> FARMERSDELIGHT_COOKING;

	/**
	 * The codec for {@link CuttingBoardInterpreter}. Will be {@code null} if Farmers Delight is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> FARMERSDELIGHT_CUTTING_BOARD;

	// ===== Immersive Engineering =====

	/**
	 * The codec for {@link MultiblockInterpreter}. Will be {@code null} if Immersive Engineering is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> IE_MULTIBLOCK;

	/**
	 * The codec for {@link IEShapedInterpreter}. Will be {@code null} if Immersive Engineering is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> IE_SHAPED;

	/**
	 * The codec for {@link AlloySmelterInterpreter}. Will be {@code null} if Immersive Engineering is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> IE_ALLOY_SMELTER;

	/**
	 * The codec for {@link CrusherInterpreter}. Will be {@code null} if Immersive Engineering is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> IE_CRUSHER;

	/**
	 * The codec for {@link MetalPressInterpreter}. Will be {@code null} if Immersive Engineering is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> IE_METAL_PRESS;

	// ===== Mekanism =====

	/**
	 * The codec for {@link ItemChemical2ItemInterpreter}. Will be {@code null} if Mekanism is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> MEKANISM_ITEM_CHEMICAL_TO_ITEM;

	/**
	 * The codec for {@link Item2ItemInterpreter}. Will be {@code null} if Mekanism is not present.
	 */
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> MEKANISM_ITEM_TO_ITEM;

	// ===== GTCEu =====

	/*
	@Nullable
	public static final Holder<MapCodec<? extends RecipeInterpreter>> GTCEU_RECIPE;
	*/

	static {
		SIMPLE = REGISTRY.register("simple", () -> SimpleRecipeInterpreter.CODEC);

		if (ModList.get().isLoaded("ae2")) {
			AE2_CHARGER = REGISTRY.register("ae2/charger", () -> ChargerInterpreter.CODEC);
			AE2_INSCRIBER = REGISTRY.register("ae2/inscriber", () -> InscriberInterpreter.CODEC);
		} else {
			AE2_CHARGER = null;
			AE2_INSCRIBER = null;
		}

		if (ModList.get().isLoaded("create")) {
			CREATE_FLUID_PROCESSING = REGISTRY.register("create/fluid_processing", () -> FluidProcessingInterpreter.CODEC);
			CREATE_SEQUENCED_ASSEMBLY = REGISTRY.register("create/sequenced_assembly", () -> SequencedAssemblyInterpreter.CODEC);
		} else {
			CREATE_FLUID_PROCESSING = null;
			CREATE_SEQUENCED_ASSEMBLY = null;
		}

		if (ModList.get().isLoaded("extradelight")) {
			EXTRADELIGHT_FEAST = REGISTRY.register("extradelight/feast", () -> FeastInterpreter.CODEC);
			EXTRADELIGHT_FERMENTING = REGISTRY.register("extradelight/fermenting", () -> FermentingInterpreter.CODEC);
			EXTRADELIGHT_FLUID_RECIPE = REGISTRY.register("extradelight/fluid_recipe", () -> ExtraDelightFluidInterpreter.CODEC);
			EXTRADELIGHT_TOOL_ON_BLOCK = REGISTRY.register("extradelight/tool_on_block", () -> ToolOnBlockInterpreter.CODEC);
			EXTRADELIGHT_OVEN = REGISTRY.register("extradelight/oven", () -> OvenInterpreter.CODEC);
		} else {
			EXTRADELIGHT_FEAST = null;
			EXTRADELIGHT_FERMENTING = null;
			EXTRADELIGHT_FLUID_RECIPE = null;
			EXTRADELIGHT_TOOL_ON_BLOCK = null;
			EXTRADELIGHT_OVEN = null;
		}

		if (ModList.get().isLoaded("farmersdelight")) {
			FARMERSDELIGHT_COOKING = REGISTRY.register("farmersdelight/cooking", () -> CookingInterpreter.CODEC);
			FARMERSDELIGHT_CUTTING_BOARD = REGISTRY.register("farmersdelight/cutting_board", () -> CuttingBoardInterpreter.CODEC);
		} else {
			FARMERSDELIGHT_COOKING = null;
			FARMERSDELIGHT_CUTTING_BOARD = null;
		}

		if (ModList.get().isLoaded("immersiveengineering")) {
			IE_MULTIBLOCK = REGISTRY.register("immersiveengineering/multiblock", () -> MultiblockInterpreter.CODEC);
			IE_SHAPED = REGISTRY.register("immersiveengineering/shaped", () -> IEShapedInterpreter.CODEC);
			IE_ALLOY_SMELTER = REGISTRY.register("immersiveengineering/alloy_smelter", () -> AlloySmelterInterpreter.CODEC);
			IE_CRUSHER = REGISTRY.register("immersiveengineering/crusher", () -> CrusherInterpreter.CODEC);
			IE_METAL_PRESS = REGISTRY.register("immersiveengineering/metal_press", () -> MetalPressInterpreter.CODEC);
		} else {
			IE_MULTIBLOCK = null;
			IE_SHAPED = null;
			IE_ALLOY_SMELTER = null;
			IE_CRUSHER = null;
			IE_METAL_PRESS = null;
		}

		if (ModList.get().isLoaded("mekanism")) {
			MEKANISM_ITEM_CHEMICAL_TO_ITEM = REGISTRY.register("mekanism/item_chemical_to_item", () -> ItemChemical2ItemInterpreter.CODEC);
			MEKANISM_ITEM_TO_ITEM = REGISTRY.register("mekanism/item_to_item", () -> Item2ItemInterpreter.CODEC);
		} else {
			MEKANISM_ITEM_CHEMICAL_TO_ITEM = null;
			MEKANISM_ITEM_TO_ITEM = null;
		}

		/*
		if (ModList.get().isLoaded("gtceu")) {
			GTCEU_RECIPE = REGISTRY.register("gtceu/recipe", () -> GTRecipeInterpreter.CODEC);
		} else
			GTCEU_RECIPE = null;
		*/
	}
}