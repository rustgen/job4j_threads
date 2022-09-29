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
        Sums[] res = RowColSum.sum(matrix);
        Sums[] exp = new Sums[] {
                new Sums(3, 4),
                new Sums(7, 6)
        };
        assertThat(res).isEqualTo(exp);
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] res = RowColSum.asyncSum(matrix);
        Sums[] exp = {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };
        assertThat(res).isEqualTo(exp);
    }
}