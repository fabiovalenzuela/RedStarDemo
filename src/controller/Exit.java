package controller;

/*
 * Class: Exit
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 24, 2020
 * For: ITEC 3860 MiniGame3
 */
public class Exit {
    private int exitID;
    private int roomID;
    private String direction;
    private int destination;
    private boolean hidden;

    /*
     --------------------------------
     Set database and get next exitID
     --------------------------------
    */
    public Exit() {
        super();
//        ExitDB exit = new ExitDB();
//        try {
//            exitID = exit.getNextExitID();
//        } catch (SQLException | InvalidGameException ige) {
//            System.out.println(ige.getMessage());
//        }
    }

    public Exit(int exitID, int roomID, String direction, int destination, boolean hidden) {
        this.exitID = exitID;
        this.roomID = roomID;
        this.direction = direction;
        this.destination = destination;
        this.hidden = hidden;
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

    public int getRoomID() {
        return this.roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
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

    public boolean getHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) { this.hidden = hidden; }

    /*
     --------
     ToString
     --------
    */
    @Override
    public String toString() {
        return "Exit exitID = " + exitID +
                "\nroom ID = " + roomID +
                "\ndirection = " + direction +
                 "\ndestination = " + destination +
                "\n hidden = " + hidden;
    }
}
