package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

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

        long beforeAsyncSum = System.nanoTime();
        RowColSum.asyncSum(matrix);
        long afterAsyncSum = System.nanoTime();

        assertThat(afterAsyncSum - beforeAsyncSum).isGreaterThan(afterSerialSum - beforeSerialSum);
    }

    @Test
    public void whenMatrixAsyncSumFasterThanSerialSum() throws ExecutionException, InterruptedException {
        Random random = new Random();
        int[][] matrix = new int[10000][10000];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = random.nextInt(100);
            }
        }
        long beforeSerialSum = System.currentTimeMillis();
        RowColSum.sum(matrix);
        long afterSerialSum = System.currentTimeMillis();

        long beforeAsyncSum = System.currentTimeMillis();
        RowColSum.asyncSum(matrix);
        long afterAsyncSum = System.currentTimeMillis();

        assertThat(afterAsyncSum - beforeAsyncSum).isLessThan(afterSerialSum - beforeSerialSum);
    }
}