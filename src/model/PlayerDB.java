package model;

import controller.GameController;
import controller.Player;
import exceptions.InvalidGameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Class: Player
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 24, 2020
 * For: ITEC 3860 Project RedStar
 */
public class PlayerDB {
    /**
     * Method: getNextPlayerID
     * Purpose: Gets the id for the next player.
     * @return int
     */
    public int getNextPlayerID() throws SQLException {
        SQLiteDB sdb = null;
        try {
            sdb = new SQLiteDB();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        int next = sdb.getMaxValue("playerNumber", "Player") + 1;
        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
        return next;
    }

    /**
     * Method: getPlayer
     * Purpose: handles db interactions to retrieve a single Player
     * @param id
     * @return Player
     * @throws SQLException
     */
    public Player getPlayer(int id) throws SQLException, InvalidGameException {
        SQLiteDB sdb = GameController.getDB();
        Player player = new Player();
        String sql = "Select * from Player WHERE playerNumber = " + id;
        ResultSet rs = sdb.queryDB(sql);
        try {
            if (rs.next()) {
                player.setID(rs.getInt("iD"));
                player.setName(rs.getString("name"));
                player.setDescription(rs.getString("description"));
                player.setHitPoints(rs.getInt("hitPoints"));
                player.setMaxDamage(rs.getInt("maxDamage"));
                player.setMinDamage(rs.getInt("minDamage"));
                player.setChanceHit(rs.getInt("chanceHit"));
                player.setRoomID(rs.getInt("roomID"));
            } else {
                throw new SQLException("Player " + id + " not found.");
            }
        } catch (InvalidGameException ige) {
            throw new InvalidGameException(ige.getMessage());
        }

        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
        return player;
    }

    /**
     * Method: updateVisited
     * Purpose: set visited = 0 for all rooms
     * @throws SQLException
     */
    public void updateHp(int hp) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update Player set hitPoints = " + hp;
        sdb.updateDB(sql);
        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
    }

    /**
     * Method: getAllPlayers
     * Purpose: Handles the DB interactions to retrieve all players
     * @return ArrayList<Player>
     * @throws SQLException
     */
    public ArrayList<Player> getAllPlayers() throws SQLException, InvalidGameException {
        ArrayList<Player> players = new ArrayList<Player>();
        SQLiteDB sdb = GameController.getDB();
        String sql = "Select * from Player";

        ResultSet rs = sdb.queryDB(sql);

        try {
            while (rs.next()) {
                Player player = new Player();
                player.setID(rs.getInt("iD"));
                player.setName(rs.getString("name"));
                player.setDescription(rs.getString("description"));
                player.setHitPoints(rs.getInt("hitPoints"));
                player.setMaxDamage(rs.getInt("maxDamage"));
                player.setMinDamage(rs.getInt("minDamage"));
                player.setChanceHit(rs.getInt("chanceHit"));
                player.setRoomID(rs.getInt("roomID"));
                players.add(player);
            }
        } catch (InvalidGameException ige) {
            throw new InvalidGameException(ige.getMessage());
        }

        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
        return players;
    }
}
