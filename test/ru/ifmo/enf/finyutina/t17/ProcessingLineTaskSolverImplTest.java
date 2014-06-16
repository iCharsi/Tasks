package ru.ifmo.enf.finyutina.t17;

import javafx.util.Pair;
import junit.framework.TestCase;
import ru.ifmo.enf.finyutina.t17.ProcessingLineTaskSolver.*;
import ru.ifmo.enf.finyutina.t17.ProcessingLineTaskSolverImpl.*;

import java.util.List;

/**
 * Created by Angelika Finyutina (charsi.npc@gmail.com) on 5/24/14.
 */
public class ProcessingLineTaskSolverImplTest extends TestCase {

    public Pair<Transfer, Transfer> getProcessingLinesFromArrays(int[][] processingCosts, int[][] transferCosts) {
        final int n = processingCosts[0].length;
        Worktable first = new WorktableImpl(processingCosts[0][n - 1], Line.FIRST, null, null);
        Worktable second = new WorktableImpl(processingCosts[1][n - 1], Line.SECOND, null, null);
        for (int i = n - 2; i >= 0; --i) {
            Transfer firstToFirstTransfer = new TransferImpl(null, first, 0);
            Transfer firstToSecondTransfer = new TransferImpl(null, second, transferCosts[1][i + 1]);
            Transfer secondToFirstTransfer = new TransferImpl(null, first, transferCosts[0][i + 1]);
            Transfer secondToSecondTransfer = new TransferImpl(null, second, 0);

            first = new WorktableImpl(processingCosts[0][i], Line.FIRST, firstToFirstTransfer, firstToSecondTransfer);
            second = new WorktableImpl(processingCosts[1][i], Line.SECOND, secondToFirstTransfer, secondToSecondTransfer);

            ((TransferImpl)firstToFirstTransfer).setFromTable(first);
            ((TransferImpl)firstToSecondTransfer).setFromTable(first);
            ((TransferImpl)secondToFirstTransfer).setFromTable(second);
            ((TransferImpl)secondToSecondTransfer).setFromTable(second);
        }
        return new Pair<Transfer, Transfer>(new TransferImpl(null, first, transferCosts[0][0]),
                new TransferImpl(null, second, transferCosts[1][0]));
    }

    public int getCostFromPath(Transfer toFirstLineTransfer, Transfer toSecondLineTransfer, List<Worktable> path) {

        int cost = 0;
        for (Worktable worktable : path) {
            if (worktable.getLine() == Line.FIRST) {
                cost += toFirstLineTransfer.getTransferCost();
            } else {
                cost += toSecondLineTransfer.getTransferCost();
            }
            cost += worktable.getProcessingCost();
            toFirstLineTransfer = worktable.getTransfer(Line.FIRST);
            toSecondLineTransfer = worktable.getTransfer(Line.SECOND);
        }

        return cost;
    }

    public void testSolver() {

        //test from Bol'shakova's presentation, last 2 transfers are included in the cost of last 2 worktables
        final int[][] processingCosts = new int[][] {{7, 9, 3, 4, 8, 7}, {8, 5, 6, 4, 5, 9}};
        final int[][] transferCosts = new int[][] {{2, 2, 1, 2, 2, 1}, {4, 2, 3, 1, 3, 4}};

        Pair<Transfer, Transfer> transfers = getProcessingLinesFromArrays(processingCosts, transferCosts);

        ProcessingLineTaskSolver solver = new ProcessingLineTaskSolverImpl();
        Result result = solver.getOptimalProcessingCost(transfers.getKey(), transfers.getValue());

        assertEquals(38, result.getTotalCost());
        assertEquals(38, getCostFromPath(transfers.getKey(), transfers.getValue(), result.getOptimalSolution()));
    }

    public void testNullTransfers() {
        try {
            new ProcessingLineTaskSolverImpl().getOptimalProcessingCost(null, null);
            fail("fail");
        } catch (NullPointerException Ex) {
            //OK
        }
    }
}
