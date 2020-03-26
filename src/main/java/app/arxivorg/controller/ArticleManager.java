package app.arxivorg.controller;

import Utils.XmlReader;
import app.arxivorg.model.Article;
import app.arxivorg.model.Category;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticleManager {
    private ArxivOrgController controller;
    private final List<Article> initialArticles;
    private List<Article> actualArticles;
    // PREDICATES
    // ...

    ArticleManager(ArxivOrgController controller) {
        this.controller = controller;

        initialArticles = XmlReader.read("1.atom");
        actualArticles = new ArrayList<>(initialArticles);
        controller.showArticles(actualArticles);
    }

    // PREDICATES
    void categoryPredicate(Category category) {

    }

    void periodPredicate(LocalDate startPeriod, LocalDate endPeriod) {

    }

    void authorsPredicate(List<String> authors) {

    }

    void keywordsPredicate(List<String> keywords) {

    }
}
