package com.github.dylanxyz.insomnia.mixin.fromtheshadows;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.sonmok14.fromtheshadows.server.entity.mob.NehemothEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = NehemothEntity.class, remap = false)
public abstract class MixinNehemothEntity
{
    @Inject(at = @At("HEAD"), method = "canNehemothSpawn", cancellable = true)
    private static void insomnia$canNehemothSpawn(EntityType<NehemothEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random, CallbackInfoReturnable<Boolean> cir) {
        Holder<Biome> biome = iServerWorld.getBiome(pos);

        cir.setReturnValue(
            reason == MobSpawnType.SPAWNER || biome.is(BiomeTags.IS_OVERWORLD) ||
            (biome.is(BiomeTags.IS_NETHER) && biome.is(Biomes.SOUL_SAND_VALLEY)) &&
            NehemothEntity.checkMonsterSpawnRules(entityType, iServerWorld, reason, pos, random)
        );
    }
}
