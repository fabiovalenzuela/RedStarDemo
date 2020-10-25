package controller;

import exceptions.InvalidGameException;
import model.ExitDB;

import java.sql.SQLException;

/*
 * Class: Exit
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 24, 2020
 * For: ITEC 3860 MiniGame3
 */
public class Exit {
    private int exitID;
    private String direction;
    private int destination;

    /*
     --------------------------------
     Set database and get next exitID
     --------------------------------
    */
    public Exit() {
        ExitDB exit = new ExitDB();
        try {
            exitID = exit.getNextExitID();
        } catch (SQLException | InvalidGameException ige) {
            System.out.println(ige.getMessage());
        }
    }

//    /*
//     ------------
//     getExit
//     Get one exit
//     ------------
//    */
//    public Exit getExit(int id) throws SQLException {
//        ExitDB exit = new ExitDB();
//        return exit.getExit(id);
//    }

    /*
     -------------------
     Getters and Setters
     -------------------
    */
    public int getExitID() {
        return this.exitID;
    }

    public void setExitID(int exitID) {
        this.exitID = exitID;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getDestination() {
        return this.destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    /*
     --------
     ToString
     --------
    */
    @Override
    public String toString() {
        return "Exit exitID = " + exitID + "\ndirection = " + direction +
                 "\ndestination = " + destination;
    }
}
