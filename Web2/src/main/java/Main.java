import database.LibraryDB;
import library.Book;
import library.Reader;
import library.Shelf;
import library.ShelfImpl;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static String pathToRead = "C:\\Users\\Shoto\\Desktop\\book.txt";


    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String passwd = "333333q";
        LibraryDB libraryDB = new LibraryDB(url, user, passwd);
        try {
            // Create all the tables
            libraryDB.setupTables();

            // Read shelf from file
            Shelf shelfImpl = new ShelfImpl("Arthur");
            shelfImpl.readBooksFromFile(pathToRead);
            shelfImpl.sort();
            System.out.println(shelfImpl);

            // Create reader
            ArrayList<Shelf> shelves = new ArrayList<>();
            shelves.add(shelfImpl);
            Reader reader = new Reader(0, "Igor", "Kilbas", "anime", shelves);

            // Add reader
            libraryDB.addReader(reader);

            // Checkout database class functionality
            // Check whether a reader is in the db
            System.out.println(libraryDB.checkReader("a", "a"));
            // Get a book from the db
            Book book = libraryDB.getBook("Harry Potter");
            System.out.println(book);
            // Remove a book from the db
            libraryDB.removeBook("Harry Potter");
            System.out.println(libraryDB.checkBook("Harry Potter"));
            // Get a list of shelves from the db
            ArrayList<Shelf> shelves2 = libraryDB.getShelves("a", "a");
            System.out.println(shelves2);

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            libraryDB.dropTables();
        }
    }
}
