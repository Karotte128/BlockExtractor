package com.Karotte128.blockextractor.item;

import com.Karotte128.blockextractor.BlockExtractor;
import com.Karotte128.blockextractor.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlockExtractor.MODID);

    public static final Supplier<CreativeModeTab> BLOCKEXTRACTOR_TAB = CREATIVE_MODE_TAB.register("blockextractor_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.EXTRACTOR_BLOCK.get()))
                    .title(Component.translatable("itemGroup.blockextractor_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.EXAMPLE);
                        output.accept(ModBlocks.EXAMPLE_BLOCK);
                        output.accept(ModBlocks.EXTRACTOR_BLOCK);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
