package com.github.dylanxyz.insomnia.compat;

import com.github.dylanxyz.insomnia.Insomnia;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Spellbooks
{
    @SubscribeEvent
    public static void onEntityAttack(LivingAttackEvent event)
    {
        if (event.getSource().getEntity() instanceof Player player) {
            if (event.getEntity() instanceof MagicSummon summon) {
                if (summon.getSummoner() instanceof Player) {
                    event.setCanceled(true);
                    event.getEntity().setLastHurtByMob(null);
                    player.setLastHurtByMob(null);
                }
            }
        } else if (event.getSource().getEntity() instanceof MagicSummon summon) {
            if (summon.getSummoner() instanceof Player && event.getEntity().getType().is(Insomnia.IS_PROTECTED)) {
                event.setCanceled(true);
                event.getEntity().setLastHurtByMob(null);

                if (event.getSource().getEntity() instanceof LivingEntity entity)
                    entity.setLastHurtByMob(null);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingAttackEvent event)
    {
        if (event.getSource().getEntity() instanceof Player player) {
            if (event.getEntity() instanceof MagicSummon summon) {
                if (summon.getSummoner() instanceof Player) {
                    event.setCanceled(true);
                    event.getEntity().setLastHurtByMob(null);
                    player.setLastHurtByMob(null);
                }
            }
        } else if (event.getSource().getEntity() instanceof MagicSummon summon) {
            if (summon.getSummoner() instanceof Player && event.getEntity().getType().is(Insomnia.IS_PROTECTED)) {
                event.setCanceled(true);
                event.getEntity().setLastHurtByMob(null);

                if (event.getSource().getEntity() instanceof LivingEntity entity)
                    entity.setLastHurtByMob(null);
            }
        }
    }
}
