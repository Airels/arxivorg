package app.arxivorg.controller;

import app.arxivorg.model.Article;
import app.arxivorg.model.Authors;
import app.arxivorg.model.Category;
import app.arxivorg.model.SubCategories;
import app.arxivorg.utils.FileManager;
import app.arxivorg.view.*;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import static app.arxivorg.model.Category.*;

/**
 * Controller of the User Interface.
 * Using ArticleManager class to generates articles when user select a predicate.
 *
 * @see ArticleManager
 * @author VIZCAINO Yohan (Airels)
 */
public class ArxivOrgController implements Initializable {

    // TODO : Implémenter les favoris utilisateurs

    private ArticleManager articleManager;
    private Article selectedArticle;

    /**
     * ListView of articles. Modified by showArticle method
     * @see ArxivOrgController#showArticles(List)
     */
    @FXML private ListView articlesList;

    /**
     * ChoiceBox filled with categories. A Predicate.
     * @see Category
     */
    @FXML private ChoiceBox<Category> categoryChoiceBox;

    /**
     * DatePicker of start of a period. A Predicate. Default value will be current day less one month.
     */
    @FXML private DatePicker periodDatePickerStart;

    /**
     * DatePicker of end of a period. A Predicate. Default value will be current day.
     */
    @FXML private DatePicker periodDatePickerEnd;

    /**
     * TextFlow who show an article when user selected one in ListView of articles.
     */
    @FXML private TextFlow articleView;

    /**
     * ScrollPane used to scroll ListView of articles.
     */
    @FXML private ScrollPane scrollPaneArticleView;

    /**
     * TextArea of authors. A Predicate. An event will be trigged when user types something.
     * @see ArxivOrgController#onAuthorsKeyReleased(KeyEvent)
     */
    @FXML private TextArea authorsPredicate;

    /**
     * TextArea of keywords. A Predicate. An event will be trigged when user types something.
     * @see ArxivOrgController#onKeywordsKeyReleased(KeyEvent)
     */
    @FXML private TextArea keywordsPredicate;

    /**
     * Checkbox to save an article as favorite.
     * Not implemented yet.
     */
    @FXML private CheckBox favCheckBox;

    /**
     * A simple button to download an article.
     * @see ArxivOrgController#onBtnDownloadClicked(MouseEvent)
     */
    @FXML private Button btnDownload;

    /**
     * A simple button to download all articles in the list
     *
     */
    @FXML private Button btnDownloadAll;

    /**
     * Menubar with all of menus buttons and submenus
     */
    @FXML private MenuBar menuBar;


    /**
     * Main method called to generate interface, articles, sets values and properties of user interface elements,
     * and sets events triggers. Also creates an object ArticleManager.
     *
     * @param location
     * @param resourceBundle
     * @see ArticleManager
     * @author VIZCAINO Yohan (Airels)
     */
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        // DISPLAY PARAMS
        scrollPaneArticleView.setFitToWidth(true);
        scrollPaneArticleView.setFitToHeight(true);

        authorsPredicate.setWrapText(true);
        keywordsPredicate.setWrapText(true);

        favCheckBox.setDisable(true);
        btnDownload.setDisable(true);

        generateCategoryChoiceBox();

        periodDatePickerStart.setValue(LocalDate.now().minusMonths(1));
        periodDatePickerEnd.setValue(LocalDate.now());

        // EVENTS
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener(this::onCategoryChanged);

        authorsPredicate.addEventFilter(KeyEvent.KEY_RELEASED, this::onAuthorsKeyReleased);
        keywordsPredicate.addEventFilter(KeyEvent.KEY_RELEASED, this::onKeywordsKeyReleased);

        periodDatePickerStart.valueProperty().addListener(this::onDatePickerStartUpdate);
        periodDatePickerEnd.valueProperty().addListener(this::onDatePickerEndUpdate);

        favCheckBox.addEventFilter(MouseEvent.MOUSE_CLICKED, this::onFavChecked);
        btnDownload.addEventFilter(MouseEvent.MOUSE_CLICKED, this::onBtnDownloadClicked);
        btnDownloadAll.addEventFilter(MouseEvent.MOUSE_CLICKED, this::onBtnDownloadAllClicked);

