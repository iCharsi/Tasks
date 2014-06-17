package ru.ifmo.enf.finyutina.t17;

import java.util.*;

/**
 * Created by Angelika Finyutina (charsi.npc@gmail.com) on 5/24/14.
 */
public class ProcessingLineTaskSolverImpl implements ProcessingLineTaskSolver {

    private final Map<Line, Vector<Integer>> bestCost = new EnumMap<Line, Vector<Integer>>(Line.class);

    private void getSolution(List<Worktable> solution, int index, Transfer toFirstLine, Transfer toSecondLine) {
        final Worktable first = toFirstLine.getToTable();
        final Worktable second = toSecondLine.getToTable();
        if (index == bestCost.get(Line.FIRST).size() - 1) {
            if (bestCost.get(Line.FIRST).get(index) < bestCost.get(Line.SECOND).get(index)) {
                solution.add(first);
            } else {
                solution.add(second);
            }
            return;
        }
        getSolution(solution, index + 1, first.getTransfer(Line.FIRST), first.getTransfer(Line.SECOND));
        if (bestCost.get(Line.FIRST).get(index) + first.getTransfer(solution.get(0).getLine()).getTransferCost() +
                solution.get(0).getProcessingCost() == bestCost.get(solution.get(0).getLine()).get(index + 1)) {
            solution.add(0, first);
        } else {
            solution.add(0, second);
        }
    }

    public Result getOptimalProcessingCost(Transfer toFirstLineTransfer, Transfer toSecondLineTransfer) {

        bestCost.put(Line.FIRST, new Vector<Integer>());
        bestCost.put(Line.SECOND, new Vector<Integer>());

        Map<Line, Worktable> tables = new EnumMap<Line, Worktable>(Line.class);
        bestCost.get(Line.FIRST).add(toFirstLineTransfer.getTransferCost() + toFirstLineTransfer.getToTable().getProcessingCost());
        bestCost.get(Line.SECOND).add(toSecondLineTransfer.getTransferCost() + toSecondLineTransfer.getToTable().getProcessingCost());
        tables.put(Line.FIRST, toFirstLineTransfer.getToTable());
        tables.put(Line.SECOND, toSecondLineTransfer.getToTable());

        while (tables.get(Line.FIRST).getTransfer(Line.FIRST) != null) {
            if (tables.get(Line.FIRST).getTransfer(Line.SECOND) == null ||
                tables.get(Line.SECOND).getTransfer(Line.FIRST) == null ||
                tables.get(Line.SECOND).getTransfer(Line.SECOND) == null) {
                throw new IllegalArgumentException("Worktable-transfer structure is broken");
            }

            final Map<Line, Worktable> newTables = new EnumMap<Line, Worktable>(Line.class);

            newTables.put(Line.FIRST, tables.get(Line.FIRST).getTransfer(Line.FIRST).getToTable());
            newTables.put(Line.SECOND, tables.get(Line.FIRST).getTransfer(Line.SECOND).getToTable());

            int costFirstToFirst = bestCost.get(Line.FIRST).lastElement() +
                    tables.get(Line.FIRST).getTransfer(Line.FIRST).getTransferCost() + newTables.get(Line.FIRST).getProcessingCost();
            int costSecondToFirst = bestCost.get(Line.SECOND).lastElement() +
                    tables.get(Line.SECOND).getTransfer(Line.FIRST).getTransferCost() + newTables.get(Line.FIRST).getProcessingCost();
            int costFirstToSecond = bestCost.get(Line.FIRST).lastElement() +
                    tables.get(Line.FIRST).getTransfer(Line.SECOND).getTransferCost() + newTables.get(Line.SECOND).getProcessingCost();
            int costSecondToSecond = bestCost.get(Line.SECOND).lastElement() +
                    tables.get(Line.SECOND).getTransfer(Line.SECOND).getTransferCost() + newTables.get(Line.SECOND).getProcessingCost();

            if (costFirstToFirst < costSecondToFirst) {
                bestCost.get(Line.FIRST).add(costFirstToFirst);
            } else {
                bestCost.get(Line.FIRST).add(costSecondToFirst);
            }

            if (costFirstToSecond < costSecondToSecond) {
                bestCost.get(Line.SECOND).add(costFirstToSecond);
            } else {
                bestCost.get(Line.SECOND).add(costSecondToSecond);
            }

            tables = newTables;
        }

        if (tables.get(Line.FIRST).getTransfer(Line.SECOND) != null ||
            tables.get(Line.SECOND).getTransfer(Line.FIRST) != null ||
            tables.get(Line.SECOND).getTransfer(Line.SECOND) != null) {
            throw new IllegalArgumentException("Worktable-transfer structure is broken");
        }

        List<Worktable> optimalSolution = new LinkedList<Worktable>();
        getSolution(optimalSolution, 0, toFirstLineTransfer, toSecondLineTransfer);
        int totalCost = Math.min(bestCost.get(Line.FIRST).lastElement(), bestCost.get(Line.SECOND).lastElement());

        Result result = new ResultImpl(optimalSolution, totalCost);

        bestCost.clear();

        return result;
    }

    private class ResultImpl implements Result {

        private final List<Worktable> optimalSolution;
        private final int totalCost;

        public List<Worktable> getOptimalSolution() {
            return optimalSolution;
        }

        public int getTotalCost() {
            return totalCost;
        }

        public ResultImpl(List<Worktable> optimalSolution, int totalCost) {
            this.optimalSolution = optimalSolution;
            this.totalCost = totalCost;
        }
    }

    public static class WorktableImpl implements Worktable {

        private int processingCost;
        private final Line line;
        private final Map<Line, Transfer> transfer = new EnumMap<Line, Transfer>(Line.class);

        public int getProcessingCost() {
            return processingCost;
        }

        public Line getLine() {
            return line;
        }

        public Transfer getTransfer(Line line) {
            return transfer.get(line);
        }

        public WorktableImpl(int processingCost, Line line, Transfer toFirstLineTransfer, Transfer toSecondLineTransfer) {
            this.processingCost = processingCost;
            this.line = line;
            this.transfer.put(Line.FIRST, toFirstLineTransfer);
            this.transfer.put(Line.SECOND, toSecondLineTransfer);
        }
    }

    public static class TransferImpl implements Transfer {

        private Worktable fromTable;
        private final Worktable toTable;
        private final int transferCost;

        public Worktable getFromTable() {
            return fromTable;
        }

        public Worktable getToTable() {
            return toTable;
        }

        public void setFromTable(Worktable fromTable) {
            this.fromTable = fromTable;
        }

        public int getTransferCost() {
            return transferCost;
        }

        public TransferImpl(Worktable fromTable, Worktable toTable, int transferCost) {
            this.fromTable = fromTable;
            this.toTable = toTable;
            this.transferCost = transferCost;
        }
    }
}
