package app.arxivorg.controller;

import app.arxivorg.model.Category;
import app.arxivorg.utils.FileManager;
import org.junit.jupiter.api.Test;

import java.util.Dictionary;

public class UserMonitoringPredicatesTest {

    @Test
    public void testCheckUserMonitoringFile() {
        FileManager fm = new FileManager(UserMonitoringPredicates.fileName);
        fm.wipeFile();

        UserMonitoringPredicates.checkUserMonitoringFile();
        assert(fm.getLineEqualsTo("=CATEGORY=") == 1);
        assert(fm.getLineEqualsTo("=AUTHORS=") != -1);
        assert(fm.getLineEqualsTo("=KEYWORDS=") != -1);
        assert(fm.getLineEqualsTo("=END=") != -1);
        assert(fm.getLine(fm.getLineStartsWith(Category.Computer_Science.getName())).contains("0"));
    }

    @Test
    public void testAddCategoryStat() {
        // TODO : Check if category selected increments on file

        FileManager fm = new FileManager(UserMonitoringPredicates.fileName);

        UserMonitoringPredicates.addCategoryStat(Category.Computer_Science);
        String line = fm.getLine(fm.getLineStartsWith(Category.Computer_Science.getName()));
        assert(line.contains("1"));

        UserMonitoringPredicates.addCategoryStat(Category.Statistics);
        UserMonitoringPredicates.addCategoryStat(Category.Statistics);
        String line2 = fm.getLine(fm.getLineStartsWith(Category.Computer_Science.getName()));
        System.out.println(line);
        assert(line2.contains("2"));
    }

    @Test
    public void testAddAuthor() {
        // TODO : Simply add Author on AUTHOR section

        FileManager fm = new FileManager(UserMonitoringPredicates.fileName);
        fm.wipeFile();

        UserMonitoringPredicates.addAuthor("Asriel Dreemurr");
        assert(fm.getLineStartsWith("Asriel Dreemurr") != -1);
        assert(fm.getLine(fm.getLineStartsWith("Asriel Dreemurr")).contains("1"));

        UserMonitoringPredicates.addAuthor("Asriel Dreemurr");
        UserMonitoringPredicates.addAuthor("Niko");
        assert(fm.getLine(fm.getLineStartsWith("Niko")).contains("1"));
        assert(fm.getLine(fm.getLineStartsWith("Asriel Dreemurr")).contains("2"));
    }

    @Test
    public void testAddKeyword() {
        // TODO : Simply add Keyword on KEYWORD section

        FileManager fm = new FileManager(UserMonitoringPredicates.fileName);
        fm.wipeFile();

        UserMonitoringPredicates.addKeyword("saucisse");
        assert(fm.getLineStartsWith("saucisse") != -1);
        assert(fm.getLine(fm.getLineStartsWith("saucisse")).contains("1"));

        UserMonitoringPredicates.addKeyword("saucisse");
        UserMonitoringPredicates.addKeyword("it just works");
        assert(fm.getLine(fm.getLineStartsWith("it just works")).contains("1"));
        assert(fm.getLine(fm.getLineStartsWith("saucisse")).contains("2"));
    }

    @Test
    public void testGetAuthors() {
        /* TODO : Creates Map (or similar) with Map<String, Integer>
            - String -> The author
            - Integer -> How much occurences appears
            - Insensitive or not ?
         */

        FileManager fm = new FileManager(UserMonitoringPredicates.fileName);
        fm.wipeFile();

        UserMonitoringPredicates.addAuthor("Asriel Dreemurr");
        UserMonitoringPredicates.addAuthor("Asriel Dreemurr");
        UserMonitoringPredicates.addAuthor("Asriel Dreemurr");
        UserMonitoringPredicates.addAuthor("Niko");
        UserMonitoringPredicates.addAuthor("GLaDOS");
        UserMonitoringPredicates.addAuthor("GLaDOS");

        Dictionary<String, Integer> dico = UserMonitoringPredicates.getAuthors();

        assert(dico.get("Asriel Dreemurr") == 3);
        assert(dico.get("GLaDOS") == 2);
        assert(dico.get("Niko") == 1);
    }

    @Test
    public void testGetKeywords() {
        // TODO : Same as authors

        FileManager fm = new FileManager(UserMonitoringPredicates.fileName);
        fm.wipeFile();

        UserMonitoringPredicates.addKeyword("nuclear");
        UserMonitoringPredicates.addKeyword("nuclear");
        UserMonitoringPredicates.addKeyword("GDPR");
        UserMonitoringPredicates.addKeyword("cover");
        UserMonitoringPredicates.addKeyword("cover");

        Dictionary<String, Integer> dico = UserMonitoringPredicates.getKeywords();

        assert(dico.get("nuclear") == 2);
        assert(dico.get("cover") == 2);
        assert(dico.get("GDPR") == 1);
    }
}
