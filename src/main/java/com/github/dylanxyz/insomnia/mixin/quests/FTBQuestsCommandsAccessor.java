package com.github.dylanxyz.insomnia.mixin.quests;

import dev.ftb.mods.ftbquests.command.FTBQuestsCommands;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FTBQuestsCommands.class)
public interface FTBQuestsCommandsAccessor
{
    @Invoker("openQuest")
    static int openQuest(ServerPlayer player, String qobId) {
        return 0;
    }
}
