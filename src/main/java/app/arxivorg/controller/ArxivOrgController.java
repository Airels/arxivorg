package app.arxivorg.controller;

import Utils.XmlReader;
import app.arxivorg.model.Article;
import app.arxivorg.model.Authors;
import app.arxivorg.model.Category;
import app.arxivorg.model.SubCategories;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class ArxivOrgController implements Initializable {

    @FXML private ListView articlesList;
    @FXML private ChoiceBox categoryChoiceBox;
    @FXML private ChoiceBox periodChoiceBox;
    @FXML private TextFlow articleView;
    @FXML private ScrollPane scrollPaneArticleView;

    //    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        generateCategoryChoiceBox();
        generatePeriodChoiceBox();
        generateArticlesList();
        scrollPaneArticleView.setFitToWidth(true);
        scrollPaneArticleView.setFitToHeight(true);
    }

    private void generateCategoryChoiceBox() {
        String categories[] = {"A", "B", "C"};
        categoryChoiceBox.setItems(FXCollections.observableArrayList(categories));
        categoryChoiceBox.setValue("A");
    }

    private void generatePeriodChoiceBox() {
        String periods[] = {"A", "B", "C"};
        periodChoiceBox.setItems(FXCollections.observableArrayList(periods));
        periodChoiceBox.setValue("A");
    }

    private void generateArticlesList() {
        List<Article> articles = XmlReader.Reader(new File("1.atom"));
        articlesList.getItems().addAll(articles);

        // Génération affichage éléments
        articlesList.setCellFactory(cell -> new ListCell<Article>() {
            final Tooltip tooltip = new Tooltip();

            @Override
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

                    EventHandler<MouseEvent> eventHandler = e -> onMouseClickArticle(e, article);
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
        }
    }
}
