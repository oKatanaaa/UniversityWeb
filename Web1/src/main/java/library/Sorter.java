package library;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sorter extends Remote {
    Shelf sort(Shelf shelf) throws RemoteException;
}
