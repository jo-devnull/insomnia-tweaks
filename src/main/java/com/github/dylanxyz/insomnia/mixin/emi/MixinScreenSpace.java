package com.github.dylanxyz.insomnia.mixin.emi;

import dev.emi.emi.config.SidebarType;
import dev.emi.emi.runtime.EmiDrawContext;
import dev.emi.emi.screen.EmiScreenManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EmiScreenManager.ScreenSpace.class, remap = false)
public abstract class MixinScreenSpace
{
    @Shadow
    public abstract SidebarType getType();

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void insomnia$hideIndex(EmiDrawContext context, int mouseX, int mouseY, float delta, int startIndex, CallbackInfo ci) {
        if (getType() == SidebarType.INDEX) {
            if (EmiScreenManager.search.getValue().isEmpty()) {
                ci.cancel();
            }
        }
    }
}
