<%-- 
    Document   : login
    Created on : Apr 29, 2020, 11:17:35 PM
    Author     : HOME
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <style>
            <%@ include file="/css/search.css" %>
        </style>
    </head>
    <body>

        <h1>Login</h1>
        <c:if test="${not empty requestScope.errorLogin}">
            <h4 style="color:red">${requestScope.errorLogin}</h4>
        </c:if>
        <form name="btAction" action="login" method="POST">
            Email <input type="text" name="email" value="admin@gmail.com" placeholder="Enter your email"/>
            Password <input type="password" name="password" value="123456" />
            <input type="submit" value="Login" />
        </form>
        <hr/>
        <a href="${pageContext.request.contextPath}/signup">sign up</a>
    </body>
</html>
