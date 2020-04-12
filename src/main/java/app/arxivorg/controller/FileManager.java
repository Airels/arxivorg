package app.arxivorg.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * FileManager used to simplify read and write file
 *
 * @author VIZCAINO Yohan (Airels)
 */
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

    public void putLine(String data) {
        if (!file.canWrite())
            throw new RuntimeException("Unable to write file");

        try {
            FileWriter writer = new FileWriter(file, true);

            writer.write(data);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getLines() {
        if (!file.canRead())
            throw new RuntimeException("Unable to read file");
    }

    public String getLine(int line) {

    }
}
