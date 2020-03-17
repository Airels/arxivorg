package app.arxivorg.controller;

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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;
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

    //    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        generateCategoryChoiceBox();
        generatePeriodChoiceBox();
        generateArticlesList();
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

    // TODO : Afficher tout les auteurs au survol de la souris
    // TODO : Générer des vrais articles quand le lecteur XML sera prêt
    private void generateArticlesList() {
        // XmlReader reader = new XmlReader();

        List<String> authors = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            authors.add("auteur " + i);
        }

        for (int i = 0; i < 100; i++) {
            Article article = new Article(
                    "Titre " + i,
                    new Authors(authors),
                    "Hello world!",
                    Category.Physics,
                    new SubCategories()
            );

            articlesList.getItems().add(article);
        }

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

                    for (int i = 0; i < 3; i++)
                        lineContent += article.getAuthors().get().get(i) + ", "; // TODO : Attente de modifications de Tom

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

        /* for (Article article : reader.....) {
            articlesListText.append(article.)
        } */
    }

    @FXML
    public void onMouseClickArticle(MouseEvent event, Article article) { // EN DOUBLE CLICK OU SIMPLE CLICK ?
        if (event.getButton() == MouseButton.PRIMARY) {
            String textArticle = "";

            textArticle += article.getTitle() + "\n\t";
            textArticle += article.getAuthors().get() + "\n";
            textArticle += "--------------------\n";
            textArticle += "Résumé: ";
            textArticle += article.getContent();


            articleView.getChildren().clear();
            articleView.getChildren().add(new Text(textArticle));
        }
    }

    @FXML
    public void onMouseHoverArticle() {

    }
}
