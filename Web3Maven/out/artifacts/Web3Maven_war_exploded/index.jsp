<%--
  Created by IntelliJ IDEA.
  User: Shoto
  Date: 4/29/2020
  Time: 8:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!

    String userIsFound(ServletContext application) {
        String topLabel = (String)application.getAttribute(
                "top_label"
        );
        if (topLabel.equals("USER NOT FOUND"))
            return "background: #ffacac;";
        else
            return "background: #c2c2c2;";
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="css/index.css">
    <meta charset="UTF-8">
    <title>Log in</title>
</head>
<body>
<div class="container">
    <section class="logo">
        <div class="logo_title">
            <span>${top_label}</span>
        </div>
    </section>
    <section class="form_container">
        <form action="index" method="post">
            <div class="inputs_wrapper">
                <div class="input_field clearfix">
                    <div class="input_name ">
                        Login
                    </div>
                    <div class="a">
                        <input type="text" name="login"  placeholder="Enter username" required style="<%=userIsFound(application)%>" >
                        <span></span>
                    </div>
                </div>
                <div class="input_field clearfix">
                    <div class="input_name ">
                        Password
                    </div>
                    <input type="password" name="password"  placeholder="Enter password" required style="<%=userIsFound(application)%>">
                </div>
            </div>
            <button>Login</button>
        </form>
    </section>
</div>
</body>
</html>