package katiearose.CollatzRand;

/**
* Main
* <p>
*  Displays a description when the jar is executed.
*
* @author Katherine Rose
* @version 1.0
*/
public class Main {
    public static void main(String[] args) {
        String doc =
                """
                This JAR file is a library containing Katherine A. Rose's Collatz Conjecture based PRNG (aka CollatzRand).
                It is not meant to be used alone, rather, you should import it into a .java file to use it.
                The source code is available at https://github.com/KiARC/CollatzRand.
                This library is under the GPLv3 license, the terms of which can be found in the GitHub repository listed above.
                """;
        System.out.println(doc);
    }
}