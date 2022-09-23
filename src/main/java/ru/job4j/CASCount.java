package ru.job4j;

import java.util.concurrent.atomic.AtomicReference;

public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int oldNum;
        int newNum;
        do {
            oldNum = count.get();
            newNum = oldNum + 1;
        } while (!count.compareAndSet(oldNum, newNum));
    }

    public int get() {
        return count.get();
    }
}
