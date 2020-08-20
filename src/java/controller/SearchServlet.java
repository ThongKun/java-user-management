package controller;

import dao.RoleDAO;
import dao.UserDAO;
import entity.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.URLConstants;

/**
 *
 * @author HOME
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        String searchKey = request.getParameter("s"); //query parameter s: search
        if (searchKey == null) {
            searchKey = "";
        }
        String roleId = request.getParameter("roleId");
      
        User u = (User) request.getSession().getAttribute("userinfo");
        List<User> users = userDAO.findUsers(searchKey, roleId == null ? -1 : Integer.parseInt(roleId), u.getRole());
        request.setAttribute("USERS", users);

        RoleDAO roleDAO = new RoleDAO();
        request.setAttribute("ROLES", roleDAO.findAll());

//        System.out.println(users.toString());
        RequestDispatcher rd = request.getRequestDispatcher(URLConstants.SEARCH_PAGE);
        rd.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
