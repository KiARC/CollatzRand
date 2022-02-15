package com.katiearose;

import java.util.Arrays;

public class Tester { // Kinda not a great test but better than nothing
  public static void main(String[] args) {
    CollatzRand c = new CollatzRand();
    long[] j = new long[10];
    for (int i = 0; i < 1000000; i++) {
      j[c.nextInt(10)] += 1;
    }
    System.out.println(Arrays.toString(j));
  }
}
