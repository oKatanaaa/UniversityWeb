package beans;


import database.DBSingleton;
import database.LibraryDB;
import library.Reader;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.sql.SQLException;

@Stateless
public class CustomerEJB {

    public Reader validateUserLogin(String login, String password) throws SQLException {
        LibraryDB db = DBSingleton.getLibraryDB();
        if (db.checkReader(login, password)) {
            return db.getReader(login, password);
        } else {
            return null;
        }
    }
}
