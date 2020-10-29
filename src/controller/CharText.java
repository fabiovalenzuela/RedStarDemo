package controller;

/*
 * Class: CharText
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 25, 2020
 * For: ITEC 3860 Project
 */

public class CharText {
    private int iD;
    private int seq;
    private int usedFlag;
    private String text;

    public CharText() {
    }

    public CharText(int iD, int seq, int usedFlag, String text) {
        this.iD = iD;
        this.seq = seq;
        this.usedFlag = usedFlag;
        this.text = text;
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
}
