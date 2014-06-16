package ru.ifmo.enf.finyutina.t17;

import java.util.List;

/**
 * Created by Angelika Finyutina (charsi.npc@gmail.com) on 5/24/14.
 */

public interface ProcessingLineTaskSolver {

    /**
     * @param toFirstLineTransfer переход на первый стол первой линии
     * @param toSecondLineTransfer переход на первый стол второй линии
     */
    Result getOptimalProcessingCost(Transfer toFirstLineTransfer,
                                    Transfer toSecondLineTransfer);

    interface Result {
        /**
         * @return последовательность рабочих мест в оптимальном решении
         */
        List<Worktable> getOptimalSolution();

        /**
         * @return общая цена решения
         */
        int getTotalCost();
    }

    /**
     * Рабочее место (элемент конвейера)
     */
    interface Worktable {
        /**
         * @return цена обработки на этом рабочем месте
         */
        int getProcessingCost();

        /**
         * @return линия, на которой работает рабочий стол
         */
        Line getLine();

        /**
         * @return переход на следующий стол
         */
        Transfer getTransfer(Line line);
    }

    /**
     * Факт перехода с одного стола на другой
     */
    interface Transfer {
        Worktable getFromTable();

        Worktable getToTable();

        /**
         * @return стоимость перехода от стола from к столу to
         */
        int getTransferCost();
    }

    /**
     * Номер линии конвейера
     */
    enum Line {
        FIRST, SECOND
    }

}
