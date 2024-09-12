package controller;

import Service.IOrdersService;
import bean.*;
import com.google.gson.Gson;
import dao.IOrdersDAO;
import dao.OrdersDAO;
import dao.ProductReviewDao;
import debug.LoggingConfig;

import javax.persistence.criteria.Order;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "OrderHistoryCL", value = "/OrderHistoryCL")
public class OrderHistoryCL extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IOrdersDAO dao;
    private static final Logger LOGGER = Logger.getLogger(OrderHistoryCL.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        this.dao = new OrdersDAO();
        LoggingConfig.setup(); // Thiết lập logging
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if(user == null){
            request.getRequestDispatcher("/login").forward(request, response);
        }else{
            String action = request.getParameter("action");
            if (action == null || action.isEmpty()) {
                listOrders(request, response, user);
            } else if ("view".equals(action)) {
                viewOrderDetails(request, response);
            } else if ("filter".equals(action)) {
                filterOrdersByStatus(request, response, user);
            }
            else if("review".equals(action)) {
                List<OrderTable> orderDetails = dao.getOrdersByUserAndStatus(user,4);
                List<OrderDetailTable> orderDetailTableList = new ArrayList<>();
                List<OrderDetailTable> temp;
                for (OrderTable order : orderDetails) {
                    temp = dao.getOrderDetailsByOrderIdAndReviewStatus(order.getId());
                    if(temp!=null){
                        orderDetailTableList.addAll(temp);
                    }
                }
                request.setAttribute("orderDetails", orderDetailTableList);
                request.getRequestDispatcher("review.jsp").forward(request, response);
            }
        }

    }

    private void listOrders(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        List<OrderTable> listOrder = dao.getOrdersByUser(user);
        for (OrderTable order : listOrder) {
            List<OrderDetailTable> listOrderDetail = dao.getOrderDetailsByOrderId(order.getId());
            order.setListDetails(listOrderDetail);
        }
        req.setAttribute("listOrder", listOrder);
        req.getRequestDispatcher("history.jsp").forward(req, resp);
    }

    private void viewOrderDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId")); // lấy ra orderID khi click vào
        List<OrderDetailTable> listOrderDetail = dao.getOrderDetailsByOrderId(orderId);
        // Trả về JSON của chi tiết đơn hàng
        Gson gson = new Gson();
        String json = gson.toJson(listOrderDetail);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    private void filterOrdersByStatus(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        int status = Integer.parseInt(req.getParameter("status"));
        List<OrderTable> listOrder;
        if(status == 5) {
            listOrder = dao.getOrdersByUser(user);
        } else {
            listOrder = dao.getOrdersByUserAndStatus(user, status);
        }
        // Chuyển đổi trạng thái đơn hàng thành văn bản
        for (OrderTable order : listOrder) {
            order.setOrderStatusText();
        }
        Gson gson = new Gson();
        String json = gson.toJson(listOrder);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String action = req.getParameter("action");
        if ("cancelOrder".equals(action)) {
            cancelOrder(req, resp);
        } else {
            doGet(req, resp);
        }
    }

    private void cancelOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        // Logic để kiểm tra và cập nhật trạng thái đơn hàng là 1 hoặc 2
        OrderTable order = dao.getOrderById(orderId);
        int currentOrderStatus = order.getOrder_status();
        String errorMessage = null;
        if (currentOrderStatus == 0) {
            errorMessage = "Đơn hàng này đã bị hủy trước đó";
        } else if (currentOrderStatus == 4) {
            errorMessage = "Đã giao thành công, không thể hủy đơn hàng";
        }else if(currentOrderStatus==3){
            errorMessage = "Đơn hàng đang trong quá trình vận chuyển, bạn không thể hủy";
        }

        if (errorMessage != null) {
            resp.getWriter().write(errorMessage);
        } else {
            dao.updateOrderStatus(orderId, 0);
            resp.getWriter().write("Success");
        }
    }
}
