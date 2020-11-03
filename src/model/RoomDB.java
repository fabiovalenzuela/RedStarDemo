package model;

import controller.Character;
import controller.Exit;
import controller.GameController;
import controller.Item;
import controller.Monster;
import controller.Room;
import exceptions.InvalidGameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class: RoomDB
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 28, 2020
 * For: ITEC 3860 Project RedStar
 * Copied/modified from Rick Price RoomDB
 */
public class RoomDB {
    /*
     * Method: getNextRoomID
     * Purpose: gets the next ID for a room
     *
     * @return int
     */
    public int getNextRoomID() throws SQLException {
        SQLiteDB sdb = null;
        try {
            sdb = new SQLiteDB();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        int next = sdb.getMaxValue("roomID", "room") + 1;
        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();

        return next;
    }

    /*
     * Method: getRoomDisplay
     * Purpose: Gets a room based upon the supplied ID
     *
     * @param id
     * @return Room
     * @throws SQLException
     */
    public Room getRoom(int id) throws SQLException, InvalidGameException {
        SQLiteDB sdb = GameController.getDB();
        Room rm = new Room();
        String sql = "Select * from Room WHERE roomID = " + id;
        ResultSet rs = sdb.queryDB(sql);
        int visit = 0;
        int visble = 0;
        if (rs.next()) {
            rm.setRoomID(rs.getInt("roomID"));
            rm.setName(rs.getString("name"));
            rm.setDescription(rs.getString("description"));
            visit = rs.getInt("visited");
            if (visit == 0) {
                rm.setVisited(false);
            }
            else {
                rm.setVisited(true);
            }
            visble = rs.getInt("visible");
            if (visble == 0) {
                rm.setVisible(false);
            }
            else {
                rm.setVisible(true);
            }
        } else {
            throw new SQLException("Room " + id + " not found");
        }

        /* Get exits */
        ArrayList<Exit> exits = new ArrayList<Exit>();
        sql = "Select b.exitID, b.direction, b.destination " +
                "from ExitRoom a Inner Join Exit b " +
                "ON a.exitID = b.exitID " +
                "where roomID = " + id;

        rs = sdb.queryDB(sql);

        while (rs.next()) {
            Exit exit = new Exit();
            exit.setExitID(rs.getInt("exitID"));
            exit.setDirection(rs.getString("direction"));
            exit.setDestination(rs.getInt("destination"));
            exits.add(exit);
        }

        rm.setExits(exits);


        /* Get Characters */
        ArrayList<Character> characters = new ArrayList<>();
        sql = "Select * from Character " +
                "where roomID = " + id;

        rs = sdb.queryDB(sql);

        while (rs.next()) {
            Character character = new Character();
            character.setID(rs.getInt("iD"));
            character.setName(rs.getString("name"));
            character.setHealth(rs.getInt("health"));
            character.setMaxDamage(rs.getInt("maxDamage"));
            character.setMinDamage(rs.getInt("minDamage"));
            character.setChanceHit(rs.getDouble("chanceHit"));
            character.setRoomID(rs.getInt("roomID"));
            characters.add(character);
        }

        rm.setChars(characters);

        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();

        /* Get items */
        ArrayList<Item> items = new ArrayList<>();
        items = getRoomItems(id);
        rm.setItems(items);

        /* Get monsters */
        ArrayList<Monster> monsters = new ArrayList<>();
        monsters = getRoomMonsters(id);
        rm.setMonsters(monsters);
        return rm;
    }

    /* ---------------------------------------
        Method: getRoomItems
        Purpose: get all items in the room
                 and return in arraylist items
        --------------------------------------
    */
    public ArrayList<Item> getRoomItems(int roomID) throws SQLException {
        SQLiteDB sdb = GameController.getDB();

        /* Get items */
        ArrayList<Item> items = new ArrayList<Item>();
        String sql = "Select itemID, name, description, " +
                "damageRate, itemRoomID " +
                "from Item " +
                "where itemRoomID = " + roomID +
                " and itemUsed = 0";

        ResultSet rs = sdb.queryDB(sql);

        while (rs.next()) {
            Item item = new Item();
            item.setItemID(rs.getInt("itemID"));
            item.setName(rs.getString("name"));
            item.setDescription(rs.getString("description"));
            item.setDamageRate(rs.getInt("damageRate"));
            item.setItemRoomID(rs.getInt("itemRoomID"));
            items.add(item);
        }

        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
        return items;
    }

    /* ------------------------------------------
        Method: getRoomMonsters
        Purpose: get all monsters in the room
                 and return in arraylist monsters
        -----------------------------------------
    */
    public ArrayList<Monster> getRoomMonsters(int roomID) throws SQLException, InvalidGameException {
        SQLiteDB sdb = GameController.getDB();

        /* Get monsters */
        ArrayList<Monster> monsters = new ArrayList<Monster>();
        String sql = "Select * from Monster where roomID = " + roomID;

        ResultSet rs = sdb.queryDB(sql);

        while (rs.next()) {
            Monster monster = new Monster();
            monster.setID(rs.getInt("iD"));
            monster.setName(rs.getString("name"));
            monster.setDescription(rs.getString("description"));
            monster.setHealth(rs.getInt("health"));
            monster.setMaxDamage(rs.getInt("maxDamage"));
            monster.setMinDamage(rs.getInt("minDamage"));
            monster.setChanceHit(rs.getDouble("chanceHit"));
            monster.setRoomID(rs.getInt("roomID"));
            monsters.add(monster);
        }

        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
        return monsters;
    }

    /*
     * Method: updateVisited
     * Purpose: set visited = 0 for all rooms
     * @throws SQLException
     */
    public void updateVisited(int roomID) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update Room set visited = 1 where roomID = " + roomID;               ;
        sdb.updateDB(sql);
        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
    }
    /*
     * Method: updateAllVisited
     * Purpose: set visited = 0 for all rooms
     * @throws SQLException
     */
    public void updateAllVisited() throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update Room set visited = 0";
        sdb.updateDB(sql);
        /* Close the SQLiteDB connection since SQLite only allows one updater */
        sdb.close();
    }
}

