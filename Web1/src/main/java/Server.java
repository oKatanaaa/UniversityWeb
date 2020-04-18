import library.Shelf;
import library.Sorter;

import java.io.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Server {

    public static void main(String[] args) throws IOException, AlreadyBoundException, InterruptedException {
        // Create anonymous sorter
        Sorter stub = (Sorter) UnicastRemoteObject.exportObject((Sorter) shelf -> {
            shelf.sort();
            return shelf;
        }, 0);

        // Bind the remote object's stub in the registry
        Registry registry = LocateRegistry.createRegistry(2001);
        registry.bind("Server", stub);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
