package view;

/**
 * Class:  LoginController
 * Copied/Modified from: R. Price
 * Modifed by: Annette Vinson
 * October 25, 2020
 * For: ITEC 3860 Project RedStar
 */

import model.LoginDB;
import model.SQLiteDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;


public class LoginController {
    @FXML private TextField userTF;
    @FXML private PasswordField passwordPF;
    @FXML private TextField errorMsg;

    private static SQLiteDB sdb;
    boolean validLogin;

    /*
     * Method: getDB
     * @return the db
     */
    public static SQLiteDB getDB() {
        try {
            sdb = new SQLiteDB("User.db");
        }
        catch (ClassNotFoundException | SQLException | RuntimeException e) {
            System.out.println("ERROR!\nThere was a problem opening the database \n" + e.getMessage());
        }

        return sdb;
    }

    @FXML
    protected void login(ActionEvent event) {
        errorMsg.setText("");
        validLogin = false;
        try {
            String userID = userTF.getText();
            String pwd = passwordPF.getText();
            LoginDB logDb = new LoginDB();
            validLogin = logDb.getUser(userID, pwd);
            if (!validLogin) {
                errorMsg.setText("Invalid login");
            }

        } catch (SQLException sqe) {
            errorMsg.setText("Invalid login");
        }

            /* valid user?
                fxml loader
             */
        if (validLogin) {
            Stage stage = new Stage();
            try {
                stage = (Stage) userTF.getScene().getWindow();
                stage.close();

                /* --------------------------- */
                /* Load stage for first screen */
                /* --------------------------- */
                FXMLLoader loader = new FXMLLoader();
                GridPane root = loader.load(getClass().getResource("GameUI.fxml"));
                Scene scene = new Scene(root, 700, 425);
                stage.setScene(scene);
                stage.setTitle("Project RedStar");
                ControllerUI uic = (ControllerUI) loader.getController();
//            uic.setUser(userID);
                stage.show();
            } catch (Exception e) {
                errorMsg.setText("Error displaying screen");
            }
        }
    }


    @FXML
    protected void createUser(ActionEvent event) {
        errorMsg.setText("");
        String userID = userTF.getText();
        String pwd = passwordPF.getText();
        LoginDB logDb = new LoginDB();
        boolean userAdded = logDb.addUser(userID, pwd);
        if (!userAdded) {
            errorMsg.setText("Error adding new user");
        }
    }

    @FXML
    protected void cancel(ActionEvent event) {
        Stage stage = (Stage) userTF.getScene().getWindow();
        stage.close();
    }
}

