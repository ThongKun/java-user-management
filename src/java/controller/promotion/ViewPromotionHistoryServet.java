package controller.promotion;

import dao.PromotionDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "ViewPromotionHistoryServet", urlPatterns = {"/view-promotion-history"})
public class ViewPromotionHistoryServet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ViewPromotionHistoryServet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sort = request.getParameter("sort");
        if (sort == null) {
            sort = "asc";
        } else if (!sort.toLowerCase().equals("asc") && !sort.toLowerCase().equals("desc")) {
            sort = "asc";
        }

        PromotionDAO promotionDAO = new PromotionDAO();
        request.setAttribute("promotionsHisotry", promotionDAO.findPromotions(sort));

        RequestDispatcher rd = request.getRequestDispatcher(URLConstants.VIEW_PROMOTION_HISTORY_PAGE);
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

    @Override
    public void init() throws ServletException {
        LOGGER.info("INITILIZED");
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroyed");
    }
}
