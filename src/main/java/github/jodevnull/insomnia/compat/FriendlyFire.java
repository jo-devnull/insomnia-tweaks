package github.jodevnull.insomnia.compat;

import corgitaco.corgilib.shadow.blue.endless.jankson.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.UUID;

public class FriendlyFire
{
    private static final TagKey<Item> BYPASS_PET = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("friendlyfire", "bypass_pet"));
    private static final TagKey<Item> BYPASS_ALL = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("friendlyfire", "bypass_all_protection"));
    private static final TagKey<EntityType<?>> GENERAL_PROTECTION = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("friendlyfire", "general_protection"));
    private static final TagKey<EntityType<?>> PLAYER_PROTECTION = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("friendlyfire", "player_protection"));
    private static final TagKey<EntityType<?>> BYPASSED_PROTECTION = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("friendlyfire", "bypassed_entity_types"));

    public static void onEntityAttack(LivingIncomingDamageEvent event) {
        if (preventAttack(event.getEntity(), event.getSource(), event.getAmount())) {
            event.setCanceled(true);
            event.getEntity().setLastHurtByMob(null);

            if (event.getSource().getEntity() instanceof LivingEntity trueSource) {
                trueSource.setLastHurtByMob(null);
            }
        }
    }

    public static boolean preventAttack(Entity target, DamageSource source, float amount) {
        final Entity attacker = source.getEntity();
        return isProtected(target, attacker, amount);
    }

    private static boolean isProtected(Entity target, Entity attacker, float amount) {
        if (target.getType().is(BYPASSED_PROTECTION))
            return false;

        if (attacker == null || attacker.isCrouching())
            return false;

        final ItemStack heldItem = attacker instanceof LivingEntity attackerLiving ? attackerLiving.getMainHandItem() : ItemStack.EMPTY;

        if (heldItem.is(BYPASS_ALL))
            return false;

        if (target.getType().is(GENERAL_PROTECTION))
            return true;

        if (attacker instanceof Player player && target.getType().is(PLAYER_PROTECTION))
            return true;

        final UUID ownerId = getOwner(target);

        if (ownerId != null && !heldItem.is(BYPASS_PET)) {
            if (ownerId.equals(attacker.getUUID()))
                return true;

            else if (ownerId.equals(getOwner(attacker)))
                return true;
        }

        return attacker instanceof Player && !(target instanceof Enemy) && target instanceof AgeableMob agable && agable.isBaby() && !attacker.isCrouching();
    }

    @Nullable
    private static UUID getOwner(Entity entity) {
        if (entity instanceof OwnableEntity ownable)
            return ownable.getOwnerUUID();

        if (entity instanceof AbstractHorse horse)
            return horse.getOwnerUUID();

        return null;
    }
}
