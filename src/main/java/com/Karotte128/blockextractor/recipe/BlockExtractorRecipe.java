package com.Karotte128.blockextractor.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record BlockExtractorRecipe(Ingredient inputItem, ItemStack outputItems, int processingTicks, int energy) implements Recipe<BlockExtractorInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(BlockExtractorInput recipeInput, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        return inputItem.test(recipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(BlockExtractorInput recipeInput, HolderLookup.Provider provider) {
        return outputItems.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return outputItems;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.BLOCK_EXTRACTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.BLOCK_EXTRACTOR_TYPE.get();
    }
}