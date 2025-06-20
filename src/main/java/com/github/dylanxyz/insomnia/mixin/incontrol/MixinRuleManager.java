package com.github.dylanxyz.insomnia.mixin.incontrol;

import com.github.dylanxyz.insomnia.Utils;
import mcjty.incontrol.data.DataStorage;
import mcjty.incontrol.rules.RulesManager;
import mcjty.incontrol.rules.SpawnRule;
import mcjty.incontrol.rules.support.SpawnWhen;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(RulesManager.class)
public abstract class MixinRuleManager
{
    @Shadow
    private static List<SpawnRule> getCorrectList(SpawnWhen when) { return null; }

    @Shadow
    private static void setCorrectList(SpawnWhen when, List<SpawnRule> list) {}

    @Shadow @Final private static List<SpawnRule> rules;

    @Inject(at = @At("HEAD"), method = "getFilteredRules", remap = false, cancellable = true)
    private static void insomnia$getFilteredRules(Level world, SpawnWhen when, CallbackInfoReturnable<List<SpawnRule>> cir) {
        List<SpawnRule> correctList = getCorrectList(when);

        if (correctList == null) {
            Set<String> phases = DataStorage.getData(world).getPhases();
            correctList = rules.stream().filter((r) -> r.getWhen() == when && Utils.hasAny(phases, r.getPhases())).collect(Collectors.toList());
        }

        setCorrectList(when, correctList);
        cir.setReturnValue(correctList);
        cir.cancel();
    }
}
