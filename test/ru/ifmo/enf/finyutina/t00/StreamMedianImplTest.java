package ru.ifmo.enf.finyutina.t00;

import junit.framework.TestCase;

/**
 * Created by Angelika Finyutina (charsi.npc@gmail.com) on 6/16/14.
 */
public class StreamMedianImplTest extends TestCase{

    public void testNoNumbersYet() {
        try {
            new StreamMedianImpl().getMedian();
            fail("works with no numbers");
        } catch (IllegalStateException ex) {
            //OK
        }
    }

    public void testStreamMedian() {
        StreamMedian solver = new StreamMedianImpl();
        solver.process(5); //5
        assertEquals(5, solver.getMedian());
        solver.process(1); //1 5
        assertEquals(1, solver.getMedian());
        solver.process(10); //1 5 10
        assertEquals(5, solver.getMedian());
        solver.process(11); //1 5 10 11
        assertEquals(5, solver.getMedian());
        solver.process(12); //1 5 10 11 12
        assertEquals(10, solver.getMedian());
        solver.process(13); //1 5 10 11 12 13
        assertEquals(10, solver.getMedian());
        solver.process(15); //1 5 10 11 12 13 15
        assertEquals(11, solver.getMedian());
        solver.process(-1); //-1 1 5 10 11 12 13 15
        assertEquals(10, solver.getMedian());
        solver.process(-10); //-10 -1 1 5 10 11 12 13 15
        assertEquals(10, solver.getMedian());
    }
}
