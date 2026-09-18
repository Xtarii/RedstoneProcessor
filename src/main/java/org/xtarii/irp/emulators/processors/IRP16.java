package org.xtarii.irp.emulators.processors;

import org.xtarii.irp.emulators.o_cores.R16bit.RCore16Bit;

/**
 * Integrated redstone processor model 16 bit
 * <p>
 * A processor of series IRP with a 16 bit
 * processing capability.
 */
public class IRP16 extends RCore16Bit {
    /**
     * Processor RAM
     */
    protected final short[] RAM = new short[4096];



    /**
     * Creates a integrated redstone processor
     */
    public IRP16() {
        super(null);
    }

    @Override
    public void processTick(Byte tick) {
        switch(tick) {
            case 1:
                if(state == State.FETCH) {
                    SPR[0] = (short)(SPR[0].shortValue() + 1);    // NPC += 1
                }
                break;
            case 2:
                if(state == State.FETCH) {
                    SPR[1] = RAM[SPR[2]];   // IR = RAM[PPC]
                }
                break;
            default:
                break;
        }
    }



    /**
     * No operations instruction, 0xF
     */
    public void nop(Byte tick) {
        if(tick == 8) {
            SPR[2] = SPR[0];    // PPC = NPC
        }
    }






    public void load(short[] program) {
        state = State.FETCH;
        setTick((byte)1);

        GPR[0] = 0x0;   // $0  = 0
        GPR[1] = 0x0;   // $ra = 0

        SPR[0] = 0x0;   // NPC = 0
        SPR[1] = 0x0;   // IR  = 0
        SPR[2] = 0x0;   // PPC = 0
        SPR[3] = 0x0;   // SR  = 0

        SEER[0] = 0x0;  // EPC = 0
        SEER[1] = 0x0;  // EEA = 0
        SEER[2] = 0x0;  // ESR = 0

        for(int i = 0; i < program.length; i++) {
            RAM[i] = program[i];
        }
    }

    public void DEBUG() {
        System.out.printf(
            "NPC: %04x PPC: %04x IR: %04x\n",
            SPR[0], SPR[2], SPR[1]
        );
    }
}
