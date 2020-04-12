package app.arxivorg.controller;

import app.arxivorg.model.Article;
import app.arxivorg.model.Category;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import app.arxivorg.utils.PDFDownloader;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static app.arxivorg.model.Category.*;

public class ArxivOrgController implements Initializable {

    private ArticleManager articleManager;

    private Article selectedArticle;

    @FXML private ListView articlesList;
    @FXML private ChoiceBox<Category> categoryChoiceBox;
    @FXML private DatePicker periodDatePickerStart;
    @FXML private DatePicker periodDatePickerEnd;
    @FXML private TextFlow articleView;
    @FXML private ScrollPane scrollPaneArticleView;
    @FXML private TextArea authorsPredicate;
    @FXML private TextArea keywordsPredicate;
    @FXML private CheckBox favCheckBox;
    @FXML private Button btnDownload;

    //@Override
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

        btnDownload.addEventFilter(MouseEvent.MOUSE_CLICKED, this::onBtnDownloadClicked);

        articlesList.addEventFilter(ScrollEvent.ANY, this::onScrollArticleList);

        // ARTICLE MANAGER
        articleManager = new ArticleManager(this);
        articleManager.setPredicates(All, null, periodDatePickerStart.getValue(), periodDatePickerEnd.getValue(), null);
    }

    private void generateCategoryChoiceBox() {
        List<Category> categories = new ArrayList<>();

        for (Category category : Category.values())
            categories.add(category);

        categoryChoiceBox.setItems(FXCollections.observableArrayList(categories));
        categoryChoiceBox.setValue(All);
    }

    public void showArticles(List<Article> articles) {
        articlesList.getItems().clear();
        articlesList.getItems().addAll(articles);

        // Génération affichage éléments
        articlesList.setCellFactory(cell -> new ListCell<Article>() {
            final Tooltip tooltip = new Tooltip();

            @Override       // Affichage et définition actions pour chaque élément
            protected void updateItem(Article article, boolean isEmpty) {
                if (article == null || isEmpty) {
                    setText(null);
                    setTooltip(null);
                } else {
                    String lineContent = "";
                    lineContent += article.getTitle();
                    lineContent += "\n\t";

                    List<String> authors = article.getAuthors().getList();

                    for (int i = 0; i < 3 && i < authors.size(); i++)
                        lineContent += authors.get(i) + ", ";

                    if (authors.size() > 3)
                        lineContent += "+" + (authors.size()-3) + " autres";

                    setText(lineContent);

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
    @FXML void onCategoryChanged(Observable ov, Category oldCategory, Category newCategory) {
        if (!oldCategory.equals(newCategory))
            articleManager.setCategoryPredicate(newCategory);
    }

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
        }
    }

    @FXML
    public void onDatePickerStartUpdate(ObservableValue<? extends LocalDate> ov, LocalDate oldValue, LocalDate newValue) {
        if (newValue.compareTo(periodDatePickerEnd.getValue()) > 0) {
            // showErrorMessage("Date invalide !", "Impossible d'entrer une date supérieur à la date de fin");
            periodDatePickerStart.setValue(oldValue);
        } else {
            articleManager.setPeriodPredicate(periodDatePickerStart.getValue(), periodDatePickerEnd.getValue());
        }
    }

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
            }

            articleManager.setAuthorsPredicate(authors);
        } else if (authorsPredicate.getText().isEmpty()) {
            articleManager.setAuthorsPredicate(null);
        }
    }

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
            }

            articleManager.setKeywordsPredicate(keywords);
        } else if (keywordsPredicate.getText().isEmpty()) {
            articleManager.setKeywordsPredicate(null);
        }
    }

    @FXML
    public void onScrollArticleList(ScrollEvent event) {
        if (event.getDeltaY() < 0) { // if scroll down
            System.out.println("Scroll!");

            System.out.println(articlesList.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void onBtnDownloadClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            System.out.println(selectedArticle.getLink());

            File selectedFile = showFileChooser();

            if (selectedFile == null)
                return;

            if (PDFDownloader.downloadFile(selectedArticle, selectedFile))
                showInfoMessage("Téléchargement terminé",
                        "Le téléchargement de " + selectedArticle.getTitle() + " est terminé !");
            else
                showErrorMessage("Téléchargement échoué",
                        "Le téléchargement de " + selectedArticle.getTitle() + " a échoué !");
        }
    }


    // ALERTS / POPUPS
    private void showErrorMessage(String message, String subMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur - ArxivOrg");
        alert.setHeaderText(message);
        alert.setContentText(subMessage);
        alert.showAndWait();
    }

    private void showInfoMessage(String message, String subMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information - ArxivOrg");
        alert.setHeaderText(message);
        alert.setContentText(subMessage);
        alert.show();
    }

    private File showFileChooser() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Télécharger l'article sous...");
        dc.setInitialDirectory(new File(System.getProperty("user.home")));

        return dc.showDialog(btnDownload.getScene().getWindow());
    }
}
