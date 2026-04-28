package com.github.dylanxyz.insomnia.commands;

import com.github.dylanxyz.insomnia.Insomnia;
import com.github.dylanxyz.insomnia.network.ISPacketHandler;
import com.github.dylanxyz.insomnia.network.ISSetAvatarPacket;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = Insomnia.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InsomniaCommands
{
    public static void register() {}

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        final CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal(Insomnia.MODID)
            .then(Commands.literal("setavatar")
                .then(Commands.argument("target", EntityArgument.player())
                .then(Commands.argument("avatar", StringArgumentType.string())
                .executes(InsomniaCommands::setAvatar))))
        );
    }

    public static int setAvatar(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        final ServerPlayer player = EntityArgument.getPlayer(context, "target");
        final String avatarName = StringArgumentType.getString(context, "avatar");

        final ISSetAvatarPacket packet = new ISSetAvatarPacket(player.getUUID(), avatarName);
        ISPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);

        return 1;
    }
}
