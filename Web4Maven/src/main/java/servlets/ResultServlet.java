package servlets;

import database.DBSingleton;
import database.LibraryDB;
import library.Reader;
import library.Shelf;
import library.ShelfImpl;

import javax.jms.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet("/result")
public class ResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String path = "/result.jsp";
        ServletContext servletContext = getServletContext();
        HttpSession session = req.getSession();
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/error.jsp");
            requestDispatcher.forward(req, resp);
        }
        try {
            LibraryDB libraryDB = DBSingleton.getLibraryDB();
            reader = libraryDB.getReader(reader.getLogin(), reader.getPassword());
            System.out.println(reader);
            session.setAttribute("user", reader);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
            try {
                requestDispatcher.forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}