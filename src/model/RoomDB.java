package model;

import controller.*;
import controller.Character;
import exceptions.InvalidGameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class: RoomDB
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 29, 2020
 * For: ITEC 3860 Project RedStar
 * Copied/modified from Rick Price RoomDB
 */
public class RoomDB {
    /*
     * Method: getNextRoomID
     * Purpose: gets the next ID for a room
     *
     * @return int
     */
    public int getNextRoomID() throws SQLException {
        SQLiteDB sdb = null;
        try {
            sdb = new SQLiteDB(GameController.dbName);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        int next = sdb.getMaxValue("roomID", "room") + 1;
        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();

        return next;
    }

    /*
     * Method: getRoomDisplay
     * Purpose: Gets a room based upon the supplied ID
     *
     * @param id
     * @return Room
     * @throws SQLException
     */
    public Room getRoom(int id) throws SQLException, InvalidGameException {
        SQLiteDB sdb = GameController.getDB();
        Room rm = new Room();
        String sql = "Select * from Room WHERE roomID = " + id;
        ResultSet rs = sdb.queryDB(sql);
        int visit = 0;
        int visble = 0;
        if (rs.next()) {
            rm.setRoomID(rs.getInt("roomID"));
            rm.setName(rs.getString("name"));
            rm.setDescription(rs.getString("description"));
            visit = rs.getInt("visited");
            if (visit == 0) {
                rm.setVisited(false);
            }
            else {
                rm.setVisited(true);
            }
            visble = rs.getInt("visible");
            if (visble == 0) {
                rm.setVisible(false);
            }
            else {
                rm.setVisible(true);
            }
        } else {
            throw new SQLException("Room " + id + " not found");
        }

        /* Get exits */
        ArrayList<Exit> exits = new ArrayList<Exit>();
        sql = "Select * from Exit " +
                "where roomID = " + id +
                " and hidden = 0";

        rs = sdb.queryDB(sql);
        ExitDB edb = new ExitDB();

        while (rs.next()) {
            Exit exit = new Exit();
            edb.loadExit(rs, exit);
            exits.add(exit);
        }

        rm.setExits(exits);


        /* Get Characters */
        ArrayList<Character> characters = new ArrayList<>();
        sql = "Select * from Character " +
                "where roomID = " + id;

        rs = sdb.queryDB(sql);
        CharacterDB charDB = new CharacterDB();

        while (rs.next()) {
            Character character = new Character();
            charDB.loadCharacter(rs, character);
            characters.add(character);
        }

        rm.setChars(characters);

        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();

        /* Get items */
        ArrayList<Item> items = new ArrayList<>();
        items = getRoomItems(id);
        rm.setItems(items);

        /* Get monsters */
        ArrayList<Monster> monsters = new ArrayList<>();
        monsters = getRoomMonsters(id);
        rm.setMonsters(monsters);

        /* Get puzzles */
        ArrayList<Puzzle> puzzles = new ArrayList<>();
        puzzles = getRoomPuzzles(id);
        rm.setPuzzles(puzzles);

        return rm;
    }

    /* ---------------------------------------
        Method: getRoomItems
        Purpose: get all items in the room
                 and return in arraylist items
        --------------------------------------
    */
    public ArrayList<Item> getRoomItems(int roomID) throws SQLException, InvalidGameException {
        SQLiteDB sdb = GameController.getDB();

        /* Get items */
        ArrayList<Item> items = new ArrayList<Item>();
        String sql = "Select * " +
                "from Item " +
                "where itemRoomID = " + roomID +
                " and itemUsed = 0";

        ResultSet rs = sdb.queryDB(sql);
        ItemDB idb = new ItemDB();

        while (rs.next()) {
            Item item = new Item();
            idb.loadItem(rs, item);
            items.add(item);
        }

        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
        return items;
    }

    /* ------------------------------------------
        Method: getRoomMonsters
        Purpose: get all monsters in the room
                 and return in arraylist monsters
        -----------------------------------------
    */
    public ArrayList<Monster> getRoomMonsters(int roomID) throws SQLException, InvalidGameException {
        SQLiteDB sdb = GameController.getDB();

        /* Get monsters */
        ArrayList<Monster> monsters = new ArrayList<Monster>();
        String sql = "Select * from Monster where roomID = " + roomID;

        ResultSet rs = sdb.queryDB(sql);

        while (rs.next()) {
            MonsterDB mdb = new MonsterDB();
            Monster monster = new Monster();
            mdb.loadMonster(rs, monster);
            monsters.add(monster);
        }

        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
        return monsters;
    }


    /* ------------------------------------------
        Method: getRoomPuzzles
        Purpose: get all puzzles in the room
                 and return in arraylist puzzles
        -----------------------------------------
    */
    public ArrayList<Puzzle> getRoomPuzzles(int roomID) throws SQLException, InvalidGameException {
        SQLiteDB sdb = GameController.getDB();

        /* Get puzzles */
        ArrayList<Puzzle> puzzles = new ArrayList<Puzzle>();
        String sql = "Select * from Puzzle where puzzleInRoom = " + roomID;

        ResultSet rs = sdb.queryDB(sql);

        while (rs.next()) {
            PuzzleDB pdb = new PuzzleDB();
            Puzzle puzzle = new Puzzle();
            pdb.loadPuzzle(rs, puzzle);
            puzzles.add(puzzle);
        }

        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
        return puzzles;
    }

    /*
     * Method: updateVisited
     * Purpose: set visited = 0 for all rooms
     * @throws SQLException
     */
    public void updateVisited(int roomID) throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update Room set visited = 1 where roomID = " + roomID;               ;
        sdb.updateDB(sql);
        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
    }
    /*
     * Method: updateAllVisited
     * Purpose: set visited = 0 for all rooms
     * @throws SQLException
     */
    public void updateAllVisited() throws SQLException {
        SQLiteDB sdb = GameController.getDB();
        String sql = "Update Room set visited = 0";
        sdb.updateDB(sql);
        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
    }
}

