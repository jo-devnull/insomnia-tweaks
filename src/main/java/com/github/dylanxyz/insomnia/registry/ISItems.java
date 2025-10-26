package com.github.dylanxyz.insomnia.registry;

import com.github.dylanxyz.insomnia.Insomnia;
import com.github.dylanxyz.insomnia.items.ImprovisedBedItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ISItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Insomnia.MODID);

    public static final RegistryObject<ImprovisedBedItem> IMPROVISED_BED =
        ITEMS.register("improvised_bed", () -> new ImprovisedBedItem(
            ISBlocks.IMPROVISED_BED.get(),
            new Item.Properties()
        ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
