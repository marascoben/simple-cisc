package components;

import java.util.List;
import java.util.logging.Logger;
import java.util.ArrayList;

import core.GeneralRegister;
import core.IndexRegister;
import core.Instruction;
import util.WordUtils;

public class Processor {

    private static final Logger LOG = Logger.getLogger(Processor.class.getName());

    // General Purpose Registers
    public char R0 = 0, R1 = 0, R2 = 0, R3 = 0;

    // Index Registers
    public char X1 = 0, X2 = 0, X3 = 0;

    // Memory Address, Memory Buffer Registers
    public char MAR = 0, MBR = 0;

    // Program Counter
    public char PC = 0;

    // Instruction Register
    private char IR = 0;

    // Memory Fault Register
    private byte MFR = 0;

    // Condition Code
    private byte CC = 0;

    // Memory Object
    private Memory memory;

    public Processor(Memory memory) {
        this.memory = memory;
    }

    public void execute(char word) {
        logInstruction(word);

        GeneralRegister r = GeneralRegister.fromWord(word);
        LOG.info(r.name());

        switch (Instruction.fromWord(word)) {
            case HLT:
                halt();
                break;
            case LDR:
                loadFromMemory(r, effectiveAddress(word));
                break;
            case STR:
                storeToMemory(GeneralRegister.fromWord(word), effectiveAddress(word));
                break;
            case LDA:
                loadAddress(GeneralRegister.fromWord(word), effectiveAddress(word));
                break;
            case LDX:
                loadIndexFromMemory(IndexRegister.fromWord(word), effectiveAddress(word));
                break;
            case STX:
                storeIndexToMemory(IndexRegister.fromWord(word), effectiveAddress(word));
                break;
            case TRP:
                break;
        }
    }

    /**
     * Given a 16-bit word, return the effective address from the word.
     * 
     * @param word The word to read from.
     * @return The effective address from the word.
     */
    public char effectiveAddress(char word) {
        IndexRegister ix = IndexRegister.fromWord(word);

        byte address = WordUtils.getAddress(word);

        if (Instruction.isIndirectAddressing(word)) {
            // Indirect addressing but NO indexing
            LOG.info("Computing effective address with indirect addressing");
            switch (ix) {
                case IX1:
                    return memory.read((char) (X1 + WordUtils.getAddress(word)));
                case IX2:
                    return memory.read((char) (X2 + WordUtils.getAddress(word)));
                case IX3:
                    return memory.read((char) (X3 + WordUtils.getAddress(word)));
                default:
                    return memory.read((char) WordUtils.getAddress(word));
            }
        } else {
            // NO indirect addressing
            LOG.info("Computing effective address without indirect addressing for address " + String.format("%5s", Integer.toBinaryString(address)).replace(' ', '0'));
            switch (ix) {
                case IX1:
                    return (char) (X1 + WordUtils.getAddress(word));
                case IX2:
                    return (char) (X2 + WordUtils.getAddress(word));
                case IX3:
                    return (char) (X3 + WordUtils.getAddress(word));
                default:
                    return (char) WordUtils.getAddress(word);
            }
        }
    }

    public char getIR() {
        return IR;
    }

    public char getMFR() {
        return (char) MFR;
    }

    public char getCC() {
        return (char) CC;
    }

    protected void halt() {
    }

    protected void loadFromMemory(GeneralRegister r, char address) {
        LOG.info("Loading to register " + r + " from address " + String.format("0x%08X", (short) address));

        switch (r) {
            case GPR0:
                R0 = memory.read(address);
                break;
            case GPR1:
                R1 = memory.read(address);
                break;
            case GPR2:
                R2 = memory.read(address);
                break;
            case GPR3:
                R3 = memory.read(address);
                break;
        }
    }

    protected void storeToMemory(GeneralRegister r, char address) {
        switch (r) {
            case GPR0:
                memory.write(address, R0);
                break;
            case GPR1:
                memory.write(address, R1);
                break;
            case GPR2:
                memory.write(address, R2);
                break;
            case GPR3:
                memory.write(address, R3);
                break;
        }
    }

    protected void loadAddress(GeneralRegister r, char address) {
        switch (r) {
            case GPR0:
                R0 = address;
                break;
            case GPR1:
                R1 = address;
                break;
            case GPR2:
                R2 = address;
                break;
            case GPR3:
                R3 = address;
                break;
        }
    }

    protected void loadIndexFromMemory(IndexRegister ix, char address) {
        switch (ix) {
            case IX1:
                X1 = memory.read(address);
                break;
            case IX2:
                X2 = memory.read(address);
                break;
            case IX3:
                X3 = memory.read(address);
                break;
            default:
                break;
        }
    }

    protected void storeIndexToMemory(IndexRegister ix, char address) {
        switch (ix) {
            case IX1:
                memory.write(address, X1);
                break;
            case IX2:
                memory.write(address, X2);
                break;
            case IX3:
                memory.write(address, X3);
                break;
            default:
                break;
        }
    }

    private void logInstruction(char word){
        GeneralRegister r = GeneralRegister.fromWord(word);
        IndexRegister ix = IndexRegister.fromWord(word);
        char address = effectiveAddress(word);

        LOG.info("Running instruction: " + Instruction.fromWord(word) + " " + r + " " + ix + " " + String.format("0x%08X", (short) address));
    }
}
