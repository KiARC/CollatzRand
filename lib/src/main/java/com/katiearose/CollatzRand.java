package com.katiearose;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

/**
* Collatz Conjecture based PRNG
* <p>
* A proof of concept PRNG based on the Collatz Conjecture (also known as 3x+1).
* @author Katherine Rose
* @version 1.0.4
*/
public class CollatzRand {
    private final AtomicLong seed;
    private static final long MODIFIER = 0x655F50619L;
    private final LinkedList<Boolean> bits = new LinkedList<>();
    //Constructors
    /**
    * Constructor (no given seed)
    * <p>
    * The default constructor, generates a seed on it's own.
    *
    * @author Katherine Rose
    */
    public CollatzRand() {
        this(-9221113093122886310L ^ System.nanoTime());
    }
    /**
    * Constructor (with user provided seed)
    * <p>
    * An alternate constructor that uses a user provided seed.
    *
    * @author Katherine Rose
    * @param seed   The seed used by this instance of the generator.
    */
    public CollatzRand(long seed) {
        this.seed = new AtomicLong(seed);
    }
    //Private methods
    /**
    * Generates bits to add to the queue, which can then be used to assemble pseudorandom values.
    *
    * @author Katherine Rose
    */
    private void regenerate() {
        long oldSeed = 0;
        long nextSeed = 0;
        AtomicLong nlSeed = this.seed;
        while (! nlSeed.compareAndSet(oldSeed, nextSeed)) {
            oldSeed = nlSeed.get();
            nextSeed = (oldSeed * MODIFIER);
        }
        long workingNum = nextSeed;
        while (workingNum > 4) { //Avoids the 4,2,1 loop that would result in every seed adding 001 to the end of the queue
            if ((workingNum & 1) == 1) {
                workingNum *= 3;
                workingNum++;
            } else {
                workingNum /= 2;
            }
            bits.push((workingNum & 1) == 1); //Gets the first bit from the number which essentially checks if it's even or odd; faster than (workingNum % 2) == 0 apparently
        }
    }
    /**
    * Gets the next {@code bitCount} bits from the queue and returns a {@linkplain BitSet} of them
    * @author Katherine Rose
    * @param bitCount the number of bits needed
    * @return a {@linkplain BitSet} of the requested bits
    */
    private BitSet next(int bitCount) {
        while (bits.size() < bitCount) regenerate(); //Make sure there are enough bits available in the queue
        BitSet b = new BitSet(bitCount);
        for (int i = 0; i < bitCount; i++) {
            if (Boolean.TRUE.equals(bits.pop())) b.flip(i);
        }
        return b;
    }
    //Public methods
    /**
     * Returns the next bit from {@link CollatzRand#next} as a boolean
     * @author Katherine Rose
     * @return a boolean
     */
    public boolean nextBoolean() {
        return next(1).get(0);
    }
    /**
     * Assembles a {@linkplain BitSet} of length 8 from {@link CollatzRand#next} into a byte
     * @author Katherine Rose
     * @return a byte
     */
    public byte nextByte() {
        BitSet b = next(8);
        byte n = 0;
        for (int i = b.nextSetBit(0); i >= 0; i = b.nextSetBit(i+1)) {
            n |= (1 << i);
        }
        return n;
    }
    /**
    * Assembles a {@linkplain BitSet} of length 16 from {@link CollatzRand#next} into a short
    * @author Katherine Rose
    * @return a short
    */
    public short nextShort() {
        BitSet b = next(16);
        short n = 0;
        for (int i = b.nextSetBit(0); i >= 0; i = b.nextSetBit(i+1)) {
            n |= (1 << i);
        }
        return n;
    }
    /**
     * Assembles a {@linkplain BitSet} of length 32 from {@link CollatzRand#next} into an int
     * @author Katherine Rose
     * @return an int
     */
    public int nextInt() {
        BitSet b = next(32);
        int n = 0;
        for (int i = b.nextSetBit(0); i >= 0; i = b.nextSetBit(i+1)) {
            n |= (1 << i);
        }
        return n;
    }
    /**
     * Assembles a {@linkplain BitSet} of length 64 from {@link CollatzRand#next} into a long
     * @author Katherine Rose
     * @return a long
     */
    public long nextLong() {
        BitSet b = next(64);
        long n = 0L;
        for (int i = b.nextSetBit(0); i >= 0; i = b.nextSetBit(i+1)) {
            n |= (1L << i);
        }
        return n;
    }
    /**
     * Assembles a {@linkplain BitSet} of length 32 from {@link CollatzRand#next} into a float
     * <p>
     * Tends to return <i>extremely</i> small numbers, will be fixed in a future version
     * @author Katherine Rose
     * @return a float
     */
    public float nextFloat() { //Tends to generate REALLY small numbers
        return ByteBuffer.wrap(next(32).toByteArray()).getFloat();
    }
    /**
     * Assembles a {@linkplain BitSet} of length 64 from {@link CollatzRand#next} into a double
     * <p>
     * Tends to return <i>extremely</i> small numbers, will be fixed in a future version
     * @author Katherine Rose
     * @return a double
     */
    public double nextDouble() { //Tends to generate REALLY small numbers
        return ByteBuffer.wrap(next(64).toByteArray()).getDouble();
    }
}