package app.arxivorg.controller;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileManagerTest {

    @Test
    public void testPutLines() {
        FileManager fm = new FileManager("testFile");
        fm.putLine("Hello world!");

        try {
            File file = new File("testFile");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            assert(reader.readLine().equals("Hello world!"));

            file.delete();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    public void testGetLines() {
        FileManager fm = new FileManager("testFile");
        fm.putLine("A");
        fm.putLine("B");
        fm.putLine("C");

        System.out.println(fm.getLines());
        assert(fm.getLines() == 3);

        new File("testFile").delete();
    }

    @Test
    public void testGetLine() {
        FileManager fm = new FileManager("testFile");
        fm.putLine("Hello");
        fm.putLine("Da Big");
        fm.putLine("World!");

        assert(fm.getLine(2).equals("Da Big"));
        assert(fm.getLine(1).equals("Hello"));
    }
}
