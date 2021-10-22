package com.katiearose;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test()
public class BenfordsLawTest {
    CollatzRand cr = new CollatzRand();
    int count = 1000000;
    double marginOfError = 0.2;
    public void testIntegers() {
        int[] distribution = new int[9];
        for (int i = count; i > 0; i--) {
            int value = Math.abs(cr.nextInt());
            int digit = Integer.parseInt(Integer.toString(value).substring(0, 1));
            distribution[digit - 1]++;
        }
        for (int i : distribution) {
            double percentage = (double) i / (double) count * 100.0;
            Assert.assertTrue(11 - marginOfError <= percentage && percentage <= 11 + marginOfError);
        }
    }
    public void testLongs() {
        int[] distribution = new int[9];
        for (int i = count; i > 0; i--) {
            long value = Math.abs(cr.nextLong());
            int digit = Integer.parseInt(Long.toString(value).substring(0, 1));
            distribution[digit - 1]++;
        }
        for (int i : distribution) {
            double percentage = (double) i / (double) count * 100.0;
            Assert.assertTrue(11 - marginOfError <= percentage && percentage <= 11 + marginOfError);
        }
    }
    public void testDoubles() {
        int[] distribution = new int[9];
        for (int i = count; i > 0; i--) {
            double value = Math.abs(cr.nextDouble());
            int digit = Integer.parseInt(Double.toString(value).substring(0, 1));
            distribution[digit - 1]++;
        }
        for (int i : distribution) {
            double percentage = (double) i / (double) count * 100.0;
            Assert.assertTrue(11 - marginOfError <= percentage && percentage <= 11 + marginOfError);
        }
    }
}
