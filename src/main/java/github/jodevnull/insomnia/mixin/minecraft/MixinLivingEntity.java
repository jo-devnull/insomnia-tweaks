package github.jodevnull.insomnia.mixin.minecraft;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class MixinLivingEntity
{
    @ModifyReturnValue(method = "checkBedExists", at = @At("RETURN"))
    private boolean insomnia$checkPlayerSleepingOnGround(boolean original) {
        if ((Object) this instanceof Player)
            return true;

        return original;
    }
}
