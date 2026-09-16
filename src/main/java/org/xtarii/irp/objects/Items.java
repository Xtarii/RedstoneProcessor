package org.xtarii.irp.objects;

import org.xtarii.irp.RedstoneProcessor;

import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Redstone processor items class
 */
public final class Items {
    /**
     * Modded items registry object
     */
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RedstoneProcessor.MOD_ID);



    /**
     * Integrated Redstone Processor ( IRP ) item
     */
    public static final DeferredItem<BlockItem> IRP = ITEMS.registerSimpleBlockItem("integrated_redstone_processor", Blocks.IRP);
}
