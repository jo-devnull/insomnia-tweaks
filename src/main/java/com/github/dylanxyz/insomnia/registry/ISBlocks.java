package com.github.dylanxyz.insomnia.registry;

import com.github.dylanxyz.insomnia.Insomnia;
import com.github.dylanxyz.insomnia.blocks.ImprovisedBedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ISBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Insomnia.MODID);

    public static final RegistryObject<ImprovisedBedBlock> IMPROVISED_BED =
        BLOCKS.register("improvised_bed", () -> new ImprovisedBedBlock(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_GREEN)
                .sound(SoundType.CHERRY_LEAVES)
                .strength(0.2F)
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY)
        ));

    public static void register(IEventBus event)
    {
        BLOCKS.register(event);
    }
}
