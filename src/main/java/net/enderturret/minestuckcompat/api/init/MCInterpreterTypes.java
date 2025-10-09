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
	}
}