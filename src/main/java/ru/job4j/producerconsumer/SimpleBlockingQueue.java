package ru.job4j.producerconsumer;

import net.jcip.annotations.GuardedBy;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public void offer(T value) {
        synchronized (this) {
            while (queue.size() == limit) {
                try {
                    /* Blocking queue is full, wait until space will be free */
                    wait();
                    if (queue.size() == 0) {
                        /* Blocking queue is empty, notify others */
                        notifyAll();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            queue.add(value);
        }
    }

    public T poll() {
        synchronized (this) {
            while (queue.size() == 0) {
                try {
                    /* Blocking queue is empty, waiting for smth to be put */
                    wait();
                    if (queue.size() == limit) {
                        /* Blocking queue is full, notify others */
                        notifyAll();
                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
            return queue.poll();
        }
    }
}
