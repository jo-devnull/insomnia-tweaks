package com.github.dylanxyz.insomnia.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Team;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ttv.migami.migamigos.entity.AmigoEntity;

@Mod.EventBusSubscriber
public class PlayerEvents
{
    public static void register() {}

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event)
    {
        if (event.getSide().isClient())
            return;

        ServerPlayer player = (ServerPlayer) event.getEntity();

         if (player.getVehicle() instanceof AbstractHorse horsey) {
            if (event.getTarget() instanceof LivingEntity entity) {
                Team playerTeam = player.getTeam();
                boolean shouldMount = false;

                if (playerTeam != null)
                    shouldMount = playerTeam.isAlliedTo(entity.getTeam());

                else if (entity instanceof TamableAnimal tamable)
                    shouldMount = tamable.isTame() && player.getUUID().equals(tamable.getOwnerUUID());

                else if (entity instanceof Animal animal)
                    shouldMount = player.getUUID().equals(animal.getUUID());

                else if (entity instanceof AmigoEntity amigo)
                    shouldMount = amigo.getPlayer() == player;

                if (shouldMount) {
                    horsey.setEating(false);
                    horsey.setStanding(false);
                    entity.startRiding(horsey);
                    event.setCanceled(true);
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityMount(EntityMountEvent event)
    {
        if (event.getLevel().isClientSide())
            return;

        if (event.isMounting())
            return;

        if (event.getEntityMounting() instanceof ServerPlayer) {
            if (event.getEntityBeingMounted() instanceof AbstractHorse horsey) {
                for (var entity : horsey.getPassengers()) {
                    if (!(entity instanceof Player) && entity instanceof LivingEntity)
                        entity.stopRiding();
                }
            }
        }
    }
}
