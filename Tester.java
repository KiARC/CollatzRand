package katierose.collatzrand;

import java.util.Arrays;

/**
* Tester
* <p>
* A relatively simple benchmark, simply finds the percentage of the sample generation that starts with each digit.
 * Nothing fancy and not a great benchmark, simply shows that the system works and generates a wide range of values.
 * <s>An interesting thing to note is that the numbers generated follow Benford's Law.</s> <s>As of v1.0.1 only ints follow Benford's Law.</s> As of v1.0.3 the PRNG no longer follows Benford's law. This was achieved by using a stupid but somehow effective way of converting longs to ints.
*
* @author Katherine Rose
* @version 1.0
*/
public class Tester {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int[] distribution = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int count = 1000000;
        CollatzRand cr = new CollatzRand();
        System.out.println("Running int test...");
        for (int i = count; i > 0; i--) {
            int value = Math.abs(cr.nextInt());
            int digit = Integer.parseInt(Integer.toString(value).substring(0, 1));
            distribution[digit - 1] = distribution[digit - 1] + 1;
        }
        System.out.println(Arrays.toString(distribution));
        for (int i = 1; i < 10; i++) {
            System.out.println(i + ": " + (double) distribution[i - 1] / (double) count * 100.0 + "%");
        }
        System.out.println("Running long test...");
        Arrays.fill(distribution, 0);
        for (int i = count; i > 0; i--) {
            long value = cr.nextLong();
            int digit = Integer.parseInt(Long.toString(value).substring(0, 1));
            distribution[digit - 1] = distribution[digit - 1] + 1;
        }
        System.out.println(Arrays.toString(distribution));
        for (int i = 1; i < 10; i++) {
            System.out.println(i + ": " + (double) distribution[i - 1] / (double) count * 100.0 + "%");
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Tests run in " + elapsedTime + " milliseconds.");
    }
}