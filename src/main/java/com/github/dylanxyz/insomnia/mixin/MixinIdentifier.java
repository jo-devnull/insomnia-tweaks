package com.github.dylanxyz.insomnia.mixin;

import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ResourceLocation.class)
public class MixinIdentifier
{
    @Inject(at = @At("RETURN"), method = "isValidResourceLocation", cancellable = true)
    private static void isValidResourceLocation(String path, CallbackInfoReturnable<Boolean> cir) {
        if (path.contains("/optifine/random/entity") && path.endsWith(".png")) {
            cir.setReturnValue(true);
        }
    }
}
