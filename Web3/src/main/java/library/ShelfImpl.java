package library;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class ShelfImpl implements Serializable, Shelf {

    private static final long serialVersionUID = 1L;

    private String author;
    private ArrayList<Book> books;

    public ShelfImpl(String author) {

        this.author = author;
        this.books = new ArrayList<>();
    }

    public String getAuthor() {
        return author;
    }

    public int getShelfSize() {
        if (books == null) {
            return 0;
        }
        return books.size();
    }

    public void addBook(int index, Book book) {
        books.add(index, book);
    }

    public Book getBook(int index) {
        return books.get(index);
    }

    public Book removeBook(int index) {
        return books.remove(index);
    }

    public boolean writeBooksToFile(String fileName) {
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            for(Book book: books) {
                Book.writeFileR(outputStream, book);
            }
            outputStream.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void readBooksFromFile(String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        Scanner scanner = new Scanner(fileInputStream);
        ArrayList<Book> books = new ArrayList<>();
        while (true) {
            try {
                books.add(Book.readFileR(scanner));
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        fileInputStream.close();
        this.books = books;
    }

    public void sort() {
        // Perform sorting and duplicate deletion via TreeSet
        TreeSet<Book> sortedBooks = new TreeSet<>((o1, o2) -> {

                return o1.getName().compareTo(o2.getName());

        });

        // Add book to the set to perform sorting
        int numEl = books.size();
        for (int i = 0; i < numEl; i++) {
            sortedBooks.add(books.remove(0));
        }

        // Get sorted books back
        int numEl2 = sortedBooks.size();
        for (int i = 0; i < numEl2; i++) {
            books.add(0, sortedBooks.pollFirst());
        }
    }

    @Override
    public String toString() {
        return books.toString();
    }
}
