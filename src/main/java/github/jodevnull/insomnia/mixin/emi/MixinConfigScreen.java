package github.jodevnull.insomnia.mixin.emi;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.emi.emi.screen.ConfigScreen;
import github.jodevnull.insomnia.compat.emi.EmiExtensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.lang.reflect.Field;

@Mixin(ConfigScreen.class)
public class MixinConfigScreen
{
    @ModifyExpressionValue(method = "init", at= @At(value = "INVOKE", target = "Ljava/lang/Class;getFields()[Ljava/lang/reflect/Field;"))
    private static Field[] insomnia$appendBindings(Field[] original) throws NoSuchFieldException {
        return EmiExtensions.addBindings(original);
    }
}
