package app.arxivorg.controller;

import java.io.*;
import java.util.Scanner;

/**
 * FileManager used to simplify read and write operations in file
 *
 * @author VIZCAINO Yohan (Airels)
 */
public class FileManager {
    private File file;
    private int lines;

    /**
     * Initialize FileManager and create a file if don't exist
     * @param nameOfFile Name of the file to open
     */
    public FileManager(String nameOfFile) {
        file = new File(nameOfFile);

        try {
            file.createNewFile(); // Will work event if file's already created
            lines = calculateLines();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Put a new line in the file
     * @param data String of data you want to write
     */
    public void putLine(String data) {
        if (!file.canWrite())
            throw new RuntimeException("Unable to write file");

        try {
            FileWriter writer = new FileWriter(file, true);

            writer.write(data + '\n');
            writer.close();

            lines++;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Put a new line or rewrite data on a specified line
     * @param data String of data you want to write
     * @param line Index of line you want to write data
     */
    public void putLine(String data, int line) {
        if (!file.canWrite())
            throw new RuntimeException("Unable to write file");

        try {
            Scanner scanner = new Scanner(file);
            StringBuilder buffer = new StringBuilder();

            for (int lineIndex = 1; lineIndex < line; lineIndex++) {
                if (!scanner.hasNextLine())
                    buffer.append('\n');
                else
                    buffer.append(scanner.nextLine()).append('\n');
            }

            buffer.append(data).append('\n');
            if (scanner.hasNextLine()) scanner.nextLine(); // Used for ignoring line to replace in file

            for (int lineIndex = line; lineIndex < lines; lineIndex++) {
                if (!scanner.hasNextLine())
                    buffer.append('\n');
                else
                    buffer.append(scanner.nextLine()).append('\n');
            }

            FileWriter writer = new FileWriter(file);
            writer.write(buffer.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Private method used to calculate lines in file
     * @return Number of lines calculated
     */
    private int calculateLines() {
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

    /**
     * Get numbers of lines in file
     * @return Number of lines
     * @author VIZCAINO Yohan (Airels)
     */
    public int getLines() {
        return lines;
    }

    /**
     * Get a specific line in a file
     * @param line Index of line to get
     * @return Line data if exist, empty String otherwise
     * @author VIZCAINO Yohan (Airels)
     */
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

    /**
     * Get index of line if it's equals to param
     * @param line Data to search
     * @return Index of line if found, -1 otherwise
     * @author VIZCAINO Yohan (Airels)
     */
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
