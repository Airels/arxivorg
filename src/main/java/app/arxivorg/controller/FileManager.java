package app.arxivorg.controller;

import java.io.*;

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

            writer.write(data + '\n');
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getLines() {
        if (!file.canRead())
            throw new RuntimeException("Unable to read file");

        int lines = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while (reader.readLine() != null) {
                lines++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return lines;
    }

    public String getLine(int line) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int lineIndex = 0;
            String lineString;

            while ((lineString = reader.readLine()) != null) {
                lineIndex++;

                if (line == lineIndex)
                    return lineString;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return "";
    }

    public int getLineEqualsTo(String line) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int lineIndex = 0;
            String lineString;

            while ((lineString = reader.readLine()) != null) {
                lineIndex++;

                if (lineString.equals(line))
                    return lineIndex;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return -1;
    }
}
