import database.LibraryDB;
import library.Book;
import library.Reader;
import library.Shelf;
import library.ShelfImpl;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DBFiller {

    public static String pathToRead = "C:\\Users\\Shoto\\Desktop\\book3.txt";


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

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            // libraryDB.dropTables();
        }
    }
}
