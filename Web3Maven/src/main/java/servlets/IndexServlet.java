package servlets;

import database.DBSingleton;
import database.LibraryDB;
import library.Reader;

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


@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String topLabel = (String) context.getAttribute("top_label");

        System.out.println(topLabel);
        HttpSession session = req.getSession();
        if (session!= null) {
            Reader reader = (Reader) session.getAttribute("user");
            if(reader != null) {
                session.removeAttribute("user");
            }
        }

        if (topLabel==null) {
            context.setAttribute("top_label", "SIGN IN");
        }

        String path = "/index.jsp";
        RequestDispatcher requestDispatcher = context.getRequestDispatcher(path);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (req == null) {
            System.out.println("Null request");
            return;
        }

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        boolean userExists = false;
        try {
            LibraryDB libraryDB = DBSingleton.getLibraryDB();
            userExists = libraryDB.checkReader(login, password);
            System.out.println(userExists);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ServletContext context = getServletContext();
        if (userExists) {
            context.setAttribute("top_label", "SIGN IN");
            Reader reader = new Reader();
            reader.setLogin(login);
            reader.setPassword(password);

            HttpSession session = req.getSession();
            session.setAttribute("user", reader);
            String path = req.getContextPath() +  "/result";
            try {
                resp.sendRedirect(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            context.setAttribute("top_label", "USER NOT FOUND");
            String path = "/index.jsp";
            RequestDispatcher requestDispatcher = context.getRequestDispatcher(path);
            try {
                requestDispatcher.forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
    }

}
