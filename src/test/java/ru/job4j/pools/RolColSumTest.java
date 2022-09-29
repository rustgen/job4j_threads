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
}