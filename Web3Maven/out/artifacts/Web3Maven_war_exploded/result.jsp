<%@ page import="library.Reader" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="library.Shelf" %>
<%@ page import="java.util.List" %>
<%@ page import="library.Book" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="library.ShelfImpl" %><%--
  Created by IntelliJ IDEA.
  User: Shoto
  Date: 4/30/2020
  Time: 11:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Reader reader = (Reader) request.getSession().getAttribute("user");
    if (reader == null) {
        response.setHeader("Error", "5;url=index.jsp");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Result</title>
    <link rel="stylesheet" type="text/css" href="css/result.css">
</head>
<body>
<header>
    <div class="container">
        <div class="title">
            <h1>Welcome, <%=reader.getName()%></h1>
        </div>
    </div>
</header>
<div class="container">
    <a href="index">Log out</a>
    <a href="result.xml">Download XML</a>
</div>
<div class="container">
    <table class="data_table">
        <caption>Your books</caption>
        <tr>

            <th>Book title</th>
            <th>Author</th>
            <th>Year</th>
            <th>Pages</th>
            <th>Cost</th>
        </tr>
        <%
            ArrayList<ShelfImpl> shelves = reader.getShelves();
            for (ShelfImpl shelf : shelves) {
                for (int i = 0; i < shelf.getShelfSize(); i++) {
                    Book book = shelf.getBook(i);
        %>

                    <tr>
                        <td><%=book.getName()%></td>
                        <td><%=shelf.getAuthor()%></td>
                        <td><%=book.getDate().get(Calendar.YEAR)%></td>
                        <td><%=book.getPageNumber()%></td>
                        <td><%=book.getCost()%></td>
                    </tr>

        <%
                }
            }
        %>
    </table>
</div>
</body>
</html>
