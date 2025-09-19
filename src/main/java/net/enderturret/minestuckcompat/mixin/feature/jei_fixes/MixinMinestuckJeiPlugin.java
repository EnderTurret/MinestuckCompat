package net.enderturret.minestuckcompat.mixin.feature.jei_fixes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mraof.minestuck.item.MSItems;

import net.enderturret.minestuckcompat.alchemy.jei.BoondollarSubtypeInterpreter;
import net.enderturret.minestuckcompat.alchemy.jei.EncodedItemSubtypeInterpreter;
import net.enderturret.minestuckcompat.alchemy.jei.FrogSubtypeInterpreter;

import mezz.jei.api.registration.ISubtypeRegistration;

@Mixin(MinestuckJeiPlugin.class)
public abstract class MixinMinestuckJeiPlugin {

	@Inject(at = @At("TAIL"), method = "registerItemSubtypes")
	private void minestuckcompat$registerSubtypes(ISubtypeRegistration registration, CallbackInfo ci) {
		registration.registerSubtypeInterpreter(MSItems.CAPTCHA_CARD.asItem(), new EncodedItemSubtypeInterpreter());
		registration.registerSubtypeInterpreter(MSItems.CRUXITE_DOWEL.asItem(), new EncodedItemSubtypeInterpreter());
		registration.registerSubtypeInterpreter(MSItems.BOONDOLLARS.asItem(), new BoondollarSubtypeInterpreter());
		registration.registerSubtypeInterpreter(MSItems.FROG.asItem(), new FrogSubtypeInterpreter());
	}
}