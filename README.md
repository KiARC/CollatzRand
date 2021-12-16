# CollatzRand
 The proof of concept for my Collatz Conjecture based PRNG. It takes advantage of the geometric Brownian motion of the Collatz sequence for a given seed to generate pseudorandom bits.
# To Use:
Adding libraries differs by IDE, but once you've added it you can import the class using `import com.katiearose.CollatzRand`.
# Version History:
- v1.0.4 - Everything is now bit based
- v1.0.3 - nextInt no longer follows Benford's Law!
- v1.0.2 - Removed useless code and added some documentation, the code is now much clearer
- v1.0.1 - Fixed Benford's Law applying to longs, ints are WIP, uploaded to GitHub
- v1.0 - First version of CollatzRand PRNG