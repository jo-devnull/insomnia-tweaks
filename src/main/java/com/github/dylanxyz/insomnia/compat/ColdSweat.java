//package com.github.dylanxyz.insomnia.compat;
//
//import com.github.dylanxyz.insomnia.Insomnia;
//import com.momosoftworks.coldsweat.api.event.core.init.DefaultTempModifiersEvent;
//import com.momosoftworks.coldsweat.api.event.core.registry.TempModifierRegisterEvent;
//import com.momosoftworks.coldsweat.api.temperature.modifier.TempModifier;
//import com.momosoftworks.coldsweat.api.util.Placement;
//import com.momosoftworks.coldsweat.api.util.Temperature;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.player.Player;
//import net.minecraftforge.eventbus.api.EventPriority;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//@Mod.EventBusSubscriber
//public class ColdSweat
//{
//    public static final TempModifier SCTempModifier = new SimpleCloudsTempModifier();
//
//    @SubscribeEvent
//    public static void defineDefaultModifiers(DefaultTempModifiersEvent event) {
//        if (event.getEntity() instanceof Player) {
//            event.addModifier(Temperature.Trait.WORLD, SCTempModifier, Placement.Duplicates.BY_CLASS, Placement.AFTER_LAST);
//        }
//    }
//
//    @SubscribeEvent(priority = EventPriority.HIGHEST)
//    public static void onRegisterTempModifier(TempModifierRegisterEvent event) {
//        event.register(new ResourceLocation(Insomnia.MODID, "storm"), SimpleCloudsTempModifier::new);
//        Insomnia.LOGGER.debug("Registered insomnia:storm TempModifier for SimpleClouds compat");
//    }
//}
