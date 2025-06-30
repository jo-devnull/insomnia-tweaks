package com.github.dylanxyz.insomnia.compat;

import com.github.dylanxyz.insomnia.Insomnia;
import com.mrbysco.lunar.LunarPhaseData;
import com.mrbysco.lunar.api.ILunarEvent;
import dev.nonamecrackers2.simpleclouds.api.common.cloud.weather.WeatherType;
import dev.nonamecrackers2.simpleclouds.common.cloud.CloudType;
import dev.nonamecrackers2.simpleclouds.common.world.CloudManager;
import mcjty.incontrol.tools.typed.Key;
import mcjty.incontrol.tools.typed.Type;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class InControl
{
    public static final Key<String> LUNAR = Key.create(Type.STRING, "lunar");
    public static final Key<Boolean> STORM = Key.create(Type.BOOLEAN, "storm");

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

    public static boolean isInsideStorm(Level world, BlockPos pos) {
        Pair<CloudType, Float> result = CloudManager.get(world).getCloudTypeAtWorldPos(pos.getX() + 0.5f, pos.getZ() + 0.5f);

        return (
            result.getLeft().weatherType() == WeatherType.THUNDERSTORM &&
            result.getRight() < 0.5
        );
    }
}
