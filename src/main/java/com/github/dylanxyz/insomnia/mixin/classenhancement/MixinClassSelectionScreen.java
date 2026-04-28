package com.github.dylanxyz.insomnia.mixin.classenhancement;

import com.github.dylanxyz.insomnia.compat.Classes;
import com.github.dylanxyz.insomnia.compat.FiguraUtils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.misanthropy.linggango.class_enhancement.ClassEnhancement;
import com.misanthropy.linggango.class_enhancement.client.ClassSelectionScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import org.figuramc.figura.FiguraMod;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.avatar.AvatarManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClassSelectionScreen.class)
public class MixinClassSelectionScreen
{
    @Shadow
    private ClassEnhancement.PlayerClass displayClass;

    @Shadow
    private ClassEnhancement.PlayerClass selectedClass;

    @Inject(method = "lambda$createClassButton$1", at = @At("TAIL"))
    public void insomnia$renderClassAvatar(ClassEnhancement.PlayerClass pc, int index, Button b, CallbackInfo ci) {
        final String avatarName = Classes.getAvatarForClass(pc);

        if (avatarName != null) {
            FiguraUtils.setAvatar(avatarName, false);
        }
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void insomnia$selectFirstClass(CallbackInfo ci) {
        displayClass = selectedClass;
        selectedClass = ClassEnhancement.CLASSES.get(0);

        final String avatarName = Classes.getAvatarForClass(selectedClass);

        if (avatarName != null) {
            FiguraUtils.setAvatar(avatarName, false);
        }
    }

    @ModifyExpressionValue(
        method = "render",
        at = @At(
            ordinal = 0,
            value = "INVOKE",
            target = "Lcom/misanthropy/linggango/class_enhancement/client/ClassSelectionScreen$StyledButton;isHoveredOrFocused()Z"
        )
    )
    private boolean insomnia$disableHover(boolean original) {
        return false;
    }

    @Inject(method = "renderPlayerPreview", at = @At("HEAD"), cancellable = true, remap = false)
    private void insomnnia$waitForAvatar(GuiGraphics g, int x, int y, int lmx, int lmy, ClassEnhancement.PlayerClass pc, CallbackInfo ci) {
        final Avatar avatar = AvatarManager.getAvatarForPlayer(FiguraMod.getLocalPlayerUUID());

        if (Classes.getAvatarForClass(pc) != null && avatar == null) {
            ci.cancel();
        }
    }
}
