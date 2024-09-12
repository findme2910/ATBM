package controller.Admin;

import Service.ProductsService;
import Service.UserService;
import bean.Products;
import bean.User;
import dao.admin.DashBoardDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "dashboard", value = "/dashboard")
public class Dashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int numUser = DashBoardDAO.getNumUser(); // Số lượng người dùng
        int numPro = DashBoardDAO.getNumPro(); // Số lượng sản phẩm
        int numOrder  = DashBoardDAO.getNumOrder(); // tổng đơn hàng
        int revenueTotal = DashBoardDAO.getRevenueTotal();// Tổng doanh thu
        int lowStockCount = DashBoardDAO.getLowStockProductCount();// số lượng sản phẩm sắp hết hàng
        int unSoldProduct = DashBoardDAO.getUnSoldProduct().size(); // số lượng sản phẩm không bán được trong 3 tháng gần nhất
        List<Integer> revenueData = DashBoardDAO.getRevenueData(); // Dữ liệu doanh thu theo tháng
        List<Integer> orderData = DashBoardDAO.getOrderData(); // Dữ liệu đơn hàng theo tháng
        List<Products> listUnSold = DashBoardDAO.getUnSoldProduct(); //dữ liệu những sản phẩm không bán được trong 3 tháng gần nhất
        List<Products> listSoldOut = DashBoardDAO.getSoldOutProduct();// dữ liệu sản phẩm bán chạy
        // Đặt các thuộc tính lên request để truyền tới JSP
        req.setAttribute("numUser", numUser);
        req.setAttribute("numPro", numPro);
        req.setAttribute("revenueData", revenueData);
        req.setAttribute("orderData", orderData);
        req.setAttribute("numOrder", numOrder);
        req.setAttribute("revenueTotal", revenueTotal);
        req.setAttribute("lowStockCount", lowStockCount);
        req.setAttribute("unSoldProduct", unSoldProduct);
        req.setAttribute("listUnSold", listUnSold);
        req.setAttribute("listSoldOut", listSoldOut);
        // Chuyển tiếp tới JSP
        req.getRequestDispatcher("admin_page/dashboard.jsp").forward(req, resp);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            this.doGet(req, resp);
    }
}