        // ARTICLE MANAGER
        Thread t = new Thread(() -> {
            Platform.runLater(this::disableAllInputs);

            showArticles(new ArrayList<>()); // For asking to user to wait a moment

            articleManager = new ArticleManager(this);
            articleManager.setPredicates(All, null, periodDatePickerStart.getValue(), periodDatePickerEnd.getValue(), null);

            Platform.runLater(this::enableAllInputs);
        });
        t.start();
    }

    /**
     * Generates Category of choice box.
     * Generated by Category enum.
     *
     * @see Category
     * @author VIZCAINO Yohan (Airels)
     */
    private void generateCategoryChoiceBox() {
        List<Category> categories = new ArrayList<>();

        Collections.addAll(categories, values());

        categoryChoiceBox.setItems(FXCollections.observableArrayList(categories));
        categoryChoiceBox.setValue(All);
    }

    /**
     * Show in list of articles List of Article specified in param.
     * Essentially used by ArticleManager.
     *
     * @param articles List of Article you want to show
     * @author VIZCAINO Yohan (Airels)
     */
    public void showArticles(List<Article> articles) {
        articlesList.getItems().clear();

        if (articles.isEmpty()) {
            List<String> authorsUS = new ArrayList<>();
            authorsUS.add("ArxivOrg");

            articles.add(new Article(
                    "Récupération des articles sur arxiv...",
                    new Authors(authorsUS),
                    "Le programme est en train de récupérer la liste des articles, veuillez patienter...\n\n" +
                            "Si ce message reste affiché pendant un moment, essayez d'avoir des filtres moins restrictifs ou de changer de page. \n\n" +
                            "Si vous venez de lancer le programme ou changer de page, veuillez patienter 1 à 2 minutes...",
                    All,
                    new SubCategories(),
                    "nolink",
                    LocalDate.now()));
        }

        articlesList.getItems().addAll(articles);

        // Génération affichage éléments
        articlesList.setCellFactory(cell -> new ListCell<Article>() {
            final Tooltip tooltip = new Tooltip();

            // Affichage et définition actions pour chaque élément
            @Override
            protected void updateItem(Article article, boolean isEmpty) {
                if (article == null || isEmpty) {
                    setText(null);
                    setTooltip(null);
                } else {
                    StringBuilder lineContent = new StringBuilder();
                    lineContent.append(article.getTitle());
                    lineContent.append("\n\t");

                    List<String> authors = article.getAuthors().getList();

                    for (int i = 0; i < 3 && i < authors.size(); i++)
                        lineContent.append(authors.get(i)).append(", ");

                    if (authors.size() > 3) {
                        lineContent.append("+").append(authors.size() - 3);

                        if (authors.size() == 4) lineContent.append(" autre");
                        else lineContent.append(" autres");
                    }


                    setText(lineContent.toString());

                    tooltip.setText(article.getAuthors().toString());
                    setTooltip(tooltip);

                    EventHandler<MouseEvent> eventHandler = e -> {
                        // Définition élement à focus
                        int articleIndex = articlesList.getItems().indexOf(article);
                        articlesList.getFocusModel().focus(articleIndex);

                        //Définition évènement click
                        onMouseClickArticle(e, article);
                    };
                    addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
                }
            }
        });
    }


    // EVENTS

    /**
     * Event when user select a category in the ChoiceBox.
     * If oldCategory is equal to newCategory, event will be ignored.
     *
     * @param ov Observable
     * @param oldCategory Category before selection
     * @param newCategory Category after selection
     *
     * @author VIZCAINO Yohan (Airels)
     */
    @FXML void onCategoryChanged(Observable ov, Category oldCategory, Category newCategory) {
        if (!oldCategory.equals(newCategory)) {
            articleManager.setCategoryPredicate(newCategory);
            UserMonitoringPredicates.addCategoryStat(newCategory);
        }
    }

    /**
     * Event when user select an Article on the List.
     *
     * @param event Mouse event (left click, right click...)
     * @param article Article selected by the user
     *
     * @author VIZCAINO Yohan (Airels)
     */
    @FXML
    public void onMouseClickArticle(MouseEvent event, Article article) {
        if (event.getButton() == MouseButton.PRIMARY) {
            String textArticle = "";

            textArticle += article.getTitle() + "\n\t";
            textArticle += article.getAuthors() + "\n";
            textArticle += "--------------------\n";
            textArticle += "Résumé: ";
            textArticle += article.getContent();

            Text text = new Text(textArticle);
            text.setWrappingWidth(Region.USE_COMPUTED_SIZE);

            articleView.getChildren().clear();
            articleView.getChildren().add(text);

            selectedArticle = article;

            favCheckBox.setDisable(false);
            btnDownload.setDisable(false);

            if (UserFavourites.isFavourite(article))
                favCheckBox.setSelected(true);
            else
                favCheckBox.setSelected(false);
        }
    }

    /**
     * Event when user enter a start of period.
     *
     * @param ov Observable value of LocalDate
     * @param oldValue Date before user selection
     * @param newValue Date after user selection
     *
     * @author VIZCAINO Yohan (Airels)
     */
    @FXML
    public void onDatePickerStartUpdate(ObservableValue<? extends LocalDate> ov, LocalDate oldValue, LocalDate newValue) {
        if (newValue.compareTo(periodDatePickerEnd.getValue()) > 0) {
            // showErrorMessage("Date invalide !", "Impossible d'entrer une date supérieur à la date de fin");
            periodDatePickerStart.setValue(oldValue);
        } else {
            articleManager.setPeriodPredicate(periodDatePickerStart.getValue(), periodDatePickerEnd.getValue());
        }
    }

    /**
     * Event when user enter an end of period.
     *
     * @param ov Observable value of LocalDate
     * @param oldValue Date before user selection
     * @param newValue Date after user selection
     *
     * @author VIZCAINO Yohan (Airels)
     */
    @FXML
    public void onDatePickerEndUpdate(ObservableValue<? extends LocalDate> ov, LocalDate oldValue, LocalDate newValue) {
        if (newValue.compareTo(periodDatePickerStart.getValue()) < 0) {
            // showErrorMessage("Date invalide !", "Impossible d'entrer une date inférieur à la date de début");
            periodDatePickerEnd.setValue(oldValue);
        } else if (newValue.compareTo(LocalDate.now()) > 0) {
            // showInfoMessage("Date invalide !", "Impossible d'entrer une date au delà du " + LocalDate.now() + ".\nDate réglé sur aujourd'hui");
            periodDatePickerEnd.setValue(LocalDate.now());
        } else {
            articleManager.setPeriodPredicate(periodDatePickerStart.getValue(), periodDatePickerEnd.getValue());
        }
    }

    /**
     * Event when user type a letter with keyboard on author predicate text box.
     * This event will automatically add a line break when user type a punctuation mark,
     * and will start to sort by authors with setAuthorsPredicate method in ArticleManager class.
     *
     * @param e KeyEvent: Key code entered (A, K, F...)
     * @see ArticleManager#setAuthorsPredicate(List)
     * @author VIZCAINO Yohan (Airels)
     */
    @FXML
    public void onAuthorsKeyReleased(KeyEvent e) {
        if (e.getText().equals(",")) {
            authorsPredicate.appendText("\n");

            String[] authorsArray = authorsPredicate.getText().split(",");
            List<String> authors = new ArrayList<>();

            for (int i = 0; i < authorsArray.length-1; i++) {
                String author = authorsArray[i];

                author = author.replace(";", ""); // MISTYPING CORRECTION
                author = author.replace("\n", "");

                authors.add(author);

                UserMonitoringPredicates.addAuthor(author);
            }

            articleManager.setAuthorsPredicate(authors);
        } else if (authorsPredicate.getText().isEmpty()) {
            articleManager.setAuthorsPredicate(null);
        }
    }

    /**
     * Event when user type a letter with keyboard on keywords predicate text box.
     * This event will automatically add a line break when user type a punctuation mark,
     * and will start to sort by keywords with setKeywordsPredicate method in ArticleManager class.
     *
     * @param e KeyEvent: Key code entered (A, J, E...)
     * @see ArticleManager#setKeywordsPredicate(List<String>)
     * @author VIZCAINO Yohan (Airels)
     */
    @FXML
    public void onKeywordsKeyReleased(KeyEvent e) {
        if (e.getText().equals(",")) {
            keywordsPredicate.appendText("\n");

            String[] keywordsArray = keywordsPredicate.getText().split(",");
            List<String> keywords = new ArrayList<>();

            for (int i = 0; i < keywordsArray.length-1; i++) {
                String keyword = keywordsArray[i];

                keyword = keyword.replace(";\n", ""); // MISTYPING CORRECTION
                keyword = keyword.replace("\n", "");

                keywords.add(keyword);

                UserMonitoringPredicates.addKeyword(keyword);
            }

            articleManager.setKeywordsPredicate(keywords);
        } else if (keywordsPredicate.getText().isEmpty()) {
            articleManager.setKeywordsPredicate(null);
        }
    }

    /**
     * Event triggered when user click on the checkbox
     * @param event MouseEvent received
     * @author VIZCAINO Yohan (Airels)
     */
    @FXML
    public void onFavChecked(MouseEvent event) {
        if (favCheckBox.isSelected() && !UserFavourites.isFavourite(selectedArticle))
            UserFavourites.addFavourite(selectedArticle);
        if (!favCheckBox.isSelected() && UserFavourites.isFavourite(selectedArticle))
            UserFavourites.removeFavourite(selectedArticle);
    }

    /**
     * When user click on download button after selected an article.
     * Will prompt a Directory Chooser and will send Article and Path to PDFDownloader.
     *
     * @param event Mouse click event
     * @see ArxivOrgController#showDirectoryChooser()
     * @see PDFDownloader#downloadFile(Article, File)
     * @author VIZCAINO Yohan (Airels)
     */
    @FXML
    public void onBtnDownloadClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            File selectedFile = showDirectoryChooser();

            if (selectedFile == null) return;

            if (PDFDownloader.downloadFile(selectedArticle, selectedFile))
                showInfoMessage("Téléchargement terminé",
                        "Le téléchargement de " + selectedArticle.getTitle() + " est terminé !");
            else
                showErrorMessage("Téléchargement échoué",
                        "Le téléchargement de " + selectedArticle.getTitle() + " a échoué !");
        }
    }

    /**
     * When user click on download all button.
     * Will prompt a Directory Chooser and will send Article and Path to PDFDownloader.
     *
     * @param event Mouse click event
     * @see ArxivOrgController#showDirectoryChooser()
     * @see PDFDownloader#downloadFile(Article, File)
     * @author VIZCAINO Yohan (Airels)
     */
    @FXML
    public void onBtnDownloadAllClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            File selectedFile = showDirectoryChooser();

            if (selectedFile == null) return;

            Alert alert = prepareWaitingMessage("Téléchargement de tous les articles...",
                    "Initialisation du téléchargement...");

            Thread t = new Thread(() -> {
                Platform.runLater(alert::show);

                int articlesListSize = articlesList.getItems().size(),
                        downloadSuccessful = 0, downloadFailed = 0;

                for (int i = 0; i < articlesListSize; i++) {
                    Article article = (Article) articlesList.getItems().get(i);

                    boolean isDownloaded = PDFDownloader.downloadFile(article, selectedFile);

                    if (isDownloaded) downloadSuccessful++;
                    else downloadFailed++;

                    int percentage = (int) (((float) i / (float) articlesListSize) * 100);
                    final String msg = percentage + "% (" + i  + " articles sur " + articlesListSize + ")"; // NEED TO BE FINAL
                    Platform.runLater(() -> alert.setContentText(msg));
                }

                StringBuilder msg = new StringBuilder();
                msg.append(downloadSuccessful).append(" articles ont été enregistré avec succès,").append('\n');
                msg.append(downloadFailed).append(" articles ont échoué");

                Platform.runLater(() -> showInfoMessage("Téléchargements de tous les articles terminé", msg.toString()));

                Platform.runLater(alert::close);
            });

            alert.getDialogPane().getScene().getWindow().setOnCloseRequest((e) -> {
                t.interrupt();

                Platform.runLater(() -> showInfoMessage(
                        "Téléchargement interrompu",
                        "Le téléchargement des articles à été interrompu"));
            });

            t.start(); // Start thread
        }
    }


    // MENU EVENTS

    /**
     * Called when user wants to delete all favourites.
     * Will ask confirmation first and delete all favourites if user accepts.
     * @author VIZCAINO Yohan (Airels)
     */
    @FXML
    public void OnClickMenuDeleteFavourites() {
        boolean confirm = showConfirmationMessage(
                "Suppression des favoris",
                "Êtes-vous sûr de vouloiv supprimer TOUS vos favoris ? Cette action est irréversible !");

        if (confirm) {
            FileManager fm = new FileManager(UserFavourites.fileName);
            fm.wipeFile();
            UserFavourites.checkUserFavouriteFile();
            showInfoMessage("Suppression des favoris terminé", "Tous vos favoris ont été supprimés.");
        }
    }

    /**
     * Will stop every thread and exit the application
     * @author VIZCAINO Yohan (Airels)
     */
    @FXML
    public void onClickMenuApplicationExit() {
        System.exit(0);
    }

    /**
     * Call refresh method to show new articles
     * @see ArticleManager#nextPage()
     * @author VIZCAINO Yohan (Airels)
     */
    @FXML
    public void onClickMenuArticlesNext() {
        Thread t = new Thread(() -> {
            Platform.runLater(this::disableAllInputs);

            articleManager.nextPage();

            Platform.runLater(this::enableAllInputs);
        });
        t.start();

        System.out.println("Next!");
    }

    /**
     * Call refresh method to show old articles
     * @see ArticleManager#previousPage()
     * @author VIZCAINO Yohan (Airels)
     */
    @FXML
    public void onClickMenuArticlesPrevious() {
        Thread t = new Thread(() -> {
            Platform.runLater(this::disableAllInputs);

            articleManager.previousPage();

            Platform.runLater(this::enableAllInputs);
        });
        t.start();

        System.out.println("Previous!");
    }

    /**
     * Show in a popup all predicates used by user
     * @author VIZCAINO Yohan (Airels)
     */
    public void showUserStats() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Statistiques de vos recherches :").append("\n\n");

        stringBuilder.append("Catégories :").append('\n');
        Dictionary<Category, Integer> categoriesStats = UserMonitoringPredicates.getCategories();
        categoriesStats.keys().asIterator().forEachRemaining(category -> stringBuilder.append("- ")
                .append(category)
                .append(" utilisé ")
                .append(categoriesStats.get(category))
                .append(" fois")
                .append('\n'));

        stringBuilder.append("\nAuteurs :").append('\n');
        Dictionary<String, Integer> authorsStats = UserMonitoringPredicates.getAuthors();
        authorsStats.keys().asIterator().forEachRemaining(author -> stringBuilder.append("- ")
                .append(author)
                .append(" recherché ")
                .append(authorsStats.get(author))
                .append(" fois")
                .append('\n'));

        stringBuilder.append("\nMots clés :").append('\n');
        Dictionary<String, Integer> keywordsStats = UserMonitoringPredicates.getKeywords();
        keywordsStats.keys().asIterator().forEachRemaining(keyword -> stringBuilder.append("- ")
                .append(keyword)
                .append(" utilisé ")
                .append(keywordsStats.get(keyword))
                .append(" fois")
                .append('\n'));

        articleView.getChildren().clear();
        articleView.getChildren().add(new Text(stringBuilder.toString()));
        favCheckBox.setDisable(true);
        btnDownload.setDisable(true);
    }

    /**
     * Show in a popup statistics of actual articles list
     * @see
     * @author VIZCAINO Yohan (Airels)
     */
    public void showArticleStats() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Statistiques de la page :").append("\n\n");

        stringBuilder.append("Catégories :").append('\n');
        int[] categoriesStat = ArticlesStatistics.statisticCategories((ArrayList<Article>) articleManager.getInitialArticles());
        int index = 0;
        for (Category category : Category.values()) {
            if (category == All || category == Favourites) continue;

            stringBuilder.append("- ")
                    .append(categoriesStat[index])
                    .append(" articles de ")
                    .append(category)
                    .append('\n');

            index++;
        }

        int nbArticlesByDay = ArticlesStatistics.statisticDate((ArrayList<Article>) articleManager.getInitialArticles());
        stringBuilder.append("\nArticles moyen par jour : ")
                .append((nbArticlesByDay == 0) ? "Moins d'un par jour" : nbArticlesByDay)
                .append('\n');

        stringBuilder.append("\nAuteurs les plus actifs :").append('\n');
        for (String author : ArticlesStatistics.statisticAuthors((ArrayList<Article>) articleManager.getInitialArticles())) {
            stringBuilder.append("- ")
                    .append(author)
                    .append('\n');
        }

        if (!keywordsPredicate.getText().isEmpty()) {
            ArrayList<String> keywordsPredicat = new ArrayList<>();
            keywordsPredicat.addAll(Arrays.asList(keywordsPredicate.getText().split(",\n")));
            String statOnTxt = ArticlesStatistics.statisticOnText((ArrayList<Article>) articleManager.getInitialArticles(), keywordsPredicat);
            stringBuilder.append("\nMot-clé le plus utilisé parmi les mots-clés :").append('\n')
                    .append(statOnTxt);
        }

        articleView.getChildren().clear();
        articleView.getChildren().add(new Text(stringBuilder.toString()));
        favCheckBox.setDisable(true);
        btnDownload.setDisable(true);
    }


    // EXTRA USER INTERFACE CONTROL

    /**
     * Used to disable main inputs and prevent user interaction
     * @author VIZCAINO Yohan (Airels)
     */
    public void disableAllInputs() {
        menuBar.setDisable(true);
        categoryChoiceBox.setDisable(true);
        periodDatePickerStart.setDisable(true);
        periodDatePickerEnd.setDisable(true);
        authorsPredicate.setDisable(true);
        keywordsPredicate.setDisable(true);
        btnDownloadAll.setDisable(true);
    }

    /**
     * Used to enable main inputs and authorise user to interact with interface
     * @author VIZCAINO Yohan (Airels)
     */
    public void enableAllInputs() {
        menuBar.setDisable(false);
        categoryChoiceBox.setDisable(false);
        periodDatePickerStart.setDisable(false);
        periodDatePickerEnd.setDisable(false);
        authorsPredicate.setDisable(false);
        keywordsPredicate.setDisable(false);
        btnDownloadAll.setDisable(false);
    }


    // ALERTS / POPUPS

    /**
     * Prompt an error pop-up when method called
     *
     * @param subtitle Subtitle of the error
     * @param message Error message
     * @author VIZCAINO Yohan (Airels)
     */
    private void showErrorMessage(String subtitle, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur - ArxivOrg");
        alert.setHeaderText(subtitle);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Prompt an information pop-up when method called
     *
     * @param subtitle Subtitle of the notification
     * @param message Notification message
     * @author VIZCAINO Yohan (Airels)
     */
    private void showInfoMessage(String subtitle, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information - ArxivOrg");
        alert.setHeaderText(subtitle);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * Prompt an information pop-up when method called but cannot be closed by user and need to be open manually
     * @param subtitle Subtitle of the notification
     * @param message Notification message
     * @return Alert dialog to close him when needed
     */
    private Alert prepareWaitingMessage(String subtitle, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information - ArxivOrg");
        alert.setHeaderText(subtitle);
        alert.setContentText(message);
        alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        alert.getDialogPane().getScene().getWindow().setOnCloseRequest(Event::consume);

        return alert;
    }

    /**
     * Prompt a confirmation pop-up when method called
     *
     * @param subtitle Subtitle of the notification
     * @param message Notification message
     * @return User choice
     * @author VIZCAINO Yohan (Airels)
     */
    private boolean showConfirmationMessage(String subtitle, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation - ArxivOrg");
        alert.setHeaderText(subtitle);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    /**
     * Prompt a Directory Chooser when method called
     *
     * @return File: Path of the directory selected by user (MAY BE NULL)
     * @author VIZCAINO Yohan (Airels)
     */
    private File showDirectoryChooser() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Télécharger l'article sous...");
        dc.setInitialDirectory(new File(System.getProperty("user.home")));

        return dc.showDialog(btnDownload.getScene().getWindow());
    }
}
