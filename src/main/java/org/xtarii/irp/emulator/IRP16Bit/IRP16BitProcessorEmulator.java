package org.xtarii.irp.emulator.IRP16Bit;

import org.xtarii.irp.emulator.IProcessor;

/**
 * Redstone Processor Emulator class
 * <p>
 * The main emulator of the minecraft
 * processor, IRP or integrated redstone processor.
 * <p>
 * The redstone processor is a 16 bit system.
 */
public class IRP16BitProcessorEmulator implements IProcessor {
    /**
     * RAM Length of the 16 Bit Integrated Redstone Processor
     */
    public static final int RAM_LENGTH = 4096;

    /**
     * Processor cycle steps
     */
    public static final short STEPS = 8;



    /**
     * Processor cycle stage
     */
    private short pulse;

    /**
     * Processor RAM
     */
    private final short[] RAM = new short[RAM_LENGTH];

    /**
     * Zero register
     * <p>
     * This will always be <code>0</code>
     */
    private final short x0 = 0x0;

    /**
     * 16 bit return address
     */
    private short ra;

    /**
     * 16 bit instruction register
     */
    private short ir;

    /**
     * 16 bit memory address registry
     */
    private short mar;

    /**
     * 16 bit temporary registers
     */
    private short t0, t1, t2;

    /**
     * 16 bit argument registers
     */
    private short a0, a1, a2;

    /**
     * 16 bit value registers
     */
    private short v0, v1, v2;

    /**
     * 16 bit stack pointer
     */
    private short sp;

    /**
     * Program counter register
     */
    private short pc = 0x0;



    /**
     * Creates a new redstone processor instance
     */
    public IRP16BitProcessorEmulator() {
        pulse = 1;
    }

    @Override
    public boolean load(short[] program) {
        pulse = 1;
        mar = pc = 0x0;
        ra = 0x0;

        for(int i = 0; i < program.length; i++) {
            RAM[i] = program[i];
        }

        return true;
    }

    @Override
    public void cycle() {
        if(pulse <= STEPS) {
            processTick(pulse);
            processInstruction(pulse);
            pulse++;
        } else {
            pulse = 1;
        }
    }

    @Override
    public void processTick(short pulse) {
        switch(pulse) {
            case 1:
                pc += 1;
                break;

            case 2:
                ir = 0x0;
                break;

            case 3:
                ir = RAM[mar];
                break;

            case 4:
                break;

            case 5:
                break;

            case 6:
                break;

            case 7:
                break;

            case 8:
                break;

            default:
                break;
        }
    }

    @Override
    public void processInstruction(short pulse) {
        byte inst = getInstruction();

        if(inst == 15) {
            doNothing();
        } else if(inst == 10) {
            doJump();
        }
    }

    @Override
    public byte getInstruction() {
        return (byte)((ir & 0xF000) >> 12);
    }

    @Override
    public void doNothing() {
        if(pulse == STEPS) {
            mar = pc;
        }
    }

    @Override
    public void doJump() {
        if(pulse == 6) {
            ra = pc;
        } else if(pulse == 7) {
            pc = (short)(ir & 0x0FFF);
        } else if(pulse == 8) {
            mar = pc;
        }
    }






    public void DEBUG() {

        System.out.printf(
            "PC: %04x IR: %08x MAR: %04x\n",
            pc, ir, mar
        );

    }
}
