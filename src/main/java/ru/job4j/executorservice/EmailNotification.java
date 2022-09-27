package ru.job4j.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    /* Creates a pool of threads based on the number of available processors. */
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        pool.submit(
                () -> {
                    String subject = "Notification " + user.getUsername() + " to email " + user.getEmail();
                    String body = "Add a new event to " + user.getUsername();
                    send(subject, body, user.getEmail());
                }
        );
    }

    /* This method closes the pool and waits for all tasks to complete. */
    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }
}
