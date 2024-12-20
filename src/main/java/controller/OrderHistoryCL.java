package controller;

import Service.DigitalSignature.DigitalSignatureService;
import bean.OrderDetailTable;
import bean.OrderTable;
import bean.User;
import com.google.gson.Gson;
import dao.IOrdersDAO;
import dao.OrdersDAO;
import debug.LoggingConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "OrderHistoryCL", value = "/OrderHistoryCL")
public class OrderHistoryCL extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private IOrdersDAO dao;
    PrintWriter out;
    DigitalSignatureService digitalSignatureService;
    private static final Logger LOGGER = Logger.getLogger(OrderHistoryCL.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        this.dao = new OrdersDAO();
        this.digitalSignatureService = new DigitalSignatureService();
        LoggingConfig.setup(); // Thiết lập logging
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setRequestResponse(request, response);
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            forward("/login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            listOrders(user);
        } else {
            switch (action) {
                case "view":
                    viewOrderDetails();
                    break;
                case "filter":
                    filterOrdersByStatus(user);
                    break;
                case "review":
                    showReviewOrders(user);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        }
    }


    private void showReviewOrders(User user) throws ServletException, IOException {
        List<OrderTable> orderDetails = dao.getOrdersByUserAndStatus(user, 4);
        List<OrderDetailTable> orderDetailTableList = new ArrayList<>();
        for (OrderTable order : orderDetails) {
            List<OrderDetailTable> temp = dao.getOrderDetailsByOrderIdAndReviewStatus(order.getId());
            if (temp != null) {
                orderDetailTableList.addAll(temp);
            }
        }
        request.setAttribute("orderDetails", orderDetailTableList);
        forward("review.jsp");
    }

    private void listOrders(User user) throws ServletException, IOException {
        List<OrderTable> listOrder = dao.getOrdersByUser(user);
        for (OrderTable order : listOrder) {
            order.setListDetails(dao.getOrderDetailsByOrderId(order.getId()));
        }
        request.setAttribute("listOrder", listOrder);
        forward("history.jsp");
    }

    private void viewOrderDetails() throws IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        List<OrderDetailTable> listOrderDetail = dao.getOrderDetailsByOrderId(orderId);
        String json = new Gson().toJson(listOrderDetail);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    private void filterOrdersByStatus(User user) throws IOException {
        int status = Integer.parseInt(request.getParameter("status"));
        List<OrderTable> listOrder = (status == 5) ? dao.getOrdersByUser(user) : dao.getOrdersByUserAndStatus(user, status);
        listOrder.forEach(OrderTable::setOrderStatusText);
        String json = new Gson().toJson(listOrder);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setRequestResponse(request, response);
        String action = request.getParameter("action");
        if ("cancelOrder".equals(action)) {
            cancelOrder();
//        } else if ("sign".equals(action)) {
//
//            try {
//                processSignOrder();
//            } catch (DigitalSignatureException e) {
//                e.printStackTrace();
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                out.println(e.getMessage());
//                out.flush();
//            }
        }
//        doGet(request, response);
    }

//    private void checkOrderStatus(int orderId) throws DigitalSignatureException {
//
//        OrderTable order = dao.getOrderById(orderId);
//        int currentStatus = order.getOrder_status();
//        if (currentStatus == 0 || currentStatus == 1 ||
//                currentStatus == 2 || currentStatus == 3 ||
//                currentStatus == 4 || currentStatus == 5) {
//
//
//            throw new DigitalSignatureException("Không thể ký");
//        }
//    }
//
//    private void processSignOrder() throws IOException, DigitalSignatureException {
//        HttpSession session = request.getSession(true);
//        User user = (User) session.getAttribute("user");
//        String privateKey = request.getParameter("privateKey");//Lấy private key
//        out = response.getWriter();
//        //Lấy id order
//        int orderId = Integer.parseInt(request.getParameter("orderId"));
//        checkOrderStatus(orderId);
//        System.out.println("orderId: " + orderId);
//        System.out.println("User: " + user);
//        System.out.println("Private key :" + privateKey);
//        //Kiểm tra xác thực ngừoi dùng
//        digitalSignatureService.verifyUser(user, privateKey);
//
//
//        //Tạo dữ liệu
//        OrderTable orderTable = dao.getOrderById(orderId);
//        List<OrderDetailTable> listOrderDetail = dao.getOrderDetailsByOrderId(orderTable.getId());
//        //Lấy thông tin đơn hàng
//        OrderSign orderSign = new OrderSign(orderTable.getId(), user.getId(), orderTable.getCreateAt(), listOrderDetail);
//        System.out.println("orderTable: \r\n" + orderTable);
//        System.out.println("orderSign: \r\n" + orderSign);
//        //Ký đơn hàng
//        digitalSignatureService.signOrder(orderSign, privateKey, user);
//        dao.updateOrderStatus(orderId, 6);
//        //Thông báo
//        response.setStatus(HttpServletResponse.SC_OK);
//        out.println("Ký đơn hàng thành công");
//        out.flush();
//
//        }


    private void cancelOrder() throws IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderTable order = dao.getOrderById(orderId);
        int currentStatus = order.getOrder_status();
        String errorMessage = null;

        switch (currentStatus) {
            case 0:
            errorMessage = "Đơn hàng này đã bị hủy trước đó";
                break;
            case 3:
            errorMessage = "Đơn hàng đang trong quá trình vận chuyển, bạn không thể hủy";
                break;
            case 4:
                errorMessage = "Đã giao thành công, không thể hủy đơn hàng";
                break;
        }
        dao.updateOrderStatus(orderId, 0);
        response.getWriter().write((errorMessage != null) ? errorMessage : "Success");
    }
}
