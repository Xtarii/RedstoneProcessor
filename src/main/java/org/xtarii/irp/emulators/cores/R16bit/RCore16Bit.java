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
public class RCore16Bit extends RedstoneCore<Short, Byte> {
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
     * Creates a 16 bit redstone processor core
     *
     * @param instructions Core instruction set
     */
    public RCore16Bit(CoreInstruction<Byte>[] instructions) {
        super(GPR, SPR, SEER, instructions);
        GPR[0] = 0x0; // Sets the GPR 0 to 0x0
    }
}
