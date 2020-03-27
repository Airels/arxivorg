package app.arxivorg.controller;

import utils.SortArticle;
import utils.XmlReader;
import app.arxivorg.model.Article;
import app.arxivorg.model.Category;
import jdk.jfr.Experimental;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticleManager {
    /***
     * IMPOSSIBLE DE FAIRE DES TESTS POUR CETTE CLASSE CAR ELLE DÉPENDS DE L'INTERFACE
     * D'AILLEURS LES TESTS IMPLIQUERAI DE TESTER SI LES METHODES TRIENT CORRECTEMENT,
     * IL EXISTE DÉJA DES TESTS POUR CELA
     */

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
        initialArticles.addAll(XmlReader.read("2.atom"));
        initialArticles.addAll(XmlReader.read("3.atom"));
        initialArticles.addAll(XmlReader.read("4.atom"));
        initialArticles.addAll(XmlReader.read("5.atom"));
        actualArticles = new ArrayList<>(initialArticles);
    }

    /**
     * @deprecated
     * CONSTRUCTOR ONLY USED FOR TESTS PURPOSE, DO NOT USE
     */
    @Deprecated
    public ArticleManager() {
        initialArticles = XmlReader.read("1.atom");
        actualArticles = new ArrayList<>(initialArticles);
    }

    private void resetArticlesList() {
        actualArticles = new ArrayList<>(initialArticles);
    }

    private void updateInterface() {
        if (controller == null)
            return;

        controller.showArticles(actualArticles);
        System.out.println("Articles updated !");
    }

    // PREDICATES
    private void categoryPredicate(Category category) {
        this.category = category;

        actualArticles = SortArticle.byCategory(actualArticles, category);
    }

    private void periodPredicate(LocalDate startPeriod, LocalDate endPeriod) {
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;

        actualArticles = SortArticle.byDate(actualArticles, startPeriod, endPeriod);
    }

    private void authorsPredicate(List<String> authors) {
        this.authors = authors;
        if (authors == null)
            return;

        for (String author : authors)
            actualArticles = SortArticle.byAuthors(actualArticles, author);
    }

    private void keywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        if (keywords == null)
            return;

        /* for (String keyword : keywords)
            actualArticles = SortArticle.byKeywords(actualArticles, keyword); */
    }

    // BRIDGE PREDICATES WITH INTERFACE
    public void setPredicates(@NotNull Category category, List<String> authors, @NotNull LocalDate startPeriod, @NotNull LocalDate endPeriod, List<String> keywords) {
        resetArticlesList();

        categoryPredicate(category);
        authorsPredicate(authors);
        periodPredicate(startPeriod, endPeriod);
        keywordsPredicate(keywords);
        updateInterface();
    }

    public void setCategoryPredicate(Category category) {
        setPredicates(category, authors, startPeriod, endPeriod, keywords);
    }

    public void setAuthorsPredicate(List<String> authors) {
        setPredicates(category, authors, startPeriod, endPeriod, keywords);
    }

    public void setPeriodPredicate(LocalDate startPeriod, LocalDate endPeriod) {
        setPredicates(category, authors, startPeriod, endPeriod, keywords);
    }

    public void setKeywordsPredicate(List<String> keywords) {
        setPredicates(category, authors, startPeriod, endPeriod, keywords);
    }
}
