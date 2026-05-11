package github.jodevnull.insomnia.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import github.jodevnull.insomnia.Insomnia;
import github.jodevnull.insomnia.mixin.quests.FTBQuestsCommandsAccessor;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;

public class MiscCommands
{
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        final var root = Commands.literal(Insomnia.MODID);

        sleepCommand(dispatcher);
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

    private static void sleepCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("sleep").executes(MiscCommands::onSleep));
    }

    private static int onSleep(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();

        if (player == null)
            return 0;

        final var result = player.startSleepInBed(player.blockPosition());

        result.ifLeft(problem -> {
            if (problem.getMessage() != null)
                player.displayClientMessage(problem.getMessage(), true);
        });

        return result.left().isEmpty() ? 1 : 0;
    }
}
