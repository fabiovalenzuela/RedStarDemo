package controller;

/*
 * Class: Puzzle
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: November 2, 2020
 * For: ITEC 3860 Project
 */

import exceptions.InvalidGameException;
import model.PuzzleDB;
import java.sql.SQLException;

public class Puzzle {
    private int puzzleID;
    private String puzzleName;
    private String puzzleDesc;
    private String puzzleType;
    private int puzzleInRoom;
    private int puzzleMonID;
    private int puzzleItemID;
    private int puzzleRoomID;
    private boolean puzzleUsed;
    private String puzzleVerb;
    private String puzzleNoun;
    private String puzzleSql;
    private String puzzleObject;
    private String puzzleText;

    /*
     ------------
     Constructors
     ------------
    */
    public Puzzle() {
        super();
    }

    public Puzzle(int puzzleID, String puzzleName,
                  String puzzleDesc, String puzzleType,
                  int puzzleInRoom, int puzzleMonID,
                  int puzzleItemID, int puzzleRoomID,
                  boolean puzzleUsed, String puzzleVerb,
                  String puzzleNoun, String puzzleSql,
                  String puzzleObject, String puzzleText) {

        setPuzzleID(puzzleID);
        setPuzzleName(puzzleName);
        setPuzzleDesc(puzzleDesc);
        setPuzzleType(puzzleType);
        setPuzzleInRoom(puzzleInRoom);
        setPuzzleMonID(puzzleMonID);
        setPuzzleItemID(puzzleItemID);
        setPuzzleRoomID(puzzleRoomID);
        setPuzzleUsed(puzzleUsed);
        setPuzzleVerb(puzzleVerb);
        setPuzzleNoun(puzzleNoun);
        setPuzzleSql(puzzleSql);
        setPuzzleObject(puzzleObject);
        setPuzzleText(puzzleText);
    }

    public Puzzle getPuzzle (int id) throws SQLException, InvalidGameException {
        PuzzleDB idb = new PuzzleDB();
        return idb.getPuzzle(id);
    }

    /*
     * Method: checkPuzzle
     * Purpose: Checks to see if puzzle has been met
     * @param id
     * @return Character
     * @throws SQLException
     */
    public boolean checkPuzzle(int iD) throws SQLException, InvalidGameException {
        Puzzle puzzle = getPuzzle(iD);
        PuzzleDB cdb = new PuzzleDB();

        boolean valid = false;
        Monster mon = new Monster();
        Item item = new Item();
        Room room = new Room();

        /* ------------------------------- */
        /*   Check puzzle                  */
        /* ------------------------------- */
        valid = true;
        if (!puzzleUsed) {
            int mID = getPuzzleMonID();
            int iID = getPuzzleItemID();
            int rID = getPuzzleRoomID();
            if (mID != 0) {
                mon = mon.getMonster(mID);
                if (mon.getHealth() <= 0) {
                    valid = false;
                }
            }
            if (valid && iID != 0) {
                item = item.getItem(iID);
                if (item.getItemRoomID() != 0) {
                    valid = false;
                }
            }
            if (valid && rID != 0) {
                room = room.getRoom(rID);
                if (!room.getVisited()) {
                    valid = false;
                }
            }

            if (valid) {
                cdb.updatePuzzleUsed(iD);
                /* exit the loop when a valid CharText is found */
            }
        }
        return valid;
    }


    /*
     -----------------
     Getters & Setters
     -----------------
    */

    public int getPuzzleID() {
        return puzzleID;
    }

    public void setPuzzleID(int puzzleID) {
        this.puzzleID = puzzleID;
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public void setPuzzleName(String puzzleName) {
        this.puzzleName = puzzleName;
    }

    public String getPuzzleDesc() {
        return puzzleDesc;
    }

    public void setPuzzleDesc(String puzzleDesc) {
        this.puzzleDesc = puzzleDesc;
    }

    public String getPuzzleType() {
        return puzzleType;
    }

    public void setPuzzleType(String puzzleType) {
        this.puzzleType = puzzleType;
    }

    public int getPuzzleInRoom() {
        return puzzleInRoom;
    }

    public void setPuzzleInRoom(int puzzleInRoom) {
        this.puzzleInRoom = puzzleInRoom;
    }

    public int getPuzzleMonID() {
        return puzzleMonID;
    }

    public void setPuzzleMonID(int puzzleMonID) {
        this.puzzleMonID = puzzleMonID;
    }

    public int getPuzzleItemID() {
        return puzzleItemID;
    }

    public void setPuzzleItemID(int puzzleItemID) {
        this.puzzleItemID = puzzleItemID;
    }

    public int getPuzzleRoomID() {
        return puzzleRoomID;
    }

    public void setPuzzleRoomID(int puzzleRoomID) {
        this.puzzleRoomID = puzzleRoomID;
    }

    public boolean isPuzzleUsed() {
        return puzzleUsed;
    }

    public void setPuzzleUsed(boolean puzzleUsed) {
        this.puzzleUsed = puzzleUsed;
    }

    public String getPuzzleVerb() {
        return puzzleVerb;
    }

    public void setPuzzleVerb(String puzzleVerb) {
        this.puzzleVerb = puzzleVerb;
    }

    public String getPuzzleNoun() {
        return puzzleNoun;
    }

    public void setPuzzleNoun(String puzzleNoun) {
        this.puzzleNoun = puzzleNoun;
    }

    public String getPuzzleSql() {
        return puzzleSql;
    }

    public void setPuzzleSql(String puzzleSql) {
        this.puzzleSql = puzzleSql;
    }

    public String getPuzzleObject() {
        return puzzleObject;
    }

    public void setPuzzleObject(String puzzleObject) {
        this.puzzleObject = puzzleObject;
    }

    public String getPuzzleText() {
        return puzzleText;
    }

    public void setPuzzleText(String puzzleText) {
        this.puzzleText = puzzleText;
    }
}
