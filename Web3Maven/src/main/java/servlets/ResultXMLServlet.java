package servlets;


import library.Reader;
import library.ShelfImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


@WebServlet("/result.xml")
public class ResultXMLServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        HttpSession session = req.getSession();
        Reader reader = (Reader) session.getAttribute("user");
        if (reader == null) {
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/error.jsp");
            requestDispatcher.forward(req, resp);
        }

        JAXBContext context = null;
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
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
