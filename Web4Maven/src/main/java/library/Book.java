package library;

import javax.xml.bind.annotation.*;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.StringTokenizer;


@XmlRootElement(name = "book")
public class Book implements Serializable {

    public static boolean writeFileR(FileOutputStream outputStream, Book book) {
        PrintStream printStream = new PrintStream(outputStream);
        printStream.println(book.toString());
        return true;
    }

    public static Book readFileR(FileInputStream inputStream) {
        Scanner  scanner = new Scanner (inputStream);
        return Book.readFileR(scanner);
    }

    public static Book readFileR(Scanner  scanner) {
        String bookString = scanner.nextLine();
        if (bookString == null) {
            throw new RuntimeException("Null string");
        }

        StringTokenizer st = new StringTokenizer(bookString, "{}(),=|");
        st.nextToken(); // take off book
        st.nextToken(); // take off name
        String name = st.nextToken();
        st.nextToken(); // take off date
        int year = Integer.parseInt(st.nextToken());
        int month = Integer.parseInt(st.nextToken());
        int day = Integer.parseInt(st.nextToken());
        st.nextToken(); // take off pageNumber
        int pageNumber = Integer.parseInt(st.nextToken());
        st.nextToken(); // take off cost
        int cost = Integer.parseInt(st.nextToken());

        return new Book(name, month, pageNumber, cost);
    }

    public static boolean writeFile(String fileName, Book book) {
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(book);
            objectOutputStream.close();

            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Book readFile(String fileName) throws IOException, ClassNotFoundException{
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return (Book)objectInputStream.readObject();
    }

    private static final long serialVersionUID = 1L;

    private String name;
    private int date;
    private int pageNumber;
    private int cost;

    public Book() {};

    public Book(String name, int date, int pageNumber, int cost) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("Number of pages must be greater zero.");
        }
        if (cost < 0) {
            throw new IllegalArgumentException("Cost must be positive.");
        }

        this.name = name;
        this.date = date;
        this.pageNumber = pageNumber;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }
    @XmlElement(name="name")
    public void setName(String name) {
        this.name = name;
    }

    public int getDate() {
        return date;
    }
    public void setDate(int date) {
        this.date = date;
    }

    public int getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(int pageNumber) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("Number of pages must be greater zero.");
        }
        this.pageNumber = pageNumber;
    }

    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Cost must be positive.");
        }
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "library.Book{" +
                "name=" + name +
                "|date=" + date +
                "|pageNumber=" + pageNumber +
                "|cost=" + cost +
                "}";
    }

}
