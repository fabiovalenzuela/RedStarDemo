package model;

import controller.GameController;
import controller.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class: ItemDB
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 2t, 2020
 * For: ITEC 3860 Project RedStar
 * Copied/modified from Rick Price ItemDB
 */
public class ItemDB {
    /*
     * Method: getNextItemID
     * Purpose: gets the next ID for a item
     *
     * @return int
     */
    public int getNextItemID() throws SQLException {
        SQLiteDB sdb = null;
        try {
            sdb = new SQLiteDB();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        int next = sdb.getMaxValue("itemID", "item") + 1;
        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();

        return next;
    }

    /*
     * Method: getItemDisplay
     * Purpose: Gets a item based upon the supplied ID
     *
     * @param id
     * @return Item
     * @throws SQLException
     */
    public Item getItem(int id) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        Item it = new Item();
        String sql = "Select * from Item WHERE itemID = " + id;
        ResultSet rs = sdb.queryDB(sql);
        if (rs.next()) {
            it.setItemID(rs.getInt("itemID"));
            it.setName(rs.getString("name"));
            it.setDescription(rs.getString("description"));
        } else {
            throw new SQLException("Item " + id + " not found");
        }

        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
        return it;
    }

    /*
     * Method: updateItemRoom
     * Purpose: set visited = 0 for all items
     * @throws SQLException
     */
    public void updateItemRoom(int itemID, int roomID) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update ItemRoom set roomID = " + roomID + "where itemID = " + itemID;
        sdb.updateDB(sql);
        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
    }

}

