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
        int result;
        if ((to - from) <= 10) {
            for (int i = from; i < to; i++) {
                return object.equals(array[i]) ? i : -1;
            }
        }
        int mid = (from + to) / 2;
        int resultLeft = 0;
        int resultRight = 0;
        ParallelFindIndex<T> leftParallel = new ParallelFindIndex<>(array, object, from, mid);
        ParallelFindIndex<T> rightParallel = new ParallelFindIndex<>(array, object, mid + 1, to);
        for (int i = from; i < mid; i++) {
            if (object.equals(leftParallel.array[i])) {
                resultLeft = i;
                break;
            }
        }
        for (int i = mid; i < to; i++) {
            if (object.equals(rightParallel.array[i])) {
                resultRight = i;
                break;
            }
        }
        leftParallel.fork();
        rightParallel.fork();
        Integer left = leftParallel.join();
        Integer right = rightParallel.join();
        result = Math.max(resultLeft, resultRight);
        return result;
    }

    public Object find(T[] array, T object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool
                .invoke(new ParallelFindIndex<T>(array, object, 0, array.length - 1));
    }
}
