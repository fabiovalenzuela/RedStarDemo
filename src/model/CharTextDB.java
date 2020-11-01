package model;

import controller.CharText;
import controller.GameController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class: CharacterDB
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 24, 2020
 * For: ITEC 3860 MiniGame3
 */
public class CharTextDB {

    public CharTextDB() {
    }

    /*
         -----------------
         getCharText for
         Get one character
         -----------------
        */
    public ArrayList<CharText> getCharText(int id) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Select * from CharText WHERE iD = " + id +
                " ORDER BY iD, seq";
        ResultSet rs = sdb.queryDB(sql);
        ArrayList<CharText> charTexts = new ArrayList<>();
        try {
            while (rs.next()) {
                CharText charText = new CharText();
                charText.setID(rs.getInt("iD"));
                charText.setSeq(rs.getInt("seq"));
                charText.setUsedFlag(rs.getInt("usedFlag"));
                charText.setText(rs.getString("text"));
                charText.setMonID(rs.getInt("monID"));
                charText.setItemID(rs.getInt("itemID"));
                charText.setRoomID(rs.getInt("roomID"));
                charTexts.add(charText);
            }
        } catch (SQLException sqe) {
        }

        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
        return charTexts;
    }

    /*
     * Method: updateUsedFlag
     * Purpose: set usedFlag = 1 for one CharText
     * @throws SQLException
     */
    public void updateUsedFlag(int iD, int seq) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update CharText set usedFlag = 1 where iD = " + iD +
                     " and seq = " + seq;
        sdb.updateDB(sql);
        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
    }

    /*
     * Method: updateAllUsed
     * Purpose: set usedFlag = 0 for all CharText
     * @throws SQLException
     */
    public void updateAllUsed() throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update CharText set usedFlag = 0 where usedFlag = 1";
        sdb.updateDB(sql);
        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
    }
}
