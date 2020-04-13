package app.arxivorg.controller;

import app.arxivorg.utils.FileManager;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileManagerTest {

    @Test
    public void testPutLine() {
        new File("testFile").delete();
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
    public void testPutLineWithIndex() {
        new File("testFile").delete();
        FileManager fm = new FileManager("testFile");
        fm.putLine("Hello");
        fm.putLine("");
        fm.putLine("!");

        fm.putLine("World", 2);
        assert(fm.getLine(2).equals("World"));

        fm.putLine("Goodbye", 10);
        assert(fm.getLine(9).equals(""));
        assert(fm.getLine(10).equals("Goodbye"));

        new File("testFile").delete();
    }

    @Test
    public void testGetLines() {
        new File("testFile").delete();
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
        new File("testFile").delete();
        FileManager fm = new FileManager("testFile");
        fm.putLine("Hello");
        fm.putLine("Da Big");
        fm.putLine("World!");

        assert(fm.getLine(2).equals("Da Big"));
        assert(fm.getLine(1).equals("Hello"));

        new File("testFile").delete();
    }

    @Test
    public void testGetLineEqualsTo() {
        new File("testFile").delete();
        FileManager fm = new FileManager("testFile");
        fm.putLine("A");
        fm.putLine("=== SECTION ===");
        fm.putLine("B");

        assert(fm.getLineEqualsTo("=== SECTION ===") == 2);

        new File("testFile").delete();
    }

    @Test
    public void testGetLineContains() {
        new File("testFile").delete();
        FileManager fm = new FileManager("testFile");
        fm.putLine("A: Hello world!");
        fm.putLine("B: Goodbye world!");
        fm.putLine("C: If you if, then I'm saying something");

        assert(fm.getLineContains("B") == 2);

        new File("testFile").delete();
    }
}
