package controller.Admin;

import Service.CategoryService;
import Service.ProductsService;
import Service.UserService;
import bean.Category;
import dao.CategoryDAO;
import dao.LogDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ToggleCategoryStatusServlet", urlPatterns = {"/disableCategory", "/cancelDisableCategory"})
public class ToggleCategoryStatusServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productID = request.getParameter("categoryID");
        String action = request.getRequestURI().substring(request.getContextPath().length());
        String actionDescription = action.equals("/disableCategory") ? "vô hiệu hóa doanh mục" : "kích hoạt doanh mục";
        boolean disable = action.equals("/disableCategory");
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        if (productID != null) {
            try {
                int id = Integer.parseInt(productID);
                // Thực hiện thao tác thay đổi trạng thái category trong cơ sở dữ liệu
                boolean result =  CategoryService.getInstance().toggleCategoryStatus(id,disable);
                Category aa= CategoryDAO.getCategoryById(id);

                if (result) {
                    LogDao.getInstance().printLog(actionDescription,aa,ipAddress,1,"");
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Success");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Failed to update user status.");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid user ID.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("User ID is required.");
        }
    }
}
