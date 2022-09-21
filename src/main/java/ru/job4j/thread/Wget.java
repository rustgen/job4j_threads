package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;

public class Wget implements Runnable {
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
            byte[] dataBuffer = new byte[1048576];
            int bytesRead;
            long startTime = System.currentTimeMillis();
            int downloadData = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1048576)) != -1) {
                System.out.println(bytesRead);
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long interval = System.currentTimeMillis() - startTime;
                    if (interval < 1000) {
                        Thread.sleep(1000 - interval);
                    }
                    downloadData = 0;
                    startTime = System.currentTimeMillis();
                }
                fos.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
        if (args.length < 3) {
            throw new IllegalArgumentException("There are should be 3 parameter for launch application.");
        }
        String url = args[0];
        String file = args[1];
        int speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(url, file, speed));
        wget.start();
        wget.join();
    }
}
