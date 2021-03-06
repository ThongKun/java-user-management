package controller.promotion;

import dao.PromotionDAO;
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
@WebServlet(name = "RemoveUserPromotionServlet", urlPatterns = {"/remove-user-promotion"})
public class RemoveUserPromotionServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(RemoveUserPromotionServlet.class);

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

    @Override
    public void init() throws ServletException {
        LOGGER.info("INITILIZED");
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroyed");
    }
}
