package com.github.dylanxyz.insomnia;

import com.mrbysco.lunar.LunarPhaseData;
import com.mrbysco.lunar.api.ILunarEvent;
import mcjty.incontrol.data.DataStorage;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;

public class LunarCompat
{
    private static boolean checked = false;

    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.level instanceof ServerLevel world) {
            int time = (int) (world.getDayTime() % 24000L);

            if (time > 0 && time < 13000) {
                if (checked) {
                    clearLunarPhases(world);
                }

                checked = false;
            }

            if (!checked && time > 13000 && time < 23000) {
                LunarPhaseData phaseData = LunarPhaseData.get(world);
                ILunarEvent lunarEvent = phaseData.getActiveLunarEvent();

                if (phaseData.hasEventActive() && lunarEvent != null) {
                    checked = true;
                    String moonPhase = lunarEvent.getID().getNamespace() + ":" + lunarEvent.getID().getPath();
                    DataStorage.getData(world).setPhase(moonPhase, true);
                    Insomnia.LOGGER.info("Enabling moon phase: {}", moonPhase);
                }
            }
        }
    }

    public static void clearLunarPhases(ServerLevel world) {
        DataStorage icData = DataStorage.getData(world);

        if (icData != null) {
            for (String phase : icData.getPhases()) {
                if (phase != null && phase.startsWith("lunar:")) {
                    icData.setPhase(phase, false);
                    Insomnia.LOGGER.info("Disabling moon phase: {}", phase);
                }
            }
        }
    }
}
