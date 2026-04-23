package github.jodevnull.insomnia.compat;

import github.jodevnull.insomnia.Insomnia;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber
public class Spellbooks
{
    @SubscribeEvent
    public static void onEntityAttack(LivingIncomingDamageEvent event)
    {
        if (event.getSource().getEntity() instanceof Player player) {
            if (event.getEntity() instanceof IMagicSummon summon) {
                if (summon.getSummoner() instanceof Player) {
                    event.setCanceled(true);
                    event.getEntity().setLastHurtByMob(null);
                    player.setLastHurtByMob(null);
                }
            }
        } else if (event.getSource().getEntity() instanceof IMagicSummon summon) {
            if (summon.getSummoner() instanceof Player && event.getEntity().getType().is(Insomnia.IS_PROTECTED)) {
                event.setCanceled(true);
                event.getEntity().setLastHurtByMob(null);

                if (event.getSource().getEntity() instanceof LivingEntity entity)
                    entity.setLastHurtByMob(null);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingIncomingDamageEvent event)
    {
        if (event.getSource().getEntity() instanceof Player player) {
            if (event.getEntity() instanceof IMagicSummon summon) {
                if (summon.getSummoner() instanceof Player) {
                    event.setCanceled(true);
                    event.getEntity().setLastHurtByMob(null);
                    player.setLastHurtByMob(null);
                }
            }
        } else if (event.getSource().getEntity() instanceof IMagicSummon summon) {
            if (summon.getSummoner() instanceof Player && event.getEntity().getType().is(Insomnia.IS_PROTECTED)) {
                event.setCanceled(true);
                event.getEntity().setLastHurtByMob(null);

                if (event.getSource().getEntity() instanceof LivingEntity entity)
                    entity.setLastHurtByMob(null);
            }
        }
    }
}
