package com.github.dylanxyz.insomnia.mixin;

import dev.emi.emi.EmiPort;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EmiPort.class)
public class MixinEmiPort
{
    @Inject(at = @At("RETURN"), method = "id(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true, remap = false)
    private static void insomnia$fixEmiBg(String namespace, String path, CallbackInfoReturnable<ResourceLocation> cir) {
        // Not sure if there is a better way of doing this (probably yes)
        if (namespace.equals("minecraft")) {
            if (path.equals("textures/gui/container/brewing_stand.png"))
                cir.setReturnValue(new ResourceLocation("emi", path));
            else if (path.equals("textures/gui/container/grindstone.png"))
                cir.setReturnValue(new ResourceLocation("emi", path));
        }
    }
}
