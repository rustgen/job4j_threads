package ru.job4j.pools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFindIndex extends RecursiveTask<Integer> {

    /* The number of cores in the system */
    private static final int SIZE = Runtime.getRuntime().availableProcessors();
    private final int[] array;
    private final int index;

    public ParallelFindIndex(int[] array, int index) {
        this.array = array;
        this.index = index;
    }

    @Override
    protected Integer compute() {
        if (array.length < 10) {
            for (int i = 0; i < array.length; i++) {
                if (index == i) {
                    return i;
                }
            }
        }

        int parts = array.length / SIZE;
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < array.length; i += parts) {
            int[] arr = Arrays.copyOfRange(array, i, Math.min(array.length, i + parts));
            list.add(arr);
        }
        for (int[] ar : list) {
            for (int i = 0; i < ar.length; i++) {
                ParallelFindIndex findIndex = new ParallelFindIndex(ar, index);
                findIndex.fork();
                if (index == i) {
                    findIndex.join();
                    return index;
                }
            }
        }
        return -1;
    }

    public static int find(int[] array, int index) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelFindIndex(array, index));
    }

    public static void main(String[] args) {
        int[] array = new int[150];
        for (int i = 0; i < array.length; i++) {
            i = (int) (Math.random() * 2359909);
        }
        System.out.println(find(array, 2));

    }
}
