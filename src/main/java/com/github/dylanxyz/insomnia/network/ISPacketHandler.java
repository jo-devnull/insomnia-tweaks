package com.github.dylanxyz.insomnia.network;

import com.github.dylanxyz.insomnia.Insomnia;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ISPacketHandler
{
    public static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(Insomnia.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;

        INSTANCE.messageBuilder(ISSetAvatarPacket.class, id++)
            .encoder(ISSetAvatarPacket::encode)
            .decoder(ISSetAvatarPacket::decode)
            .consumerMainThread(ISSetAvatarPacket::handle)
            .add();
    }
}
