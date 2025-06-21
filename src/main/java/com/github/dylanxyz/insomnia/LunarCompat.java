package com.github.dylanxyz.insomnia;

import com.mrbysco.lunar.LunarPhaseData;
import com.mrbysco.lunar.api.ILunarEvent;
import mcjty.incontrol.tools.typed.Key;
import mcjty.incontrol.tools.typed.Type;
import net.minecraft.server.level.ServerLevel;

public class LunarCompat
{
    public static final Key<String> DURING = Key.create(Type.STRING, "during");

    public static String getLunarPhase(ServerLevel world) {
        LunarPhaseData phaseData = LunarPhaseData.get(world);
        ILunarEvent lunarEvent = phaseData.getActiveLunarEvent();

        if (phaseData.hasEventActive() && lunarEvent != null)
            return lunarEvent.getID().getPath();

        return "default";
    }
}
