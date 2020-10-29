package controller;

/*
 * Class: CharText
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 25, 2020
 * For: ITEC 3860 Project
 */

import exceptions.InvalidGameException;
import model.CharTextDB;
import model.CharacterDB;

import java.sql.SQLException;

public class CharText {
    private int iD;
    private int seq;
    private int usedFlag;
    private String text;
    /* get past the monster to use this text */
    private int monID;
    /* have this item to use this text */
    private int itemID;
    /* visited this room to use this text */
    private int roomID;

    public CharText() {
    }

    public CharText(int iD, int seq, int usedFlag, String text,
                    int monID, int itemID, int roomID) {
        this.iD = iD;
        this.seq = seq;
        this.usedFlag = usedFlag;
        this.text = text;
        this.setMonID(monID);
        this.setItemID(itemID);
        this.setRoomID(roomID);
    }


    /*
     * Method: getCharText
     * Purpose: Gets a specified character from the Character table
     * @param id
     * @return Character
     * @throws SQLException
     */
    public String getCharText() throws SQLException, InvalidGameException {
        CharText ct = new CharText();
        int id = 0;
        CharTextDB cdb = new CharTextDB();
        boolean valid = false;
        Monster mon = new Monster();
        Item item = new Item();
        Room room = new Room();
        String charDisplay = "";
        try {
            do {
                id += 1;
                charDisplay = cdb.getCharText(id,ct);
                if (usedFlag != 1){
                    if (ct.monID != 0){
                        mon = mon.getMonster(monID);
                    }
                    if (ct.itemID != 0){
                        item = item.getItem(itemID);
                    }
                    if (ct.roomID != 0){
                        room = room.getRoom(roomID);
                    }
                    if (((ct.monID != 0 && mon.getHealth() == 0) ||
                            ct.monID == 0)){

                    }
                }
            } while(!valid);
        } catch (SQLException sqe) {
            /* no character text found */
            throw new InvalidGameException("No character text found");
        }

        return charDisplay;
    }

    /*
    Getters & Setters
     */

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(int usedFlag) {
        this.usedFlag = usedFlag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMonID() {
        return monID;
    }

    public void setMonID(int monID) {
        this.monID = monID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
}
