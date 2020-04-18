package database;

import library.Book;
import library.Reader;
import library.Shelf;
import library.ShelfImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class LibraryDB {

    // Tables creation
    private static String CREATE_READER_TABLE = "CREATE TABLE READER" +
            "(" +
            "READER_ID INT PRIMARY KEY, " +
            "NAME VARCHAR(30), LOGIN VARCHAR(30), PASSWORD VARCHAR(10), " +
            "UNIQUE(READER_ID)" +
            ");";
    private static String CREATE_SHELF_TABLE = "CREATE TABLE SHELF" +
            "(" +
            "AUTHOR VARCHAR(20) PRIMARY KEY" +
            ");";
    private static String CREATE_READERSHELF_TABLE = "CREATE TABLE READERSHELF" +
            "(" +
            "READER_ID INT, SHELF_ID VARCHAR(20)," +
            "PRIMARY KEY (READER_ID, SHELF_ID)," +
            "FOREIGN KEY (READER_ID) REFERENCES READER(READER_ID)," +
            "FOREIGN KEY (SHELF_ID) REFERENCES SHELF(AUTHOR)" +
            ");";
    private static String CREATE_BOOK_TABLE = "CREATE TABLE BOOK" +
            "(" +
            "TITLE VARCHAR(20) PRIMARY KEY, " +
            "YEAR INT, N_PAGES INT, COST INT, AUTHOR VARCHAR(20) NOT NULL," +
            "FOREIGN KEY (AUTHOR) REFERENCES SHELF(AUTHOR)" +
            ");";

    // Tables dropping
    private static String DROP_TABLE = "DROP TABLE ";

    // INSERT transactions
    private static String INSERT_READER = "INSERT INTO READER VALUES(?, ?, ?, ?)";      // (int, str, str, str)
    private static String INSERT_SHELF = "INSERT INTO SHELF VALUES(?)";                 // (str)
    private static String INSERT_READERSHELF = "INSERT INTO READERSHELF VALUES(?, ?)";  // (int, str)
    private static String INSERT_BOOK = "INSERT INTO BOOK VALUES(?, ?, ?, ?, ?)";       // (str, int, int, int, str)

    // DELETE transactions
    private static String DELETE_BOOK = "DELETE FROM BOOK WHERE TITLE=?";

    // SELECT transactions
    // ADDITIONAL TRANSACTION-1 ACCORDING TO THE TASK
    private static String SELECT_BOOK_TITLE = "SELECT * FROM BOOK WHERE TITLE=?";
    private static String SELECT_BOOK_AUTHOR = "SELECT * FROM BOOK WHERE AUTHOR=?";
    private static String SELECT_READERS_SHELVES = "SELECT S.AUTHOR FROM " +
            "SHELF S, READERSHELF RS, READER R " +
            "WHERE R.LOGIN=? AND R.PASSWORD=? AND R.READER_ID=RS.READER_ID";


    // For checking whether an object is present in the database
    // ADDITIONAL TRANSACTION-2 ACCORDING TO THE TASK
    private static String CHECK_BOOK = "SELECT COUNT(*) FROM BOOK WHERE TITLE=?";
    private static String CHECK_READER = "SELECT COUNT(*) FROM READER WHERE " +
            "LOGIN=? AND PASSWORD=?";


    private String url;
    private String user;
    private String password;
    private Connection connection;

    public LibraryDB(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public boolean setupTables() {
        try {
            PreparedStatement createReader = connection.prepareStatement(LibraryDB.CREATE_READER_TABLE);
            createReader.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create READER table.");
            return false;
        }
        try {
            PreparedStatement createShelf = connection.prepareStatement(LibraryDB.CREATE_SHELF_TABLE);
            createShelf.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create SHELF table.");
            return false;
        }
        try {
            PreparedStatement createReaderShelf = connection.prepareStatement(LibraryDB.CREATE_READERSHELF_TABLE);
            createReaderShelf.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create READERSHELF table.");
            return false;
        }
        try {
            PreparedStatement createBook = connection.prepareStatement(LibraryDB.CREATE_BOOK_TABLE);
            createBook.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create BOOK table.");
            return false;
        }
        return true;
    }

    public boolean dropTables() {
        boolean withoutErrors = true;
        // Drop dependent table first
        try {
            PreparedStatement dropReaderShelf = connection.prepareStatement(LibraryDB.DROP_TABLE + "READERSHELF");
            dropReaderShelf.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to drop READERSHELF table.");
            withoutErrors = false;
        }
        try {
            PreparedStatement dropBook = connection.prepareStatement(LibraryDB.DROP_TABLE + "BOOK");
            dropBook.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to drop BOOK table.");
            withoutErrors = false;
        }
        // Drop independent
        try {
            PreparedStatement dropReader = connection.prepareStatement(LibraryDB.DROP_TABLE + "READER");
            dropReader.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to drop READER table.");
            withoutErrors = false;
        }
        try {
            PreparedStatement dropShelf = connection.prepareStatement(LibraryDB.DROP_TABLE + "SHELF");
            dropShelf.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to drop SHELF table.");
            withoutErrors = false;
        }

        return withoutErrors;
    }


    public void addBook(Book book, String author) throws SQLException {
        String name = book.getName();
        // Only year is needed.
        // P.S. I overcomplicated the task but realized it only now.
        int year = book.getDate().get(Calendar.YEAR);
        int n_pages = book.getPageNumber();
        int cost = book.getCost();

        // Add book to the BOOK table
        PreparedStatement statement = connection.prepareStatement(LibraryDB.INSERT_BOOK);
        statement.setString(1, name);
        statement.setInt(2, year);
        statement.setInt(3, n_pages);
        statement.setInt(4, cost);
        statement.setString(5, author);
        statement.execute();
    }

    public void addShelf(Shelf shelf, int reader_id) throws SQLException {
        // Check if the method was called by the `addReader` method
        boolean innerCall = true;
        if (connection.getAutoCommit()) {
            connection.setAutoCommit(false);
            innerCall = false;
        }

        // Add shelf to the SHELF table first
        String author = shelf.getAuthor();
        PreparedStatement statement = connection.prepareStatement(LibraryDB.INSERT_SHELF);
        statement.setString(1, author);
        statement.execute();

        // Add all books to the database
        int nbooks = shelf.getShelfSize();
        for(int i = 0; i < nbooks; i++) {
            Book book = shelf.getBook(i);
            this.addBook(book, author);
        }

        // Add shelf to the READERSHELF table
        PreparedStatement statement2 = connection.prepareStatement(LibraryDB.INSERT_READERSHELF);
        statement2.setInt(1, reader_id);
        statement2.setString(2, author);
        statement2.execute();

        // If it is not an inner call, then commit changes and set auto-commit on
        if (!innerCall) {
            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    public void addReader(Reader reader) throws SQLException {
        // Disable auto-commit in order to combine all the statements into one transaction
        connection.setAutoCommit(false);

        // Add reader to the READER table first
        int reader_id = reader.getId();
        String name = reader.getName();
        String login = reader.getLogin();
        String password = reader.getPassword();
        PreparedStatement statement = connection.prepareStatement(LibraryDB.INSERT_READER);
        statement.setInt(1, reader_id);
        statement.setString(2, name);
        statement.setString(3, login);
        statement.setString(4, password);
        statement.execute();

        // Add shelves to the SHELF table
        int n_shelves = reader.shelvesCount();
        for(int i = 0; i < n_shelves; i++) {
            Shelf shelf = reader.getShelf(i);
            this.addShelf(shelf, reader_id);
        }
        connection.commit();

        connection.setAutoCommit(true);
    }

    // ADDITIONAL TRANSACTION-2 ACCORDING TO THE TASK
    public boolean checkBook(String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(LibraryDB.CHECK_BOOK);
        statement.setString(1, name);
        statement.execute();
        ResultSet set = statement.getResultSet();
        set.next();
        return set.getInt(1) != 0;
    }

    // ADDITIONAL TRANSACTION-1 ACCORDING TO THE TASK
    public Book getBook(String name) throws SQLException {
        if (!this.checkBook(name)) {
            throw new RuntimeException(name + " book was not found.");
        }

        PreparedStatement statement = connection.prepareStatement(LibraryDB.SELECT_BOOK_TITLE);
        statement.setString(1, name);
        statement.execute();
        ResultSet set = statement.getResultSet();
        set.next();

        int year = set.getInt("YEAR");
        int npages = set.getInt("N_PAGES");
        int cost = set.getInt("COST");
        GregorianCalendar date = new GregorianCalendar(year, Calendar.JANUARY, 1);
        return new Book(name, date, npages, cost);
    }

    public boolean removeBook(String name) throws SQLException {
        if (!this.checkBook(name)) {
            return false;
        }
        PreparedStatement statement = connection.prepareStatement(LibraryDB.DELETE_BOOK);
        statement.setString(1, name);
        statement.execute();
        return true;
    }

    public boolean checkReader(String login, String password) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(LibraryDB.CHECK_READER);
        statement.setString(1, login);
        statement.setString(2, password);
        statement.execute();
        ResultSet set = statement.getResultSet();
        set.next();
        return set.getInt(1) != 0;
    }

    // This method is equal to "выбор всех элементов заданного списка"
    public Shelf getShelf(String author) throws SQLException {
        Shelf shelf = new ShelfImpl(author);
        PreparedStatement statement = connection.prepareStatement(LibraryDB.SELECT_BOOK_AUTHOR);
        statement.setString(1, author);
        statement.execute();
        ResultSet set = statement.getResultSet();
        while(set.next()) {
            Book book = this.getBook(set.getString("TITLE"));
            shelf.addBook(0, book);
        }
        return shelf;
    }

    public ArrayList<Shelf> getShelves(String login, String password) throws SQLException {
        if (!this.checkReader(login, password)) {
            throw new RuntimeException("User was not found.");
        }

        ArrayList<Shelf> shelves = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(LibraryDB.SELECT_READERS_SHELVES);
        statement.setString(1, login);
        statement.setString(2, password);
        statement.execute();
        ResultSet set = statement.getResultSet();
        while(set.next()) {
            Shelf shelf = this.getShelf(set.getString("AUTHOR"));
            shelves.add(shelf);
        }
        return shelves;
    }
}
