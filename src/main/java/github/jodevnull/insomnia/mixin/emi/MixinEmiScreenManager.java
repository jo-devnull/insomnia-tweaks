package github.jodevnull.insomnia.mixin.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.stack.EmiStackInteraction;
import dev.emi.emi.config.SidebarType;
import dev.emi.emi.input.EmiBind;
import dev.emi.emi.runtime.EmiDrawContext;
import dev.emi.emi.screen.EmiScreenBase;
import dev.emi.emi.screen.EmiScreenManager;
import dev.emi.emi.screen.widget.EmiSearchWidget;
import github.jodevnull.insomnia.compat.emi.EmiCategory;
import github.jodevnull.insomnia.compat.emi.EmiExtensions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(value = EmiScreenManager.class, remap = false)
public abstract class MixinEmiScreenManager
{
    @Shadow
    @Nullable
    public static EmiScreenManager.@Nullable ScreenSpace getHoveredSpace(int mouseX, int mouseY) {
        return null;
    }

    @Shadow
    public static EmiSearchWidget search;

    @Inject(method = "renderCurrentTooltip", at = @At("HEAD"), cancellable = true)
    private static void insomnia$hideTooltip(EmiDrawContext context, int mouseX, int mouseY, float delta, EmiScreenBase base, CallbackInfo ci) {
        EmiScreenManager.ScreenSpace space = getHoveredSpace(mouseX, mouseY);

        if (space == null)
            return;

        if (space.getType() == SidebarType.INDEX && search.getValue().isEmpty()) {
            ci.cancel();
        }
    }

    @Inject(method = "stackInteraction", at = @At(value = "INVOKE", target = "Ldev/emi/emi/runtime/EmiFavorites;addFavorite(Ldev/emi/emi/api/stack/EmiIngredient;Ldev/emi/emi/api/recipe/EmiRecipe;)V"), cancellable = true)
    private static void insomnia$stackInteraction(EmiStackInteraction stack, Function<EmiBind, Boolean> function, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getStack() instanceof EmiCategory) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "recipeInteraction", at=@At("HEAD"), cancellable = true)
    private static void insomnia$copyRecipe(EmiRecipe recipe, Function<EmiBind, Boolean> function, CallbackInfoReturnable<Boolean> cir) {
        if (recipe == null) {
            cir.setReturnValue(false);
        } else if (function.apply(EmiExtensions.OpenRecipeBinding)) {
            final var recipeLocation = recipe.getId();

            if (recipeLocation != null) {
                EmiExtensions.openRecipe(recipeLocation);
                return;
            }

            cir.setReturnValue(true);
        }
    }
}
