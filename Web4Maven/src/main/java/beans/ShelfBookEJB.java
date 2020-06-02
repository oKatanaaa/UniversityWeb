package beans;

import database.DBSingleton;
import database.LibraryDB;
import library.Book;

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import java.sql.SQLException;

public class ShelfBookEJB {

    public void addBook(Book book, String author) throws SQLException {
        LibraryDB db = DBSingleton.getLibraryDB();
        if (db.checkBook(book.getName())){
            FacesMessage facesMessage = new FacesMessage("Такая книга уже существует.");
            throw new ValidatorException(facesMessage);
        }
        db.addBook(book, author);
    }
}
