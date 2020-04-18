import library.Shelf;
import library.ShelfImpl;
import library.Sorter;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    public static String readPath() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


    public static void main(String[] args) throws IOException, NotBoundException {
        // "C:\\Users\\Shoto\\Desktop\\book.txt"
        System.out.println("Enter path to the input file: ");
        String pathToRead = readPath();
        System.out.println("Enter path to the output file: ");
        String pathToWrite = readPath();
        Shelf shelfImpl = new ShelfImpl("Arthur");
        shelfImpl.readBooksFromFile(pathToRead);
        System.out.println(shelfImpl);

        // Get the stub
        Registry registry = LocateRegistry.getRegistry(2001);
        Sorter stub = (Sorter) registry.lookup("Server");

        // Perform sorting
        Shelf a = stub.sort(shelfImpl);
        System.out.println(a);
        a.writeBooksToFile(pathToWrite);

    }
}
