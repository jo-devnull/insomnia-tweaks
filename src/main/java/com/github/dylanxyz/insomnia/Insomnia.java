package com.github.dylanxyz.insomnia;

import com.github.dylanxyz.insomnia.compat.Lunar;
import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Insomnia.MODID)
public class Insomnia
{
    public static final String MODID = "insomnia";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Insomnia()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber
    public static class PlayerEventHandler
    {
        @SubscribeEvent
        public static void onPlayerSleep(PlayerSleepInBedEvent event)
        {
            Player player = event.getEntity();
            Level level = player.level();

            if (level instanceof ServerLevel world) {
                if (Lunar.cantSleep(world)) {
                    event.setResult(Player.BedSleepingProblem.NOT_SAFE);
                }
            }
        }
    }
}
