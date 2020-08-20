package controller.promotion;

import dao.PromotionDAO;
import java.io.IOException;
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
@WebServlet(name = "UpdateUserPromotionServlet", urlPatterns = {"/update-user-promotion"})
public class UpdateUserPromotionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String promotionId = request.getParameter("id");
        String score = request.getParameter("score");

        if (promotionId == null || promotionId.isEmpty()
                || score == null || score.isEmpty()
                || Integer.parseInt(score) < 1 || Integer.parseInt(score) > 10) {
            response.sendRedirect(URLConstants.VIEW_PROMOTION_REQUEST);
        }

        PromotionDAO promotionDAO = new PromotionDAO();
        promotionDAO.updatePromotion(Integer.parseInt(promotionId), Integer.parseInt(score));

        response.sendRedirect(URLConstants.VIEW_PROMOTION_REQUEST);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
