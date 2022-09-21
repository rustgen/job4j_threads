package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;

public class Wget implements Runnable {
    private static final int SIZE_LIMIT = 1024;
    private final String url;
    private final String file;
    private final int speed;

    public Wget(String url, String file, int speed) {
        this.url = url;
        this.file = file;
        this.speed = speed;
    }

    @Override
    public void run() {
        /* Download file */
        if (!isUrlValid(url)) {
            throw new IllegalArgumentException(String.format("URL \"%s\" doesn't exist or doesn't respond", url));
        }
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fos = new FileOutputStream(file)) {
            byte[] dataBuffer = new byte[SIZE_LIMIT];
            int bytesRead;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, SIZE_LIMIT)) != -1) {
                long afterTime = System.currentTimeMillis();
                fos.write(dataBuffer, 0, bytesRead);
                long difference = afterTime - startTime;
                if (afterTime < difference) {
                    Thread.sleep(2000);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public static boolean isUrlValid(String url) {
        try {
            URL obj = new URL(url);
            obj.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        String file = args[1];
        int speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(url, file, speed));
        wget.start();
        wget.join();
    }
}
