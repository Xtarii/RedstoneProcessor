package org.xtarii.irp.emulators.processors;

import org.xtarii.irp.emulators.cores.R16bit.RCore16Bit;

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






    public void DEBUG() {
        System.out.printf(
            "NPC: %04x PPC: %04x IR: %04x\n",
            SPR[0], SPR[2], SPR[1]
        );
    }
}
