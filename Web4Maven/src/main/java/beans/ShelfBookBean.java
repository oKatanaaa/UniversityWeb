package beans;


import library.Book;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.io.IOException;
import java.sql.SQLException;

@ManagedBean
@SessionScoped
public class ShelfBookBean {
    private ShelfBookEJB shelfBookEJB = new ShelfBookEJB();
    private String author = "";
    private String bookName;
    private Integer date;
    private Integer pageNumber;
    private Integer cost;

    public ShelfBookBean() {
        update();
    }

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

    public Integer getDate() {
        if (date < 0)
            return null;
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getPageNumber() {
        if (pageNumber < 0)
            return null;

        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getCost() {
        if (cost < 0)
            return null;
        return cost;
    }

    public void setCost(Integer cost) {
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
            if (shelfBookEJB.checkBookExists(toValidate)) {
                FacesMessage facesMessage = new FacesMessage("Такая книга уже существует.");
                throw new ValidatorException(facesMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            FacesMessage facesMessage = new FacesMessage("Ошибка запроса.");
            throw new ValidatorException(facesMessage);
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
            update();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "error";
        }
        return "success";
    }

    private void update() {
        author = "";
        bookName = "";
        date = -1;
        cost = -1;
        pageNumber = -1;
    }

}
