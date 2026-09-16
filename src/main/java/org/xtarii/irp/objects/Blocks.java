package org.xtarii.irp.objects;

import java.util.function.Supplier;

import org.xtarii.irp.RedstoneProcessor;
import org.xtarii.irp.objects.blocks.ProcessorBlockEntity;
import org.xtarii.irp.objects.blocks.ProcessorEntityBlock;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Redstone processor block class
 */
public final class Blocks {
    /**
     * Modded block registry object
     */
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RedstoneProcessor.MOD_ID);

    /**
     * Block entity registry
     */
    public static final DeferredRegister<BlockEntityType<?>> ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, RedstoneProcessor.MOD_ID);



    /**
     * Integrated Redstone Processor ( IRP ) block
     */
    public static final DeferredBlock<Block> IRP = BLOCKS.register("integrated_redstone_processor", () -> new ProcessorEntityBlock());



    /**
     * Processor block entity registry
     */
    public static final Supplier<BlockEntityType<ProcessorBlockEntity>> PROCESSORS = ENTITIES.register(
        "integrated_redstone_processor_entity",
        () -> BlockEntityType.Builder.of(
            ProcessorBlockEntity::new,
            IRP.get()
        ).build(null)
    );
}
