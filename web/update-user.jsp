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
        <div class="custom-shape-divider-top-1597926621">
            <svg data-name="Layer 1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1200 120" preserveAspectRatio="none">
            <path d="M321.39,56.44c58-10.79,114.16-30.13,172-41.86,82.39-16.72,168.19-17.73,250.45-.39C823.78,31,906.67,72,985.66,92.83c70.05,18.48,146.53,26.09,214.34,3V0H0V27.35A600.21,600.21,0,0,0,321.39,56.44Z" class="shape-fill"></path>
            </svg>
        </div>
        <div class="nav-bar">
            <span>${sessionScope.userinfo.name} > Role: ${sessionScope.userinfo.role.name} </span>
            <ul class="menu">
                <li><a href="${pageContext.request.contextPath}/">Search Page</a></li>
                <li><a href="logout">Log Out</a></li>
            </ul>
        </div>

        <h1 align="center">Update User Info</h1>


        <c:if test="${requestScope.success != null}">
            <font color="green" align="center" style="display: block;">
            ${requestScope.success}
            </font>
        </c:if>
        <c:if test="${not empty requestScope.errors.duplicateEmailError}">
            <p style="color:red">${requestScope.errors.duplicateEmailError}</p>
        </c:if>

        <form class="user-form" action="update-user?id=${param.id}" method="POST" enctype="multipart/form-data">
            <img src="images/${requestScope.selected_user.img}" height="100px" width="auto"/><br/><br/>
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
