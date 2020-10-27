package model;

import controller.CharText;
import controller.GameController;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class: CharacterDB
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 24, 2020
 * For: ITEC 3860 MiniGame3
 */
public class CharTextDB {

    /*
     -----------------
     getCharText for
     Get one character
     -----------------
    */
    public String getCharText(int id) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        CharText charText = new CharText();
        String sql = "Select * from CharText WHERE iD = " + id +
                " ORDER BY iD, seq, usedFlag";
        ResultSet rs = sdb.queryDB(sql);
        if (rs.next()) {
            charText.setiD(rs.getInt("iD"));
            charText.setSeq(rs.getInt("seq"));
            charText.setUsedFlag(rs.getInt("usedFlag"));
            charText.setText(rs.getString("text"));
        }
        else {
            throw new SQLException("Character Text " + id + " not found.");
        }


        /*
        Load room description
        */
        String desc[] = charText.getText().split("[|]");
        String textStr = "";
        for (String s : desc) {
            textStr = textStr + s + "\n";
        }

        /* Close the SQLiteDB connection since SQLite only allows one updater */
        sdb.close();
        return textStr;
    }

}
