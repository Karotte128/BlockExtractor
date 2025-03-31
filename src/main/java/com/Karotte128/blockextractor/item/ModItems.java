package com.Karotte128.blockextractor.item;

import com.Karotte128.blockextractor.BlockExtractor;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BlockExtractor.MODID);

//    public static final DeferredItem<Item> EXAMPLE = ITEMS.register("example_item",
//            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
