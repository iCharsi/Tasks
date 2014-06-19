package ru.ifmo.enf.finyutina.t04;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by Angelika Finyutina (charsi.npc@gmail.com) on 2/28/14.
 */
public class ProbablyPrimeCalculatorImplTest extends TestCase {

    public void testNegativeN() {
        ProbablyPrimeCalculator calculator = new ProbablyPrimeCalculatorImpl(new Random(987654321));
        assertEquals(false, calculator.isProbablyPrime(-5));
    }

    public void testZeroN() {
        ProbablyPrimeCalculator calculator = new ProbablyPrimeCalculatorImpl(new Random(987654321));
        assertEquals(false, calculator.isProbablyPrime(0));
    }

    public void testOneN() {
        ProbablyPrimeCalculator calculator = new ProbablyPrimeCalculatorImpl(new Random(987654321));
        try {
            calculator.isProbablyPrime(1);
            fail("no exception with n = 1");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    public void testTwoN() {
        ProbablyPrimeCalculator calculator = new ProbablyPrimeCalculatorImpl(new Random(987654321));
        assertEquals(true, calculator.isProbablyPrime(2));
    }

    public void testThreeN() {
        ProbablyPrimeCalculator calculator = new ProbablyPrimeCalculatorImpl(new Random(987654321));
        assertEquals(true, calculator.isProbablyPrime(3));
    }

    public void testEvenN() {
        ProbablyPrimeCalculator calculator = new ProbablyPrimeCalculatorImpl(new Random(987654321));
        assertEquals(false, calculator.isProbablyPrime(10));
    }

    public void testPrime1() {
        ProbablyPrimeCalculator calculator = new ProbablyPrimeCalculatorImpl(new Random(987654321));
        assertEquals(true, calculator.isProbablyPrime(1000000009));
    }

    public void testPrime2() {
        ProbablyPrimeCalculator calculator = new ProbablyPrimeCalculatorImpl(new Random(987654321));
        assertEquals(true, calculator.isProbablyPrime(1000000007));
    }

    public void testNotPrime() {
        ProbablyPrimeCalculator calculator = new ProbablyPrimeCalculatorImpl(new Random(987654321));
        assertEquals(false, calculator.isProbablyPrime((long) 1000000009 * 1000000007));
    }

    public void testMinValueRandom() {
        Random mockRandom = new Random() {
            @Override
            public long nextLong() {
                return Long.MIN_VALUE;
            }
        };

        ProbablyPrimeCalculator calculator = new ProbablyPrimeCalculatorImpl(mockRandom);

        //with mocked random we can only be sure that the test will return true if the prime number is given
        assertEquals(true, calculator.isProbablyPrime(13));
        assertEquals(true, calculator.isProbablyPrime(17));
        assertEquals(true, calculator.isProbablyPrime(19));
        assertEquals(true, calculator.isProbablyPrime(23));
        assertEquals(true, calculator.isProbablyPrime(29));
        assertEquals(true, calculator.isProbablyPrime(31));
        assertEquals(true, calculator.isProbablyPrime(37));
        assertEquals(true, calculator.isProbablyPrime(41));
        assertEquals(true, calculator.isProbablyPrime(43));
        assertEquals(true, calculator.isProbablyPrime(47));
        assertEquals(true, calculator.isProbablyPrime(53));
        assertEquals(true, calculator.isProbablyPrime(59));
        assertEquals(true, calculator.isProbablyPrime(61));
        assertEquals(true, calculator.isProbablyPrime(67));
        assertEquals(true, calculator.isProbablyPrime(1000000007));
        assertEquals(true, calculator.isProbablyPrime(1000000009));
    }
}
