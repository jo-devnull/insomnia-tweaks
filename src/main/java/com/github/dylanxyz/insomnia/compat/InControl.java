package com.github.dylanxyz.insomnia.compat;

 import dev.corgitaco.enhancedcelestials.EnhancedCelestials;
 import dev.corgitaco.enhancedcelestials.api.EnhancedCelestialsRegistry;
 import dev.corgitaco.enhancedcelestials.api.lunarevent.LunarEvent;
 import dev.corgitaco.enhancedcelestials.lunarevent.EnhancedCelestialsLunarForecastWorldData;
import mcjty.incontrol.tools.typed.Key;
import mcjty.incontrol.tools.typed.Type;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class InControl
{
    public static final Key<String> LUNAR = Key.create(Type.STRING, "lunar");

    public static String getLunarPhase(ServerLevel world)
    {
        if (world.dimension() != Level.OVERWORLD) {
            return "default";
        }

        long time = world.getDayTime() % 24000;

        if (time < 13000 || time > 23000) {
            return "default";
        }

        Optional<EnhancedCelestialsLunarForecastWorldData> query =
            EnhancedCelestials.lunarForecastWorldData(world);

        if (query.isEmpty()) {
            return "default";
        }

        EnhancedCelestialsLunarForecastWorldData data = query.get();
        Holder<LunarEvent> eventHolder = data.getLunarEventForDay(data.getCurrentDay());

        if (eventHolder.isBound()) {
            ResourceLocation eventKey = world
                .registryAccess()
                .registry(EnhancedCelestialsRegistry.LUNAR_EVENT_KEY)
                .orElseThrow()
                .getKey(eventHolder.value());

            if (eventKey != null)
                return eventKey.getNamespace() + ":" + eventKey.getPath();
        }

        return "default";
    }
}
