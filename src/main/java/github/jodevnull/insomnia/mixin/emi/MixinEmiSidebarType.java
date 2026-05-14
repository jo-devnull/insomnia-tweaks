package github.jodevnull.insomnia.mixin.emi;

import dev.emi.emi.config.SidebarType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(SidebarType.class)
public abstract class MixinEmiSidebarType
{
    @Shadow
    @Final
    @Mutable
    private static SidebarType[] $VALUES;

    @Invoker("<init>")
    public static SidebarType invokeInit(String internalName, int internalId, String name, int u, int v) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addEmiCategoryType(CallbackInfo ci) {
        final var values = new ArrayList<>(Arrays.asList($VALUES));
        final var CATEGORIES = invokeInit("CATEGORIES", values.size(), "categories", 32, 136);
        values.add(CATEGORIES);
        $VALUES = values.toArray(SidebarType[]::new);
    }
}
