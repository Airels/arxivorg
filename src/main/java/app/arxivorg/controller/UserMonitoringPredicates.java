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

        // CREATING FILE
        fm.putLine("=CATEGORY=");
        for (Category category : Category.values()) {
            if (category.equals(Category.All)) continue;

            fm.putLine(category.getName() + " 0");
        }

        fm.putLine("=AUTHORS=");
        fm.putLine("=KEYWORDS=");
        fm.putLine("=END=");
    }

    public static void addCategoryStat(Category category) {
        FileManager fm = new FileManager(fileName);
    }

    public static void addAuthor(String author) {
        // TODO : Have to add \n BEFORE string to add in file
    }

    public static void addKeyword(String keyword) {
        // TODO : Have to add \n BEFORE string to add in file
    }

    public static Dictionary<String, Integer> getAuthors() {
        return null;
    }

    public static Dictionary<String, Integer> getKeywords() {
        return null;
    }
}
