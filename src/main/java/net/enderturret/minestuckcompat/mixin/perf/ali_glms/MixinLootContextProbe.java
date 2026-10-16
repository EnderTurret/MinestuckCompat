package net.enderturret.minestuckcompat.mixin.perf.ali_glms;

import java.util.HashSet;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import net.neoforged.neoforge.common.loot.CanItemPerformAbility;

// Works around a major performance issue in ALI where analyzing GLMs can take upwards of 8 seconds (in Minestuck Compat's dev env).
// This mixin only applies in specifically Minestuck Compat's development environment, to avoid causing issues for other people.
@Mixin(targets = "com.yanny.ali.plugin.glm.LootContextProbe")
public abstract class MixinLootContextProbe {

	@Unique
	private static Set<Class> NOPE_CLASSES = null;

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/predicates/LootItemCondition;test(Ljava/lang/Object;)Z"),
			method = "probe")
	private static boolean minestuckcompat$optimize(LootItemCondition condition, Object context, Operation<Boolean> original) {
		if (NOPE_CLASSES == null) {
			final Set<Class> classes = new HashSet<>(Set.of(
					CanItemPerformAbility.class,
					LootItemRandomChanceCondition.class,
					MatchTool.class,
					LootItemBlockStatePropertyCondition.class
					));

			try {
				classes.add(Class.forName("blusunrize.immersiveengineering.common.util.loot.LootBlockStateFromLocationPredicate"));
			} catch (ClassNotFoundException ignored) {}

			NOPE_CLASSES = Set.copyOf(classes);
		}

		return !NOPE_CLASSES.contains(condition.getClass()) && original.call(condition, context);
	}
}