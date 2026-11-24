package org.xtarii.irp.objects.blocks;

import java.util.UUID;

import javax.annotation.Nonnull;

import org.xtarii.irp.RedstoneProcessor;
import org.xtarii.irp.emulators.processors.IRP1608;
import org.xtarii.irp.objects.Blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

/**
 * Integrated Redstone Processor block
 */
@EventBusSubscriber(modid = RedstoneProcessor.MOD_ID)
public class ProcessorBlockEntity extends BlockEntity {
    /**
     * Processor ID
     */
    private UUID pid;

    /**
     * Processor power status
     */
    private boolean power;

    /**
     * 16 bit Integrated Redstone Processor
     */
    private IRP1608 emulator;



    /**
     * Creates a processor block instance
     * <p>
     * This should never be called.
     */
    public ProcessorBlockEntity(BlockPos pos, BlockState state) {
        super(Blocks.PROCESSORS.get(), pos, state);

        this.pid = UUID.randomUUID();
        this.power = false; // Processor is off by default
        emulator = new IRP1608();
    }

    @Override
    protected void loadAdditional(@Nonnull CompoundTag tag, @Nonnull HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        this.pid = tag.getUUID("pid");
    }

    @Override
    protected void saveAdditional(@Nonnull CompoundTag tag, @Nonnull HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putUUID("pid", pid);
    }



    /**
     * Gets the processor ID
     * <p>
     * This is used to identify the
     * processor in the game.
     *
     * @return Processor ID
     */
    public UUID getId() {
        return pid;
    }

    /**
     * Sets the processor power status
     * <p>
     * A <code>false</code> status indicates
     * that the processor has no power and
     * therefore can not complete it's work.
     *
     * @param power Power status
     */
    public void setPower(boolean power) {
        this.power = power;
    }

    /**
     * Switches the power status on the processor
     * <p>
     * See {@link #setPower(boolean)} for more
     * information.
     */
    public void setPower() {
        this.setPower(!this.power);
    }

    /**
     * Gets processor power status
     *
     * @return Processor power status
     */
    public boolean getPower() {
        return this.power;
    }



    @SubscribeEvent
    public static void onInteraction(InputEvent.InteractionKeyMappingTriggered event) {
        HitResult hit = Minecraft.getInstance().hitResult;
        ClientLevel level = Minecraft.getInstance().level;
        if(hit == null || level == null) return;

        if(hit.getType() == HitResult.Type.BLOCK && event.isUseItem()) {
            BlockHitResult block = (BlockHitResult)hit;

            if(level.getBlockEntity(block.getBlockPos()) instanceof ProcessorBlockEntity processor) {

                if(!processor.getPower()) {

                    short[] program = {
                        (short)0xC305,  // li $t1 0x5
                        (short)0xC200,  // li $t0 0x0
                        (short)0xC60A,  // li $s0 0xA
                        (short)0xA623,  // blt $s0 $t0 $t1

                        (short)0x0010,  // jmp 0x010

                        (short)0xC401,  // li $t2 0x1
                        (short)0x3224,  // add $t0 $t0 $t2
                        (short)0x0006,  // jmp 0x004

                        (short)0xCA00,  // li $v0 0x0
                        (short)0xB000,  // sys
                    };

                    processor.emulator.load(program);
                    processor.emulator.setPower(true);
                }

                UUID pid = processor.getId();
                RedstoneProcessor.LOGGER.debug("Started processor: " + pid);
            }
        }
    }



    /**
     * Processor updater
     *
     * @param level World level
     * @param pos Block position
     * @param state Block state
     * @param processor Processor instance
     */
    public static void tick(Level level, BlockPos pos, BlockState state, ProcessorBlockEntity processor) {
        if(!processor.emulator.getPower()) return;

        processor.emulator.cycle();
        processor.emulator.DEBUG();
    }
}
