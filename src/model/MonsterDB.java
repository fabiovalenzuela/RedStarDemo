package model;

import controller.GameController;
import controller.Monster;
import exceptions.InvalidGameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Class: Monster
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 24, 2020
 * For: ITEC 3860 Project RedStar
 * Copied/modified from Rick Price Monster
 */
public class MonsterDB {
    /**
     * Method: getNextMonsterID
     * Purpose: Gets the id for the next monster.
     * @return int
     */
    public int getNextMonsterID() throws SQLException {
        SQLiteDB sdb = null;
        try {
            sdb = new SQLiteDB();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        int next = sdb.getMaxValue("monsterNumber", "Monster") + 1;
        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
        return next;
    }

    /**
     * Method: getMonster
     * Purpose: handles db interactions to retrieve a single Monster
     * @param id
     * @return Monster
     * @throws SQLException
     */
    public Monster getMonster(int id) throws SQLException, InvalidGameException {
        SQLiteDB sdb = GameController.getDB();
        Monster monster = new Monster();
        String sql = "Select * from Monster WHERE monsterNumber = " + id;
        ResultSet rs = sdb.queryDB(sql);
        try {
            if (rs.next()) {
                monster.setID(rs.getInt("iD"));
                monster.setName(rs.getString("name"));
                monster.setDescription(rs.getString("description"));
                monster.setHitPoints(rs.getInt("hitPoints"));
                monster.setMaxDamage(rs.getInt("maxDamage"));
                monster.setMinDamage(rs.getInt("minDamage"));
                monster.setChanceHit(rs.getInt("chanceHit"));
            } else {
                throw new SQLException("Monster " + id + " not found.");
            }
        } catch (InvalidGameException ige) {
            throw new InvalidGameException(ige.getMessage());
        }

        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
        return monster;
    }

    /**
     * Method: updateVisited
     * Purpose: set visited = 0 for all rooms
     * @throws SQLException
     */
    public void updateHp(int hp) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update Monster set hitPoints = " + hp;
        sdb.updateDB(sql);
        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
    }

    /**
     * Method: getAllMonsters
     * Purpose: Handles the DB interactions to retrieve all monsters
     * @return ArrayList<Monster>
     * @throws SQLException
     */
    public ArrayList<Monster> getAllMonsters() throws SQLException, InvalidGameException {
        ArrayList<Monster> monsters = new ArrayList<Monster>();
        SQLiteDB sdb = GameController.getDB();
        String sql = "Select * from Monster";

        ResultSet rs = sdb.queryDB(sql);

        try {
            while (rs.next()) {
                Monster monster = new Monster();
                monster.setID(rs.getInt("iD"));
                monster.setName(rs.getString("name"));
                monster.setDescription(rs.getString("description"));
                monster.setHitPoints(rs.getInt("hitPoints"));
                monster.setMaxDamage(rs.getInt("maxDamage"));
                monster.setMinDamage(rs.getInt("minDamage"));
                monster.setChanceHit(rs.getInt("chanceHit"));
                monsters.add(monster);
            }
        } catch (InvalidGameException ige) {
            throw new InvalidGameException(ige.getMessage());
        }

        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
        return monsters;
    }
}
