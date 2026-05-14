package github.jodevnull.insomnia.mixin.emi;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.config.SidebarType;
import dev.emi.emi.runtime.EmiSidebars;
import github.jodevnull.insomnia.compat.emi.EmiCategories;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(EmiSidebars.class)
public class MixinEmiSidebars
{
    @WrapMethod(method = "getStacks")
    private static List<? extends EmiIngredient> insomnia$getStacks(SidebarType type, Operation<List<? extends EmiIngredient>> original) {
        final var result = original.call(type);

        if (result.isEmpty() && type.ordinal() == 8 /* may break */) {
            return EmiCategories.categories;
        }

        return result;
    }
}
