//package com.github.dylanxyz.insomnia.mixin.sanity;
//
//import com.github.dylanxyz.insomnia.Insomnia;
//import croissantnova.sanitydim.capability.ISanity;
//import croissantnova.sanitydim.passive.Darkness;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.tags.TagKey;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.common.util.LazyOptional;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Unique;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//import top.theillusivec4.curios.api.CuriosApi;
//import top.theillusivec4.curios.api.SlotResult;
//import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
//
//import java.util.List;
//import java.util.Optional;
//
//@Mixin(Darkness.class)
//public class MixinDarkness
//{
//    @Unique
//    private static final TagKey<Item> EMITS_LIGHT = TagKey.create(Registries.ITEM, new ResourceLocation(Insomnia.MODID, "emits_light"));
//
//    @Inject(at = @At("RETURN"), method = "get", remap = false, cancellable = true)
//    private void insomnia$get(ServerPlayer player, ISanity cap, ResourceLocation dim, CallbackInfoReturnable<Float> cir) {
//        if (insomnia$emitsLight(player.getMainHandItem())) {
//            cir.setReturnValue(0f);
//        }
//
//        else if (insomnia$emitsLight(player.getOffhandItem())) {
//            cir.setReturnValue(0f);
//        }
//
//        else {
//            CuriosApi.getCuriosInventory(player).ifPresent(itemHandler -> {
//                List<SlotResult> beltSlots = itemHandler.findCurios("belt");
//
//                for (SlotResult slot : beltSlots) {
//                    if (insomnia$emitsLight(slot.stack())) {
//                        cir.setReturnValue(0f);
//                        break;
//                    }
//                }
//            });
//        }
//    }
//
//    @Unique
//    private boolean insomnia$emitsLight(ItemStack itemStack) {
//        return !itemStack.isEmpty() && itemStack.is(EMITS_LIGHT);
//    }
//}
