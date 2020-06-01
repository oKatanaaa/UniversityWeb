package library;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
public class Reader {

    private int id;
    private String name;
    private String login;

    private String password;

    private ArrayList<ShelfImpl> shelves;

    public Reader() {};

    public Reader(int id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.shelves = null;
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
    @XmlTransient
    public void setPassword(String password) {
        this.password = password;
    }


    public ArrayList<ShelfImpl> getShelves() { return this.shelves; }
    @XmlElementWrapper(name = "shelves")
    @XmlElement(name = "shelf")
    public void setShelves(ArrayList<ShelfImpl> shelves) { this.shelves = shelves; }

    public void addShelf(ShelfImpl shelf) {
        shelves.add(shelf);
    }
    public ShelfImpl getShelf(int i) {
        return shelves.get(i);
    }
    public ShelfImpl removeShelf(int i) {
        return shelves.remove(i);
    }
    public int shelvesCount() {
        return shelves.size();
    }
}
