package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFindIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T object;
    private final int from;
    private final int to;

    public ParallelFindIndex(T[] array, T object, int from, int to) {
        this.array = array;
        this.object = object;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        int result = -1;
        if ((to - from) <= 10) {
            for (int i = from; i <= to; i++) {
                if (object.equals(array[i])) {
                    result = i;
                    break;
                }
            }
            return result;
        }
        int mid = (from + to) / 2;
        ParallelFindIndex<T> leftParallel = new ParallelFindIndex<>(array, object, from, mid);
        ParallelFindIndex<T> rightParallel = new ParallelFindIndex<>(array, object, mid + 1, to);
        leftParallel.fork();
        rightParallel.fork();
        return Math.max(leftParallel.join(), rightParallel.join());
    }

    public Integer find(T[] array, T object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool
                .invoke(new ParallelFindIndex<T>(array, object, 0, array.length - 1));
    }
}
