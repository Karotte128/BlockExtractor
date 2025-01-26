package com.Karotte128.blockextractor.blockentity;

import com.Karotte128.blockextractor.Config;
import com.Karotte128.blockextractor.menu.ExtractorMenu;
import com.Karotte128.blockextractor.recipe.BlockExtractorInput;
import com.Karotte128.blockextractor.recipe.BlockExtractorRecipe;
import com.Karotte128.blockextractor.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ExtractorBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public final EnergyStorage energyHandler = new EnergyStorage(Config.energyStorage);

    int tickCounter = 0;

    public ExtractorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.EXTRACTOR_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockentity.blockextractor.extractor");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new ExtractorMenu(id, playerInventory, this);
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putShort("TickCounter", (short) this.tickCounter);
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("energy", energyHandler.getEnergyStored());
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        this.tickCounter = tag.getShort("TickCounter");
        energyHandler.receiveEnergy(tag.getInt("energy"), false);
    }

    public @Nullable IItemHandler getItemHandler() {
        return itemHandler;
    }

    public @Nullable IEnergyStorage getEnergyHandler() {
        return energyHandler;
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    public void tick(Level level, BlockPos blockPos, BlockState state) {

        if (!level.isClientSide()) {
            if (tickCounter > 0) {
                tickCounter--;
            } else if (tickCounter == 0) {
                BlockPos belowPos = blockPos.below();
                BlockState belowBlockState = level.getBlockState(belowPos);

                craftItem(belowBlockState);
            } else if (tickCounter < 0) {
                System.out.println("tick counter smaller than 0");
                tickCounter = 0;
            }

        }
    }

    private Optional<RecipeHolder<BlockExtractorRecipe>> getCurrentRecipe(BlockState belowBlockState) {
        ItemStack inputStack = new ItemStack(belowBlockState.getBlock().asItem());
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.BLOCK_EXTRACTOR_TYPE.get(), new BlockExtractorInput(inputStack), level);
    }

    private void craftItem(BlockState belowBlockState) {
        Optional<RecipeHolder<BlockExtractorRecipe>> recipe = getCurrentRecipe(belowBlockState);
        ItemStack containerItemStack = itemHandler.getStackInSlot(0);

        if (!recipe.isEmpty()) {
            if (recipe.get().value().energy() <= energyHandler.getEnergyStored()) {
                Item outputItem = recipe.get().value().outputItems().getItem();
                int outputAmount = recipe.get().value().outputItems().getCount();

                if (containerItemStack.getItem().equals(outputItem)) {
                    int count = itemHandler.getStackInSlot(0).getCount();
                    int newCount = Math.min(count + outputAmount, 64);
                    containerItemStack.setCount(newCount);
                    itemHandler.setStackInSlot(0, containerItemStack);
                } else if (containerItemStack.isEmpty()) {
                    itemHandler.setStackInSlot(0, new ItemStack(outputItem, outputAmount));
                }
                tickCounter = recipe.get().value().processingTicks();
                energyHandler.extractEnergy(recipe.get().value().energy(), false);
            }
        }
    }
}