package controller;

/*
 * Class:   Item
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Class:   ITEC 3860 Project RedStar
 * Date:    October 25, 2020
 *
 * */

import exceptions.InvalidGameException;
import model.ItemDB;

import java.sql.SQLException;

public class Item {

    private int itemID;
    private String name;
    private String description;
    private int itemRoomID;
    private int damageRate;
    private boolean itemUsed;

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
                int itemRoomID,
                int damageRate,
                boolean itemUsed) {
        setItemID(itemID);
        setName(name);
        setDescription(description);
        setItemRoomID(itemRoomID);
        setDamageRate(damageRate);
        setItemUsed(itemUsed);

    }

    /*
     * Method: getItem
     * Purpose: Gets a specified item from the Item table
     * @param id
     * @return Item
     * @throws SQLException
     */
    public Item getItem(int id) throws SQLException, InvalidGameException {
        ItemDB idb = new ItemDB();
        return idb.getItem(id);
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

    public int getDamageRate() {
        return damageRate;
    }

    public void setDamageRate(int damageRate) { this.damageRate = damageRate; }

    public boolean isItemUsed() {
        return itemUsed;
    }

    public void setItemUsed(boolean itemUsed) {
        this.itemUsed = itemUsed;
    }

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
                ", current room ID = " + itemRoomID +
                ", damage rate = " + damageRate;
    }
}

