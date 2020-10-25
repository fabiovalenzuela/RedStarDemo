package controller;

/*
 * Class: Puzzle
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 25, 2020
 * For: ITEC 3860 Project
 */

public class Puzzle {
    private int puzzleID;
    private String puzzleName;
    private String puzzleDescription;
    private String puzzleType;
    private int puzzleRoomID;


    /*
     ------------
     Constructors
     ------------
    */
    public Puzzle() {
        super();
    }

    public Puzzle(int puzzleID, String puzzleName,
                String puzzleDescription,
                  String puzzleType, int puzzleRoomID) {
        setPuzzleID(puzzleID);
        setPuzzleName(puzzleName);
        setPuzzleDescription(puzzleDescription);
        setPuzzleType(puzzleType);
        setPuzzleRoomID(puzzleRoomID);
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

    public String getPuzzleDescription() {
        return puzzleDescription;
    }

    public void setPuzzleDescription(String puzzleDescription) {
        this.puzzleDescription = puzzleDescription;
    }

    public String getPuzzleType() {
        return puzzleType;
    }

    public void setPuzzleType(String puzzleType) {
        this.puzzleType = puzzleType;
    }

    public int getPuzzleRoomID() {
        return puzzleRoomID;
    }

    public void setPuzzleRoomID(int puzzleRoomID) {
        this.puzzleRoomID = puzzleRoomID;
    }
}
