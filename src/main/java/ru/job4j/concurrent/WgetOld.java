package ru.job4j.concurrent;

public class WgetOld {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 101; i++) {
                            System.out.print("\rLoading : " + i  + "%");
                            Thread.sleep(1_000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
    }
}
