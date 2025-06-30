package com.github.dylanxyz.insomnia.compat;

import com.momosoftworks.coldsweat.api.temperature.modifier.TempModifier;
import com.momosoftworks.coldsweat.api.util.Temperature;
import com.momosoftworks.coldsweat.util.math.CSMath;
import dev.nonamecrackers2.simpleclouds.common.cloud.CloudType;
import dev.nonamecrackers2.simpleclouds.common.world.CloudManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

public class SimpleCloudsTempModifier extends TempModifier
{
    public SimpleCloudsTempModifier() {
        this.tickRate(40);
    }

    @Override
    public Function<Double, Double> calculate(LivingEntity entity, Temperature.Trait trait) {
        if (entity.level().isClientSide())
            return temp -> temp;

        BlockPos pos = entity.blockPosition();
        CloudManager<Level> manager = CloudManager.get(entity.level());
        Pair<CloudType, Float> info = manager.getCloudTypeAtWorldPos(pos.getX()+.5f, pos.getZ()+.5f);
        CloudType cloud = info.getLeft();

        double stormTemp;

        if (cloud.weatherType().includesRain())
            stormTemp = CSMath.blend(0, cloud.storminess() * 20, info.getRight(), 10, 0);
        else
            stormTemp = 0.0;

        return temp -> temp - Temperature.convert(stormTemp, Temperature.Units.C, Temperature.Units.MC, true);
    }
}
