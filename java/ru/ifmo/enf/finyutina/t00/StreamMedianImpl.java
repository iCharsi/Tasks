package ru.ifmo.enf.finyutina.stream_median;

import java.util.*;

/**
 * Created by Angelika Finyutina (charsi.npc@gmail.com) on 6/16/14.
 */

public class StreamMedianImpl implements StreamMedian {

    int median;
    PriorityQueue<Integer> right = new PriorityQueue<Integer>();
    PriorityQueue<Integer> left = new PriorityQueue<Integer>(1, Collections.reverseOrder());
    int numberCount = 0;

    public void process(int nextNumber) {

        if (numberCount == 0) {
            median = nextNumber;
        } else if (numberCount % 2 == 0) {
            if (nextNumber <= median) {
                left.add(nextNumber);
            } else {
                right.add(nextNumber);
                left.add(median);
                median = right.poll();
            }
        } else {
            if (nextNumber >= median) {
                right.add(nextNumber);
            } else {
                left.add(nextNumber);
                right.add(median);
                median = left.poll();
            }
        }
        ++numberCount;
    }

    public int getMedian() {

        if (numberCount == 0) {
            throw new IllegalStateException("no numbers yet!");
        }
        return median;
    }
}
