package org.xtarii.irp.emulators.cores;

import org.xtarii.irp.RedstoneProcessor;

/**
 * Redstone core 16 bit and 8 tick cycle
 * <p>
 * The core supplies the processor with
 * 16 bit registers and processes a
 * processor cycle with 8 ticks.
 */
public abstract class RCore1608 {
    /**
     * Processor RAM
     */
    protected final short[] RAM;

    /**
     * 16 bit general purpose registers
     */
    protected final short[] GPR = new short[0xF];

    /**
     * 16 bit special purpose registers
     * <p>
     * <pre>
     * x0 = NPC - Next program count
     * x1 = IR  - Instruction register
     * x2 = PPC - Previous program count
     * x3 = SR  - Supervision register, processor status
     * </pre>
     */
    protected final short[] SPR = new short[4];

    /**
     * 16 bit state exception registers
     * <p>
     * <pre>
     * x0 = EPC - Exception program counter register
     * x1 = EEA - Exception effective address
     * x2 = ESR - Exception supervision register
     * </pre>
     */
    protected final short[] SER = new short[3];

    /**
     * Processor instruction set
     */
    private Instruction[] INST = null;

    /**
     * Processor cycle state
     */
    protected State state;

    /**
     * Processor pulse tick
     */
    private byte tick;

    /**
     * Processor power
     */
    private boolean power;



    /**
     * Creates a redstone core 16 bit
     *
     * @param size Processor RAM size
     */
    protected RCore1608(short size) {
        RAM = new short[size];
        GPR[0] = 0x0; // $0 is always 0

        state = State.FETCH;
        tick = 1;
    }



    /**
     * Updates the processor
     * <p>
     * Goes through one tick of the processor
     * cycle and advances the tick for the
     * next {@link #cycle()} call.
     */
    public void cycle() {
        if(tick <= 8) {
            processTick(tick);
            processInstruction(tick);
            tick++;
        } else {
            tick = 1; // Resets the tick for a new cycle
        }
    }

    /**
     * Processes the current instruction loaded
     * into the processors instruction register.
     * <p>
     * This will load and execute the instruction
     * requested from the loaded instruction set,
     * see {@link #setInstructionSet(Instruction[])}
     * for more details.
     *
     * @param tick Pulse tick
     */
    public void processInstruction(byte tick) {
        byte inst = getInstruction();
        Instruction instruction = INST[inst];
        if(instruction == null) {
            RedstoneProcessor.LOGGER.warn("Instruction, {}, does not exist", inst);
            power = false;  // Stops the processor
        } else {
            instruction.execute(tick);
        }
    }

    /**
     * Gets the current instruction loaded
     * from the instruction register.
     * <p>
     * This will get the <code>opcode</code>
     * from a 16 bit instruction.
     *
     * @return Instruction <code>opcode</code>
     */
    public byte getInstruction() {
        return (byte)((SPR[1] & 0xF000) >> 12);
    }

    /**
     * Processes a processor cycle tick
     * <p>
     * This is called by the processor cycle
     * when the processor is ready to
     * processes the current tick.
     *
     * @param tick Pulse tick
     */
    public void processTick(byte tick) {
        switch(tick) {
            case 1:
                if(state == State.FETCH) {
                    SPR[0] += 1;    // NPC += 1
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
     * Resets processor tick
     * <p>
     * This can be used to reset
     * a processors update cycle
     * when a new program is loaded.
     */
    protected void resetTick() {
        this.tick = 1;
    }

    /**
     * Sets the processor instruction set
     * <p>
     * This loads a set of instructions available
     * for the processor to execute. Therefore
     * this should only be called once in the
     * constructor.
     * <p>
     * When the processor has a set of instructions
     * it may use any of the instructions whenever
     * a program requests it. It will match the
     * instructions index with a <code>opcode</code>
     * and execute the instruction at the opcode index
     *
     * @param instructions Instruction set
     * @throws IllegalAccessException This is thrown if there is already
     * a instruction set loaded.
     */
    protected void setInstructionSet(Instruction[] instructions) throws IllegalAccessException {
        if(this.INST == null) {
            this.INST = instructions;
        } else {
            throw new IllegalAccessException("An instruction set is already loaded.");
        }
    }

    /**
     * Gets processor power status
     *
     * @return Power status
     */
    public boolean getPower() {
        return this.power;
    }

    /**
     * Sets processor power status
     *
     * @param power New power status
     */
    public void setPower(boolean power) {
        this.power = power;
    }





    /**
     * Core instruction interface
     */
    protected static interface Instruction {
        /**
         * Executes the instruction
         *
         * @param tick Pulse tick
         */
        public void execute(byte tick);
    }

    /**
     * Processor cycle state
     */
    protected enum State {
        /**
         * Fetch state
         */
        FETCH,

        /**
         * Execute state
         */
        EXECUTE,
    }
}
