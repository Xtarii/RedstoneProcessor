package org.xtarii.irp.emulators.o_cores;

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
 * @param <V> Instruction size type
 * @param <E> Core tick pulse type
 */
public abstract class RedstoneCore<T, V, E> implements IRedstoneCore<E> {
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
     * Processor pipeline registers
     * <p>
     * The size and amount may vary from
     * implementation to implementation.
     * <p>
     * These registers are used explicitly by
     * the processor in the {@link RedstoneCore#cycle()}
     * to store data between tick processing steps.
     */
    protected final T[] PPR;

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

    /**
     * Core cycle state
     */
    protected State state = State.FETCH;



    /**
     * Constructs a redstone processor core
     *
     * @param gpr General purpose registers
     * @param spr Special purpose registers
     * @param seer State execution exception registers
     * @param ppr Processor pipeline registers
     * @param inst Core instruction set
     */
    public RedstoneCore(T[] gpr, T[] spr, T[] seer, T[] ppr, CoreInstruction<V>[] inst) {
        GPR     = gpr;
        SPR     = spr;
        SEER    = seer;
        PPR     = ppr;
        INST    = inst;
    }

    /**
     * Gets the instruction bits from the processors
     * current instruction processing.
     *
     * @return Instruction bits
     */
    public abstract E getInstruction();





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

    /**
     * Redstone processor cycle state
     */
    public static enum State {
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
