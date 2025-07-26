package com.github.dylanxyz.insomnia.mixin.totweaks;

import com.gametechbc.traveloptics.compat.effects.SunstrikeEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SunstrikeEffect.class)
public class MixinSunstrikeEffect
{
    @Unique
    private static final TagKey<EntityType<?>> PLAYER_PROTECTION_TAG = TagKey.create(
        Registries.ENTITY_TYPE,
        new ResourceLocation("friendlyfire", "player_protection")
    );

    @Inject(at = @At("RETURN"), method = "isValidTarget", remap = false, cancellable = true)
    public void insomnia$skipProtectedMobs(LivingEntity target, LivingEntity caster, double radius, CallbackInfoReturnable<Boolean> cir)
    {
        boolean result = cir.getReturnValue();

        if (result && caster instanceof Player) {
            cir.setReturnValue(!target.getType().is(PLAYER_PROTECTION_TAG));
        }
    }
}
