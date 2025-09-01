package net.enderturret.minestuckcompat;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.language.ModFileScanData.AnnotationData;

import net.enderturret.minestuckcompat.alchemy.MCInterpreterTypes;

@Mod(MinestuckCompat.MOD_ID)
public final class MinestuckCompat {

	public static final String MOD_ID = "minestuckcompat";
	public static final Logger LOGGER = LoggerFactory.getLogger("MinestuckCompat");

	public MinestuckCompat(ModContainer mc, IEventBus modBus) {
		mc.registerConfig(ModConfig.Type.COMMON, MinestuckCompatConfig.COMMON_SPEC);
		MCInterpreterTypes.REGISTRY.register(modBus);

		modBus.addListener(this::onLoadComplete);
	}

	private void onLoadComplete(FMLLoadCompleteEvent e) {
		e.enqueueWork(this::nukeAnnotations);
	}

	private void nukeAnnotations() {
		final Set<String> removals = Set.of(
				"com.google.common.annotations.VisibleForTesting",
				"com.mojang.blaze3d.DontObfuscate",
				"com.mojang.blaze3d.FieldsAreNonnullByDefault",
				"com.mojang.blaze3d.MethodsReturnNonnullByDefault",
				"com.mojang.math.FieldsAreNonnullByDefault",
				"com.mojang.math.MethodsReturnNonnullByDefault",
				"java.lang.annotation.Documented",
				"java.lang.annotation.Retention",
				"java.lang.annotation.Target",
				"java.lang.Deprecated",
				"java.lang.FunctionalInterface",
				"java.lang.SafeVarargs",
				"javax.annotation.CheckReturnValue",
				"javax.annotation.concurrent.Immutable",
				"javax.annotation.concurrent.ThreadSafe",
				"javax.annotation.Nonnegative",
				"javax.annotation.Nonnull",
				"javax.annotation.Nullable",
				"javax.annotation.ParametersAreNonnullByDefault",
				"mekanism.api.annotations.FieldsAreNotNullByDefault",
				"mekanism.api.annotations.NothingNullByDefault",
				"mekanism.api.annotations.ParametersAreNotNullByDefault",
				"mezz.jei.core.FieldsAndMethodsAreNonnullByDefault",
				"net.minecraft.FieldsAreNonnullByDefault",
				"net.minecraft.MethodsReturnNonnullByDefault",
				"net.minecraft.obfuscate.DontObfuscate",
				"net.minecraft.util.VisibleForDebug",
				"net.neoforged.api.distmarker.OnlyIn", // Save 3 MB of RAM with this one neat trick!
				"org.jetbrains.annotations.ApiStatus$Experimental",
				"org.jetbrains.annotations.ApiStatus$Internal",
				"org.jetbrains.annotations.ApiStatus$NonExtendable",
				"org.jetbrains.annotations.ApiStatus$OverrideOnly",
				"org.jetbrains.annotations.ApiStatus$ScheduledForRemoval",
				"org.jetbrains.annotations.Contract",
				"org.jetbrains.annotations.MustBeInvokedByOverriders",
				"org.jetbrains.annotations.NotNull",
				"org.jetbrains.annotations.Nullable",
				"org.jetbrains.annotations.VisibleForTesting",
				// I think these are safe, since Mixin is loader-agnostic.
				"org.spongepowered.asm.mixin.Final",
				"org.spongepowered.asm.mixin.gen.Accessor",
				"org.spongepowered.asm.mixin.gen.Invoker",
				"org.spongepowered.asm.mixin.injection.At",
				"org.spongepowered.asm.mixin.injection.Coerce",
				"org.spongepowered.asm.mixin.injection.Inject",
				"org.spongepowered.asm.mixin.injection.ModifyArg",
				"org.spongepowered.asm.mixin.injection.ModifyVariable",
				"org.spongepowered.asm.mixin.injection.Redirect",
				"org.spongepowered.asm.mixin.injection.Slice",
				// Keep this one in the scan, just in case someone's using it to identify mixins.
				//"org.spongepowered.asm.mixin.Mixin",
				"org.spongepowered.asm.mixin.Mutable",
				"org.spongepowered.asm.mixin.Overwrite",
				"org.spongepowered.asm.mixin.Pseudo",
				"org.spongepowered.asm.mixin.Shadow",
				"org.spongepowered.asm.mixin.Unique",
				"com.llamalad7.mixinextras.injector.ModifyExpressionValue",
				"com.llamalad7.mixinextras.injector.ModifyReturnValue",
				"com.llamalad7.mixinextras.injector.v2.WrapWithCondition",
				"com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation"
				);
		final Set<String> kinds = new HashSet<>();
		int removed = 0;

		for (ModFileScanData scan : ModList.get().getAllScanData()) {
			for (var it = scan.getAnnotations().iterator(); it.hasNext(); ) {
				final AnnotationData anno = it.next();
				final String cls = anno.annotationType().getClassName();
				if (removals.contains(cls)) {
					it.remove();
					removed++;
				}
				else kinds.add(cls);
			}
		}

		MinestuckCompat.LOGGER.info("Annotations types: {}", kinds);
		MinestuckCompat.LOGGER.info("Nuked {} annotations from the scan data!", removed);
	}
}