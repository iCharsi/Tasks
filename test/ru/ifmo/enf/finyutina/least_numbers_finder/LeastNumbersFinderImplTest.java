package ru.ifmo.enf.finyutina.least_numbers_finder;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by Angelika Finyutina (charsi.npc@gmail.com) on 6/24/14.
 */
public class LeastNumbersFinderImplTest extends TestCase {

    public void testNegativeK() {
        try {
            new LeastNumbersFinderImpl().find(new int[0], -1);
            fail("works with negative k");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    public void testKGreaterThanN() {
        try {
            new LeastNumbersFinderImpl().find(new int[0], 1);
            fail("works with k > n");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    public void testZeroK() {
        int result[] = new LeastNumbersFinderImpl().find(new int[] {2, 3}, 0);
        assertEquals(0, result.length);
    }

    public void testKEqualsN() {
        int result[] = new LeastNumbersFinderImpl().find(new int[] {1, 2, 1, 1}, 4);
        assertTrue(Arrays.equals(new int[] {1, 1, 1, 2}, result));
    }

    public void testMultipleCalls() {
        LeastNumbersFinder finder = new LeastNumbersFinderImpl();
        assertTrue(Arrays.equals(new int[] {1, 2}, finder.find(new int[] {1, 2, 3}, 2)));
        assertTrue(Arrays.equals(new int[] {3, 4}, finder.find(new int[] {5, 4, 3}, 2)));
        assertTrue(Arrays.equals(new int[] {1, 2}, finder.find(new int[] {3, 2, 1}, 2)));
    }

    public void testFindTheSmallestOne() {
        int result[] = new LeastNumbersFinderImpl().find(new int[] {4, 3, 2, 1, 2, 3, 4}, 1);
        assertTrue(Arrays.equals(new int[]{1}, result));
    }

    public void testLeastNumbersFinder() {
        int result[] = new LeastNumbersFinderImpl().find(new int[] {9, 8, 7, 1, 2, 3, 2, 5, 5}, 5);
        assertTrue(Arrays.equals(new int[] {1, 2, 2, 3, 5}, result));
    }
}
