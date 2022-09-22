package ru.job4j.io;

import java.io.*;

public class SaveFile implements SaverFile {

    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    @Override
    public synchronized void saveContent(String content) {
        try (OutputStream o = new FileOutputStream(file);
             BufferedOutputStream out = new BufferedOutputStream(o)) {
            for (int i = 0; i < content.length(); i++) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
