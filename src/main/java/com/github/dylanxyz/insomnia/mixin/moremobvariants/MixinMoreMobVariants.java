package com.github.dylanxyz.insomnia.mixin.moremobvariants;

import com.github.dylanxyz.insomnia.MoreVariants;
import com.github.nyuppo.MoreMobVariants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = MoreMobVariants.class, remap = false)
public class MixinMoreMobVariants
{
    @Inject(at = @At("TAIL"), method = "onReload")
    private void insomnia$onReload(AddReloadListenerEvent event, CallbackInfo ci) {
        event.addListener(new SimpleJsonResourceReloadListener(new Gson(), "moremobvariants") {
            @Override
            protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager manager, ProfilerFiller profilerFiller) {
                MoreVariants.registerVariants();
            }
        });
    }
}
