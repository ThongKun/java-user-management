package controller;

import dao.AuthenticationDAO;
import entity.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import util.URLConstants;
import validation.AuthenticationValidator;

/**
 *
 * @author HOME
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Going doGet");
        RequestDispatcher rd = request.getRequestDispatcher(URLConstants.LOGIN_PAGE);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Going doPost");
        boolean flag = false; //if login successfull, redirect reponse 

        String url = URLConstants.LOGIN_PAGE;

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        AuthenticationDAO blo = new AuthenticationDAO();

        if (AuthenticationValidator.check(email, password) == false) {
            request.setAttribute("errorLogin", "email or password can not be empty");
        } else {
            User result = blo.checkLogin(email, password);
            if (result != null) {
                url = request.getContextPath();
                HttpSession session = request.getSession();
                session.setAttribute("userinfo", result);
                flag = true;
            } else {
                request.setAttribute("errorLogin", "user not found or password wrong");
            }
        }

        if (flag) {
            response.sendRedirect(url);
        } else {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init() throws ServletException {
        LOGGER.info("INITILIZED");
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroyed");
    }
}
