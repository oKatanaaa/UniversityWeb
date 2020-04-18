package library;

import java.io.IOException;

public interface Shelf  {
    String getAuthor() ;
    int getShelfSize();
    void addBook(int index, Book book);
    Book removeBook(int index);
    boolean writeBooksToFile(String fileName);
    void readBooksFromFile(String fileName) throws IOException;
    void sort() ;


}
