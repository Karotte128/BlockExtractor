package com.Karotte128.blockextractor.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ExtractorBlockEntity extends BlockEntity {
    public ExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EXTRACTOR_BLOCK_ENTITY.get(), pos, state);
    }

int tickCounter = 0;

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        ExtractorBlockEntity tile = (ExtractorBlockEntity) t;

        if (!level.isClientSide()) {
            tile.tickCounter++;

            if (tile.tickCounter == 20) {
                BlockPos belowPos = blockPos.below();
                BlockState belowBlockState = level.getBlockState(belowPos);
                ItemStack itemStack = new ItemStack(belowBlockState.getBlock().asItem());
                level.addFreshEntity(new ItemEntity(level, blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, itemStack));
                tile.tickCounter = 0;
            }
        }
    }
}
