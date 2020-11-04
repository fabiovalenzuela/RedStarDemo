package model;

import controller.GameController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Class: GameDBCreate
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 28, 2020
 * For: ITEC 3860 Project RedStar
 * Copied/modified from Rick Price GameDBCreate
 */
public class GameDBCreate {
    SQLiteDB sdb;

    /*
     * Method: buildTables
     * Purpose: Build all tables
     * @return void
     * @throws SQLException
     */
    public void buildTables() throws SQLException {

        String gameID = GameController.gameID;

        buildTable(gameID + "BuildRoom.txt");
        buildTable(gameID + "BuildExit.txt");
        buildTable(gameID + "BuildMonster.txt");
        buildTable(gameID + "BuildPlayer.txt");
        buildTable(gameID + "BuildCharacter.txt");
        buildTable(gameID + "BuildExitRoom.txt");
        buildTable(gameID + "BuildCharText.txt");
        buildTable(gameID + "BuildItem.txt");
        buildTable(gameID + "BuildPuzzle.txt");
    }

    /*
     * Method: buildTable
     * Purpose: Build a table and load data
     * @return void
     * @throws SQLException
     */
    public void buildTable(String fileName) throws SQLException {
        sdb = GameController.getDB();

        FileReader fr;
        try {
            fr = new FileReader(fileName);
            Scanner inFile = new Scanner(fr);
            while (inFile.hasNextLine()) {
                String sql = inFile.nextLine();
                sdb.updateDB(sql);
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
    }

}
