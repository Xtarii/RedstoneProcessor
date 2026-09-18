package org.xtarii.irp.emulators.cores;

/**
 * Redstone processor core
 * <p>
 * The abstract redstone processor core <code>( RPC )</code>
 * is a general purpose core for all implementations
 * of the Redstone Processor.
 * <p>
 * This provides the basic framework of any
 * Redstone Processor no matter the bit size
 * of its core instructions specified by <code>T</code>
 * <p>
 * The redstone processor core is based on
 * the specifications of <code>OpenRISC 1000</code>
 *
 * @param <T> Type of core
 * @param <V> Core tick pulse type
 */
public abstract class RedstoneCore<T, V> {
    /**
     * General purpose registers
     * <p>
     * A set of general purpose registers
     * provided by the core.
     * <p>
     * The size of the registers and the
     * amount may vary from implementation
     * to implementation.
     */
    protected final T[] GPR;

    /**
     * Special purpose registers
     * <p>
     * A set of special purpose registers
     * provided by the core.
     * <p>
     * The size of the registers and the
     * amount may vary from implementation
     * to implementation.
     */
    protected final T[] SPR;

    /**
     * State execution exception registers
     * <p>
     * These registers are used to
     * handle exceptions in the processors
     * state of execution if there was
     * an error.
     * <p>
     * The size and amount of registers
     * may vary from implementation to
     * implementation.
     */
    protected final T[] SEER;

    /**
     * Core instructions set
     * <p>
     * The instructions and each <code>opcode</code>
     * may vary from implementation to implementation.
     * <p>
     * The instructions are used to preform
     * arithmetic logic on the processor.
     */
    protected final CoreInstruction<V>[] INST;



    public RedstoneCore(T[] gpr, T[] spr, T[] seer, CoreInstruction<V>[] inst) {
        GPR     = gpr;
        SPR     = spr;
        SEER    = seer;
        INST    = inst;
    }





    /**
     * Redstone core instruction
     * <p>
     * This represents a core instruction
     * located in a instruction set and
     * called with the corresponding
     * <code>opcode</code>
     *
     * @param <T> Core tick
     */
    public static interface CoreInstruction<T> {
        /**
         * Executes the instruction
         *
         * @param tick Processor pulse tick
         */
        public void execute(T tick);
    }
}
