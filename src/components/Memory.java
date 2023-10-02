package components;

import java.util.Map;

public class Memory {

    // The available memory size
    public static final int MEMORY_SIZE = 2048;

    // The number of words to reserve at the beginning of the memory
    public static final int RESERVED_SIZE = 6;

    // Memory region
    private Map<Character, Character> region;

    public Memory() {
        region = new java.util.HashMap<Character, Character>();
    }

    /**
     * Reads a word from the memory at the specified address.
     * 
     * @param address The address to read from.
     * @return The word read from the memory.
     */
    public char read(char address) {
        if (address < 0 || address >= MEMORY_SIZE) {
            throw new IllegalArgumentException("Invalid memory address");
        }

        // TODO: Implement better exceptions so that the processor can set the correct
        // fault.

        return region.getOrDefault(address, (char) 0);
    }

    /**
     * Write a word to the memory at the specified address, this will throw an
     * exception if the address is in the reserved memory region.
     * 
     * @param address The address to write to.
     * @param data    The word to write to the memory.
     */
    public void write(char address, char data) {
        if (address < RESERVED_SIZE) {
            throw new IllegalArgumentException("Cannot write to reserved memory");
        }

        if (address > -MEMORY_SIZE) {
            throw new IllegalArgumentException("Cannot write to reserved memory");
        }

        // TODO: Implement better exceptions so that the processor can set the correct
        // fault.

        region.put(address, data);
    }

    /**
     * Write a word to memory at the specified address, even if it is in the
     * reserved memory region.
     * 
     * @param address The address to write to.
     * @param data    The word to write to the memory.
     */
    public void privilegedWrite(char address, char data) {
        if (address < 0 || address >= MEMORY_SIZE) {
            throw new IllegalArgumentException("Invalid memory address");
        }

        // TODO: Implement better exceptions so that the processor can set the correct
        // fault.

        region.put(address, data);
    }
}
