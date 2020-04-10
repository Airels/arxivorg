package app.arxivorg.controller;

import app.arxivorg.model.Category;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

@ExtendWith(ApplicationExtension.class)
public class ArxivOrgControllerTest {

    @Start
    public void start(Stage stage) throws Exception {
        Parent main = FXMLLoader.load(getClass().getResource("/app/arxivorg/view/arxivorg.fxml"));
        stage.setScene(new Scene(main));
        stage.show();
    }

    @Test
    void testArticles(FxRobot robot) {
        robot.clickOn("#articlesList");

        // TODO : CHECK ARTICLES VIEW IF NOT EMPTY
    }

    // PREDICATES
    @Test
    void testCategory(FxRobot robot) {
        robot.clickOn("#categoryChoiceBox");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);

        ChoiceBox<Category> choiceBox = (ChoiceBox<Category>) robot.lookup("#categoryChoiceBox").tryQuery().get();
        Assertions.assertThat(choiceBox.getSelectionModel().getSelectedIndex()).isEqualTo(1);

    }

    @Test
    void testPeriods(FxRobot robot) {
        ListView lv = robot.lookup("#articlesList").queryListView();
        int size = lv.getItems().size();

        robot.clickOn("#periodDatePickerStart");
        robot.write("30/01/2000");

        Assertions.assertThat(lv.getItems().size()).isNotEqualTo(size);
    }

    @Test
    void testAuthors(FxRobot robot) {
        ListView lv = robot.lookup("#articlesList").queryListView();
        int size = lv.getItems().size();

        robot.clickOn("#authorsPredicate");
        robot.write("isaypoopythings,");

        Assertions.assertThat(lv.getItems().size()).isNotEqualTo(size);
    }

    @Test
    void testKeywords(FxRobot robot) {
        ListView lv = robot.lookup("#articlesList").queryListView();
        int size = lv.getItems().size();

        robot.clickOn("#keywordsPredicate");
        robot.write("notavalidwordlolelolol,");

        Assertions.assertThat(lv.getItems().size()).isNotEqualTo(size);
    }
}