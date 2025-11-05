package net.enderturret.minestuckcompat.mixin.perf.small_grist_set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.mraof.minestuck.api.alchemy.DefaultMutableGristSet;
import com.mraof.minestuck.command.argument.GristSetArgument;

import net.enderturret.minestuckcompat.perf.SmallMutableGristSet;

@Mixin(GristSetArgument.class)
public abstract class MixinGristSetArgument {

	@ModifyConstant(constant = @Constant(classValue = DefaultMutableGristSet.class), method = "getGristArgument")
	private static Class<?> minestuckcompat$useCorrectClass(Class<?> original) {
		return SmallMutableGristSet.class;
	}
}