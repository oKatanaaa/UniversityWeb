package library;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.IOException;


public interface Shelf {
    String getAuthor();
    int getShelfSize();
    void addBook(int index, Book book);
    Book getBook(int index);
    Book removeBook(int index);
    boolean writeBooksToFile(String fileName);
    void readBooksFromFile(String fileName) throws IOException;
    void sort();
}
