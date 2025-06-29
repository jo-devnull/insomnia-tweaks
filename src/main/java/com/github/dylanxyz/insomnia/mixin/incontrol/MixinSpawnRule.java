package com.github.dylanxyz.insomnia.mixin.incontrol;

import com.github.dylanxyz.insomnia.compat.InControl;
import mcjty.incontrol.rules.SpawnRule;
import mcjty.incontrol.tools.typed.Attribute;
import mcjty.incontrol.tools.typed.GenericAttributeMapFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnRule.class)
public class MixinSpawnRule
{
    @Shadow @Final private static GenericAttributeMapFactory FACTORY;

    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void insomnia$addLunarCompat(CallbackInfo ci) {
        FACTORY
            .attribute(Attribute.createMulti(InControl.LUNAR))
            .attribute(Attribute.create(InControl.STORM));
    }
}
