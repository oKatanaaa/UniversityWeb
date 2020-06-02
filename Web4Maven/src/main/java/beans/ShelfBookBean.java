package beans;


import library.Book;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.sql.SQLException;

@ManagedBean
@SessionScoped
public class ShelfBookBean {
    private ShelfBookEJB shelfBookEJB = new ShelfBookEJB();
    private String author = "hui";
    private String bookName;
    private int date;
    private int pageNumber;
    private int cost;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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
        this.pageNumber = pageNumber;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void insertBook(String author) {
        this.author = author;
        System.out.println(author);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("insert.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void costValidator(FacesContext facesContext, UIComponent uiComponent, Object o) {
        int toValidate;
        try {
            toValidate = Integer.parseInt(o.toString());
            if (toValidate < 0) {
                FacesMessage facesMessage = new FacesMessage("Цена должна быть неотрицательной.");
                throw new ValidatorException(facesMessage);
            }
        } catch (NumberFormatException e) {
            FacesMessage facesMessage = new FacesMessage("Число должно быть целым.");
            throw new ValidatorException(facesMessage);
        }
    }

    public void dateValidator(FacesContext facesContext, UIComponent uiComponent, Object o) {
        int toValidate;
        try {
            toValidate = Integer.parseInt(o.toString());
            if (toValidate < 0) {
                FacesMessage facesMessage = new FacesMessage("Год должен быть неотрицательным.");
                throw new ValidatorException(facesMessage);
            }
        } catch (NumberFormatException e) {
            FacesMessage facesMessage = new FacesMessage("Число должно быть целым.");
            throw new ValidatorException(facesMessage);
        }
    }

    public void bookNameValidator(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String toValidate = "filler";
        try {
            toValidate = o.toString();
            if (toValidate.equals("")) {
                FacesMessage facesMessage = new FacesMessage("Имя должно быть непустым.");
                throw new ValidatorException(facesMessage);
            }
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }

    public void pageValidator(FacesContext facesContext, UIComponent uiComponent, Object o) {
        int toValidate;
        try {
            toValidate = Integer.parseInt(o.toString());
            if (toValidate <= 0) {
                FacesMessage facesMessage = new FacesMessage("Количество страниц должно быть положительным.");
                throw new ValidatorException(facesMessage);
            }
        } catch (NumberFormatException e) {
            FacesMessage facesMessage = new FacesMessage("Число должно быть целым.");
            throw new ValidatorException(facesMessage);
        }
    }

    public String addBook(CustomerBean bean) {
        Book book = new Book(bookName, date, pageNumber, cost);
        try {
            shelfBookEJB.addBook(book, author);
            bean.updateReader();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "error";
        }
        return "success";
    }

}
