package com.katiearose;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Collatz Conjecture based PRNG
 *
 * <p>A proof of concept PRNG based on the Collatz Conjecture (also known as 3x+1).
 *
 * @author Katherine Rose
 * @version 1.0.4
 */
public class CollatzRand {
  private final AtomicLong seed;
  private static final long MODIFIER = 0x655F50619L;
  private final LinkedBlockingQueue<Boolean> bits = new LinkedBlockingQueue<>();
  // Constructors
  /**
   * Constructor (no given seed)
   *
   * <p>The default constructor, generates a seed on it's own.
   *
   * @author Katherine Rose
   */
  public CollatzRand() {
    this(-9221113093122886310L ^ System.nanoTime());
  }
  /**
   * Constructor (with user provided seed)
   *
   * <p>An alternate constructor that uses a user provided seed.
   *
   * @author Katherine Rose
   * @param seed The seed used by this instance of the generator.
   */
  public CollatzRand(long seed) {
    this.seed = new AtomicLong(seed);
  }
  // Private methods
  /**
   * Generates bits to add to the queue, which can then be used to assemble pseudorandom values.
   *
   * @author Katherine Rose
   */
  private void regenerate() {
    long oldSeed = 0;
    long nextSeed = 0;
    while (!this.seed.compareAndSet(oldSeed, nextSeed)) {
      oldSeed = this.seed.get();
      nextSeed = (oldSeed * MODIFIER);
    }
    long workingNum = nextSeed;
    LinkedList<Long> sequence = new LinkedList<>();
    while (workingNum > 1) {
      sequence.push(workingNum);
      if ((workingNum & 1) == 1) { // Collatz Transform
        workingNum *= 3;
        workingNum++;
      } else workingNum /= 2;
    }
    for (int i = 0;
        i < sequence.size();
        i++) { // Removes the first even number after every odd number
      // According to Google, the Collatz Sequence for any number has a 2:1 ratio of even to
      // odd
      // numbers
      if ((sequence.get(i) & 1) == 1) {
        boolean done = false;
        int offset = 1;
        while (!done) {
          if (i + offset >= sequence.size()) done = true;
          else {
            if ((sequence.get(i + offset) & 1) != 1) {
              sequence.remove(i + offset);
              done = true;
            }
          }
          offset++;
        }
      }
    }
    while (!sequence.isEmpty()) {
      try {
        bits.put((sequence.poll() & 1) == 1);
      } catch (InterruptedException e) {
        e.printStackTrace();
        Thread.currentThread().interrupt();
      }
    }
  }
  /**
   * Gets the next {@code bitCount} bits from the queue and returns an {@code int} from them
   *
   * @author Katherine Rose
   * @param bitCount the number of bits needed
   * @return an {@code int} from the requested bits
   */
  private int next(int bitCount) {
    // Make sure there are enough bits available in the queue
    while (bits.size() < 48 + bitCount) regenerate();
    BitSet b = new BitSet(48);
    for (int i = 0; i < 48; i++) if (Boolean.TRUE.equals(bits.poll())) b.flip(i);
    long n = 0L;
    for (int i = b.nextSetBit(0); i >= 0; i = b.nextSetBit(i + 1)) n |= (1L << i);
    return (int) n >>> (48 - bitCount);
  }
  // Public methods
  /**
   * Returns the next bit from {@link CollatzRand#next} as a {@code boolean}
   *
   * @author Katherine Rose
   * @return a boolean
   */
  public boolean nextBoolean() {
    return next(1) != 0;
  }
  /**
   * Returns a {@code byte} made from 8 bits from the PRNG
   *
   * @author Katherine Rose
   * @return a byte
   */
  public byte nextByte() {
    return (byte) next(8);
  }
  /**
   * Returns a {@code short} made from 16 bits from the PRNG
   *
   * @author Katherine Rose
   * @return a short
   */
  public short nextShort() {
    return (short) next(16);
  }
  /**
   * Same as {@link #nextShort()}, except it limits the output to the range 0 to limit-1
   *
   * @author Katherine Rose
   * @return a short
   * @param limit the max allowable value
   */
  public int nextShort(int limit) {
    return Math.abs(nextShort()) % limit;
  }
  /**
   * Returns an {@code int} made from 32 bits from the PRNG
   *
   * @author Katherine Rose
   * @return an int
   */
  public int nextInt() {
    return next(32);
  }
  /**
   * Same as {@link #nextInt()}, except it limits the output to the range 0 to limit-1
   *
   * @author Katherine Rose
   * @return an int
   * @param limit the max allowable value
   */
  public int nextInt(int limit) {
    return Math.abs(nextInt()) % limit;
  }
  /**
   * Returns a {@code long} made from two sets of 32 bits from the PRNG, set in two words
   *
   * @author Katherine Rose
   * @return a long
   */
  public long nextLong() {
    return ((long) (next(32)) << 32) + next(32);
  }
  /**
   * Same as {@link #nextLong()}, except it limits the output to the range 0 to limit-1
   *
   * @author Katherine Rose
   * @return a long
   * @param limit the max allowable value
   */
  public long nextLong(long limit) {
    return Math.abs(nextLong()) % limit;
  }
  /**
   * Assembles a {@linkplain BitSet} of length 32 from {@link CollatzRand#next} into a float
   *
   * <p>Tends to return <i>extremely</i> small numbers, will be fixed in a future version
   *
   * @author Katherine Rose
   * @return a float
   */
  public float nextFloat() { // Tends to generate REALLY small numbers
    return next(24) / ((float) (1 << 24));
  }
  /**
   * Assembles a {@linkplain BitSet} of length 64 from {@link CollatzRand#next} into a double
   *
   * <p>Tends to return <i>extremely</i> small numbers, will be fixed in a future version
   *
   * @author Katherine Rose
   * @return a double
   */
  public double nextDouble() { // Tends to generate REALLY small numbers
    return (((long) (next(26)) << 27) + next(27)) * (0x1.0p-53);
  }
}
