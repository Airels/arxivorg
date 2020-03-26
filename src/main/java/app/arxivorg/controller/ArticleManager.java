package app.arxivorg.controller;

import Utils.SortArticle;
import Utils.XmlReader;
import app.arxivorg.model.Article;
import app.arxivorg.model.Category;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticleManager {
    private ArxivOrgController controller;
    private final ArrayList<Article> initialArticles;
    private ArrayList<Article> actualArticles;

    // PREDICATE
    private Category category;
    private List<String> authors, keywords;
    private LocalDate startPeriod, endPeriod;


    ArticleManager(ArxivOrgController controller) {
        this.controller = controller;

        initialArticles = XmlReader.read("1.atom");
        actualArticles = new ArrayList<>(initialArticles);
    }

    private void resetArticlesList() {
        actualArticles = new ArrayList<>(initialArticles);
    }

    private void updateInterface() {
        controller.showArticles(actualArticles);
        System.out.println("Articles updated !");
    }

    // PREDICATES
    private void categoryPredicate(Category category) {
        this.category = category;

        if (category == Category.All) {
            resetArticlesList();
            updateInterface();
        } else
            actualArticles = SortArticle.byCategory(actualArticles, category);
    }

    private void periodPredicate(LocalDate startPeriod, LocalDate endPeriod) {
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;

        // SortArticle.byDate();
    }

    private void authorsPredicate(List<String> authors) {
        this.authors = authors;

        for (String author : authors)
            actualArticles = SortArticle.byAuthors(actualArticles, author);
    }

    private void keywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    // BRIDGE PREDICATES WITH INTERFACE
    void setPredicates(@NotNull Category category, List<String> authors, LocalDate startPeriod, LocalDate endPeriod /*, List<String> keywords */) {
        resetArticlesList();

        if (authors != null)
            authorsPredicate(authors);

        categoryPredicate(category);
        periodPredicate(startPeriod, endPeriod);
        // keywordsPredicate(keywords);
        updateInterface();
    }

    void setCategoryPredicate(Category category) {
        setPredicates(category, authors, startPeriod, endPeriod);
    }

    void setAuthorsPredicate(List<String> authors) {
        setPredicates(category, authors, startPeriod, endPeriod);
    }

    void setPeriodPredicate(LocalDate startPeriod, LocalDate endPeriod) {
        setPredicates(category, authors, startPeriod, endPeriod);
    }

    /*
    void setKeywordsPredicate(List<String> keywords) {

    }
    */
}
