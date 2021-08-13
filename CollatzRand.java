package katierose.collatzrand;

import java.util.concurrent.atomic.AtomicLong;
/**
* Collatz Conjecture based PRNG
* <p>
* A proof of concept PRNG based on the Collatz Conjecture (also known as 3x+1).
 * <br>
 * nextInt should not be used in practice as it follows Benford's Law, nextLong is safe.
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
    private static final long MODIFIER = 0x655F50619L;
    public int nextInt() {
        return Math.abs((int) nextLong());
    }
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
    }
    public CollatzRand() {
        this(-9221113093122886310L ^ System.nanoTime());
    }
    public CollatzRand(long seed) {
        this.seed = new AtomicLong(seed);
    }
}