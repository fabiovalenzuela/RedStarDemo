package model;

import controller.GameController;
import controller.Item;
import exceptions.InvalidGameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
     * Method: getItem
     * Purpose: Gets a item based upon the supplied ID
     *
     * @param id
     * @return Item
     * @throws SQLException
     */
    public Item getItem(int id) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        Item item = new Item();
        String sql = "Select * from Item WHERE itemID = " + id;
        ResultSet rs = sdb.queryDB(sql);
        if (rs.next()) {
            item.setItemID(rs.getInt("itemID"));
            item.setName(rs.getString("name"));
            item.setDescription(rs.getString("description"));
            item.setDamageRate(rs.getInt("damageRate"));
            item.setItemRoomID(rs.getInt("itemRoomID"));
        } else {
            throw new SQLException("Item " + id + " not found");
        }

        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
        return item;
    }

    /*
     * Method: updateItemRoom
     * Purpose: set visited = 0 for all items
     * @throws SQLException
     */
    public void updateItemRoom(int itemID, int roomID) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update Item set itemRoomID = " + roomID + " where itemID = " + itemID;
        sdb.updateDB(sql);
        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
    }

    /**
     * Method: getItemDesc
     * Purpose: Get item descriptions for a room
     * @return String
     * @throws SQLException
     */
    public String getItemDesc(int roomID) throws SQLException, InvalidGameException {
        String itemDesc = "";
        SQLiteDB sdb = GameController.getDB();
        String sql = "Select * from Item where itemRoomID = " + roomID;

        if (roomID == 0) {
            itemDesc = "\nItems in backpack:  ";
        } else {
            itemDesc = "\nItems in room " + roomID + ":  ";
        }

        ResultSet rs = sdb.queryDB(sql);

        try {
            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getInt("itemID"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setDamageRate(rs.getInt("damageRate"));
                item.setItemRoomID(rs.getInt("itemRoomID"));
                itemDesc = itemDesc + "itemID = " + item.getItemID() +
                        ", name = " + item.getName() +
                        ", description = " + item.getDescription() + "\n";
            }
        } catch (SQLException ige) {
            throw new InvalidGameException(ige.getMessage());
        }

        return itemDesc;
    }

    /**
     * Method: getAllItems
     * Purpose: Handles the DB interactions to retrieve all items
     * @return ArrayList<Item>
     * @throws SQLException
     */
    public ArrayList<Item> getAllItems() throws SQLException, InvalidGameException {
        ArrayList<Item> items = new ArrayList<Item>();
        SQLiteDB sdb = GameController.getDB();
        String sql = "Select * from Item";

        ResultSet rs = sdb.queryDB(sql);

        try {
            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getInt("itemID"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setDamageRate(rs.getInt("damageRate"));
                item.setItemRoomID(rs.getInt("itemRoomID"));
                items.add(item);
            }
        } catch (SQLException ige) {
            throw new InvalidGameException(ige.getMessage());
        }

        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
        return items;
    }
}



