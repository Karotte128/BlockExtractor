package com.Karotte128.blockextractor.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ExtractorBlockEntity extends BaseContainerBlockEntity {
    public ExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EXTRACTOR_BLOCK_ENTITY.get(), pos, state);
    }

    public static final int SIZE = 9;

    int tickCounter = 0;

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        ExtractorBlockEntity tile = (ExtractorBlockEntity) t;

        if (!level.isClientSide()) {
            tile.tickCounter++;

            if (tile.tickCounter == 20) {
                BlockPos belowPos = blockPos.below();
                BlockState belowBlockState = level.getBlockState(belowPos);
                ItemStack itemStack = new ItemStack(belowBlockState.getBlock().asItem());
                ItemStack containerItemStack = tile.getItem(0);
                if (containerItemStack.getItem().equals(itemStack.getItem())) {
                    int count = tile.getItem(0).getCount();
                    int newCount = Math.min(count + 1, 64);
                    containerItemStack.setCount(newCount);
                    tile.setItem(0, containerItemStack);
                } else if (containerItemStack.isEmpty()) {
                    tile.setItem(0, itemStack);
                }
                tile.tickCounter = 0;
            }
        }
    }

    private NonNullList<ItemStack> items = NonNullList.withSize(
            SIZE,
            ItemStack.EMPTY
    );
    @Override
    public int getContainerSize() {
        return SIZE;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.blockextractor.extractorblockentity");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = items;
    }

    @Override
    public boolean isEmpty() {
        return this.items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = ContainerHelper.removeItem(this.items, slot, amount);
        this.setChanged();
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = ContainerHelper.takeItem(this.items, slot);
        this.setChanged();
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        stack.limitSize(this.getMaxStackSize(stack));
        this.items.set(slot, stack);
        this.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        items.clear();
        this.setChanged();
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }
}
