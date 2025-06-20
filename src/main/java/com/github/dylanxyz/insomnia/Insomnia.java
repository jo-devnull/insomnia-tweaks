package com.github.dylanxyz.insomnia;

import com.mojang.logging.LogUtils;
import com.mrbysco.lunar.LunarPhaseData;
import com.mrbysco.lunar.api.ILunarEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
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
    public static class LevelTickHandler
    {
        @SubscribeEvent
        public static void onLevelTick(TickEvent.LevelTickEvent event) {
            LunarCompat.onWorldTick(event);
        }
    }
}
