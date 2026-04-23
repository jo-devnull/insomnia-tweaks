package github.jodevnull.insomnia.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import github.jodevnull.insomnia.Insomnia;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;

public class MiscCommands
{
    public static void createCommand(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        dispatcher.register(Commands.literal(Insomnia.MODID)
            .then(Commands.literal("sleep").executes(MiscCommands::sleepCommand)));
    }

    private static int sleepCommand(CommandContext<CommandSourceStack> context) {
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
