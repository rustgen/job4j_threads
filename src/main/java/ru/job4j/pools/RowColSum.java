package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] array = new Sums[matrix.length];
        Sums sums = new Sums();
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            array[i] = new Sums(rowSum, colSum);
            rowSum = 0;
            colSum = 0;
        }
        return array;
    }
    
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] array = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(matrix, i));
        }
        for (Integer key : futures.keySet()) {
            array[key] = futures.get(key).get();
        }
        return array;
    }

    /**
     * @param matrix - matrix with values
     * @param line - line(row) of matrix
     * @return - CompletableFuture<Sums> for next use in main thread (extract data from line of matrix and write sum)
     */
    public static CompletableFuture<Sums> getTask(int[][] matrix, int line) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
            int rowSum = 0;
            int colSum = 0;
            for (int i = 0; i < matrix.length; i++) {
                rowSum += matrix[line][i];
                colSum += matrix[i][line];
                sums.setRowSum(rowSum);
                sums.setColSum(colSum);
            }
            return sums;
        });
    }
}
