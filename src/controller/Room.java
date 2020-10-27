package controller;

import exceptions.InvalidGameException;

import java.util.ArrayList;
/*
 * Class: Room
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 25, 2020
 * For: ITEC 3860 Project RedStar
 */

public class Room {
    private int roomID;
    private String name;
    private String  description;
    private boolean visited;
    private boolean visible;
    private ArrayList<Exit> exits = new ArrayList<Exit>();
    private ArrayList<Item> items = new ArrayList<>();

    /*
     ------------
     Constructors
     ------------
    */
    public Room() {
        super();
    }

    public Room(int roomID, String name,
                String description,
                boolean visited,
                boolean visible,
                ArrayList<Exit> exits,
                ArrayList<Item> items) {
        setRoomID(roomID);
        setName(name);
        setDescription(description);
        setVisited(visited);
        setVisible(visible);
        setExits(exits);
        setItems(items);
    }


    /*
     --------------------------
     look
     Get String to look at room
     --------------------------
    */
    public String look() {
        String lookStr = getRoomDisplay();
        Exit exit = new Exit();

        for (int i=0; i<exits.size(); i++) {
            exit = exits.get(i);
            lookStr = lookStr + "\n" + exit.getDirection();
        }

        return lookStr;
    }

    /*
     -------------------------------
     getRoomDisplay
     Get room description to display
     -------------------------------
    */
    public String getRoomDisplay() {

        String display = "";
        // not visited = you haven't been to this room before
        if (!visited) {
            display = name + " / not visited\n";
            setVisited(true);
        } else {
            display = name + " / visited\n";
        }

        /*
        Load room description
        */
        String desc[] = this.description.split("[|]");
        for (String s : desc) {
            display = display + s + "\n";
        }

        /*
        display items in room
        */
        if (items.size() > 0) {
            display = display + "Items in the room include: ";
            Item item = new Item();
            for (int i = 0; i < items.size(); i++) {
                item = items.get(i);
                display = display + item.getDescription();
                if ((i < (items.size() - 2))) {
                    display = display + ", ";
                } else if ((items.size() > 1) && (i != (items.size() - 1))) {
                    display = display + " and ";
                }
            }
            display = display + "\n";
        }

        /*
        display directions user can go
        */
        display = display + "You can go ";
        Exit exit = new Exit();
        for (int i = 0; i < exits.size(); i++) {
            exit = exits.get(i);
            display = display + exit.getDirection();
            if ((i < (exits.size() - 2)) && exits.size() > 1) {
                display = display + ", ";
            } else if ((exits.size() > 1) && (i != (exits.size() - 1))) {
                display = display + " or ";
            }
        }
        display = display + " from here.";

        return display;
    }

    /*
     -----------------------------------------
     verifyDirection
     Verify direction and get next room number
     to visit if a valid direction is entered
     -----------------------------------------
    */
    public int verifyDirection(String command) throws InvalidGameException {

        int nextRm = -1;
        int index = 0;

        String firstChar = "";
        boolean found = false;
        Exit exit = new Exit();

        do {
            exit = exits.get(index);
            // get the 1st character of the room command
            firstChar = exit.getDirection().substring(0, 1);

            // Is this the command entered?
            if (command.equalsIgnoreCase(exit.getDirection()) ||
                    (command.equalsIgnoreCase(firstChar))) {

                // The direction should give you the next room number to visit
                nextRm = exit.getDestination();
                found = true;
            }
            index++;
        } while (!found && index < exits.size());

        if (!found || (nextRm <= 0)) {
            throw new InvalidGameException("Invalid direction choice");
        }

        return nextRm;
    }

    /*
     -----------------
     Getters & Setters
     -----------------
    */
    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setExits(ArrayList<Exit> exits) {
        this.exits = exits;
    }

    public ArrayList<Exit> getExits() {
        return exits;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() { return items; }

    /*
     --------
     ToString
     --------
    */
    @Override
    public String toString() {
        return "Room{" +
                "roomID = " + roomID +
                ", room Name = " + name + '\'' +
                ", room description = " + description +
                ", exits = " + exits +
                ", items = " + items +
                '}';
    }


}
