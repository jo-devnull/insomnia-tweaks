package com.github.dylanxyz.insomnia.mixin.coldsweat;

import com.momosoftworks.coldsweat.compat.CompatManager;
import com.momosoftworks.coldsweat.util.world.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static com.momosoftworks.coldsweat.util.world.WorldHelper.canSeeSky;

@Mixin(value = WorldHelper.class, remap = false)
public class MixinWorldHelper
{
    /**
     * @author jo-devnull
     * @reason Add Compat with SimpleClouds
     */
    @Overwrite
    public static boolean isRainingAt(Level level, BlockPos pos) {
        return (level.isRainingAt(pos) && level.getBiomeManager().getBiome(pos).value().getPrecipitationAt(pos) == Biome.Precipitation.RAIN || CompatManager.Weather2.isRainstormAt(level, pos)) && canSeeSky(level, pos.above(), level.getMaxBuildHeight()) && !CompatManager.SereneSeasons.isColdEnoughToSnow(level, pos);
    }
}
