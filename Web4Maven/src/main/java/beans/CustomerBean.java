package beans;

import library.Reader;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.SQLException;

@ManagedBean
@SessionScoped
public class CustomerBean {
    private final String MSG_USER_NOT_FOUND = "notfound";
    private final String MSG_USER_FOUND = "found";
    private final String MSG_SQL_ERROR = "sqlerror";

    @EJB
    private final CustomerEJB customerEJB;
    private Reader reader;
    private String login;
    private String password;
    private String message = MSG_SQL_ERROR;

    public CustomerBean() {
        this.customerEJB = new CustomerEJB();
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


    public String validateUserLogin() {
        try {
            reader = customerEJB.validateUserLogin(login, password);

            if (reader == null) {
                return message = MSG_USER_NOT_FOUND;
            }
            else {
                return message = MSG_USER_FOUND;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return message = MSG_SQL_ERROR;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
