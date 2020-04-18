package app.arxivorg.controller;

import app.arxivorg.model.Category;
import app.arxivorg.utils.FileManager;

import java.util.Dictionary;
import java.util.Hashtable;

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
        fm.putLine("Do not modify this file. If this file is not properly modified, software will reset data.");
    }

    public static void addCategoryStat(Category category) {
        checkUserMonitoringFile();

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
        checkUserMonitoringFile();

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
        checkUserMonitoringFile();

        FileManager fm = new FileManager(fileName);
        int lineKeywords = fm.getLineEqualsTo("=KEYWORDS=");
        int line = fm.getLineStartsWith(keyword);

        if (line == -1) {
            fm.putLine("=KEYWORDS=\n" + keyword + " 1", lineKeywords);
            return;
        }

        String[] lineKeywordSplitted = fm.getLine(line).split(" ");
        int keywordCount = Integer.parseInt(lineKeywordSplitted[lineKeywordSplitted.length-1]);

        fm.putLine(keyword + ' ' + (keywordCount+1), line);
    }

    public static Dictionary<Category, Integer> getCategories() {
        checkUserMonitoringFile();

        Dictionary<Category, Integer> dico = new Hashtable<>();
        FileManager fm = new FileManager(UserMonitoringPredicates.fileName);

        int lineCategory = fm.getLineEqualsTo("=CATEGORY=");
        int lineAuthors = fm.getLineEqualsTo("=AUTHORS=");

        for (int i = lineCategory+1; i < lineAuthors; i++) {
            String line = fm.getLine(i);

            Category category = Category.getCategory(line.substring(0, line.length()-2));
            int categoryCount = Integer.parseInt(line.substring(line.length()-1));

            dico.put(category, categoryCount);
        }

        return dico;
    }

    public static Dictionary<String, Integer> getAuthors() {
        checkUserMonitoringFile();

        Dictionary<String, Integer> dico = new Hashtable<>();
        FileManager fm = new FileManager(UserMonitoringPredicates.fileName);

        int lineAuthors = fm.getLineEqualsTo("=AUTHORS=");
        int lineKeywords = fm.getLineEqualsTo("=KEYWORDS=");

        for (int i = lineAuthors+1; i < lineKeywords; i++) {
            String line = fm.getLine(i);

            String author = line.substring(0, line.length()-2);
            int authorCount = Integer.parseInt(line.substring(line.length()-1));

            dico.put(author, authorCount);
        }

        return dico;
    }

    public static Dictionary<String, Integer> getKeywords() {
        checkUserMonitoringFile();

        Dictionary<String, Integer> dico = new Hashtable<>();
        FileManager fm = new FileManager(UserMonitoringPredicates.fileName);

        int lineKeywords = fm.getLineEqualsTo("=KEYWORDS=");
        int lineEnd = fm.getLineEqualsTo("=END=");

        for (int i = lineKeywords+1; i < lineEnd; i++) {
            String line = fm.getLine(i);

            String keyword = line.substring(0, line.length()-2);
            int keywordCount = Integer.parseInt(line.substring(line.length()-1));

            dico.put(keyword, keywordCount);
        }

        return dico;
    }
}
