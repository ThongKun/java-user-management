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
import org.apache.log4j.Logger;
import util.Constants;
import util.URLConstants;

/**
 *
 * @author HOME
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    
    static final Logger LOGGER = Logger.getLogger(SearchServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        String searchKey = request.getParameter("s"); //query parameter s: search
        if (searchKey == null) {
            searchKey = "";
        }
        String roleId = request.getParameter("roleId");
        request.setAttribute("roleId", roleId);
        request.setAttribute("s", searchKey);
        
        // Find/Search User
        User u = (User) request.getSession().getAttribute("userinfo");
        
        // Get User Number Based on Search Condition + User Role + Role Selected
        int usersNumber = userDAO.getUsersNumbers(searchKey, (roleId == null || roleId.isEmpty()) ? -1 : Integer.parseInt(roleId), u.getRole());
//        request.setAttribute("USERS", users);
        
        // Find Max Page 
        int maxPages = (int) Math.ceil((float)usersNumber/7);
        request.setAttribute("MAX_PAGES", maxPages);
        int selected_page = request.getParameter("page") == null || request.getParameter("page").isEmpty() 
                ? 1 : Integer.parseInt(request.getParameter("page"));
        request.setAttribute("selected_page", selected_page);
        
        List<User> users2 = userDAO.findUsers(searchKey, (roleId == null || roleId.isEmpty()) ? -1 : Integer.parseInt(roleId), u.getRole(), Constants.MAX_RESULT, selected_page);
        request.setAttribute("USERS", users2);
        
        RoleDAO roleDAO = new RoleDAO();
        request.setAttribute("ROLES", roleDAO.findAll());

        RequestDispatcher rd = request.getRequestDispatcher(URLConstants.SEARCH_PAGE);
        rd.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    public void init() throws ServletException {
        LOGGER.info("INITILIZED");
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroyed");
    }
    
    
}
