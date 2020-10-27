package controller;

import model.GameDBCreate;

import java.sql.SQLException;

/**
 * Class: CreateFilesController
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 24, 2020
 * For: ITEC 3860 Project RedStar
 * Copied from Rick Price CreateFilesController
 */
public class CreateFilesController {

    /**
     * Method: createFile
     * Purpose: Create the database for GameDB
     * @return void
     */
    public void createFile() {
        try {
            GameDBCreate sdb = new GameDBCreate();
            sdb.buildTables();
        }
        catch (SQLException e) {
            System.out.println("Creation failed");
        }
    }
}
