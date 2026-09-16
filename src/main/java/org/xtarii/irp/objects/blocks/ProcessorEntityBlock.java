package org.xtarii.irp.objects.blocks;

import javax.annotation.Nonnull;

import org.xtarii.irp.objects.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Integrated redstone processor entity block class
 */
public class ProcessorEntityBlock extends Block implements EntityBlock {
    /**
     * Creates a processor entity block instance
     * <p>
     * Do not call this.
     */
    public ProcessorEntityBlock() {
        super(BlockBehaviour.Properties.of()
            .sound(SoundType.COPPER)
            .requiresCorrectToolForDrops()
            .destroyTime(0.2f)
        );
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new ProcessorBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> type) {
        return getTicker(type, Blocks.PROCESSORS.get(), ProcessorBlockEntity::tick);
    }



    /**
     * Gets processor ticker method
     *
     * @param <T> Desired ticker and block type
     * @param <V> Block type
     * @param type Type of block
     * @param want Block to look for
     * @param ticker Ticker method
     * @return The ticking method desired or <code>null</code>
     */
    public static <T extends BlockEntity, V extends BlockEntity> BlockEntityTicker<V> getTicker(BlockEntityType<V> type, BlockEntityType<T> want, BlockEntityTicker<? super T> ticker) {
        return want == type ? (BlockEntityTicker<V>) ticker : null;
    }
}
