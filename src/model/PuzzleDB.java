package model;

import controller.GameController;
import controller.Puzzle;
import exceptions.InvalidGameException;

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
            sdb = new SQLiteDB(GameController.dbName);
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
    public Puzzle getPuzzle(int id) throws SQLException, InvalidGameException {
        SQLiteDB sdb = GameController.getDB();
        Puzzle puzzle = new Puzzle();

        String sql = "Select * from Puzzle WHERE puzzleID = " + id;
        ResultSet rs = sdb.queryDB(sql);

        if (rs.next()) {
            loadPuzzle(rs, puzzle);
        } else {
            throw new SQLException("Puzzle " + id + " not found");
        }

        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
        return puzzle;
    }

    /*
     * Method: updatePuzzleRoom
     * Purpose: set visited = 0 for all puzzles
     * @throws SQLException
     */
    public void updatePuzzleUsed(int puzzleID) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update Puzzle set puzzleUsed = 1 where puzzleID = " + puzzleID;
        sdb.updateDB(sql);
        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
    }


    /*
     * Method: updateSql
     * Purpose: runs the SQL from  puzzleSql
     * @throws SQLException
     */
    public void updateSql(String sql) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        sdb.updateDB(sql);
        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
    }


    /*
     * Method: puzzleSql
     * Purpose: run Sql from the puzzle to see
     *          if this is a valid puzzle
     * return true if a puzzle was found
     * @throws SQLException
     */
    public boolean puzzleSql(String sql, Puzzle puzzle) throws SQLException, InvalidGameException {
        SQLiteDB sdb = GameController.getDB();
        boolean valid = false;

        ResultSet rs = sdb.queryDB(sql);

        if (rs.next()) {
            loadPuzzle(rs, puzzle);
            valid = true;
        }
        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
        return valid;
    }


    public void loadPuzzle(ResultSet rs, Puzzle puzzle) throws SQLException, InvalidGameException {
        puzzle.setPuzzleID(rs.getInt("puzzleID"));
        puzzle.setPuzzleName(rs.getString("puzzleName"));
        puzzle.setPuzzleDesc(rs.getString("puzzleDesc"));
        puzzle.setPuzzleType(rs.getString("puzzleType"));
        puzzle.setPuzzleInRoom(rs.getInt("puzzleInRoom"));
        puzzle.setPuzzleMonID(rs.getInt("puzzleMonID"));
        puzzle.setPuzzleItemID(rs.getInt("puzzleItemID"));
        puzzle.setPuzzleRoomID(rs.getInt("puzzleRoomID"));
        int used = (rs.getInt("puzzleUsed"));
        if (used == 0) {
            puzzle.setPuzzleUsed(false);
        }
        else {
            puzzle.setPuzzleUsed(true);
        }
        puzzle.setPuzzleVerb(rs.getString("puzzleVerb"));
        puzzle.setPuzzleNoun(rs.getString("puzzleNoun"));
        puzzle.setPuzzleSql(rs.getString("puzzleSql"));
        puzzle.setPuzzleObject(rs.getString("puzzleObject"));
        puzzle.setPuzzleText(rs.getString("puzzleText"));
    }
}
