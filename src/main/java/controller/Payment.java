package controller;

import Service.IOrdersService;
import Service.OrdersService;
import bean.*;
import org.hibernate.annotations.common.util.impl.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "payment", value = "/payment")
public class Payment extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IOrdersService orderService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.orderService = new OrdersService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("user");
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
        String address = (String) session.getAttribute("address");
        String txt_inv_customer = (String) session.getAttribute("txt_inv_customer");
        String txt_billing_mobile = (String) session.getAttribute("txt_billing_mobile");
        List<CartItem> products = shoppingCart.getCartItemList();
        Float result = null;
        Double doubleResult = (Double) session.getAttribute("result");
        if (doubleResult != null) {
            result = doubleResult.floatValue();
        }
        Orders order = new Orders(user.getId(), (float) result,
                0, address, txt_billing_mobile,"Đã Thanh Toán");
        order.setLp(products);
        System.out.println(order);
        this.orderService.insertOrderDetail(order);
        double total = shoppingCart.getTotalPrice();
        session.setAttribute("total", total);
        session.removeAttribute("cart");
        resp.sendRedirect("/HomePageController");

    }

}
