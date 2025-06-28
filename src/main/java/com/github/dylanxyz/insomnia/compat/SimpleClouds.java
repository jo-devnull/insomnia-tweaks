package com.github.dylanxyz.insomnia.compat;

import dev.nonamecrackers2.simpleclouds.common.world.CloudManager;
import mcjty.incontrol.tools.typed.Key;
import mcjty.incontrol.tools.typed.Type;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class SimpleClouds
{
    public static final Key<Boolean> STORM = Key.create(Type.BOOLEAN, "storm");

    public static boolean isRainingAt(ServerLevel world, BlockPos pos) {
        CloudManager<ServerLevel> cloudManager = CloudManager.get(world);
        float rainLevel = cloudManager.getRainLevel(pos.getX(), pos.getY(), pos.getZ());
        return rainLevel > 0f;
    }
}
