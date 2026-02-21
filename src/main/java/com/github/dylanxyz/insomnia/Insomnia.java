package com.github.dylanxyz.insomnia;

import com.github.dylanxyz.insomnia.event.PlayerEvents;
import com.github.dylanxyz.insomnia.registry.ISBlocks;
import com.github.dylanxyz.insomnia.registry.ISCreativeTabs;
import com.github.dylanxyz.insomnia.registry.ISItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;

@Mod(Insomnia.MODID)
public class Insomnia
{
    public static final String MODID = "insomnia";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Set<String> LOADED_MODS = new HashSet<>();

    public static final TagKey<EntityType<?>> IS_PROTECTED = TagKey.create(
        Registries.ENTITY_TYPE,
        new ResourceLocation("friendlyfire", "player_protection")
    );

    public Insomnia(FMLJavaModLoadingContext context)
    {
        IEventBus event = context.getModEventBus();
        context.registerConfig(ModConfig.Type.COMMON, Config.GENERAL_SPEC, "insomnia.toml");

        for (IModInfo mod : ModList.get().getMods()) {
            LOADED_MODS.add(mod.getModId());
        }

        ISBlocks.register(event);
        ISItems.register(event);
        ISCreativeTabs.register(event);

        MinecraftForge.EVENT_BUS.register(this);
        PlayerEvents.register();
    }

    public static boolean canSkipResource(ResourceLocation id) {
        if (id == null) return false;

        String namespace = id.getNamespace();

        if (Config.getPrunerKeep().contains(namespace))
            return false;

        return !LOADED_MODS.contains(namespace);
    }
}
