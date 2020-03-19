package app.arxivorg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ArxivOrg extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/app/arxivorg/view/arxivorg.fxml"));
        primaryStage.setTitle("ArxivOrg");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(570);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}
