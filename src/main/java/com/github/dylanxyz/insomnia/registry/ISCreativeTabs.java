package com.github.dylanxyz.insomnia.registry;

import com.github.dylanxyz.insomnia.Insomnia;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ISCreativeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Insomnia.MODID);

    public static RegistryObject<CreativeModeTab> INSOMNIA = CREATIVE_MODE_TABS.register("insomnia", () -> CreativeModeTab.builder()
        .icon(() -> new ItemStack(ISItems.IMPROVISED_BED.get()))
        .title(Component.translatable("itemGroup.insomnia")).displayItems(ISCreativeTabs::displayItems).build());

    public static void displayItems(CreativeModeTab.ItemDisplayParameters displayParameters, CreativeModeTab.Output output)
    {
        output.accept(ISItems.IMPROVISED_BED.get());
    }

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
