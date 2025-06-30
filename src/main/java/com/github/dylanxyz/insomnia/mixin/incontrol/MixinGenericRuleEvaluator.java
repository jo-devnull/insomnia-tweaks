package com.github.dylanxyz.insomnia.mixin.incontrol;

import com.github.dylanxyz.insomnia.compat.InControl;
import mcjty.incontrol.rules.support.GenericRuleEvaluator;
import mcjty.incontrol.tools.rules.IEventQuery;
import mcjty.incontrol.tools.typed.AttributeMap;
import mcjty.incontrol.tools.varia.Tools;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.BiFunction;

import static com.github.dylanxyz.insomnia.compat.InControl.LUNAR;
import static com.github.dylanxyz.insomnia.compat.InControl.STORM;

@Mixin(GenericRuleEvaluator.class)
public class MixinGenericRuleEvaluator
{
    @Shadow @Final
    private List<BiFunction<Object, IEventQuery<Object>, Boolean>> checks;

    @Inject(at = @At("TAIL"), method = "addChecks", remap = false)
    private void insomnia$addCompatChecks(AttributeMap map, CallbackInfo ci) {
        map.consumeAsList(LUNAR, (phases) -> {
            if (!phases.isEmpty()) {
                checks.add((event, query) -> {
                    ServerLevel world = Tools.getServerWorld(query.getWorld(event));
                    return phases.contains(InControl.getLunarPhase(world));
                });
            }
        });

        map.consume(STORM, (value) -> {
            checks.add((event, query) -> {
                ServerLevel world = Tools.getServerWorld(query.getWorld(event));
                return InControl.isInsideStorm(world, query.getEntity(event).blockPosition());
            });
        });
    }
}
