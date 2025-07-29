package com.github.dylanxyz.insomnia;

import com.github.dylanxyz.insomnia.event.PlayerEvents;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
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

        PlayerEvents.register();
    }
}
