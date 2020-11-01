package controller;

/*
 * Class: CharText
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 27, 2020
 * For: ITEC 3860 Project
 */

import exceptions.InvalidGameException;
import model.CharTextDB;
import model.SQLiteDB;

import java.sql.SQLException;
import java.util.ArrayList;

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
        this.setID(iD);
        this.setSeq(seq);
        this.setUsedFlag(usedFlag);
        this.setText(text);
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
    public String getCharTextDisplay(int iD) throws SQLException, InvalidGameException {
        CharText charText = new CharText();

        CharTextDB cdb = new CharTextDB();
        ArrayList<CharText> charTexts = new ArrayList<>();
        charTexts = cdb.getCharText(iD);

        boolean valid = false;
        Monster mon = new Monster();
        Item item = new Item();
        Room room = new Room();
        String charDisplay = "";
        int saveIndex = -1;

        for (int index=0; index < charTexts.size(); index++) {
            charText = charTexts.get(index);

            /* ------------------------------- */
            /*   Extract CharText to Display   */
            /* ------------------------------- */
            valid = true;
            if (charText.getUsedFlag() == 0) {
                int mID = charText.getMonID();
                int iID = charText.getItemID();
                int rID = charText.getRoomID();
                if (mID != 0) {
                    mon = mon.getMonster(mID);
                    if (mon.getHealth() <= 0) {
                        valid = false;
                    }
                }
                if (valid && iID != 0) {
                    item = item.getItem(iID);
                    if (item.getItemRoomID() != 0) {
                        valid = false; }
                }
                if (valid && rID != 0) {
                    room = room.getRoom(rID);
                    if (!room.getVisited()) {
                        valid = false;
                    }
                }

                if (valid) {
                    charText.updateUsedFlag();
                    /* exit the loop when a valid CharText is found */
                    break;
                }
            } else {
                /* save last used index */
                saveIndex = index;
            }
        }

        if (valid) {
            charDisplay = extractText(charText);
        } else if (saveIndex > 0) {
            charText = charTexts.get(saveIndex);
            charDisplay = extractText(charText);
        } else {
            charDisplay = "\nThis character has nothing to say to you";
        }

        return charDisplay;
    }

    /* -----------------------------------------
       Method: extractText
               extract CharText text for display
       ----------------------------------------- */
    private String extractText(CharText charText) {

        /*
        Load character text description
        */
        String desc[] = charText.getText().split("[|]");
        String textStr = "";
        for (String s : desc) {
            textStr = textStr + "\n" + s;
        }

        return textStr;
    }

    /*
     * Method: updateUsedFlag
     * Purpose: set visited = 0 for all rooms
     * @throws SQLException
     */
    public void updateUsedFlag() throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update CharText set usedFlag = 1 where iD = " +
                      this.getID() + " and seq = " + this.getSeq();
        sdb.updateDB(sql);
        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
    }


    /*
    Getters & Setters
     */

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
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

    @Override
    public String toString() {
        return "CharText{" +
                "iD=" + iD +
                ", seq=" + seq +
                ", usedFlag=" + usedFlag +
                ", text='" + text + '\'' +
                ", monID=" + monID +
                ", itemID=" + itemID +
                ", roomID=" + roomID +
                '}';
    }
}
