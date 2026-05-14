package github.jodevnull.insomnia.mixin.emi;

import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.emi.emi.runtime.EmiPersistentData;
import github.jodevnull.insomnia.compat.emi.EmiCategories;
import net.minecraft.util.GsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EmiPersistentData.class)
public class MixinEmiPersistentData
{
    @ModifyExpressionValue(method = "save", at= @At(value = "NEW", target = "()Lcom/google/gson/JsonObject;"))
    private static JsonObject insomnia$saveCategories(JsonObject original) {
        original.add("categories", EmiCategories.save());
        return original;
    }

    @Inject(method = "load", at= @At(value = "INVOKE", target = "Ldev/emi/emi/runtime/EmiSidebars;load(Lcom/google/gson/JsonObject;)V"))
    private static void insomnia$loadCategories(CallbackInfo ci, @Local(name = "json") JsonObject json) {
        if (GsonHelper.isArrayNode(json, "categories")) {
            EmiCategories.load(GsonHelper.getAsJsonArray(json, "categories"));
        }
    }
}
