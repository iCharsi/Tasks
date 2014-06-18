package ru.ifmo.enf.finyutina.t04;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Angelika Finyutina (charsi.npc@gmail.com) on 2/28/14.
 */
public class ProbablyPrimeCalculatorImpl {

    private final static int ROUNDS = 50;
    private final Random random;

    //taken from stackoverflow, comments from me
    //another solution would be to use (long)(random.nextDouble() * n), but it may lack in precision :)
    long nextLong(Random random, long n) {
        long bits, val;
        do {
            bits = (random.nextLong() << 1) >>> 1; //removing the sign bit
            val = bits % n; //taking number in range [0..n - 1]
        } while (bits - val + (n - 1) < 0L); //rejecting numbers that would result in an uneven distribution (due to the fact that 2^31 is not divisible by n)
        return val;
    }

    public boolean isProbablyPrime(long n) {

        if (n <= 1) {
            throw new IllegalArgumentException();
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }

        //n - 1 = 2^s * t
        int s = 0;
        long t = n - 1;
        while (t % 2 == 0) {
            ++s;
            t /= 2;
        }

        for (int round = 0; round < ROUNDS; ++round) {
            long a = 2 + nextLong(random, n - 3); //a in [2, n - 2]
            long x = BigInteger.valueOf(a).modPow(BigInteger.valueOf(t), BigInteger.valueOf(n)).longValue(); //x = (a^t) % n
            if (x == 1 || x == n - 1) {
                continue;
            }
            boolean notPrime = true;
            for (int i = 0; i < s - 1; ++i) {
                x = BigInteger.valueOf(x).multiply(BigInteger.valueOf(x)).mod(BigInteger.valueOf(n)).longValue(); //x = (x^2) % n
                if (x == 1) {
                    return false;
                }
                if (x == n - 1) {
                    notPrime = false;
                    break;
                }
            }
            if (notPrime) {
                return false;
            }
        }
        return true;
    }

    public ProbablyPrimeCalculatorImpl(long randomSeed) {
        random = new Random(randomSeed);
    }
}
