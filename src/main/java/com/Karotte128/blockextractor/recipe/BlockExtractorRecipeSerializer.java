package com.Karotte128.blockextractor.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BlockExtractorRecipeSerializer implements RecipeSerializer<BlockExtractorRecipe> {
    public static final MapCodec<BlockExtractorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                    Ingredient.CODEC_NONEMPTY.fieldOf("block").forGetter(BlockExtractorRecipe::inputItem),
                    ItemStack.CODEC.fieldOf("result").forGetter(BlockExtractorRecipe::outputItems),
                    Codec.INT.fieldOf("ticks").forGetter(BlockExtractorRecipe::processingTicks)
            )
            .apply(inst, BlockExtractorRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BlockExtractorRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, BlockExtractorRecipe::inputItem,
                    ItemStack.STREAM_CODEC, BlockExtractorRecipe::outputItems,
                    ByteBufCodecs.INT, BlockExtractorRecipe::processingTicks,
                    BlockExtractorRecipe::new
            );

    @Override
    public MapCodec<BlockExtractorRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, BlockExtractorRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
