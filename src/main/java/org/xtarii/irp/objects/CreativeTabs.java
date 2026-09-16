package org.xtarii.irp.objects;

import org.xtarii.irp.RedstoneProcessor;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Redstone Processor creative mode tabs
 */
@EventBusSubscriber(modid = RedstoneProcessor.MOD_ID)
public final class CreativeTabs {
    /**
     * Modded creative menu tab
     */
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RedstoneProcessor.MOD_ID);

    /**
     * Creative mode modded blocks tab
     */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLOCKS = TABS.register("blocks", () -> CreativeModeTab.builder()
        .title(Component.translatable("itemGroup.redstoneprocessor"))
        .icon(() -> Items.IRP.get().getDefaultInstance())
        .displayItems((param, out) -> {

            out.accept(Items.IRP.get());

        })
        .build());



    @SubscribeEvent
    public static void onCreativeModTabs(final BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(Items.IRP.value());
        }
    }
}
