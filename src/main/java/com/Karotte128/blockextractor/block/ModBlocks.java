package com.Karotte128.blockextractor.block;

import com.Karotte128.blockextractor.BlockExtractor;
import com.Karotte128.blockextractor.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BlockExtractor.MODID);

    public static final DeferredBlock<Block> EXTRACTOR_BLOCK = registerBlock("extractor_block",
            () -> new ExtractorBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.COPPER_GRATE)
                    .noOcclusion()
            ));

//    public static final DeferredBlock<Block> EXAMPLE_BLOCK = registerBlock("example_block",
//            () -> new Block(BlockBehaviour.Properties.of()
//                    .strength(4f)
//                    .requiresCorrectToolForDrops()
//                    .sound(SoundType.AMETHYST)
//            ));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
