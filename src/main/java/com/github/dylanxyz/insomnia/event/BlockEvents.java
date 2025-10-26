package com.github.dylanxyz.insomnia.event;

import com.github.dylanxyz.insomnia.registry.ISBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class BlockEvents
{
    private static final Random RANDOM = new Random();

    public static void register() {}

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event)
    {
        if (event.getState().is(BlockTags.LEAVES)) {
            if (RANDOM.nextFloat() < 0.25f) {
                if (!event.getLevel().isClientSide()) {
                    ServerLevel level = (ServerLevel) event.getLevel();
                    BlockPos pos = event.getPos();
                    ItemStack drop = new ItemStack(event.getState().getBlock().asItem());
                    ItemEntity entity = new ItemEntity(level, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, drop);
                    level.addFreshEntity(entity);
                }
            }
        } else if (event.getState().is(ISBlocks.IMPROVISED_BED.get())) {
            if (RANDOM.nextFloat() < 0.44f) {
                ServerLevel level = (ServerLevel) event.getLevel();
                BlockPos pos = event.getPos();
                ItemStack drop = new ItemStack(Items.STICK, 2);
                ItemEntity entity = new ItemEntity(level, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, drop);
                level.addFreshEntity(entity);
            }
        }
    }
}
