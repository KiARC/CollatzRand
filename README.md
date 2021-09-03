# CollatzRand
 The proof of concept for my Collatz Conjecture based PRNG. It takes advantage of the geometric Brownian motion of the sequence for a given seed and uses a small enough step count that the overall trend is irrelevant (i.e. no 4,2,1 loops).
<br>
# Features:
- Avoids 4,2,1 loops and geometric trend
- Avoids Benford's Law