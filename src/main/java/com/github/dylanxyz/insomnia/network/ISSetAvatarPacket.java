package com.github.dylanxyz.insomnia.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public record ISSetAvatarPacket(UUID playerId, String avatarName)
{
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUUID(playerId);
        buffer.writeUtf(avatarName);
    }

    public static ISSetAvatarPacket decode(FriendlyByteBuf buffer) {
        return new ISSetAvatarPacket(buffer.readUUID(), buffer.readUtf());
    }

    public static void handle(ISSetAvatarPacket packet, Supplier<NetworkEvent.Context> ctx) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ISSetAvatarPacketHandler.handlePacket(packet, ctx));
    }
}
