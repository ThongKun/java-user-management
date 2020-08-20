package controller.promotion;

import dao.PromotionDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "RemoveUserPromotionServlet", urlPatterns = {"/remove-user-promotion"})
public class RemoveUserPromotionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String promotionId = request.getParameter("id");
        if (promotionId == null || promotionId.isEmpty()) {
            response.sendRedirect(URLConstants.VIEW_PROMOTION_REQUEST);
        }

        PromotionDAO promotionDAO = new PromotionDAO();
        promotionDAO.removePromotion(Integer.parseInt(promotionId));

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

}
