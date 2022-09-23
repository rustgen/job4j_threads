package ru.job4j;

import java.util.concurrent.atomic.AtomicReference;

public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int oldNum;
        int newNum;
        do {
            oldNum = count.get();
            newNum = oldNum++;
        } while (!count.compareAndSet(oldNum, newNum));
        throw new UnsupportedOperationException("Count is not impl.");
    }

    public int get() {
        return count.get();
    }
}
