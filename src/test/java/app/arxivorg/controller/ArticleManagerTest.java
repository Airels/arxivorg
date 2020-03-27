package app.arxivorg.controller;

import app.arxivorg.model.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class ArticleManagerTest {
    private ArticleManager articleManager;

    @Test
    public void testAll() {
        articleManager = new ArticleManager();
        testSetPredicates();
        testCategoryPredicate();
        testPeriodPredicate();
        resetPredicates();
        testAuthorsPredicate();
        testKeyWordsPredicate();
    }

    void testSetPredicates() {
        articleManager.setPredicates(
                Category.All,
                null,
                LocalDate.of(2019, 1, 1),
                LocalDate.of(2020, 3, 27),
                null);

        System.out.println(articleManager.getArticles().size());
        assertEquals(2555, articleManager.getArticles().size());
    }

    void testCategoryPredicate() {
        articleManager.setCategoryPredicate(Category.Statistics);
        assertEquals(1, articleManager.getArticles().size());
    }

    void testPeriodPredicate() {
        articleManager.setPeriodPredicate(LocalDate.now(), LocalDate.now());
        assertEquals(0, articleManager.getArticles().size());
    }

    void testAuthorsPredicate() {
        ArrayList<String> authors = new ArrayList<>();
        authors.add("Shen Gao");

        articleManager.setAuthorsPredicate(authors);
        assertEquals(3, articleManager.getArticles().size());
    }

    void testKeyWordsPredicate() {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("critical");
        keywords.add("crucial");

        articleManager.setKeywordsPredicate(keywords);
        assertEquals(3, articleManager.getArticles().size());
    }


    void resetPredicates() {
        articleManager.setPredicates(
                Category.All,
                null,
                LocalDate.of(2019, 1, 1),
                LocalDate.of(2020, 3, 27),
                null);
    }
}
