package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000); /* we simulate the execution of a parallel task for 1 second. */
        progress.interrupt();
    }

    @Override
    public void run() {
        char[] process = {'-', '\\', '|', '/'};
        int count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + process[count++]);
            if (count == process.length) {
                count = 0;
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
