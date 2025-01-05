package com.Karotte128.blockextractor.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record BlockExtractorRecipe(Ingredient inputItem, ItemStack output) implements Recipe<BlockExtractorInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(BlockExtractorInput pInput, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return inputItem.test(pInput.getItem(0));
    }

    @Override
    public ItemStack assemble(BlockExtractorInput pInput, HolderLookup.Provider pRegistries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.BLOCK_EXTRACTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.BLOCK_EXTRACTOR_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<BlockExtractorRecipe> {
        public static final MapCodec<BlockExtractorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(BlockExtractorRecipe::inputItem), ItemStack.CODEC.fieldOf("result").forGetter(BlockExtractorRecipe::output)).apply(inst, BlockExtractorRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, BlockExtractorRecipe> STREAM_CODEC = StreamCodec.composite(Ingredient.CONTENTS_STREAM_CODEC, BlockExtractorRecipe::inputItem, ItemStack.STREAM_CODEC, BlockExtractorRecipe::output, BlockExtractorRecipe::new);

        @Override
        public MapCodec<BlockExtractorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BlockExtractorRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}