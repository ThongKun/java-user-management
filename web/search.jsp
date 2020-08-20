<%-- 
    Document   : search
    Created on : Aug 17, 2020, 1:52:02 PM
    Author     : HOME
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
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
            <span>Van Thong > Role: ${sessionScope.userinfo.role.name} </span>
            <ul class="menu">
                <li><a href="view-promotion">Promotion List</a></li>
                <li><a href="logout">Log Out</a></li>
            </ul>
        </div>

        <h1 class="title">Search Page</h1>

        <form class="search" action="search">
            <input type="text" name="s" value="${param.s}"/>
            <input type="submit" value="Search" />
        </form>
        <br/>

        <hr/>

        <button style="border: none;    border-bottom: 1px inset;    border-radius: unset; float:right">
            <a href="create-new-user" style="color:#8BC34A;font-size: 15px;">Create new user</a>
        </button><br/><br/>


        <div class="user-container">
            <div class="role-list">
                <ul class="tab-info-ul">
                    <li class="tab-info-li" <c:if test="${empty param.roleId}">style="background:#897870;"</c:if>>
                        <a href="${pageContext.request.contextPath}?s=${not empty param.s ? param.s : ''}">All</a>
                    </li>
                    <c:if test="${sessionScope.userinfo.role.id eq 1}">
                        <li class="tab-info-li" <c:if test="${param.roleId eq 1}">style="background:#897870;"</c:if>>
                            <a href="${pageContext.request.contextPath}?roleId=1&s=${not empty param.s ? param.s : ''}">Admin</a>
                        </li>
                    </c:if>
                    <c:forEach items="${requestScope.ROLES}" var="item" begin="1">
                        <li class="tab-info-li" <c:if test="${param.roleId eq item.id}">style="background:#897870;"</c:if>>
                            <a href="${pageContext.request.contextPath}?roleId=${item.id}&s=${not empty param.s ? param.s : ''}">${item.name}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="user-table">
                <c:choose>
                    <c:when test="${not empty requestScope.USERS}">
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>No.</th>
                                    <th>email</th>
                                    <th>name</th>
                                    <th>phone</th>
                                    <th>photo</th>
                                    <th>role</th>
                                    <th>active</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.USERS}" var="item" varStatus="counter">
                                    <tr>
                                        <td>${counter.count}</td>
                                        <td>${item.email}</td>
                                        <td>${item.name}</td>
                                        <td>${item.phone}</td>
                                        <td class="photo">
                                            <img src="images/${item.img}" width="80" height="auto" alt="No Image" />
                                        </td>
                                        <td>${item.role.name}</td>
                                        <td>${item.status}</td>
                                        <td>
                                            <button type="submit">
                                                <a href="update-user?id=${item.id}" style="color:blue">Update</a>
                                            </button>
                                            <c:choose>
                                                <c:when test="${item.status}">
                                                    <button>
                                                        <a href="change-user-status?id=${item.id}" style="color:#6c6a6c">Ban</a>
                                                    </button> 
                                                </c:when>
                                                <c:otherwise>
                                                    <button>
                                                        <a href="change-user-status?id=${item.id}" style="color:#3cb027">Activate</a>
                                                    </button> 
                                                </c:otherwise>
                                            </c:choose>
                                            <button>
                                                <a class="rank-up" 
                                                   href="add-to-promotion?id=${item.id}" 
                                                   onclick="return <c:if test="${item.promotion.score > 0}">warning()</c:if><c:if test="${empty item.promotion}">confirmInsert()</c:if>"
                                                       >
                                                       Rank Up
                                                   </a>
                                                </button> 
                                            </td>
                                        </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        Not Found
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="user-pagination">
                <a>🔺</a>
                <a>1</a>
                <a>2</a>
                <a>3</a>
                <a>4</a>
                <a>5</a>
                <a>6</a>
                <a>7</a>
                <a>🔻</a>
            </div>
        </div>

        <script>
                                                       function warning() {
                                                           alert('User was already scored in ranking list!')
                                                           return false;
                                                       }
                                                       function confirmInsert() {
                                                           return confirm('Are you want to add this user to ranking ?')
                                                       }
        </script>
    </body>
</html>
