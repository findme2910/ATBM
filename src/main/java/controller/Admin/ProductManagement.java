package controller.Admin;

import Service.ProductsService;
import bean.Product;
import bean.Products;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductManagement", value = "/maProduct")
public class ProductManagement extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Products> allProducts= ProductsService.getInstance().getAllProducts();
        req.setAttribute("allProducts",allProducts);
        req.getRequestDispatcher("admin_page/quanlyProduct.jsp").forward(req,resp);
    }
}
