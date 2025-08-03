package com.github.dylanxyz.insomnia.mixin.fromtheshadows;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.sonmok14.fromtheshadows.server.entity.mob.NehemothEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(value = NehemothEntity.class, remap = false)
public abstract class MixinNehemothEntity
{
    @Shadow
    private static boolean isNether(ServerLevelAccessor worldIn, BlockPos position) {
        return false;
    }

    @Shadow
    private static boolean isBiomeSoulSandValley(LevelAccessor worldIn, BlockPos position) {
        return false;
    }

    @Shadow
    public static boolean checkMonsterSpawnRules(EntityType<? extends Monster> p_33018_, ServerLevelAccessor p_33019_, MobSpawnType p_33020_, BlockPos p_33021_, RandomSource p_33022_) {
        return false;
    }

    /**
     * @author jo-devnull
     * @reason Fix nehemoth spawning
     */
    @Overwrite
    public static <T extends Mob> boolean canNehemothSpawn(EntityType<NehemothEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return reason == MobSpawnType.SPAWNER || iServerWorld.getBiome(pos).is(BiomeTags.IS_OVERWORLD) || (isNether(iServerWorld, pos) && isBiomeSoulSandValley(iServerWorld, pos)) && checkMonsterSpawnRules(entityType, iServerWorld, reason, pos, random);
    }
}
