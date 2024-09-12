package controller.Admin;

import Service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import bean.Category;
import com.google.gson.Gson;
import dao.CategoryDAO;
import dao.LogDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "InsertCategory", value = "/insertCate")
public class InsertCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        List<Category> listCategories = CategoryService.getInstance().getList();
        int index = listCategories.size()+1;
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        }

        String nameCate = req.getParameter("nameCate");
        Category a = new Category(index,nameCate);
        boolean success = CategoryService.getInstance().insertCategory(nameCate);

        Map<String, String> result = new HashMap<>();
        if (success) {
            LogDao.getInstance().insertModel(a,ipAddress,1,"");
            result.put("status", "success");
            result.put("message", "Danh mục mới đã được thêm thành công!");
        } else {
            result.put("status", "error");
            result.put("message", "Lỗi xảy ra khi thêm danh mục mới!");
        }
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(result);
        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
        out.flush();
    }
}
