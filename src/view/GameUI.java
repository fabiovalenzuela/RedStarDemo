package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Class: GameUI
 * Author: Annette Vinson
 * Date: October 24, 2020
 * For: ITEC 3860 Project RedStar
 * Copied/modified from Rick Price GameUI
 */

public class GameUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
//        GameController gc = new GameController();
        FXMLLoader loader = new FXMLLoader();
        GridPane root = loader.load(getClass().getResource("GameUI.fxml"));
        Scene scene = new Scene(root, 700, 425);
        stage.setScene(scene);
        stage.setTitle("Mini Game 3");
        ControllerUI uic = (ControllerUI)loader.getController();
        stage.show();
    }
}

