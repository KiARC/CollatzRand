package com.katiearose;

import java.util.Arrays;

class Tester { // Kinda not a great test but better than nothing
  public static void main(String[] args) {
    distributionTest();
    stressTest();
  }

  public static void distributionTest() {
    CollatzRand c = new CollatzRand();
    long[] j = new long[10];
    for (int i = 0; i < 1000000; i++) {
      j[c.nextInt(10)] += 1;
    }
    System.out.println("Distribution: " + Arrays.toString(j));
  }

  public static void stressTest() {
    CollatzRand c = new CollatzRand();
    for (int i = 0; i < 100000; i++) {
      c.nextInt();
      c.nextBoolean();
      c.nextByte();
      c.nextDouble();
      c.nextFloat();
      c.nextLong();
      c.nextShort();
    }
    System.out.println("No crashes, nice.");
  }
}
