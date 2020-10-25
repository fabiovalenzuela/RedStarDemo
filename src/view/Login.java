package view;

/**
 * Class: Login
 * Copied from: R. Price
 * Modifed by: Annette Vinson
 * October 25, 2020
 * For: ITEC 3860 Project RedStar
 *
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Login extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            GridPane root = loader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(root, 400, 275);
            scene.getStylesheets().add(getClass().getResource("game.css").toExternalForm());

            BackgroundImage bi = new BackgroundImage(new Image("view/bground.jpg"), null, null, null, null);
            root.setBackground(new Background(bi));

            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

