package app.arxivorg.utils;

import app.arxivorg.utils.FileManager;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileManagerTest {

    @Test
    public void testPutLine() {
        File file = new File("testFilePutLine");
        file.delete();

        FileManager fm = new FileManager("testFilePutLine");
        fm.putLine("Hello world!");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            assert(reader.readLine().equals("Hello world!"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    public void testPutLineWithIndex() {
        File file = new File("testFilePutLineWithIndex");
        file.delete();

        FileManager fm = new FileManager("testFilePutLineWithIndex");
        fm.putLine("Hello");
        fm.putLine("");
        fm.putLine("!");

        fm.putLine("World", 2);
        assert(fm.getLine(2).equals("World"));

        fm.putLine("Goodbye", 10);
        assert(fm.getLine(9).equals(""));
        assert(fm.getLine(10).equals("Goodbye"));
    }

    @Test
    public void testGetLines() {
        File file = new File("testFileGetLines");
        file.delete();

        FileManager fm = new FileManager("testFileGetLines");
        fm.putLine("A");
        fm.putLine("B");
        fm.putLine("C");

        assert(fm.getLines() == 3);
    }

    @Test
    public void testGetLine() {
        File file = new File("testFileGetLine");
        file.delete();

        FileManager fm = new FileManager("testFileGetLine");
        fm.putLine("Hello");
        fm.putLine("Da Big");
        fm.putLine("World!");

        assert(fm.getLine(2).equals("Da Big"));
        assert(fm.getLine(1).equals("Hello"));
    }

    @Test
    public void testGetLineEqualsTo() {
        File file = new File("testFileGetLineEqualsTo");
        file.delete();

        FileManager fm = new FileManager("testFileGetLineEqualsTo");
        fm.putLine("A");
        fm.putLine("=== SECTION ===");
        fm.putLine("B");

        assert(fm.getLineEqualsTo("=== SECTION ===") == 2);
    }

    @Test
    public void testGetLineContains() {
        File file = new File("testFileContains");
        file.delete();

        FileManager fm = new FileManager("testFileContains");
        fm.putLine("A: Hello world!");
        fm.putLine("B: Goodbye world!");
        fm.putLine("C: If you if, then I'm saying something");

        assert(fm.getLineContains("B") == 2);
    }

    @Test
    public void testWipeFile() {
        File file = new File("testFileWipeFile");
        file.delete();

        FileManager fm = new FileManager("testFileWipeFile");
        fm.putLine("Hello");
        fm.putLine("The");
        fm.putLine("World!");

        fm.wipeFile();

        assert(fm.getLines() == 0);
    }
}
