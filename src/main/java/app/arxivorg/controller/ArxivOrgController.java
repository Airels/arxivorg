package app.arxivorg.controller;

import Utils.XmlReader;
import app.arxivorg.model.Article;
import app.arxivorg.model.Category;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ArxivOrgController implements Initializable {

    @FXML private ListView articlesList;
    @FXML private ChoiceBox categoryChoiceBox;
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
        scrollPaneArticleView.setFitToWidth(true);
        scrollPaneArticleView.setFitToHeight(true);

        authorsPredicate.setWrapText(true);
        keywordsPredicate.setWrapText(true);

        favCheckBox.setDisable(true);
        btnDownload.setDisable(true);

        generateCategoryChoiceBox();
        generateArticlesList();

        periodDatePickerStart.setValue(LocalDate.now());
        periodDatePickerEnd.setValue(LocalDate.now());

        periodDatePickerStart.valueProperty().addListener(this::onDatePickerStartUpdate);
        periodDatePickerEnd.valueProperty().addListener(this::onDatePickerEndUpdate);
    }

    private void generateCategoryChoiceBox() {
        List<Category> categories = new ArrayList<>();

        for (Category category : Category.values())
            categories.add(category);

        categoryChoiceBox.setItems(FXCollections.observableArrayList(categories));
        categoryChoiceBox.setValue(Category.All);
    }

    private void generateArticlesList() {
        List<Article> articles = XmlReader.read("1.atom");
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
    @FXML
    public void onMouseClickArticle(MouseEvent event, Article article) { // EN DOUBLE CLICK OU SIMPLE CLICK ?
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

            favCheckBox.setDisable(false);
            btnDownload.setDisable(false);
        }
    }

    @FXML
    public void onDatePickerStartUpdate(ObservableValue<? extends LocalDate> ov, LocalDate oldValue, LocalDate newValue) {
        if (newValue.compareTo(periodDatePickerEnd.getValue()) > 0) {
            showErrorMessage("Date invalide !", "Impossible d'entrer une date supérieur à la date de fin");
            periodDatePickerStart.setValue(oldValue);
        }
    }

    @FXML
    public void onDatePickerEndUpdate(ObservableValue<? extends LocalDate> ov, LocalDate oldValue, LocalDate newValue) {
        if (newValue.compareTo(periodDatePickerStart.getValue()) < 0) {
            showErrorMessage("Date invalide !", "Impossible d'entrer une date inférieur à la date de début");
            periodDatePickerEnd.setValue(oldValue);
        } else if (newValue.compareTo(LocalDate.now()) > 0) {
            // showInfoMessage("Date invalide !", "Impossible d'entrer une date au delà du " + LocalDate.now() + ".\nDate réglé sur aujourd'hui");
            periodDatePickerEnd.setValue(LocalDate.now());
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
}
