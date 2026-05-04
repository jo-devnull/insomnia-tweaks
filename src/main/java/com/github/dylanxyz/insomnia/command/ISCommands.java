package com.github.dylanxyz.insomnia.command;

import com.github.dylanxyz.insomnia.Insomnia;
import com.github.dylanxyz.insomnia.mixin.quests.FTBQuestsCommandsAccessor;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;

public class ISCommands
{
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        final var root = Commands.literal(Insomnia.MODID);
        addQuestCommand(root);
        dispatcher.register(root);
    }

    public static void addQuestCommand(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(
            Commands.literal("open_quest").then(
            Commands.argument("player", EntityArgument.player()).then(
            Commands.argument("questId", StringArgumentType.string()).executes(context -> {
                final var player = EntityArgument.getPlayer(context, "player");
                final var questId = StringArgumentType.getString(context, "questId");
                return FTBQuestsCommandsAccessor.openQuest(player, questId);
            })))
        );
    }
}
