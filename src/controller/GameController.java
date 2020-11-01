package controller;

/*
 * Class: GameController
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 28, 2020
 * For: ITEC 3860 Project
 */

import exceptions.InvalidGameException;
import model.CharTextDB;
import model.RoomDB;
import model.SQLiteDB;

import java.sql.SQLException;

/*
 * Class: GameController
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 6, 2020
 * For: ITEC 3860 MiniGame3
 * Some code copied from Dr R Price
 */
public class GameController {
    private static SQLiteDB sdb;
    public Room room = new Room();

    public GameController() {
        super();
    }

    /*
     * Method: getDB
     * @return the db
     */
    public static SQLiteDB getDB() {
        try {
            sdb = new SQLiteDB();
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println("ERROR!\nThere was a problem opening the database \n" + e.getMessage());
        }

        return sdb;
    }

    /*
     * Method: getRoomData
     * Purpose: gets room data and returns a String containing it
     * @return String
     * @throws SQLException
     */
    public String getRoomData(int roomID) throws SQLException, InvalidGameException {
        RoomDB rdb = new RoomDB();
        room = rdb.getRoom(roomID);
        rdb.updateVisited(roomID);
        return room.getRoomDisplay();
    }

    /*
     * Method: updateVisited
     * Purpose: sets visited flag to zero for all rooms
     * @throws SQLException
     */
    public void updateAllVisited() throws SQLException {
        RoomDB rdb = new RoomDB();
        rdb.updateAllVisited();
    }


    /*
     * Method: updateAllUsed
     * Purpose: sets usedFlag to zero for all CharText
     * @throws SQLException
     */
    public void updateAllUsed() throws SQLException {
        CharTextDB cdb = new CharTextDB();
        cdb.updateAllUsed();
    }
    /*
     ---------------------------------------------
     checkCommand
     Check for commands and process them if found.
     Return true if a command is found
     ---------------------------------------------
    */
    public String checkCommand(String command) throws InvalidGameException {

        String firstChar;
        String commandStr = "";

        /* get the 1st character of the command entered */
        firstChar = command.substring(0, 1);

        /* Set command string */
        /* if (command.matches("north|south|east|west|n|s|e|w")) { */
        if (command.equalsIgnoreCase("EAST") ||
                (firstChar.equalsIgnoreCase("E"))) {
            commandStr = "EAST";
        }
        else if (command.equalsIgnoreCase("WEST") ||
                (firstChar.equalsIgnoreCase("W"))) {
            commandStr = "WEST";
        }
        else if (command.equalsIgnoreCase("NORTH") ||
                (firstChar.equalsIgnoreCase("N"))) {
            commandStr = "NORTH";
        }
        else if (command.equalsIgnoreCase("SOUTH") ||
                (firstChar.equalsIgnoreCase("S"))) {
            commandStr = "SOUTH";
        }
        else if (command.equalsIgnoreCase("UP") ||
                (firstChar.equalsIgnoreCase("U"))) {
            commandStr = "UP";
        }
        else if (command.equalsIgnoreCase("DOWN") ||
                (firstChar.equalsIgnoreCase("D"))) {
            commandStr = "DOWN";
       }
        else if (command.equalsIgnoreCase("GET") ||
                (command.equalsIgnoreCase("TAKE")) ||
                (command.equalsIgnoreCase("PICKUP")) ||
                (firstChar.equalsIgnoreCase("G"))) {
            commandStr = "GET";
        } else if (command.equalsIgnoreCase("REMOVE") ||
                (command.equalsIgnoreCase("DROP")) ||
                (firstChar.equalsIgnoreCase("R"))) {
            commandStr = "REMOVE";
        } else if (command.equalsIgnoreCase("LOOK") ||
                (firstChar.equalsIgnoreCase("L"))) {
            commandStr = "LOOK";
        } else if (command.equalsIgnoreCase("BACKPACK") ||
                (firstChar.equalsIgnoreCase("B"))) {
            commandStr = "BACKPACK";
        } else if (command.equalsIgnoreCase("TALK") ||
                (firstChar.equalsIgnoreCase("T"))) {
            commandStr = "TALK";
        } else {
            throw new InvalidGameException("Invalid Command");
        }

        return commandStr;

    }

    /*
     -----------------------------------
     getWelcome
     Get welcome message to be displayed
     -----------------------------------
    */
    public String getWelcome() {
        return ("Welcome to Project RedStar. \n" +
                "You will proceed through rooms based upon your entries.\n" +
                "You can navigate by using the entire direction \n" +
                "or just the first letter.\n" +
                "To exit the game, press Cancel\n\n" +
                "Press Start to begin.");
    }


}
