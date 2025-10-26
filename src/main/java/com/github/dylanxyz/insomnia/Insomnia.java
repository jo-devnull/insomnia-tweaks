package com.github.dylanxyz.insomnia;

import com.github.dylanxyz.insomnia.event.BlockEvents;
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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Insomnia.MODID)
public class Insomnia
{
    public static final String MODID = "insomnia";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final TagKey<EntityType<?>> IS_PROTECTED = TagKey.create(
        Registries.ENTITY_TYPE,
        new ResourceLocation("friendlyfire", "player_protection")
    );

    public Insomnia(FMLJavaModLoadingContext context)
    {
        IEventBus event = context.getModEventBus();

        ISBlocks.register(event);
        ISItems.register(event);
        ISCreativeTabs.register(event);

        MinecraftForge.EVENT_BUS.register(this);
        PlayerEvents.register();
        BlockEvents.register();
    }
}
