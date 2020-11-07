package controller;

import exceptions.InvalidGameException;
import model.PlayerDB;
import model.SQLiteDB;

import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Class: Player
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 25, 2020
 * For: ITEC 3860 Project
 * Copied/modified from Rick Price Player
 */
public class Player extends Being {


    /*
     * Constructor: Player
     */
    public Player() {
        super();
//        PlayerDB mdb = new PlayerDB();
//        try {
//            setID(mdb.getNextPlayerID());
//        } catch (SQLException sqe) {
//            System.out.println(sqe.getMessage());
//        }
    }

    /*
     * Method: getPlayer
     * Purpose: Gets a specified player from the Player table
     * @param id
     * @return Player
     * @throws SQLException
     */
    public Player getPlayer(int id) throws SQLException, InvalidGameException {
        PlayerDB mdb = new PlayerDB();
        return mdb.getPlayer(id);
    }

    /*
     * Method: getAllPlayers
     * Purpose: gets all players from the Player table
     * @return ArrayList<Player>
     * @throws SQLException
     */
    public ArrayList<Player> getAllPlayers() throws SQLException, InvalidGameException {
        PlayerDB mdb = new PlayerDB();
        return mdb.getAllPlayers();
    }

    /*
     * Method: updateVisited
     * Purpose: set visited = 0 for all rooms
     * @throws SQLException
     */
    public void updatePlayer() throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update Player set iD = " + getID() +
                ", name = '" + getName() +
                "', description = '" + getDescription() +
                "', health = " + getHealth() +
                ", minDamage = " + getMinDamage() +
                ", maxDamage = " + getMaxDamage() +
                ", chanceHit = " + getChanceHit() +
                ", roomID = " + getRoomID() +
                " where iD = 1";
        sdb.updateDB(sql);
        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
    }

    /*
     * Method: toString
     * Purpose: Returns a String of the Player class
     * @return
     */
    @Override
    public String toString() {
        return "Player iD = " + getID() +
                "\nplayer name = " + getName() +
                "\nplayer description = " + getDescription() +
                "\nplayer health = " + getHealth() +
                "\nplayer minDamage = " + getMinDamage() +
                "\nplayer maxDamage = " + getMaxDamage() +
                "\nplayer chanceHit = " + getChanceHit() +
                "\nplayer roomID = " + getRoomID();
    }

}

