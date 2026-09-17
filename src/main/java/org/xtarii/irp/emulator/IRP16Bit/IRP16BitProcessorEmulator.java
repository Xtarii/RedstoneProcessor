package org.xtarii.irp.emulator.IRP16Bit;

import org.xtarii.irp.RedstoneProcessor;
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
     * The amount of registers that this processor uses
     */
    public static final short REGISTERS = 16;

    /**
     * The amount of instructions available on this processor
     */
    public static final byte INSTRUCTIONS = 16;

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
     * Processor registers
     * <p>
     * 16 registers of size 16 bits.
     * The registers are divided into
     * groups and usage with a few not
     * user accessible registers.
     * <pre>
     * x0 = 0x0         // Read-only static 0 value register
     * x1               // Return address registry
     *
     * x2, x3, x4       // Temporary memory registers
     * x5, x6, x7       // Argument registers
     * x8, x9, x10      // Value registers, used to store return values
     * x11              // Error register, stores an error code or 0 if no error
     * x12              // Stack register
     *
     * x13              // Instruction register, processor only
     * x14              // Memory address registry, processor only
     * x15              // Program count, processor only
     * </pre>
     */
    private final short[] REG = new short[REGISTERS];

    /**
     * Processor instructions
     * <p>
     * Each instruction has an <code>opcode</code>
     * corresponding to it's index in this array.
     */
    private final IInstruction[] INST = new IInstruction[INSTRUCTIONS];

    /**
     * Processor error status
     */
    private boolean error = false;



    /**
     * Creates a new redstone processor instance
     */
    public IRP16BitProcessorEmulator() {
        pulse = 1;
        REG[0] = 0x0; // This will always be 0

        // Processor instruction set
        INST[0x0] = this::doNothing;
        INST[0xA] = this::doJump;
    }

    @Override
    public boolean load(short[] program) {
        pulse = 1;

        REG[15] = 0x0;  // Sets the PC count to beginning of RAM
        REG[14] = 0x0;  // Sets Memory Address Registry to PC
        REG[1]  = 0x0;  // Clears the return address
        REG[11] = 0x0;  // Clears any previous errors

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
                REG[15] += 1;   // PC += 1
                break;

            case 2:
                REG[13] = 0x0;  // IR = 0x0
                break;

            case 3:
                REG[13] = RAM[REG[14]]; // IR = RAM[MAR]
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
        IInstruction instruction = INST[inst];

        if(instruction == null) {
            RedstoneProcessor.LOGGER.warn("16-bit IRP invalid instruction %08x at %04x", REG[13], REG[14]);
            REG[11] = 0xFFF; // Sets execution error
            error = true;
        } else {
            instruction.execute();
        }
    }

    @Override
    public byte getInstruction() {
        return (byte)((REG[13] & 0xF000) >> 12);
    }

    @Override
    public boolean hasError() {
        return error;
    }



    /**
     * Does nothing
     */
    public void doNothing() {
        if(pulse == 8) {
            REG[14] = REG[15];  // MAR = PC
        }
    }

    /**
     * Does a jump
     */
    public void doJump() {
        if(pulse == 6) {
            REG[1] = REG[15];   // RA = PC, stores return address
        } else if(pulse == 7) {
            REG[15] = (short)(REG[13] & 0x0FFF); // PC = IR & 0x0FFF
        } else if(pulse == 8) {
            REG[14] = REG[15];  // MAR = PC
        }
    }






    public void DEBUG() {

        System.out.printf(
            "PC: %04x IR: %08x MAR: %04x ER: %04x\n",
            REG[15], REG[13], REG[14], REG[11]
        );

    }
}
