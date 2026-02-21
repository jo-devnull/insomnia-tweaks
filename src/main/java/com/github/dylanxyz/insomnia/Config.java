package com.github.dylanxyz.insomnia;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class Config
{
    public static final ForgeConfigSpec GENERAL_SPEC;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> prunerKeep;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }

    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        prunerKeep = builder.defineList("pruner_keep", List.of("insomnia", "modpack"), o -> true);
    }

    public static List<? extends String> getPrunerKeep() {
        return prunerKeep.get();
    }
}
