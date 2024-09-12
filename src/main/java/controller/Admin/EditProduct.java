package controller.Admin;

import CKeditor.HtmlUtils;
import Service.ProductsService;
import bean.Products;
import dao.LogDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "EditProduct", value = "/editPro")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)

public class EditProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String proID = req.getParameter("proID");
        int proIDint = 0;
        if (proID != null && !proID.isEmpty()) {
            proIDint = Integer.parseInt(proID);
        }
        Products a = ProductsService.getInstance().getProductById(proIDint);
        HttpSession session = req.getSession();
        session.setAttribute("product", a);
        req.getRequestDispatcher("admin_page/editProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        Products oldPro = (Products) session.getAttribute("product");
        int proIDOld= oldPro.getId();
        String productName = req.getParameter("productName");// tên sp
        String idCategory = req.getParameter("productCate");// idCategory
        String inventory_quantity = req.getParameter("inventory"); //   inventory quantity
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        }

        int inventoryInt = 0;
        if (inventory_quantity != null && !inventory_quantity.isEmpty()) {
             inventoryInt = Integer.parseInt(inventory_quantity);
        }

        int idCategoryInt = 0;
        if (idCategory != null && !idCategory.isEmpty()) {
            idCategoryInt = Integer.parseInt(idCategory);
        }
        String productNum = req.getParameter("productNum");//số lượng
        int num = 0;
        if (productNum != null && !productNum.isEmpty()) {
            num = Integer.parseInt(productNum);
        }

        Part filePart = req.getPart("imageFile"); // path ảnh
// Xử lý ảnh
        String fileName = filePart.getSubmittedFileName();
        String root = getServletContext().getRealPath("/assets/img/product/"); // Thay đổi đường dẫn lưu trữ ảnh tạm thời
        File check = new File(root);
        if (!check.exists()) {
            check.mkdirs();
        }

        String imagePath;
        if (filePart != null && fileName != null && !fileName.isEmpty()) {
            filePart.write(root + fileName);
            imagePath = "assets/img/product/" + fileName; // Đường dẫn lưu trữ ảnh trong cơ sở dữ liệu
        } else {
            // Người dùng không chọn ảnh, sử dụng đường dẫn ảnh cũ
            imagePath = oldPro.getImage();
        }

        // Giá sản phẩm
        String price = req.getParameter("price");
        int priceInt = 0;
        try {
            String numericString = price.replace(".", "");
            BigDecimal numericValue = new BigDecimal(numericString);
            priceInt = numericValue.intValue();
            System.out.println(priceInt);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String specifications = HtmlUtils.removeHtmlTags(req.getParameter("specifications"));
        String proDesc = HtmlUtils.removeHtmlTags(req.getParameter("proDesc"));

        if (proDesc == null || proDesc.isEmpty()) {
            proDesc = oldPro.getDes();
        }
        //status:Trạng thái sản phẩm
        String active = req.getParameter("active");
        int activeInt = 0;
        try {
            if ("Mở bán".equals(active)) {
                activeInt = 1;
            } else if ("Hủy bán".equals(active)) {
                activeInt = 0;
            }
        } catch (ArithmeticException e) {
            // Xử lý ngoại lệ nếu cần thiết
        }
        // public void editProduct(String name,String picture,int price,int idCategory,int quantity,int status,String specifications,String proDesc,int id){
        //        ProductsDao.editProduct(name, picture, price, idCategory, quantity, status, specifications, proDesc, id);
        //    }
        ProductsService.getInstance().editProduct(productName,imagePath,priceInt,idCategoryInt,activeInt,proDesc,inventoryInt,proIDOld);
        Products newProducts = new Products();
        newProducts.setId(proIDOld);
        newProducts.setProduct_name(productName);
        newProducts.setImage(imagePath);
        newProducts.setPrice(priceInt);
        newProducts.setId_category(idCategoryInt);
        newProducts.setStatus(activeInt);
        newProducts.setDes(proDesc);
        newProducts.setInventory_quantity(inventoryInt);
        LogDao.getInstance().updateModel(oldPro,newProducts,ipAddress,1,"");
        resp.sendRedirect("./admin_dashboard?page=./maProduct");
    }
}
