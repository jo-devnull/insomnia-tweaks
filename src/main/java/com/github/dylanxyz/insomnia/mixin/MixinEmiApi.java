package com.github.dylanxyz.insomnia.mixin;

import dev.emi.emi.VanillaPlugin;
import dev.emi.emi.api.EmiApi;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.TagEmiIngredient;
import dev.emi.emi.recipe.EmiTagRecipe;
import dev.emi.emi.runtime.EmiFavorite;
import dev.emi.emi.screen.EmiScreenManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.emi.emi.api.EmiApi.getRecipeManager;

@Mixin(EmiApi.class)
public class MixinEmiApi
{
    @Inject(method = "displayRecipes", at = @At("HEAD"), remap = false, cancellable = true)
    private static void insomnia$displayRecipes(EmiIngredient stack, CallbackInfo ci) {
        if (stack instanceof EmiFavorite fav) {
            if (fav.getStack() instanceof TagEmiIngredient tag) {
                for(EmiRecipe recipe : getRecipeManager().getRecipes(VanillaPlugin.TAG)) {
                    if (recipe instanceof EmiTagRecipe tr && tr.key.equals(tag.key)) {
                        final ResourceLocation tagLoc = tag.key.location();
                        final String searchText = "#" + tagLoc.getNamespace() + ":" + tagLoc.getPath();
                        EmiScreenManager.search.setValue(searchText);
                        ci.cancel();
                    }
                }
            }
        }
    }
}
