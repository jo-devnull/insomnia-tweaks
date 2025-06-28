package com.github.dylanxyz.insomnia.mixin.coldsweat;

import com.momosoftworks.coldsweat.compat.CompatManager;
import com.momosoftworks.coldsweat.util.world.WorldHelper;
import dev.nonamecrackers2.simpleclouds.common.world.CloudManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.momosoftworks.coldsweat.util.world.WorldHelper.canSeeSky;

@Mixin(WorldHelper.class)
public class MixinWorldHelper
{
    @Inject(at = @At("HEAD"), method = "isRainingAt", remap = false, cancellable = true)
    private static void insomnia$isRainingAt(Level level, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        CloudManager<Level> cloudManager = CloudManager.get(level);
        float rainLevel = cloudManager.getRainLevel(pos.getX(), pos.getY(), pos.getZ());
        boolean isRaining = rainLevel > 0f;

        cir.setReturnValue(
            (
                isRaining &&
                level.getBiomeManager().getBiome(pos).value().getPrecipitationAt(pos) == Biome.Precipitation.RAIN ||
                CompatManager.Weather2.isRainstormAt(level, pos)
            ) &&

            canSeeSky(level, pos.above(), level.getMaxBuildHeight()) && !CompatManager.SereneSeasons.isColdEnoughToSnow(level, pos)
        );

        cir.cancel();
    }
}
