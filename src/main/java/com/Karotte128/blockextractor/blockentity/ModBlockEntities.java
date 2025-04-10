package com.Karotte128.blockextractor.blockentity;

import com.Karotte128.blockextractor.BlockExtractor;
import com.Karotte128.blockextractor.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BlockExtractor.MODID);

    public static final Supplier<BlockEntityType<ExtractorBlockEntity>> EXTRACTOR_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "extractor_block_entity",
            () -> BlockEntityType.Builder.of(
                            ExtractorBlockEntity::new,
                            ModBlocks.EXTRACTOR_BLOCK.get()
                    )
                    .build(null)
    );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, EXTRACTOR_BLOCK_ENTITY.get(), (object, context) -> object.getItemHandler());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, EXTRACTOR_BLOCK_ENTITY.get(), (object, context) -> object.getEnergyHandler());
    }

}
