package ru.job4j.pool;

import ru.job4j.producerconsumer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    /* The number of cores in the system */
    private static final int SIZE = Runtime.getRuntime().availableProcessors();

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(SIZE);

    public ThreadPool() {
        for (int i = 0; i < SIZE; i++) {
            Thread thread = new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.poll().run();
                            } catch (Exception e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            );
            thread.start();
            threads.add(thread);
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
        for (int i = 0; i < 50; i++) {
            try {
                Job job = new Job(i);
                pool.work(job);
                System.out.println(job);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
        pool.shutdown();
    }
}
