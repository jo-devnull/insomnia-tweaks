package github.jodevnull.insomnia.compat.emi;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.serializer.EmiIngredientSerializer;
import net.minecraft.util.GsonHelper;

import java.util.List;

public class EmiCategories
{
    public static final List<EmiCategory> categories = Lists.newArrayList();

    public static JsonArray save() {
        JsonArray arr = new JsonArray();

        for (EmiCategory cat : categories) {
            JsonElement stack = EmiIngredientSerializer.getSerialized(cat.getStack());

            if (stack != null) {
                JsonObject obj = new JsonObject();
                obj.add("stack", stack);
                arr.add(obj);
            }
        }

        return arr;
    }

    public static void load(JsonArray arr) {
        categories.clear();

        for (JsonElement el : arr) {
            if (el.isJsonObject()) {
                JsonObject json = el.getAsJsonObject();

                if (GsonHelper.isValidNode(json, "stack")) {
                    EmiIngredient ingredient = EmiIngredientSerializer.getDeserialized(json.get("stack"));

                    if (ingredient.isEmpty())
                        continue;

                    if (ingredient instanceof EmiStack es)
                        ingredient = es.copy();

                    categories.add(new EmiCategory(ingredient));
                }
            }
        }
    }
}
