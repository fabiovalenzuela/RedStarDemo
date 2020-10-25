package model;

import controller.Exit;
import controller.GameController;
import exceptions.InvalidGameException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class: ExitDB
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 25, 2020
 * For: ITEC 3860 MiniGame3
 */
public class ExitDB {

    /*
     --------------------------
     getNextExitID
     Gets the next valid exitID
     --------------------------
    */
    public int getNextExitID() throws SQLException, InvalidGameException {
        SQLiteDB sdb = null;
        try {
            sdb = new SQLiteDB();
        }
        catch (ClassNotFoundException | SQLException ige) {
            throw new InvalidGameException(ige.getMessage());
        }
        int next = sdb.getMaxValue("exitID", "Exit") + 1;
        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
        return next;
    }

    /*
     ------------
     getExit
     Get one exit
     ------------
    */
    public Exit getExit(int id) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        Exit exit = new Exit();
        String sql = "Select * from Exit WHERE exitNumber = " + id;
        ResultSet rs = sdb.queryDB(sql);
        if (rs.next()) {
            exit.setExitID(rs.getInt("exitID"));
            exit.setDirection(rs.getString("direction"));
            exit.setDestination(rs.getInt("destination"));
        }
        else {
        	throw new SQLException("Exit " + id + " not found.");
		}

        /* Close the SQLiteDB connection since SQLite only allows one updater */
        sdb.close();
        return exit;
    }

}
