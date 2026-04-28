package com.github.dylanxyz.insomnia.mixin.classenhancement;

import com.github.dylanxyz.insomnia.compat.Classes;
import com.github.dylanxyz.insomnia.network.ISPacketHandler;
import com.github.dylanxyz.insomnia.network.ISSetAvatarPacket;
import com.misanthropy.linggango.class_enhancement.ClassEnhancement;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClassEnhancement.class)
public class MixinClassEnhancement
{
    @Inject(method = "executeClassCommands", at = @At("HEAD"), remap = false)
    private static void insomnia$setAvatar(ServerPlayer player, ClassEnhancement.PlayerClass pc, CallbackInfo ci) {
        final String avatarName = Classes.getAvatarForClass(pc);

        if (avatarName != null) {
            final ISSetAvatarPacket packet = new ISSetAvatarPacket(player.getUUID(), avatarName);
            ISPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
        }
    }
}
