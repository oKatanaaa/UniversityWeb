package beans;

import database.DBSingleton;
import database.LibraryDB;
import library.Book;

import javax.ejb.Stateless;
import java.sql.SQLException;

@Stateless
public class ShelfBookEJB {

    public boolean checkBookExists(String bookName) throws SQLException {
        LibraryDB db = DBSingleton.getLibraryDB();
        return db.checkBook(bookName);
    }

    public void addBook(Book book, String author) throws SQLException {
        LibraryDB db = DBSingleton.getLibraryDB();
        db.addBook(book, author);
    }
}
