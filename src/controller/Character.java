package controller;

import exceptions.InvalidGameException;
import model.CharacterDB;

import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Class: Character
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 24, 2020
 * For: ITEC 3860 Project
 * Copied/modified from Rick Price
 */
public class Character extends Being {


    /*
     * Constructor: Character
     */
    public Character() {
        CharacterDB mdb = new CharacterDB();
        try {
            setID(mdb.getNextCharacterID());
        } catch (SQLException sqe) {
            System.out.println(sqe.getMessage());
        }
    }

    /*
     * Method: getCharacter
     * Purpose: Gets a specified character from the Character table
     * @param id
     * @return Character
     * @throws SQLException
     */
    public Character getCharacter(int id) throws SQLException, InvalidGameException {
        CharacterDB mdb = new CharacterDB();
        return mdb.getCharacter(id);
    }

    /*
     * Method: getAllCharacters
     * Purpose: gets all characters from the Character table
     * @return ArrayList<Character>
     * @throws SQLException
     */
    public ArrayList<Character> getAllCharacters() throws SQLException, InvalidGameException {
        CharacterDB mdb = new CharacterDB();
        return mdb.getAllCharacters();
    }

    /*
     * Method: toString
     * Purpose: Returns a String of the Character class
     * @return
     */
    @Override
    public String toString() {
        return "Character iD = " + getID() +
                "\ncharacter name = " + getName() +
                "\ncharacter description = " + getDescription() +
                "\ncharacter hitPoints = " + getHitPoints() +
                "\ncharacter minDamage = " + getMinDamage() +
                "\ncharacter maxDamage = " + getMaxDamage() +
                "\ncharacter chanceHit = " + getChanceHit();
    }


}

