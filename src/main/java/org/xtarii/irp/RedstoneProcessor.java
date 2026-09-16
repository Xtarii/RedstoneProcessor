package org.xtarii.irp;

import org.slf4j.Logger;
import org.xtarii.irp.config.Config;
import org.xtarii.irp.objects.Blocks;
import org.xtarii.irp.objects.CreativeTabs;
import org.xtarii.irp.objects.Items;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

/**
 * Redstone Processor mod class
 * <p>
 * The base class for the Redstone Processor mod.
 */
@Mod(RedstoneProcessor.MOD_ID)
public class RedstoneProcessor {
    /**
     * MOD ID
     * <p>
     * Define mod id in a common place for everything to reference
     */
    public static final String MOD_ID = "redstoneprocessor";

    /**
     * Minecraft debug logger
     */
    public static final Logger LOGGER = LogUtils.getLogger();





    /**
     * Creates and setup the redstone processor mod
     * <p>
     * This should never be called.
     *
     * @param modEventBus Mod event bus
     * @param modContainer Mod container
     */
    public RedstoneProcessor(IEventBus modEventBus, ModContainer modContainer) {

        // Registers mod registries
        Blocks.BLOCKS.register(modEventBus);
        Blocks.ENTITIES.register(modEventBus);
        Items.ITEMS.register(modEventBus);
        CreativeTabs.TABS.register(modEventBus);


        // modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (RedstoneProcessor) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }



    // private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code



        // LOGGER.info("HELLO FROM COMMON SETUP");

        // if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
        //     LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(net.minecraft.world.level.block.Blocks.DIRT));
        // }

        // LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        // Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    // }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
