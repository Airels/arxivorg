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

        // CHECK FILE INTEGRITY
        int lineCategory = fm.getLineEqualsTo("=CATEGORY=");
        int lineAuthors = fm.getLineEqualsTo("=AUTHORS=");
        int lineKeywords = fm.getLineEqualsTo("=KEYWORDS=");
        int lineEND = fm.getLineEqualsTo("=END=");

        if (lineCategory == -1 || lineAuthors == -1 || lineKeywords == -1 || lineEND == -1) {
            createFile();
            return;
        }

        // CHECK CATEGORY INTEGRITY
        for (Category category : Category.values()) {
            if (category.equals(Category.All)) continue;

            if (fm.getLineContains(category.getName()) == -1) {
                createFile();
                return;
            }
        }
    }

    private static void createFile() {
        // CREATING FILE
        FileManager fm = new FileManager(fileName);
        fm.wipeFile();

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
        int line = fm.getLineStartsWith(category.getName());

        if (line == -1) {
            System.err.println("UserMonitoringPredicates error: File altered, recreating...");
            createFile();
            addCategoryStat(category);
            return;
        }

        int categoryCount = Integer.parseInt(fm.getLine(line).split(" ")[1]); // Line example : cs 3

        fm.putLine(category.getName() + ' ' + (categoryCount+1), line);
    }

    public static void addAuthor(String author) {
        FileManager fm = new FileManager(fileName);
        int lineAuthors = fm.getLineEqualsTo("=AUTHORS=");
        int line = fm.getLineStartsWith(author);

        if (line == -1) {
            fm.putLine("=AUTHORS=\n" + author + " 1", lineAuthors);
            return;
        }

        String[] lineAuthorSplitted = fm.getLine(line).split(" ");
        int authorCount = Integer.parseInt(lineAuthorSplitted[lineAuthorSplitted.length-1]);

        fm.putLine(author + ' ' + (authorCount+1), line);
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
