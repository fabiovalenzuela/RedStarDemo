package controller;

import exceptions.InvalidGameException;
import model.MonsterDB;

import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Class: Monster
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 25, 2020
 * For: ITEC 3860 Project
 * Copied/modified from Rick Price Monster
 */
public class Monster extends Being {


    /*
     * Constructor: Monster
     */
    public Monster() {
        MonsterDB mdb = new MonsterDB();
        try {
            setID(mdb.getNextMonsterID());
        } catch (SQLException sqe) {
            System.out.println(sqe.getMessage());
        }
    }

    /*
     * Method: getMonster
     * Purpose: Gets a specified monster from the Monster table
     * @param id
     * @return Monster
     * @throws SQLException
     */
    public Monster getMonster(int id) throws SQLException, InvalidGameException {
        MonsterDB mdb = new MonsterDB();
        return mdb.getMonster(id);
    }

    /*
     * Method: getAllMonsters
     * Purpose: gets all monsters from the Monster table
     * @return ArrayList<Monster>
     * @throws SQLException
     */
    public ArrayList<Monster> getAllMonsters() throws SQLException, InvalidGameException {
        MonsterDB mdb = new MonsterDB();
        return mdb.getAllMonsters();
    }

    /*
     * Method: toString
     * Purpose: Returns a String of the Monster class
     * @return
     */
    @Override
    public String toString() {
        return "Monster iD = " + getID() +
                "\nmonster name = " + getName() +
                "\nmonster description = " + getDescription() +
                "\nmonster hitPoints = " + getHitPoints() +
                "\nmonster minDamage = " + getMinDamage() +
                "\nmonster maxDamage = " + getMaxDamage() +
                "\nmonster chanceHit = " + getChanceHit() +
                "\nMonster roomID = " + getRoomID();
    }


}

