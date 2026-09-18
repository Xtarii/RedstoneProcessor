package org.xtarii.irp.emulators.cores.R16bit;

import org.xtarii.irp.emulators.cores.RedstoneCore;

/**
 * Redstone 16 bit core
 * <p>
 * A 16 bit processor core for the
 * redstone processors.
 * <p>
 * Instructions and registers use 16 bits
 * and the core provides 16 general purpose registers
 * <code>GPRs</code>, where register $0 is always
 * the value 0x0.
 */
public abstract class RCore16Bit extends RedstoneCore<Short, Byte, Byte> {
    /**
     * General purpose registers
     * <p>
     * This is a constructor specific object
     * used in the {@link #RCore16Bit()}
     * constructor to implement the <code>GPRs</code>
     */
    public static final Short[] GPR = new Short[16];

    /**
     * Special purpose registers
     * <p>
     * This is a constructor specific object
     * used in the {@link #RCore16Bit()} constructor
     * to implement the <code>SPRs</code>
     */
    public static final Short[] SPR = {
        0x0,    // NPC - Next program count
        0x0,    // IR  - Instruction register
        0x0,    // PPC - Previous program count
        0x0,    // SR  - Supervision register, processor status
    };

    /**
     * State execution exception registers
     * <p>
     * This is a constructor specific object
     * used in the {@link #RCore16Bit()} constructor
     * to implement the <code>SEERs</code>
     */
    public static final Short[] SEER = {
        0x0,    // EPC - Exception program counter register
        0x0,    // EEA - Exception effective address
        0x0,    // ESR - Exception supervision register
    };

    /**
     * Processor pipeline registers
     * <p>
     * This is a constructor specific object
     * used in the {@link #RCore16Bit()} constructor
     * to implement the <code>PPRs</code>
     */
    public static final Short[] PPR = new Short[4];



    /**
     * Processor cycle tick
     */
    private byte tick;



    /**
     * Creates a 16 bit redstone processor core
     *
     * @param instructions Core instruction set
     */
    public RCore16Bit(CoreInstruction<Byte>[] instructions) {
        super(GPR, SPR, SEER, PPR, instructions);
        GPR[0] = 0x0; // Sets the GPR 0 to 0x0
        tick = 1;
    }



    @Override
    public void cycle() {
        if(tick <= 8) { // Makes a maximum of 8 ticks per cycle
            processTick(tick);

            // Run instruction

            System.out.println("\nINST: " + getInstruction() + "\n");

            tick++;
        } else {
            tick = 1; // Resets tick cycle
        }
    }

    @Override
    public Byte getInstruction() {
        return (byte)((SPR[1] & 0xF000) >> 12);
    }



    /**
     * Sets the processor clock pulse tick
     *
     * @param tick New pulse tick
     */
    protected void setTick(byte tick) {
        this.tick = tick;
    }
}
