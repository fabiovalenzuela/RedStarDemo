package controller;

import exceptions.InvalidGameException;

import java.util.Random;

/*
 * Class: Being
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 27, 2020
 * For: ITEC 3860 Project
 * Copied/modified from Rick Price Being
 */
abstract public class Being {
    private int iD;
    private String name;
    private String description;
    private int health;
    private int minDamage;
    private int maxDamage;
    private double chanceHit;
    private int roomID;

    public Being() {
    }


    public Being(int iD) throws InvalidGameException {
        setID(iD);
        setName("");
        setDescription("");
        setHealth(0);
        setMinDamage(0);
        setMaxDamage(0);
        setChanceHit(0.0);
        setRoomID(0);
    }

    public Being(int iD, String name, String description, int health,
                 int minDamage, int maxDamage, double chanceHit, int roomID)
            throws InvalidGameException {
        setID(iD);
        setName(name);
        setDescription(description);
        setHealth(health);
        setMinDamage(minDamage);
        setMaxDamage(maxDamage);
        setChanceHit(chanceHit);
        setRoomID(roomID);
    }


    /*---------------------------------
     * Method: updateHealth
     * @param health the health to set
     ----------------------------------*/
    public void updateHealth() {
        /* create instance of Random class */
        int change = 0;
        if (maxDamage != 0 && maxDamage >minDamage) {
            Random rand = new Random();
            change = rand.nextInt(maxDamage - minDamage);
        }
        health -= change;
        if (health<0) { health = 0; }
        setHealth(health);
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
     * Method: getHealth
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /*
     * Method: setHealth
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
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
    public void setChanceHit(double chanceHit) throws InvalidGameException {
        if (chanceHit >= 0 && chanceHit <= 1) {
            this.chanceHit = chanceHit;
        }
        else {
            throw new InvalidGameException("chance hit must be 0 thru 1");
        }
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    /*
     * Method: toString
     * Purpose: Returns a String of the Monster class
     * @return
     */

    /*
     * Method: Damage
     * Purpose: Damages both the player and the monster using a random value within a range
     * @return
     */
//    public int Damage(){
//        int damageDealt = random.randint(20,30);
//        int damageRecieved = random.randint(0,damageDealt);
//        int enemyHealth;-= damageDealt;
//        int playerHealth -= damageRecieved;
//
//    }


    @Override
    public String toString() {
        return "Monster iD = " + iD +
                "\nname = " + name +
                "\ndescription = " + description +
                "\nhealth = " + health +
                "\nminDamage = " + minDamage +
                "\nmaxDamage = " + maxDamage +
                "\nchanceHit = " + chanceHit +
                "\nroomID = " + getRoomID();
    }


}


