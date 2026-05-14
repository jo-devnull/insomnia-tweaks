package github.jodevnull.insomnia.compat.emi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import dev.emi.emi.config.EmiConfig;
import dev.emi.emi.input.EmiBind;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class EmiExtensions
{
    @EmiConfig.Comment("Open the recipe file in the default editor")
    @EmiConfig.ConfigValue("binds.open_recipe")
    public static EmiBind OpenRecipeBinding = new EmiBind("key.emi.open_recipe", GLFW.GLFW_MOUSE_BUTTON_3);

    private static final Path RECIPES = Path.of("insomnia/recipes");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static Field[] addBindings(Field[] fields) throws NoSuchFieldException {
        for (int i = 0; i < fields.length; i++) {
            EmiConfig.ConfigValue annot = fields[i].getAnnotation(EmiConfig.ConfigValue.class);

            if (annot != null && annot.value().equals("binds.favorite")) {
                return ArrayUtils.insert(i + 1, fields, EmiExtensions.class.getDeclaredField("OpenRecipeBinding"));
            }
        }

        return fields;
    }

    public static ResourceLocation getLocationReal(ResourceLocation location) {
        final var segments = new ArrayList<>(
            Arrays.stream(location.getPath().split("/")).filter(s -> !s.trim().isEmpty()).toList()
        );

        var namespace = location.getNamespace();

        if (location.getPath().startsWith("/")) {
            namespace = segments.removeFirst();
        }

        final var path = String.join("/", segments) + ".json";
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    public static Optional<File> getRecipeFile(ResourceLocation recipeLocation) {
        final var server = ServerLifecycleHooks.getCurrentServer();
        final var minecraft = Minecraft.getInstance();
        final var player = minecraft.player;

        if (server == null)
            return Optional.empty();

        final var location = getLocationReal(recipeLocation);
        final var tempdir = FMLPaths.getOrCreateGameRelativePath(RECIPES.resolve(location.getNamespace()));
        final var recipe = tempdir.resolve(location.getPath());

        if (Files.exists(recipe)) {
            return Optional.of(recipe.toFile());
        }

        final var resource = server.getResourceManager().getResource(location.withPrefix("recipe/"));

        if (resource.isPresent()) try {
            final var reader = new JsonReader(resource.get().openAsReader());
            reader.setLenient(true);

            Files.createDirectories(recipe.getParent());
            Files.writeString(recipe, GSON.toJson(JsonParser.parseReader(reader)));
            recipe.toFile().deleteOnExit();

            return Optional.of(recipe.toFile());
        } catch (IOException e) {
            if (player != null)
                player.displayClientMessage(Component.literal("Failed to open recipe!"), false);
        }

        return Optional.empty();
    }

    public static void openRecipe(ResourceLocation recipeLocation) {
        final var server = ServerLifecycleHooks.getCurrentServer();
        final var minecraft = Minecraft.getInstance();
        final var player = minecraft.player;

        if (server == null)
            return;

        try {
            getRecipeFile(recipeLocation).ifPresent(Util.getPlatform()::openFile);
        } catch (Exception e) {
            if (player != null)
                player.displayClientMessage(Component.literal("Failed recipe to clipboard!"), false);
        }
    }
}
