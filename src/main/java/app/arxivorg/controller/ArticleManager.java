package app.arxivorg.controller;

import app.arxivorg.utils.SortArticle;
import app.arxivorg.utils.XmlReader;
import app.arxivorg.model.Article;
import app.arxivorg.model.Category;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * An article manager who assist ArxivOrgController.
 * This object will call sort methods, generate and call Controller ListView modification method.
 *
 * @see ArxivOrgController
 * @see SortArticle
 * @author VIZCAINO Yohan (Airels)
 */
public class ArticleManager {
    /**
     * A simple reference to the controller. Used to update list of articles.
     * @see ArticleManager#updateInterface()
     */
    private ArxivOrgController controller;

    /**
     * List of initial article when user interface is initialized.
     * Also updated when predicates of user show no articles.
     */
    private final ArrayList<Article> initialArticles;

    /**
     * List of articles when user uses predicates.
     * List reset when user changes a predicate to avoid errors.
     */
    private ArrayList<Article> actualArticles;

    // PREDICATE
    /**
     * Category predicate saved when user choose one.
     */
    private Category category;

    /**
     * List of string for authors and keywords when user chooses ones.
     */
    private List<String> authors, keywords;

    /**
     * LocalDate of periods when user specifies them.
     */
    private LocalDate startPeriod, endPeriod;

    private static int articlesFromToIndex = 10;


    /**
     * Constructor of ArticleManager.
     *
     * @param controller Controller of User Interface
     * @see ArxivOrgController
     * @author VIZCAINO Yohan (Airels)
     */
    ArticleManager(ArxivOrgController controller) {
        this.controller = controller;

        initialArticles = new ArrayList<>();


        generateInitialArticles();
    }

    /**
     * Private method used to reset actualArticles list.
     * @author VIZCAINO Yohan (Airels)
     */
    private void resetArticlesList() {
        actualArticles = new ArrayList<>(initialArticles);
    }

    /**
     * Private method used to updates User Interface when predicates was applied.
     * @see ArxivOrgController#showArticles(List<Article>)
     * @author VIZCAINO Yohan (Airels)
     */
    private void updateInterface() {
        if (controller == null)
            return;

        controller.showArticles(actualArticles);
        System.out.println("Articles updated !");
    }


    // PREDICATES

    /**
     * Call SortArticle method to sort by a category.
     * @see SortArticle#byCategory(Category)
     * @param category Category selected
     * @author VIZCAINO Yohan (Airels)
     */
    private void categoryPredicate(Category category) {
        this.category = category;

        actualArticles = SortArticle.byCategory(actualArticles, category);
    }

    /**
     * Call SortArticle method to sort by two dates: a start and an end of a period.
     * @param startPeriod Date start of period.
     * @param endPeriod Date end of period.
     * @see SortArticle#byDate(ArrayList, LocalDate, LocalDate)
     * @author VIZCAINO Yohan (Airels)
     */
    private void periodPredicate(LocalDate startPeriod, LocalDate endPeriod) {
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;

        actualArticles = SortArticle.byDate(actualArticles, startPeriod, endPeriod);
    }

    /**
     * Call SortArticle method with a ForEach loop for every authors specified by user.
     * @see SortArticle#byAuthors(ArrayList, String)
     * @param authors List of authors specified by user.
     * @author VIZCAINO Yohan (Airels)
     */
    private void authorsPredicate(List<String> authors) {
        this.authors = authors;
        if (authors == null)
            return;

        for (String author : authors)
            actualArticles = SortArticle.byAuthors(actualArticles, author);
    }

    /**
     * Call SortArticle method with a ForEach loop for every keywords specified by user.
     * @see SortArticle#byKeyword(ArrayList, String);
     * @param keywords
     */
    private void keywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        if (keywords == null)
            return;

