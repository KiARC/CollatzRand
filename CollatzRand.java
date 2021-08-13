package katierose.collatzrand;

import java.util.concurrent.atomic.AtomicLong;
/**
* Collatz Conjecture based PRNG
* <p>
* A proof of concept PRNG based on the Collatz Conjecture (also known as 3x+1).
 * Should not be used in practice.
*<br><br>
 * Version History:
 * <ul>
 *     <li>v1.0.1 Fixed Benford's Law applying to longs, ints are WIP, uploaded to GitHub</li>
 *     <li>v1.0 - First version of CollatzRand PRNG</li>
 * </ul>
* @author Katherine Rose
* @version 1.0.1
*/
public class CollatzRand {
    private final AtomicLong seed;
    private static final long MULTI = 0x655F50619L;
    private static  final long ADD = 0xDL;
    private static long makeSeedUnique() {
        while (true) {
            long current = makeSeedUnique.get();
            long next = current * 121517396276632881L;
            if (makeSeedUnique.compareAndSet(current, next))
                return next;
        }
    }
    private static final AtomicLong makeSeedUnique = new AtomicLong(7652522734144634L);
    private static long seedScramble(long seed) {
        return (seed ^ MULTI);
    }
    public synchronized void setSeed(long seed) {
        this.seed.set(seedScramble(seed));
    }
    public int nextInt() {
        return (int) nextLong();
    }
    public long nextLong() {
        long oldSeed = 0;
        long nextSeed = 0;
        AtomicLong nlSeed = this.seed;
        while (!nlSeed.compareAndSet(oldSeed, nextSeed)) {
            oldSeed = nlSeed.get();
            nextSeed = (oldSeed * MULTI + ADD);
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
    }
    public CollatzRand() {
        this(makeSeedUnique() ^ System.nanoTime());
    }
    public CollatzRand(long seed) {
        /*Warning: If the same seed is used twice the numbers generated will be the same.
        You should only use this for testing purposes, or if you don't care about security
        or actual randomness.*/
        this.seed = new AtomicLong(seedScramble(seed));
    }
}