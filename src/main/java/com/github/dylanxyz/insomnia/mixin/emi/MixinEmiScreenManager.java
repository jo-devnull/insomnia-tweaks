package com.github.dylanxyz.insomnia.mixin.emi;

import dev.emi.emi.config.SidebarType;
import dev.emi.emi.runtime.EmiDrawContext;
import dev.emi.emi.screen.EmiScreenBase;
import dev.emi.emi.screen.EmiScreenManager;
import dev.emi.emi.screen.widget.EmiSearchWidget;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EmiScreenManager.class, remap = false)
public abstract class MixinEmiScreenManager
{
    @Shadow
    @Nullable
    public static EmiScreenManager.@Nullable ScreenSpace getHoveredSpace(int mouseX, int mouseY) {
        return null;
    }

    @Shadow
    public static EmiSearchWidget search;

    @Inject(method = "renderCurrentTooltip", at = @At("HEAD"), cancellable = true)
    private static void insomnia$hideTooltip(EmiDrawContext context, int mouseX, int mouseY, float delta, EmiScreenBase base, CallbackInfo ci) {
        EmiScreenManager.ScreenSpace space = getHoveredSpace(mouseX, mouseY);

        if (space == null)
            return;

        if (space.getType() == SidebarType.INDEX && search.getValue().isEmpty()) {
            ci.cancel();
        }
    }
}
