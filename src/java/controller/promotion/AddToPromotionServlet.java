package controller.promotion;

import dao.PromotionDAO;
import dao.UserDAO;
import entity.Promotion;
import entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import util.URLConstants;

/**
 *
 * @author ThongLV
 */
@WebServlet(name = "AddToPromotionServlet", urlPatterns = {"/add-to-promotion"})
public class AddToPromotionServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(AddToPromotionServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("id");
        if (userId == null || userId.isEmpty()) {
            response.sendRedirect(URLConstants.SEARCH_REQUEST);
        }
        PromotionDAO promotionDAO = new PromotionDAO();
        boolean check = promotionDAO.findPromotionByUserId(Integer.parseInt(userId)) != null;

        if (!check) {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findUserById(Integer.parseInt(userId));
            Promotion promotion = new Promotion();
            promotion.setUserId(user);

            promotionDAO.persist(promotion);
        }

        response.sendRedirect(URLConstants.VIEW_PROMOTION_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
