package app.arxivorg.controller;

import app.arxivorg.model.Category;
import app.arxivorg.utils.FileManager;
import app.arxivorg.utils.FileManagerTest;
import org.junit.jupiter.api.Test;

public class UserMonitoringPredicatesTest {

    @Test
    public void testCheckUserMonitoringFile() {

    }

    @Test
    public void testAddCategoryStat() {
        // TODO : Check if category selected increments on file

        FileManager fm = new FileManager(UserMonitoringPredicates.fileName);

        UserMonitoringPredicates.addCategoryStat(Category.Computer_Science);
        String line = fm.getLine(fm.getLineContains(Category.Computer_Science.getName()));
        assert(line.contains("1"));

        UserMonitoringPredicates.addCategoryStat(Category.Statistics);
        UserMonitoringPredicates.addCategoryStat(Category.Statistics);
        String line2 = fm.getLine(fm.getLineContains(Category.Computer_Science.getName()));
        assert(line2.contains("2"));
    }

    @Test
    public void testAddAuthor() {
        // TODO : Simply add Author on AUTHOR section

        FileManager fm = new FileManager(UserMonitoringPredicates.fileName);

        UserMonitoringPredicates.addAuthor("machin bidule");
        assert(fm.getLineContains("machin bidule") != -1);
        assert(fm.getLine(fm.getLineContains("machin bidule")).contains("1"));

        UserMonitoringPredicates.addAuthor("machin bidule");
        UserMonitoringPredicates.addAuthor("machin truc");
        assert(fm.getLine(fm.getLineContains("machin truc")).contains("1"));
        assert(fm.getLine(fm.getLineContains("machin bidule")).contains("2"));
    }

    @Test
    public void testAddKeyword() {
        // TODO : Simply add Keyword on KEYWORD section
    }

    @Test
    public void testGetAuthors() {
        /* TODO : Creates Map (or similar) with Map<String, Integer>
            - String -> The author
            - Integer -> How much occurences appears
            - Insensitive or not ?
         */
    }

    @Test
    public void testGetKeywords() {
        // TODO : Same as authors
    }
}
