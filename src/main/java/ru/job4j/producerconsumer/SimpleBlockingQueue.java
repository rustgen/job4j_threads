package ru.job4j.producerconsumer;

import net.jcip.annotations.GuardedBy;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == limit) {
            wait();
        }
        queue.add(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T rsl = queue.poll();
        notifyAll();
        return rsl;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
