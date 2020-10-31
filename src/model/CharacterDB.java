package model;

import controller.Character;
import controller.GameController;
import exceptions.InvalidGameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Class: Character
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 24, 2020
 * For: ITEC 3860 MiniGame3
 * Copied/modified from Rick Price Character
 */
public class CharacterDB {


    /*
     * Method: getNextCharacterID
     * Purpose: Gets the id for the next character.
     * @return int
     */
    public int getNextCharacterID() throws SQLException {
        SQLiteDB sdb = null;
        try {
            sdb = new SQLiteDB();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        int next = sdb.getMaxValue("iD", "Character") + 1;
        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
        return next;
    }

    /*
     * Method: getCharacter
     * Purpose: handles db interactions to retrieve a single Character
     * @param id
     * @return Character
     * @throws SQLException
     */
    public Character getCharacter(int id) throws SQLException, InvalidGameException {
        SQLiteDB sdb = GameController.getDB();
        Character character = new Character();
        String sql = "Select * from Character WHERE iD = " + id;
        ResultSet rs = sdb.queryDB(sql);
        try {
            if (rs.next()) {
                character.setID(rs.getInt("iD"));
                character.setName(rs.getString("name"));
                character.setDescription(rs.getString("description"));
                character.setHealth(rs.getInt("health"));
                character.setMaxDamage(rs.getInt("maxDamage"));
                character.setMinDamage(rs.getInt("minDamage"));
                character.setChanceHit(rs.getDouble("chanceHit"));
                character.setRoomID(rs.getInt("roomID"));
            } else {
                throw new SQLException("Character " + id + " not found.");
            }
        } catch (InvalidGameException ige) {
            throw new InvalidGameException(ige.getMessage());
        }

        /* Close the SQLiteDB connection since SQLite only allows one updater */
        sdb.close();
        return character;
    }

    /*
     * Method: updateVisited
     * Purpose: set visited = 0 for all rooms
     * @throws SQLException
     */
    public void updateHp(int hp) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update Character set health = " + hp;
        sdb.updateDB(sql);
        //Close the SQLiteDB connection since SQLite only allows one updater
        sdb.close();
    }

    /*
     * Method: getAllCharacters
     * Purpose: Handles the DB interactions to retrieve all characters
     * @return ArrayList<Character>
     * @throws SQLException
     */
    public ArrayList<Character> getAllCharacters() throws SQLException, InvalidGameException {
        ArrayList<Character> characters = new ArrayList<Character>();
        SQLiteDB sdb = GameController.getDB();
        String sql = "Select * from Character";

        ResultSet rs = sdb.queryDB(sql);

        try {
            while (rs.next()) {
                Character character = new Character();
                character.setID(rs.getInt("iD"));
                character.setName(rs.getString("name"));
                character.setDescription(rs.getString("description"));
                character.setHealth(rs.getInt("health"));
                character.setMaxDamage(rs.getInt("maxDamage"));
                character.setMinDamage(rs.getInt("minDamage"));
                character.setChanceHit(rs.getInt("chanceHit"));
                character.setRoomID(rs.getInt("roomID"));
                characters.add(character);
            }
        } catch (InvalidGameException ige) {
            throw new InvalidGameException(ige.getMessage());
        }

        /* Close the SQLiteDB connection since SQLite only allows one updater */
        sdb.close();
        return characters;
    }
}
