package controller;

import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
/**
 *
 * @author HOME
 */
@WebServlet(name = "ChangeUserStatusServlet", urlPatterns = {"/change-user-status"})
public class ChangeUserStatusServlet extends HttpServlet {
    
    static final Logger LOGGER = Logger.getLogger(CreateUserServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            UserDAO userDAO = new UserDAO();
            userDAO.changeUserStatus(Integer.parseInt(id));
        }

        String searchKey = request.getParameter("s"); //query parameter s: search
        if (searchKey == null) {
            searchKey = "";
        }
        String roleId = request.getParameter("roleId");
        if (roleId == null) {
            roleId = "-1";
        }
        String url = request.getHeader("referer");
        response.sendRedirect(url);
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
