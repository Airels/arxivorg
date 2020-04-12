package app.arxivorg.controller;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private File file;

    public FileManager(String nameOfFile) {
        file = new File(nameOfFile);

        try {
            file.createNewFile(); // Will work event if file's already created
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void putLine() {

    }

    public String getLines() {

    }

    public String getLine(int line) {

    }
}
