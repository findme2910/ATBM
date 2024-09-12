package controller.Admin;

import CKeditor.HtmlUtils;
import Service.CategoryService;
import Service.ProductsService;
import bean.Category;
import bo.CategoryBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.math.BigDecimal;

@WebServlet(name = "InsertProduct", value = "/insertPro")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)
public class InsertProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        CategoryBO bo = new CategoryBO();
        List<Category> allCate = bo.getListCategory();
        req.setAttribute("allCate", allCate);
        req.getRequestDispatcher("admin_page/addProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        }

        String productName = req.getParameter("productName");
        String categoryId = req.getParameter("productCate");
        int intCategoryId = 0;
        if (categoryId != null && !categoryId.isEmpty()) {
            intCategoryId = Integer.parseInt(categoryId);
        }

        String quantity = req.getParameter("productNum");
        int num = 0;
        if (quantity != null && !quantity.isEmpty()) {
            num = Integer.parseInt(quantity);
        }

        Part filePart = req.getPart("imageFile");
        String fileName = filePart.getSubmittedFileName();
        String root = getServletContext().getRealPath("/assets/img/product/");
        File check = new File(root);
        if (!check.exists()) {
            check.mkdirs();
        }
        String imagePath = "";
        if (filePart != null && fileName != null && !fileName.isEmpty()) {
            filePart.write(root + fileName);
            imagePath = "assets/img/product/" + fileName;
        }

        String price = req.getParameter("price");
        int priceInt = 0;
        try {
            String numericString = price.replace(".", "");
            BigDecimal numericValue = new BigDecimal(numericString);
            priceInt = numericValue.intValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String active = req.getParameter("active");
        int status = 0;
        try {
            if ("Mở bán".equals(active)) {
                status = 1;
            } else if ("Hủy bán".equals(active)) {
                status = 0;
            }
        } catch (ArithmeticException e) {
            // Xử lý ngoại lệ nếu cần thiết
        }
        String proDesc = HtmlUtils.removeHtmlTags(req.getParameter("proDesc"));
        ProductsService.getInstance().insertProduct(productName, imagePath, priceInt, intCategoryId, status, num, proDesc,ipAddress);
        resp.sendRedirect("./admin_dashboard?page=./maProduct");
    }
}
