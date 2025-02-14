package com.Karotte128.blockextractor.compat;

import com.Karotte128.blockextractor.BlockExtractor;
import com.Karotte128.blockextractor.block.ModBlocks;
import com.Karotte128.blockextractor.recipe.BlockExtractorRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ExtractingJEICategory implements IRecipeCategory<BlockExtractorRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(BlockExtractor.MODID, "extracting");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BlockExtractor.MODID, "textures/gui/extractor/extractor_gui.png");

    public static final RecipeType<BlockExtractorRecipe> EXTRACTOR_RECIPE_TYPE = new RecipeType<>(UID, BlockExtractorRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public ExtractingJEICategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.EXTRACTOR_BLOCK.get()));
    }


    @Override
    public RecipeType<BlockExtractorRecipe> getRecipeType() {
        return EXTRACTOR_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.blockextractor.extractor_block");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BlockExtractorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 40, 35).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 35).addItemStack(recipe.getResultItem(null));
    }
}
