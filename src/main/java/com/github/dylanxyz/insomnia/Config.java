package com.github.dylanxyz.insomnia;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class Config
{
    public static final ForgeConfigSpec GENERAL_SPEC;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }

    private static void setupConfig(ForgeConfigSpec.Builder builder) {}
}