        for (String keyword : keywords)
            actualArticles = SortArticle.byKeyword(actualArticles, keyword);
    }

    // BRIDGE PREDICATES WITH INTERFACE
    /**
     * Bridge predicated called by interface to specify every predicates user wants.
     * @see ArxivOrgController
     * @param category Category wanted by user. Cannot be null.
     * @param authors List of authors typed by user. Can be null.
     * @param startPeriod Date start of a wanted period. Cannot be null.
     * @param endPeriod Date end of a wanted period. Cannot be null.
     * @param keywords List of keywords typed by user. Can be null.
     * @author VIZCAINO Yohan (Airels)
     */
    public void setPredicates(@NotNull Category category, List<String> authors, @NotNull LocalDate startPeriod, @NotNull LocalDate endPeriod, List<String> keywords) {
        Thread t = new Thread(() -> {
            resetArticlesList();

            categoryPredicate(category);
            authorsPredicate(authors);
            periodPredicate(startPeriod, endPeriod);
            keywordsPredicate(keywords);

            updateInterface();

            if (actualArticles.size() < 5) {
                articlesFromToIndex += 20;

                List<Article> newArticles = SortArticle.byCategoryFromTo(category, articlesFromToIndex-10, articlesFromToIndex+10);
                initialArticles.addAll(newArticles);

                System.out.println(newArticles.size());

                if (newArticles.size() >= 5)
                    setPredicates(category, authors, startPeriod, endPeriod, keywords);
            }
        });

        t.start();
    }

    /**
     * Used by interface to set a category predicate.
     * @param category
     * @author VIZCAINO Yohan (Airels)
     */
    public void setCategoryPredicate(Category category) {
        setPredicates(category, authors, startPeriod, endPeriod, keywords);
    }

    /**
     * Used by interface to set a list of authors wanted by user.
     * @param authors List of authors (String)
     * @author VIZCAINO Yohan (Airels)
     */
    public void setAuthorsPredicate(List<String> authors) {
        setPredicates(category, authors, startPeriod, endPeriod, keywords);
    }

    /**
     * Used by interface to set a wanted period.
     * @param startPeriod Date start of wanted period.
     * @param endPeriod Date end of wanted period.
     * @author VIZCAINO Yohan (Airels)
     */
    public void setPeriodPredicate(LocalDate startPeriod, LocalDate endPeriod) {
        setPredicates(category, authors, startPeriod, endPeriod, keywords);
    }

    /**
     * Used by interface to set a category predicate.
     * @param keywords List of keywords (String)
     * @author VIZCAINO Yohan (Airels)
     */
    public void setKeywordsPredicate(List<String> keywords) {
        setPredicates(category, authors, startPeriod, endPeriod, keywords);
    }


    public void generateInitialArticles() {
        System.out.println("Getting articles...");
        for (Category category : Category.values()) {
            if (category == Category.All) continue;
            System.out.println("..." + category);
            initialArticles.addAll(SortArticle.byCategoryFromTo(category, articlesFromToIndex-10, articlesFromToIndex));

            actualArticles = new ArrayList<>(initialArticles);
        }
    }

    public void nextPage() {
        articlesFromToIndex += 10;

        initialArticles.clear();
        actualArticles.clear();

        updateInterface();

        generateInitialArticles();
        setPredicates(category, authors, startPeriod, endPeriod, keywords);
    }

    public void previousPage() {
        if (articlesFromToIndex > 10) {
            articlesFromToIndex -= 10;

            initialArticles.clear();
            actualArticles.clear();

            updateInterface();

            generateInitialArticles();
            setPredicates(category, authors, startPeriod, endPeriod, keywords);
        }
    }


    // /!\ ALL METHODS HERE ARE USED FOR TESTS PURPOSE ONLY /!\
    /**
     * Generates list of articles without user interface.
     * @deprecated <b>This constructor exists only for tests. Not used for other purposes.</b>
     * @author VIZCAINO Yohan (Airels)
     */
    @Deprecated
    public ArticleManager() {
        initialArticles = XmlReader.read("1.atom");
        initialArticles.addAll(XmlReader.read("2.atom"));
        initialArticles.addAll(XmlReader.read("3.atom"));
        initialArticles.addAll(XmlReader.read("4.atom"));
        initialArticles.addAll(XmlReader.read("5.atom"));
        actualArticles = new ArrayList<>(initialArticles);
    }

    /**
     * @deprecated <b>Method only used for tests purpose. Never used for other purposes.</b>
     * @return List of articles
     * @author VIZCAINO Yohan (Airels)
     */
    @Deprecated
    public List<Article> getArticles() {
        return actualArticles;
    }
}
