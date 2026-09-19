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

        inst[0x0] = this::jump;
        inst[0x1] = this::jumpRegister;
        inst[0x2] = this::loadImmediate;

        inst[0x3] = this::add;

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

    /**
     * Jump instruction
     */
    private void jump(byte tick) {
        if(tick == 5) {
            GPR[1] = SPR[0];    // RA = NPC
        } else if(tick == 6) {
            PPR[0] = (short)(SPR[1] & 0x0FFF);  // PPRx0 = JMP address
        } else if(tick == 7) {
            SPR[0] = PPR[0];    // NPC = PPRx0
        } else if(tick == 8) {
            SPR[2] = SPR[0];    // PPC = NPC
        }
    }

    /**
     * Jump to register instruction
     */
    private void jumpRegister(byte tick) {
        if(tick == 6) {
            PPR[0] = (short)((SPR[1] & 0x00F0) >> 4);   // PPRx0 = rB
        } else if(tick == 7) {
            SPR[0] = GPR[PPR[0]];   // NPC = rB
        } else if(tick == 8) {
            SPR[2] = SPR[0];    // PPC = NPC
        }
    }

    /**
     * Loads immediate instruction
     */
    private void loadImmediate(byte tick) {
        if(tick == 5) {
            PPR[0] = (short)((SPR[1] & 0x0F00) >> 8); // PPRx0 = rB
        } else if(tick == 6) {
            PPR[1] = (short)(SPR[1] & 0x00FF);  // PPRx1 = immediate
        } else if(tick == 7) {
            GPR[PPR[0]] = PPR[1];   // rB = immediate
        } else if(tick == 8) {
            SPR[2] = SPR[0];    // PPC = NPC
        }
    }

    /**
     * Add instruction
     */
    private void add(byte tick) {
        if(state == State.FETCH) {
            if(tick == 5) {
            }
        }
    }










    public void DEBUG() {
        System.out.printf(
            "NPC: %04x PPC: %04x IR: %04x RA: %04x $4: %04x\n",
            SPR[0], SPR[2], SPR[1], GPR[1], GPR[4]
        );
    }
}
