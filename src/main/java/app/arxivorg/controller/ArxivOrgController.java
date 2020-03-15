package app.arxivorg.controller;

import app.arxivorg.model.Article;
import app.arxivorg.model.Authors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class ArxivOrgController implements Initializable {

    @FXML private ListView articlesList;
    @FXML private ChoiceBox categoryChoiceBox;
    @FXML private ChoiceBox periodChoiceBox;

    //    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        generateCategoryChoiceBox();
        generatePeriodChoiceBox();
        generateArticlesList();
    }

    @FXML
    private void generateCategoryChoiceBox() {
        String categories[] = {"A", "B", "C"};
        categoryChoiceBox.setItems(FXCollections.observableArrayList(categories));
        categoryChoiceBox.setValue("A");
    }

    @FXML
    private void generatePeriodChoiceBox() {
        String periods[] = {"A", "B", "C"};
        periodChoiceBox.setItems(FXCollections.observableArrayList(periods));
        periodChoiceBox.setValue("A");
    }

    // TODO : Afficher tout les auteurs au survol de la souris
    // TODO : Générer des vrais articles quand le lecteur XML sera prêt
    @FXML
    private void generateArticlesList() {
        // XmlReader reader = new XmlReader();

        ObservableList articles = FXCollections.observableArrayList();
        List<String> authors = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            authors.add("auteur " + i);
        }

        for (int i = 0; i < 100; i++) {
            String string = "Titre " + i + "\n\t";

            for (int authorID = 0; authorID < 3; authorID++)
                string += authors.get(authorID) + ", ";

            if (authors.size() > 3)
                string += "+" + (authors.size()-3) + " autres";

            articles.add(string);
        }

        articlesList.setItems(articles);

        /* for (Article article : reader.....) {
            articlesListText.append(article.)
        } */
    }

}
