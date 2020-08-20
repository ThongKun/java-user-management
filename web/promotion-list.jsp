<%-- 
    Document   : promotion-list
    Created on : Aug 20, 2020, 1:15:41 PM
    Author     : HOME
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ranking</title>
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
                <li><a href="${pageContext.request.contextPath}/">Search Page</a></li>
                <li><a href="logout">Log Out</a></li>
            </ul>
        </div>

        <h1 class="title">Ranking</h1>
        <div class="promotion-container">
            <c:choose>
                <c:when test="${fn:length(requestScope.promotions) > 0}">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Avatar</th>
                                <th>Name</th>
                                <th>Role</th>
                                <th>Score</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.promotions}" var="item" varStatus="counter">
                            <form action="update-user-promotion?id=${item.id}" method="post">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>
                                        <img src="images/${item.userId.img}" width="100px" height="auto"/>
                                    </td>
                                    <td>${item.userId.name}</td>
                                    <td>${item.userId.role.name}</td>
                                    <td>
                                        <select name="score">
                                            <option value="5" <c:if test="${item.score eq 5}">selected</c:if>>
                                                    5✨
                                                </option>
                                            <c:forEach begin="1" end="10" var="i">
                                                <c:if test="${i != 5}">
                                                    <option value="${i}" <c:if test="${item.score eq i}">selected</c:if>>
                                                        ${i}✨
                                                    </option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <button type="submit" style="color:blue" onclick="return confirmUpdate(this)">
                                            Update
                                        </button>
                                        <button>
                                            <a href="remove-user-promotion?id=${item.id}" style="color:#6c6a6c">
                                                Remove
                                            </a>
                                        </button>
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <h3>Ranking List Empty!</h3>
                </c:otherwise>
            </c:choose>
        </div>

        <script>
            function confirmUpdate(e) {
                let check = confirm("Are you sure to update the user's rank ?")
                return check;
            }
        </script>
    </body>
</html>
