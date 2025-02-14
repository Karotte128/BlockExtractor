package com.Karotte128.blockextractor.compat;

import com.Karotte128.blockextractor.BlockExtractor;
import com.Karotte128.blockextractor.block.ModBlocks;
import com.Karotte128.blockextractor.recipe.BlockExtractorRecipe;
import com.Karotte128.blockextractor.recipe.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIModPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(BlockExtractor.MODID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ExtractingJEICategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<BlockExtractorRecipe> extractorRecipes = recipeManager.getAllRecipesFor(ModRecipes.BLOCK_EXTRACTOR_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(ExtractingJEICategory.EXTRACTOR_RECIPE_TYPE, extractorRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        //IModPlugin.super.registerGuiHandlers(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(ModBlocks.EXTRACTOR_BLOCK.asItem().getDefaultInstance(), ExtractingJEICategory.EXTRACTOR_RECIPE_TYPE);
    }
}
