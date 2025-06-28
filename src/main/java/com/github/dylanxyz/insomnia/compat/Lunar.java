package com.github.dylanxyz.insomnia.compat;

import com.mrbysco.lunar.LunarPhaseData;
import com.mrbysco.lunar.api.ILunarEvent;
import mcjty.incontrol.tools.typed.Key;
import mcjty.incontrol.tools.typed.Type;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class Lunar
{
    public static final Key<String> LUNAR = Key.create(Type.STRING, "lunar");
    public static final List<String> CANT_SLEEP_PHASE = List.of("blood_moon", "eclipse_moon", "crimson_moon");

    public static String getLunarPhase(ServerLevel world) {
        LunarPhaseData phaseData = LunarPhaseData.get(world);
        ILunarEvent lunarEvent = phaseData.getActiveLunarEvent();

        if (phaseData.hasEventActive() && lunarEvent != null)
            return lunarEvent.getID().getPath();

        return "default";
    }

    public static boolean cantSleep(ServerLevel world) {
        return CANT_SLEEP_PHASE.contains(getLunarPhase(world));
    }
}
