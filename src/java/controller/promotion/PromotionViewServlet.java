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
@WebServlet(name = "PromotionServlet", urlPatterns = {"/view-promotion"})
public class PromotionViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PromotionDAO promotionDAO = new PromotionDAO();
        System.out.println("promotionDAO.findPromotions(): " + promotionDAO.findPromotions());
        request.setAttribute("promotions", promotionDAO.findPromotions());
        RequestDispatcher rd = request.getRequestDispatcher(URLConstants.PROMOTION_VIEW_PAGE);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
