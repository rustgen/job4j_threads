package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000); /* we simulate the execution of a parallel task for 5 seconds. */
        progress.interrupt();
    }

    @Override
    public void run() {
        char[] process = {'-', '\\', '|', '/'};
        int count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + process[count++]);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                /**
                 * if the sleep(), join() or wait() methods are used,
                 * then you need to call an interrupt in the catch block
                 */
                Thread.currentThread().interrupt();
            }
            if (count == process.length) {
                count = 0;
            }
        }
    }
}
