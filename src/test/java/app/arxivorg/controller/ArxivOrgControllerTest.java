package app.arxivorg.controller;

import app.arxivorg.model.Category;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

public class ArxivOrgControllerTest extends ApplicationTest {

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

        Button btn = robot.lookup("#categoryChoiceBox").queryButton();
        Assertions.assertThat(btn).hasText(Category.Physics.toString());
    }

    @Test
    void testPeriods(FxRobot robot) {
        ListView lv = robot.lookup("#articlesList").queryListView();
        System.out.println(lv.getItems().size());


    }

    @Test
    void testAuthors(FxRobot robot) {
        ListView lv = robot.lookup("#articlesList").queryListView();
        System.out.println(lv.getItems().size());
        Assertions.assertThat(lv.getItems().size() > 0);

        robot.clickOn("#authorsPredicate");
        robot.write("isaypoopythings,");

        Assertions.assertThat(lv.getItems()).hasSize(0);
    }

    @Test
    void testKeywords(FxRobot robot) {
        ListView lv = robot.lookup("#articlesList").queryListView();
        System.out.println(lv.getItems().size());
        Assertions.assertThat(lv.getItems().size() > 0);

        robot.clickOn("#keywordsPredicate");
        robot.write("notavalidwordlolelolol,");

        Assertions.assertThat(lv.getItems()).hasSize(0);
    }
}