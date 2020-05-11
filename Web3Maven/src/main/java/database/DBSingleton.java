package database;

import java.sql.SQLException;

public class DBSingleton {
    private final static String url = "jdbc:postgresql://localhost:5432/postgres";
    private final static String user = "postgres";
    private final static String passwd = "333333q";
    private static LibraryDB db = null;

    public static LibraryDB getLibraryDB() throws SQLException {
        if (db == null) {
            db = new LibraryDB(
                    DBSingleton.url,
                    DBSingleton.user,
                    DBSingleton.passwd
            );
        }
        return db;
    }
}
