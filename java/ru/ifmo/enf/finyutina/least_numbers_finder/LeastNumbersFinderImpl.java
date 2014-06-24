package ru.ifmo.enf.finyutina.least_numbers_finder;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created by Angelika Finyutina (charsi.npc@gmail.com) on 6/24/14.
 */
public class LeastNumbersFinderImpl implements LeastNumbersFinder {

    public int[] find(int input[], int k) {

        final PriorityQueue<Integer> leastK = new PriorityQueue<Integer>(1, Collections.reverseOrder());

        if (k < 0 || k > input.length) {
            throw new IllegalArgumentException();
        }
        if (k == 0) {
            return new int[0];
        }

        for (int i = 0; i < k; ++i) {
            leastK.add(input[i]);
        }
        for (int i = k; i < input.length; ++i) {
            leastK.add(input[i]);
            leastK.poll();
        }

        int[] result = new int[k];
        for (int i = 0; i < k; ++i) {
            result[k - 1 - i] = leastK.poll();
        }

        return result;
    }
}
