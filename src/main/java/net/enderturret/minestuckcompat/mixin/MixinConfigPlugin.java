package net.enderturret.minestuckcompat.mixin;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import net.neoforged.fml.loading.LoadingModList;

public final class MixinConfigPlugin implements IMixinConfigPlugin {

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		if (mixinClassName.contains("immersiveengineering"))
			return LoadingModList.get().getModFileById("immersiveengineering") != null;

		if (mixinClassName.contains("jei_fixes"))
			return LoadingModList.get().getModFileById("jei") != null;

		if (mixinClassName.contains("data.")) {
			if (mixinClassName.contains("extradelight"))
				return LoadingModList.get().getModFileById("extradelight") != null;
		}

		return true;
	}

	@Override
	public List<String> getMixins() {
		if (Boolean.getBoolean("minestuckcompat.datagen"))
			return List.of(
					"perf.small_grist_set.data.MixinContainerGristCostBuilder",
					"perf.small_grist_set.data.MixinGristCostRecipeBuilder",
					"perf.small_grist_set.data.MixinSourceGristCostBuilder",
					"data.extradelight.MixinDataGen"
					);

		return null;
	}

	@Override
	public void onLoad(String mixinPackage) {}

	@Override
	public String getRefMapperConfig() { return null; }

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

}