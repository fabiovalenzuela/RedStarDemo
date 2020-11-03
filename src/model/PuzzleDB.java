package model;

import controller.GameController;
import controller.Puzzle;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Class: PuzzleDB
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 24, 2020
 * For: ITEC 3860 Project RedStar
 */

public class PuzzleDB {

    /*
     * Method: getNextPuzzleID
     * Purpose: gets the next ID for a puzzle
     *
     * @return int
     */
    public int getNextPuzzleID() throws SQLException {
        SQLiteDB sdb = null;
        try {
            sdb = new SQLiteDB();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        int next = sdb.getMaxValue("puzzleID", "puzzle") + 1;
        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();

        return next;
    }

    /*
     * Method: getPuzzle
     * Purpose: Gets a puzzle based upon the supplied ID
     *
     * @param id
     * @return Puzzle
     * @throws SQLException
     */
    public Puzzle getPuzzle(int id) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        Puzzle puzzle = new Puzzle();
        String sql = "Select * from Puzzle WHERE puzzleID = " + id;
        ResultSet rs = sdb.queryDB(sql);
        if (rs.next()) {
            puzzle.setPuzzleID(rs.getInt("puzzleID"));
            puzzle.setPuzzleName(rs.getString("puzzleName"));
            puzzle.setPuzzleDescription(rs.getString("puzzleDescription"));
            puzzle.setPuzzleType(rs.getString("puzzleType"));
            puzzle.setPuzzleRoomID(rs.getInt("puzzleRoomID"));
        } else {
            throw new SQLException("Puzzle " + id + " not found");
        }

        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
        return puzzle;
    }

    /*
     * Method: updatePuzzleRoom
     * Purpose: set visited = 0 for all puzzles
     * @throws SQLException
     */
    public void updatePuzzleRoom(int puzzleID, int roomID) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update PuzzleRoom set roomID = " + roomID + "where puzzleID = " + puzzleID;
        sdb.updateDB(sql);
        //Close the SQLiteDB connection since SQLite only allows one update
        sdb.close();
    }
}
