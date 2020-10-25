package controller;

/*
 * Class:   Item
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Class:   ITEC 3860 Fall 2020
 * Date:    September 12, 2020
 *
 * */

public class Item {

    private int itemID;
    private String name;
    private String description;
    private int itemRoomID;

    /*
     ------------
     Constructors
     ------------
    */
    public Item() {
        super();
    }

    public Item(int itemID, String name,
                String description,
                int itemRoomID) {
        setItemID(itemID);
        setName(name);
        setDescription(description);
        setItemRoomID(itemRoomID);

    }
    /*
     --------------------------------
     Item added to player's inventory
     --------------------------------
    */
    public String itemAdded(int itemRoomID) {
        String itemAdd = this.getName() +
                "has been picked up from the room and ' + " +
                "'successfully added to the player inventory.";
        return itemAdd;
    }

    /*
     -----------------
     Getters & Setters
     -----------------
    */
    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
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

    public int getItemRoomID() {
        return itemRoomID;
    }

    public void setItemRoomID(int itemRoomID) { this.itemRoomID = itemRoomID; }

    /*
     --------
     ToString
     --------
    */
    @Override
    public String toString() {
        return "Item: " +
                "itemID = " + itemID +
                ", name = " + name +
                ", description = " + description +
                ", current room ID = " + itemRoomID;
    }
}

