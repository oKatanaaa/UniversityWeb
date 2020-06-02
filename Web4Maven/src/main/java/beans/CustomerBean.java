package beans;

import library.Reader;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
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

    public void downloadXML() {
        JAXBContext context = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletResponse resp = (HttpServletResponse) ctx.getExternalContext().getResponse();
        System.out.println("a");
        try {
            context = JAXBContext.newInstance(Reader.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // Write to System.out
            m.marshal(reader, System.out);
            resp.setHeader("Content-disposition","attachment; filename=result.xml");
            resp.setContentType("application/xml");
            // Convert xml to string
            StringWriter writer = new StringWriter();
            m.marshal(reader, writer);
            PrintWriter respWriter = resp.getWriter();
            respWriter.println(writer.toString());
            writer.close();
            ctx.responseComplete();
        } catch (JAXBException | IOException e) {
            e.printStackTrace();

        }
    }

    public String logOut() {
        reader = null;
        return "logout";
    }

    public void deleteBook(String bookName) {
        try {
            customerEJB.deleteBook(bookName);
            updateReader();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateReader() throws SQLException {
        reader = customerEJB.validateUserLogin(login, password);
    }


}
