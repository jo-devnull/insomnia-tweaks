package com.github.dylanxyz.insomnia.mixin.moremobvariants;

import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractZombieRenderer.class)
public class MixinZombieRenderer
{
    @Inject(at = @At("RETURN"), method = "getTextureLocation(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
    private void insomnia$getTextureLocation(Entity zombie, CallbackInfoReturnable<ResourceLocation> cir) {
        final String texturePrefix = "textures/entity/zombie/";
        final String optifinePrefix = "optifine/random/entity/zombie";

        final ResourceLocation location = cir.getReturnValue();
        final String path = location.getPath();

        if (path.startsWith(texturePrefix) && path.contains(optifinePrefix)) {
            final ResourceLocation textureLoc = location.withPath(s -> s.replace(texturePrefix, ""));
            cir.setReturnValue(textureLoc);
        }
    }
}
