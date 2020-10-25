package controller;

import exceptions.InvalidGameException;

/*
 * Class: Being
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 24, 2020
 * For: ITEC 3860 Project
 * Copied/modified from Rick Price Being
 */
abstract public class Being {
    private int iD;
    private String name;
    private String description;
    private int hitPoints;
    private int minDamage;
    private int maxDamage;
    private double chanceHit;

    public Being() {
    }


    public Being(int iD) throws InvalidGameException {
        setID(iD);
        setName("");
        setDescription("");
        setHitPoints(0);
        setMinDamage(0);
        setMaxDamage(0);
        setChanceHit(0.0);
    }

    public Being(int iD, String name, String description, int hitPoints,
                 int minDamage, int maxDamage, double chanceHit) throws InvalidGameException {
        setID(iD);
        setName(name);
        setDescription(description);
        setHitPoints(hitPoints);
        setMinDamage(minDamage);
        setMaxDamage(maxDamage);
        setChanceHit(chanceHit);
    }

    /*
     * Method: getID
     * @return the iD
     */
    public int getID() {
        return iD;
    }

    /*
     * Method: setID
     *
     * @param iD the iD to set
     */
    public void setID(int iD) {
        this.iD = iD;
    }

    /*
     * Method: getName
     * @return the name
     */
    public String getName() {
        return name;
    }

    /*
     * Method: setName
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Method: getDescription
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /*
     * Method: setDescription
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /*
     * Method: getHitPoints
     * @return the hitPoints
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /*
     * Method: setHitPoints
     * @param hitPoints the hitPoints to set
     */
    public void setHitPoints(int hitPoints) throws InvalidGameException {
        if (hitPoints >= 0 && hitPoints <= 1) {
            this.hitPoints = hitPoints;
        }
        else {
            throw new InvalidGameException("hitPoints must be 0 thru 1");
        }
    }

    /*
     * Method: getMinDamage
     * @return the minDamage
     */
    public int getMinDamage() {
        return minDamage;
    }

    /*
     * Method: setMinDamage
     * @param minDamage the minDamage to set
     */
    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    /*
     * Method: getMaxDamage
     * @return the maxDamage
     */
    public int getMaxDamage() {
        return maxDamage;
    }

    /*
     * Method: setMaxDamage
     * @param maxDamage the maxDamage to set
     */
    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    /*
     * Method: getChanceHit
     * @return the chanceHit
     */
    public double getChanceHit() {
        return chanceHit;
    }

    /*
     * Method: setChanceHit
     * @param chanceHit the chanceHit to set
     */
    public void setChanceHit(double chanceHit) {
        this.chanceHit = chanceHit;
    }

    /*
     * Method: toString
     * Purpose: Returns a String of the Monster class
     * @return
     */
    @Override
    public String toString() {
        return "Monster iD = " + iD +
                "\nname = " + name +
                "\ndescription = " + description +
                "\nhitPoints = " + hitPoints +
                "\nminDamage = " + minDamage +
                "\nmaxDamage = " + maxDamage +
                "\nchanceHit = " + chanceHit;
    }


}


