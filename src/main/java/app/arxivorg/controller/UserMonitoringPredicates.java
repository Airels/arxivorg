package app.arxivorg.controller;

import app.arxivorg.model.Category;
import app.arxivorg.utils.FileManager;

import java.util.Dictionary;

/**
 * Class used to control user predicates statistics.
 * Called by the controller when user selects a predicate.
 *
 * @author VIZCAINO Yohan (Airels)
 */
public class UserMonitoringPredicates {
    public final static String fileName = "UserPredicatesStats.txt";

    /**
     * Used to create file for monitoring.
     * IF FILE EXISTS IT WILL NOT BE MODIFIED !
     * IF FILE HAS BEEN ALTERED, IT WILL WIPE OUT ALL FILE !
     *
     * @author VIZCAINO Yohan (Airels)
     */
    public static void checkUserMonitoringFile() {
        FileManager fm = new FileManager(fileName);

        int lineCategory = fm.getLineEqualsTo("=CATEGORY=");
        int lineAuthors = fm.getLineEqualsTo("=AUTHORS=");
        int lineKeywords = fm.getLineEqualsTo("=KEYWORDS=");
        int lineEND = fm.getLineEqualsTo("=END=");

        if (lineCategory == -1 || lineAuthors == -1 || lineKeywords == -1 || lineEND == -1)
            fm.wipeFile();
    }

    public static void addCategoryStat(Category category) {
        FileManager fm = new FileManager(fileName);
    }

    public static void addAuthor(String author) {

    }

    public static void addKeyword(String keyword) {

    }

    public static Dictionary<String, Integer> getAuthors() {

    }

    public static Dictionary<String, Integer> getKeywords() {

    }
}
