package view;

/**
 * Class: ControllerUI
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 29, 2020
 * For: ITEC 3860 Project RedStar
 */

import controller.Character;
import controller.CharText;
import controller.GameController;
import controller.Item;
import controller.Room;
import exceptions.InvalidGameException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CharacterDB;
import model.ItemDB;
import model.RoomDB;

import java.sql.SQLException;
import java.util.ArrayList;


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
        commandTF.setVisible(false);
        msgTF.setVisible(false);
    }

    /*
     ------------------------------------------------------------
     Method: process
     Process action when OK or ENTER pressed
     ------------------------------------------------------------
    */
    @FXML
    protected void process(ActionEvent event) throws SQLException {
        String btnText = okBtn.getText();
        msgTF.clear();

        /* -------- */
        /* 1st time */
        /* -------- */
        if (btnText.equalsIgnoreCase("Start")) {
            okBtn.setText("Ok");
            try {
                /* Set visited = 0 in Room */
                gc.updateAllVisited();
                /* Set usedFlag in CharText = 0 */
                gc.updateAllUsed();
                /* Get room 1 */
                descTA.setText(gc.getRoomData(1));
                commandTF.setVisible(true);
            } catch (SQLException | InvalidGameException sqe) {
                msgTF.setId("#errorMsg");
                msgTF.setVisible(true);
                msgTF.setText(sqe.getMessage());
            }
        /*
         ---------------
         process command
         ---------------
        */
        } else if (!commandTF.getText().isEmpty()) {
            try {
                /* extract command text to a string */
                String commandText = commandTF.getText();
                /* the first word in commandText should be a verb */
                String verb = this.getVerb(commandText);
                /* the 2nd word in commandText should be a noun or blanks */
                String noun = this.getNoun(commandText);
                /* check the verb for accuracy */
                String command = gc.checkCommand(verb);
                /* process the command */
                this.processCommand(command, noun);
            } catch (InvalidGameException e) {
                msgTF.setId("#errorMsg");
                msgTF.setText(e.getMessage());
                msgTF.setVisible(true);
            }
        }
        /* Invalid command entered */
        else {
            msgTF.setText("Must enter a valid command");
            msgTF.setVisible(true);
        }


    }


    @FXML
    protected void cancel(ActionEvent event) {
        Stage stage = (Stage) commandTF.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    /*
 -------------------
 processCommand
 Process the command
 -------------------
*/
    public void processCommand(String command, String noun) {
        try {
            switch (command) {
                case "GET":
                    processGET(noun);
                    break;
                case "REMOVE":
                    processREMOVE(noun);
                    break;
                case "TALK":
                    processTALK(noun);
                    break;
                case "BACKPACK":
                    processBACKPACK(noun);
                    break;
                case "LOOK":
                    processLOOK(noun);
                    break;
                case "ATTACK":
                    processATTACK(noun);
                    break;
                case "PUSH":
                    processPUSH(noun);
                    break;
                case "THROW":
                    processTHROW(noun);
                    break;
                /* Default checks for direction */
                default:
                    processROOM(command);
            }
        } catch (InvalidGameException | SQLException e) {
            msgTF.setId("#errorMsg");
            msgTF.setText(e.getMessage());
            msgTF.setVisible(true);
        }
    }

    /* ------------------------------------------
       Method: processGET
       If user enters a GET command, verify the
       item, put the item in the user's backpack
       (flag the itemRoomID = 0)
       ------------------------------------------ */
    private void processGET(String noun) throws InvalidGameException, SQLException {
        Item item = new Item();
        /* get current room ID */
        int roomID = gc.room.getRoomID();

        /* get item based on noun */
        try {
            item = this.getSelectedItem(noun);
        } catch (InvalidGameException ige) {
            throw new InvalidGameException(ige.getMessage());
        }

        /* Is item in current room */
        if (item.getItemRoomID() != roomID) {
            throw new InvalidGameException("Item not in room to get");
        }

        /* Update item room -- roomID -- set to 0 */
        try {
            ItemDB idb = new ItemDB();
            idb.updateItemRoom(item.getItemID(), 0);
        } catch (SQLException sqe) {
            throw new InvalidGameException("problem updating backpack");
        }

        /* get items in room */
        RoomDB rdb = new RoomDB();
        rdb.getRoomItems(roomID);

        /* get room data to update item list */
        descTA.setText(gc.getRoomData(roomID));
        commandTF.setText("");
        msgTF.setVisible(false);

    }

    /* ------------------------------------------
   Method: processREMOVE
   If user enters a REMOVE command, verify the
   item, put the item in the current room
   ------------------------------------------ */
    private void processREMOVE(String noun) throws InvalidGameException, SQLException {
        Item item = new Item();
        /* get current room ID */
        int roomID = gc.room.getRoomID();

        /* get item based on noun */
        try {
            item = this.getSelectedItem(noun);
        } catch (InvalidGameException ige) {
            throw new InvalidGameException(ige.getMessage());
        }

        /* Is item in current room */
        if (item.getItemRoomID() != 0) {
            throw new InvalidGameException("Item not in backpack to remove");
        }

        /* Update item room -- roomID -- set to current roomID */
        try {
            ItemDB idb = new ItemDB();
            idb.updateItemRoom(item.getItemID(), roomID);
        } catch (SQLException sqe) {
            throw new InvalidGameException("problem updating backpack");
        }

        /* get items in room */
        RoomDB rdb = new RoomDB();
        rdb.getRoomItems(roomID);

        /* get room data to update item list */
        descTA.setText(gc.getRoomData(roomID));
        commandTF.setText("");
        msgTF.setVisible(false);
    }

    /* ------------------------------------------
        Method: processTALK
        Talk to a character
    ------------------------------------------ */
    private void processTALK(String noun) throws InvalidGameException {
        String talkText = "";
        Character character = new Character();
        CharText charText = new CharText();

        /* get current room ID */
        int roomID = gc.room.getRoomID();

        /* split noun to check for to/with/the */
        String[] words = noun.split(" ");
        String name = noun;

        /* find the 1st word that is not to/with/the/a */
        for (String s : words) {
            if ((!s.equalsIgnoreCase("to")) &&
                    (!s.equalsIgnoreCase("with")) &&
                    (!s.equalsIgnoreCase("the")) &&
                    (!s.equalsIgnoreCase("on")) &&
                    (!s.equalsIgnoreCase("a"))) {
                name = s;
                break;
            }
        }

        try {
            /* get character by name */
            CharacterDB cdb = new CharacterDB();
            character = cdb.getCharByName(name);

            /* Is the character in this room? */
            if (roomID != character.getRoomID()) {
                talkText = "\nThe " + name + " is not here";
            } else {

                talkText = charText.getCharTextDisplay(character.getID());
            }

            /* get room data to update display text */
            String display = descTA.getText() + talkText;
            descTA.setText(display);
            commandTF.setText("");
            msgTF.setVisible(false);
        } catch (InvalidGameException | SQLException ige) {
            throw new InvalidGameException("This character has nothing to say");
        }

    }

    /* ------------------------------------------
        Method: processBACKPACK
        Show inventory in backpack
    ------------------------------------------ */
    private void processBACKPACK(String noun) throws InvalidGameException, SQLException {
        String display = "";

        Item item = new Item();
        /* get current room ID */
        int roomID = gc.room.getRoomID();

        /* get items in backpack (when roomID = 0 */
        RoomDB rdb = new RoomDB();
        rdb.getRoomItems(0);

        display = gc.getRoomData(roomID);

        ItemDB idb = new ItemDB();
        display = display + idb.getItemDesc(0);

        /* get room data to update item list */
        descTA.setText(display);
        commandTF.setText("");
        msgTF.setVisible(false);


    }

    private void processLOOK(String noun) throws SQLException, InvalidGameException {
        descTA.setText(gc.getRoomData(gc.room.getRoomID()));
        commandTF.setText("");
        commandTF.setVisible(false);
    }

    private void processATTACK(String noun) {

    }

    private void processPUSH(String noun) {

    }
    private void processTHROW(String noun) {

    }

    /*
     roomProcess
     Command string is passed in.
     */
    private void processROOM(String command) throws InvalidGameException, SQLException {
        Room room = gc.room;
        int nextRoomID = room.verifyDirection(command);
        if (nextRoomID <= 0) {
            throw new InvalidGameException("Invalid direction");
        }
        descTA.setText(gc.getRoomData(nextRoomID));
        commandTF.setText("");
        msgTF.setVisible(false);
    }

    private Item getSelectedItem(String noun) throws InvalidGameException {
        ArrayList<Item> items = new ArrayList<>();
        ItemDB idb = new ItemDB();
        try {
            items = idb.getAllItems();
        } catch (SQLException | InvalidGameException sqe) {
            throw new InvalidGameException("No items available");
        }

        Item item = new Item();
        try {
            item = idb.getItemByName(noun);
        } catch (InvalidGameException | SQLException ige) {
            throw new InvalidGameException("No item with that name");
        }
            return item;
    }


    /*
     ----------------------------------------------------
     getVerb
     Command string is passed in.
     The first part will be a verb: Get/Inspect/Remove
     The 2nd part will be a noun (or blanks)
     ----------------------------------------------------
    */
    public String getVerb(String command) {
        String verb = "";

        // get location of 1st space after G or GET in command line
        int spaceLoc = command.indexOf(" ");

        // if no spaces found, verb is entire command
        if (spaceLoc < 0) {
            verb = command;
        } else {
            // if a space is found, get the item name for the command
            verb = command.substring(0, spaceLoc);
        }
        return verb;
    }

    /*
     ----------------------------------------------------
     getNoun
     Command string is passed in.
     The first part will be a verb: Get/Inspect/Remove
     The 2nd part will be a noun (or blanks)
     ----------------------------------------------------
    */
    public String getNoun(String command) {
        String noun = "";

        // get location of 1st space after G or GET in command line
        int spaceLoc = command.indexOf(" ");

        // if no spaces found, verb is entire command
        if (spaceLoc < 0) {
            noun = "";
        } else {
            // if a space is found, get the item name for the command
            noun = command.substring(spaceLoc+1);
        }
        return noun;
    }
}
