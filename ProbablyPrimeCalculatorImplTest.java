package ru.ifmo.enf.finyutina.t04;

import junit.framework.TestCase;

/**
 * Created by Angelika Finyutina (charsi.npc@gmail.com) on 2/28/14.
 */
public class ProbablyPrimeCalculatorImplTest extends TestCase {

    public void testNegativeN() {
        ProbablyPrimeCalculatorImpl calculator = new ProbablyPrimeCalculatorImpl(987654321);
        try {
            calculator.isProbablyPrime(-5);
            fail("no exception with negative n");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    public void testZeroN() {
        ProbablyPrimeCalculatorImpl calculator = new ProbablyPrimeCalculatorImpl(987654321);
        try {
            calculator.isProbablyPrime(0);
            fail("no exception with n = 0");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    public void testOneN() {
        ProbablyPrimeCalculatorImpl calculator = new ProbablyPrimeCalculatorImpl(987654321);
        try {
            calculator.isProbablyPrime(1);
            fail("no exception with n = 1");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    public void testEvenN() {
        ProbablyPrimeCalculatorImpl calculator = new ProbablyPrimeCalculatorImpl(987654321);
        try {
            calculator.isProbablyPrime(120);
            fail("no exception with even n");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    public void testPrime1() {
        ProbablyPrimeCalculatorImpl calculator = new ProbablyPrimeCalculatorImpl(987654321);
        assertEquals(true, calculator.isProbablyPrime(1000000009));
    }

    public void testPrime2() {
        ProbablyPrimeCalculatorImpl calculator = new ProbablyPrimeCalculatorImpl(987654321);
        assertEquals(true, calculator.isProbablyPrime(1000000007));
    }

    public void testNotPrime() {
        ProbablyPrimeCalculatorImpl calculator = new ProbablyPrimeCalculatorImpl(987654321);
        assertEquals(false, calculator.isProbablyPrime((long)1000000009 * 1000000007));
    }
}
