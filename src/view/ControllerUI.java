package view;

/**
 * Class: ControllerUI
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 25, 2020
 * For: ITEC 3860 Project RedStar
 */

import controller.GameController;
import controller.Room;
import exceptions.InvalidGameException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;


public class ControllerUI {
    @FXML private TextArea descTA;
    @FXML private TextField commandTF;
    @FXML private Button okBtn;
    @FXML private TextField msgTF;

    private GameController gc = new GameController();

    @FXML
    public void initialize() {
        descTA.setText(gc.getWelcome());
        okBtn.setText("Start");
        msgTF.clear();
    }

    @FXML
    protected void process(ActionEvent event) throws SQLException {
        String btnText = okBtn.getText();
        msgTF.clear();
        // 1st time
        if (btnText.equalsIgnoreCase("Start")) {
            okBtn.setText("Ok");
            try {
                // Set visited = 0
                gc.updateVisited();
                // Get room 1
                descTA.setText(gc.getRoomData(1));
            } catch (SQLException sqe) {
                msgTF.setId("#errorMsg");
                msgTF.setText(sqe.getMessage());
            }
        // ---------------
        // process command
        // ---------------
        } else if (!commandTF.getText().isEmpty()) {
            try {
                String command = gc.checkCommand(commandTF.getText());
                Room room = gc.room;
                int nextRoomID = room.verifyDirection(command);
                if (nextRoomID <= 0) {
                    throw new InvalidGameException("Invalid direction");
                }
                descTA.setText(gc.getRoomData(nextRoomID));
                commandTF.setText("");
            } catch (InvalidGameException e) {
                msgTF.setId("#errorMsg");
                msgTF.setText(e.getMessage());
            }
        }
        // Invalid command entered
        else {
            msgTF.setText("Must enter a valid command");
        }


    }


    @FXML
    protected void cancel(ActionEvent event) {
        Stage stage = (Stage) commandTF.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

}
