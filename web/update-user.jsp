<%-- 
    Document   : signup
    Created on : Apr 29, 2020, 11:51:34 PM
    Author     : HOME
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update User Info</title>     
        <style>
            <%@ include file="/css/search.css" %>
        </style>
    </head>
    <body>
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/">Search Page</a>
            </li>
        </ul> 
        <h1>Update User Info</h1>
        
        <img src="images/${requestScope.selected_user.img}" height="100px" width="auto"/><br/><br/>
        <c:if test="${requestScope.success != null}">
            <font color="green">
            ${requestScope.success}
            </font>
        </c:if>
        <c:if test="${not empty requestScope.errors.duplicateEmailError}">
            <p style="color:red">${requestScope.errors.duplicateEmailError}</p>
        </c:if>

        <form action="update-user?id=${param.id}" method="POST" enctype="multipart/form-data">
            <c:if test="${not empty requestScope.errors.nameError}">
                <p style="color:red">${requestScope.errors.nameError}</p>
            </c:if>
            Name <input type="text" name="name" value="${requestScope.selected_user.name}" /><br/>

            <c:if test="${not empty requestScope.errors.emailError}">
                <p style="color:red">${requestScope.errors.emailError}</p>
            </c:if>
            Email <input type="email" value="${requestScope.selected_user.email}" disabled/>
            <input type="hidden" name="email" value="${requestScope.selected_user.email}" /><br/>

            <c:if test="${not empty requestScope.errors.passwordError}">
                <p style="color:red">${requestScope.errors.passwordError}</p>
            </c:if>
            Password <input type="password" name="password" value="123456" /><br/>

            Role<select name="role" id="role">
                <c:forEach items="${requestScope.ROLES}" var="role">
                    <option value="${role.id}" <c:if test="${role.id eq requestScope.selected_user.role.id}">selected</c:if> >${role.name}</option>
                </c:forEach>
            </select><br/>
            Phone:<input type="text" name="phone" value="${requestScope.selected_user.phone}"/><br/><br/>
            <input name="uploadfile" type="file" size="50" accept=".jpg, .jpeg, .png"/><br/><br/>

            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
