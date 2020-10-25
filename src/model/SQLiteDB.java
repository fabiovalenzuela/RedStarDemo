/**
 * Class : SQLiteDB.java
 *
 * @author: Rick Price
 * @version: 1.0
 * Course: ITEC 3860
 * Written: June 18, 2019
 * <p>
 * This class the base interface to the SQLite database
 * <p>
 * Purpose: Execute queries and updates in the SQLite database
 */
package model;

import java.sql.DriverManager;
import java.sql.SQLException;

/** Class : SQLiteDB.java
 * @author: Rick Price
 * @version: 1.0
 * Course: ITEC 3860
 * Written: June 16, 2020
 * This class is the business logic for the Room objects
 */
public class SQLiteDB extends DB {

    /** Constructor: SQLiteDB
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public SQLiteDB() throws SQLException, ClassNotFoundException {
        //Build the connection String
        sJdbc = "jdbc:sqlite";
        sDriverName = "org.sqlite.JDBC";
        // register the driver
        Class.forName(sDriverName);
        sDbUrl = sJdbc + ":" + dbName;
        conn = DriverManager.getConnection(sDbUrl);
    }

    /** Constructor: SQLiteDB
     * @param dbName
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public SQLiteDB(String dbName) throws SQLException, ClassNotFoundException {
        //Build the connection String
        sJdbc = "jdbc:sqlite";
        sDriverName = "org.sqlite.JDBC";
        // register the driver
        Class.forName(sDriverName);
        sDbUrl = sJdbc + ":" + dbName;

        this.dbName = dbName;
        conn = DriverManager.getConnection(sDbUrl);
    }

    /** Method: close
     * Purpose: Close the database connection
     * @throws SQLException
     * @return void
     */
    public void close() throws SQLException {
        conn.close();
    }
}
