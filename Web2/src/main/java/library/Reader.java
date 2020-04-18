package library;

import java.util.ArrayList;

public class Reader {

    private int id;
    private String name;
    private String login;
    private String password;
    private ArrayList<Shelf> shelves;

    public Reader(int id, String name, String login, String password, ArrayList<Shelf> shelves) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.shelves = shelves;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addShelf(Shelf shelf) {
        shelves.add(shelf);
    }

    public Shelf getShelf(int i) {
        return shelves.get(i);
    }

    public Shelf removeShelf(int i) {
        return shelves.remove(i);
    }

    public int shelvesCount() {
        return shelves.size();
    }
}
