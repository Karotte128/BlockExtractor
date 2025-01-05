package com.Karotte128.blockextractor.recipe;

import com.Karotte128.blockextractor.BlockExtractor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, BlockExtractor.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, BlockExtractor.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BlockExtractorRecipe>> BLOCK_EXTRACTOR_SERIALIZER = SERIALIZERS.register("extracting", BlockExtractorRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<BlockExtractorRecipe>> BLOCK_EXTRACTOR_TYPE = TYPES.register("extracting", () -> new RecipeType<BlockExtractorRecipe>() {
        @Override
        public String toString() {
            return "extracting";
        }
    });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}