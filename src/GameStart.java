import model.LoginDB;
import model.SQLiteDB;
import view.Login;

import java.io.File;
import java.sql.SQLException;


/**
 * Class: GameStart
 * Author: Annette Vinson
 * Date: October 24, 2020
 * For: ITEC 3860 Project RedStar
 */
public class GameStart {
    SQLiteDB sdb;

    public static void main(String[] args) {
        File dbFile;


        /* Create User file if not found */
        try {
            dbFile = new File("User.db");
            if (!dbFile.exists()) {
                LoginDB logDB = new LoginDB();
                logDB.createUserFile();
            }

//        GameUI.launch(GameUI.class);

            Login.launch(Login.class);
        } catch (SQLException sql) {
            System.out.println("Cannot create User file");
        }

        System.exit(0);
    }
}
