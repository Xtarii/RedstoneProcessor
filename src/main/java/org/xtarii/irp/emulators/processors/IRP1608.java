package org.xtarii.irp.emulators.processors;

import org.xtarii.irp.RedstoneProcessor;
import org.xtarii.irp.emulators.cores.RCore1608;

/**
 * Integrated Redstone Processor ( IRP )
 * with a 16 bit 8 cycle core.
 */
public class IRP1608 extends RCore1608 {
    /**
     * Processor pipeline registers
     */
    private final short[] PPR = new short[4];



    /**
     * Creates a IRP 16 bit processor object
     */
    public IRP1608() {
        super((short)4096);

        Instruction[] inst = new Instruction[16];

        inst[0xF] = this::nop;

        try {
            setInstructionSet(inst);
        } catch(IllegalAccessException e) {
            RedstoneProcessor.LOGGER.warn("Failed to load instructions to processor");
        }
    }

    /**
     * Loads program into processor core
     *
     * @param program Program instructions
     * @return Program load status
     */
    public boolean load(short[] program) {
        if(getPower()) return false; // Can't load program into a running processor

        state = State.FETCH;
        resetTick();

        GPR[0] = 0x0;   // $0 = 0
        GPR[1] = 0x0;   // ra = 0

        SPR[0] = 0x0;   // NPC = 0
        SPR[1] = 0x0;   // IR  = 0
        SPR[2] = 0x0;   // PPC = 0
        SPR[3] = 0x0;   // SR  = 0

        SER[0] = 0x0;  // EPC = 0
        SER[1] = 0x0;  // EEA = 0
        SER[2] = 0x0;  // ESR = 0

        for(int i = 0; i < program.length; i++) {
            RAM[i] = program[i];
        }

        return true;
    }



    /**
     * No operation instruction
     */
    private void nop(byte tick) {
        if(tick == 8) {
            SPR[2] = SPR[0];    // PPC = NPC
        }
    }
}
