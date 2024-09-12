package controller.Admin;

import Service.CategoryService;
import bean.Category;
import com.google.gson.Gson;
import dao.CategoryDAO;
import dao.LogDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "EditCategory", value = "/editCategory")
public class EditCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String categoryName = req.getParameter("categoryName");
        String categoryId = req.getParameter("cateID");
        int intCategoryId = 0;
        Map<String, String> response = new HashMap<>();
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        }

        try {
            if (categoryId != null && !categoryId.isEmpty()) {
                intCategoryId = Integer.parseInt(categoryId);
            }
            Category oldCategory = CategoryDAO.getCategoryById(intCategoryId);
            boolean updateSuccess = CategoryService.getInstance().updateCategory(categoryName, intCategoryId);
            Category newCategory = new Category(intCategoryId,categoryName);
            if (updateSuccess) {
                LogDao.getInstance().updateModel(oldCategory,newCategory,ipAddress,1,"");
                response.put("status", "success");
                response.put("message", "Chỉnh sửa danh mục thành công!");
            } else {
                response.put("status", "error");
                response.put("message", "Không thể chỉnh sửa danh mục.");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Lỗi: " + e.getMessage());
        }

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(response);
        resp.getWriter().write(jsonResponse);
    }
}
