package ru.job4j.pool;

import ru.job4j.producerconsumer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private static final int SIZE = Runtime.getRuntime().availableProcessors();

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(SIZE);

    public ThreadPool() {
        for (int i = 0; i < SIZE; i++) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Runnable poll = tasks.poll();
                    Thread thread = new Thread(poll);
                    threads.add(thread);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool();

        for (int i = 0; i < 5; i += 1) {
            try {
                pool.work((Runnable) new Job(i));
                System.out.println(new Job(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        pool.shutdown();
    }
}
