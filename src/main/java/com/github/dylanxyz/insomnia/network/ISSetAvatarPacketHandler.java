package com.github.dylanxyz.insomnia.network;

import com.github.dylanxyz.insomnia.Insomnia;
import com.github.dylanxyz.insomnia.compat.FiguraUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ISSetAvatarPacketHandler
{
    public static void handlePacket(ISSetAvatarPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;

            if (player != null && player.getUUID() != packet.playerId()) {
                FiguraUtils.setAvatar(packet.avatarName());
            } else {
                Insomnia.LOGGER.error("Failed to set avatar: player is either null or has invalid UUID");
            }
        });
    }
}
