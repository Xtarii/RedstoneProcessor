package org.xtarii.irp.emulators.cores;

/**
 * Interface class for all redstone cores
 * <p>
 * This provides the core with a set of
 * base methods used when running the
 * processor.
 */
public interface IRedstoneCore {
    /**
     * Preforms a processor cycle tick
     * <p>
     * This updates the processor and
     * its core, state and other parts that may
     * require so. However it does not run a full
     * cycle, rather it runs a cycle tick before
     * stopping and waiting for the next call to
     * {@link #cycle()} at which point it precedes.
     */
    public void cycle();
}
