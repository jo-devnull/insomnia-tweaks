package com.github.dylanxyz.insomnia.mixin.migamigos;

import com.github.dylanxyz.insomnia.Insomnia;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ttv.migami.migamigos.entity.AmigoEntity;

@Mixin(value = AmigoEntity.class, remap = false)
public class MixinAmigoEntity
{
    @Inject(at = @At("RETURN"), method = "wantsToAttack", cancellable = true)
    private void insomnia$checkTarget(LivingEntity pTarget, LivingEntity pOwner, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue())
            return;

        if (pTarget.getType().is(Insomnia.IS_PROTECTED))
            cir.setReturnValue(false);
    }
}
