package katierose.rand;

import java.util.concurrent.atomic.AtomicLong;
/**
* Collatz Conjecture based PRNG
* <p>
* A proof of concept PRNG based on the Collatz Conjecture (also known as 3x+1).
 * <br>
 * <s>nextInt should not be used in practice as it follows Benford's Law, nextLong is safe.</s> Both methods appear safe to use!
*<br><br>
 * Version History:
 * <ul>
 *     <li>v1.0.3 - nextInt no longer follows Benford's Law!</li>
 *     <li>v1.0.2 - Removed useless code and added some documentation, the code is now much clearer</li>
 *     <li>v1.0.1 - Fixed Benford's Law applying to longs, ints are WIP, uploaded to GitHub</li>
 *     <li>v1.0 - First version of CollatzRand PRNG</li>
 * </ul>
* @author Katherine Rose
* @version 1.0.3
*/
public class CollatzRand {
    private final AtomicLong seed;
    private static final long MODIFIER = 0x655F50619L;
    /**
    * Next Integer
    * <p>
    * Generates a long, uses the magic of math to turn it into an int, then checks for leading zeros and tries again if there are any.
     * <br>
     * <s>Unfortunately for whatever reason it obeys Benford's Law, I genuinely don't know why.</s> It's fixed!
     * <br>
     * <s>I will be fixing that in a later version.</s>
    *
    * @author Katherine Rose
    * @return A long generated by CollatzRand's long generator, converted to an int via a stupid process that preserves disobedience of Benford's Law.
    */
    public int nextInt() {
        int num = Integer.parseInt(String.valueOf((nextLong() / 100000000)));
        if (Integer.parseInt(Integer.toString(num).substring(0, 1)) == 0) {
            return nextInt();
        } else {
            return num;
        }
    }
    /**
    * Next Double
    * <p>
    * Generates two longs and puts them together with a "." in between.
     * <br>
     * Yes it is as stupid as it sounds but it works fine.
    *
    * @author Katherine Rose
    * @return A double generated by putting together two longs.
    */
    public double nextDouble() {
        return Double.parseDouble(nextLong() + "." + nextLong());
    }
    /**
    * Next Long
    * <p>
    * Uses the algorithm from the Collatz Conjecture to generate a pseudorandom long.
     * <br>
     * As of version 1.0.1 this method does not obey Benford's law and is now harder to predict.
    *
    * @author Katherine Rose
    * @return A long generated using the algorithm from the Collatz Conjecture.
    */
    public long nextLong() {
        long oldSeed = 0;
        long nextSeed = 0;
        AtomicLong nlSeed = this.seed;
        while (!nlSeed.compareAndSet(oldSeed, nextSeed)) {
            oldSeed = nlSeed.get();
            nextSeed = (oldSeed * MODIFIER);
        }
        long workingNum = nextSeed;
        long steps = workingNum % 20;
        while (steps > 0) {
            if ((workingNum & 1) == 1) {
                workingNum *= 3;
                workingNum++;
            } else {
                workingNum /= 2;
            }
            steps--;
        }
        workingNum = Long.parseLong(Long.toString(workingNum).substring(3)); //Defeats Benford's law.
        return workingNum;
    } //TODO: Find way to generate larger numbers effectively
                                // TODO: Find a way to generate smaller numbers effectively
                                // Specifically bytes, if bytes can be solved I can rewrite
                                // the other methods to concatenate bytes rather than shrinking or expanding longs
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
}
