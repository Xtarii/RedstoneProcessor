package org.xtarii.irp.emulator;

/**
 * Base interface for all processor emulators
 */
public interface IProcessor {
    /**
     * Processor cycle state
     */
    public enum State {
        /**
         * Fetch state
         */
        FETCH,

        /**
         * Execute state
         */
        EXECUTE,
    }

    /**
     * Processor instruction callable
     */
    public interface IInstruction {
        /**
         * Execute the instruction
         */
        public void execute();
    }



    /**
     * Loads program into processor RAM
     *
     * @param program Program data buffer
     * @return Program load status
     */
    public boolean load(short[] program);

    /**
     * Performs the processors main cycle
     */
    public void cycle();

    /**
     * Processes a cycle tick
     *
     * @param pulse Pulse value
     */
    public void processTick(short pulse);

    /**
     * Processes the current instruction
     *
     * @param pulse Pulse value
     */
    public void processInstruction(short pulse);

    /**
     * Gets the current instruction
     *
     * @return Current instruction
     */
    public byte getInstruction();

    /**
     * Processor error status
     *
     * @return Processor error status
     */
    public boolean hasError();
}
