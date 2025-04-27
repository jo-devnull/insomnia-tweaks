package com.github.dylanxyz.insomnia;

import com.github.nyuppo.config.Variants;
import com.github.nyuppo.variant.MobVariant;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.world.entity.EntityType;

import java.util.List;

public class MoreVariants
{
    public static void addVariants(ResourceLocation location, IoSupplier<?> io) {
        final String[] path = location.getPath().split("/");
        final String name = path[path.length - 1];

        if (name.endsWith("_e.png"))
            return;

        if (name.startsWith("zombie") && ResourceLocation.isValidPath(location.getPath())) {
            final ResourceLocation variantId = location.withPath(s -> s.replace(".png", ""));
            Variants.addVariant(EntityType.ZOMBIE, new MobVariant(variantId, 1));
        }
    }

    public static void registerVariants() {
        final List<PackResources> packs = Minecraft.getInstance().getResourceManager().listPacks().toList();

        for (var pack : packs) {
            for (var namespace : pack.getNamespaces(PackType.CLIENT_RESOURCES)) {
                pack.listResources(PackType.CLIENT_RESOURCES, namespace, "optifine/random/entity/zombie", MoreVariants::addVariants);
            }
        }
    }
}
