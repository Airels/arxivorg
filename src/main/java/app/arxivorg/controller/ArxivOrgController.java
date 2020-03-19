package app.arxivorg.controller;

import Utils.XmlReader;
import app.arxivorg.model.Article;
import app.arxivorg.model.Category;
import javafx.collections.FXCollections;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ArxivOrgController implements Initializable {

    @FXML private ListView articlesList;
    @FXML private ChoiceBox categoryChoiceBox;
    @FXML private ChoiceBox periodChoiceBox;
    @FXML private TextFlow articleView;
    @FXML private ScrollPane scrollPaneArticleView;
    @FXML private CheckBox favCheckBox;
    @FXML private Button btnDownload;

    //@Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        scrollPaneArticleView.setFitToWidth(true);
        scrollPaneArticleView.setFitToHeight(true);

        favCheckBox.setDisable(true);
        btnDownload.setDisable(true);

        generateCategoryChoiceBox();
        generatePeriodChoiceBox();
        generateArticlesList();
    }

    private void generateCategoryChoiceBox() {
        List<Category> categories = new ArrayList<>();

        for (Category category : Category.values())
            categories.add(category);

        categoryChoiceBox.setItems(FXCollections.observableArrayList(categories));
        categoryChoiceBox.setValue(Category.All);
    }

    private void generatePeriodChoiceBox() {
        String periods[] = {"A", "B", "C"};
        periodChoiceBox.setItems(FXCollections.observableArrayList(periods));
        periodChoiceBox.setValue("A");
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
}
