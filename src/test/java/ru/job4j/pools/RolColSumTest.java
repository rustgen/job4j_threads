package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RowColSumTest {

    @Test
    public void whenSerialSum() {
        int[][] matrix = {
                {1, 2},
                {3, 4}
        };
        RowColSum.Sums[] res = RowColSum.sum(matrix);
        assertThat(6).isEqualTo(res[matrix.length - 1].getColSum());
        assertThat(7).isEqualTo(res[matrix.length - 1].getRowSum());
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RowColSum.Sums[] res = RowColSum.asyncSum(matrix);
        assertThat(18).isEqualTo(res[matrix.length - 1].getColSum());
        assertThat(15).isEqualTo(res[matrix.length - 2].getRowSum());
    }

    @Test
    public void whenMatrixAsyncSumNotFasterThanSerialSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        long beforeSerialSum = System.nanoTime();
        RowColSum.sum(matrix);
        long afterSerialSum = System.nanoTime();
        long serialSum = afterSerialSum - beforeSerialSum;

        long beforeAsyncSum = System.nanoTime();
        RowColSum.asyncSum(matrix);
        long afterAsyncSum = System.nanoTime();
        long asyncSum = afterAsyncSum - beforeAsyncSum;

        assertThat(asyncSum).isGreaterThan(serialSum);
    }

    @Test
    public void whenMatrixAsyncSumFasterThanSerialSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[4000][4000];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 12;
            }
        }
        long beforeSerialSum = System.currentTimeMillis();
        RowColSum.sum(matrix);
        long afterSerialSum = System.currentTimeMillis();
        long serialSum = afterSerialSum - beforeSerialSum;

        long beforeAsyncSum = System.currentTimeMillis();
        RowColSum.asyncSum(matrix);
        long afterAsyncSum = System.currentTimeMillis();
        long asyncSum = afterAsyncSum - beforeAsyncSum;

        assertThat(asyncSum).isLessThan(serialSum);
    }
}