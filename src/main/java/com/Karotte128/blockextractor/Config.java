package com.Karotte128.blockextractor;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = BlockExtractor.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue ENERGY_STORAGE = BUILDER
            .comment("Amount of energy the extractor can store")
            .defineInRange("storedEnergy", 10000, 0, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static int energyStorage;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        energyStorage = ENERGY_STORAGE.get();
    }
}
