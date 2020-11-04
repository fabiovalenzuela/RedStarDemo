package model;

import view.LoginController;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class: LoginDB
 * Authors: Annette Vinson, Alejandrov Valenzuela, Adrian Argueta
 * Date: October 25, 2020
 * For: ITEC 3860 Project RedStar
 */
public class LoginDB {

    SQLiteDB sdb;

    /*
     * Method: getUser
     * Purpose: Gets a user based upon the supplied userID
     *
     * @param id
     * @return Item
     * @throws SQLException
     */
    public boolean getUser(String userID, String pwd) throws SQLException {
        SQLiteDB sdb = LoginController.getDB();

        boolean valid = false;
        String sql = "Select pwd from User WHERE userID = '" + userID + "'";
        ResultSet rs = sdb.queryDB(sql);
        if (rs.next()) {
            String password = rs.getString("pwd");
            if (pwd.equals(password)) {
                valid = true;
            }
        } else {
            throw new SQLException("Invalid User ID / Password");
        }
        return valid;
    }


    /*
     * Method: addUser
     * Create a new user
     * @throws SQLException
     */
    public boolean addUser(String userID, String pwd) {
        try {
            SQLiteDB sdb = LoginController.getDB();
            String sql = "INSERT INTO User(userID, pwd) Values('" + userID + "', '" + pwd + "')";
            sdb.updateDB(sql);
            /* Close the SQLiteDB connection since SQLite only allows one update */
            sdb.close();
            return true;
        } catch (SQLException sqe) {
            return false;
        }
    }

    /*
     * Method: updateUserPwd
     * Update user password
     * @throws SQLException
     */
    public void updateUserPwd(String user, String pwd) throws SQLException {
        SQLiteDB sdb = LoginController.getDB();
        String sql = "Update User set userPwd = " + pwd + "where user = " + user;
        sdb.updateDB(sql);
        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
    }

    /**
     * Method: createUserFile
     * Purpose: Build the Monster table and load data
     * @return void
     * @throws SQLException
     */
    public void createUserFile() throws SQLException {
        sdb = LoginController.getDB();
        String sql = "CREATE TABLE User(userID text Primary Key not Null, pwd text not null)";

        try {
            sdb.updateDB(sql);
        } catch (SQLException sqe) {
            System.out.println("Error creating User file");
        }

        /* Close the SQLiteDB connection since SQLite only allows one update */
        sdb.close();
    }
}
