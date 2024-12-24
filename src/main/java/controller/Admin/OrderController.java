package controller.Admin;


import Service.OrdersService;
import bean.OrderDetailTable;
import bean.OrderTable;
import bean.Orders;
import bean.digitalsignature.SignedOrder;
import com.google.gson.Gson;

import dao.IOrdersDAO;
import dao.LogDao;
import dao.OrdersDAO;
import dao.digitalsignature.SignedOrderDAO;
import utils.DigitalSignature;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static utils.Hash.hash;

@WebServlet(name = "OrderManagement", value = "/orderManagement")
public class OrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IOrdersDAO orderDao;
    private SignedOrderDAO signedOrderDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        orderDao = new OrdersDAO();
        signedOrderDAO = new SignedOrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String action = req.getParameter("action");
        if (action == null || action.isEmpty()) {
            listOrders(req, resp);
        } else if ("view".equals(action)) {
            viewOrderDetails(req, resp);
        }
    }

    private void listOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<OrderTable> listOrder = orderDao.getOrderforAdmin();
        for (OrderTable order : listOrder) {
            List<OrderDetailTable> listOrderDetail = orderDao.getOrderDetailsByOrderId(order.getId());
            order.setListDetails(listOrderDetail);

            // Kiểm tra chữ ký và cập nhật trạng thái
            boolean isSignatureValid = checkOrderSignature(order.getId());
            order.setSignatureStatus(isSignatureValid ? 1 : 0);
        }

        req.setAttribute("listOrder", listOrder);
        req.getRequestDispatcher("admin_page/quanlyDonHang.jsp").forward(req, resp);
    }

    private void viewOrderDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        List<OrderDetailTable> listOrderDetail = orderDao.getOrderDetailsByOrderId(orderId);
        // Trả về JSON của chi tiết đơn hàng
        Gson gson = new Gson();
        String json = gson.toJson(listOrderDetail);
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
        if ("updatePayment".equals(action)) {
            updatePaymentStatus(req, resp);
        } else if ("updateOrder".equals(action)) {
            updateOrderStatus(req, resp);
        } else {
            doGet(req, resp);
        }
    }

    private void updatePaymentStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        }
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        String paymentStatus = req.getParameter("paymentStatus");
        OrderTable oldOrder = orderDao.getOrderById(orderId);
        orderDao.updatePaymentStatus(orderId, paymentStatus);
        OrderTable newOrder = orderDao.getOrderById(orderId);
        LogDao.getInstance().toggleStatus("Thay đổi trạng thái thanh toàn đơn hàng mã "+orderId,oldOrder.getPayment_status(),newOrder.getPayment_status(),ipAddress,1,"");
        resp.getWriter().write("Success");
    }


    private void updateOrderStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        int newOrderStatus = Integer.parseInt(req.getParameter("orderStatus"));
        OrderTable order = orderDao.getOrderById(orderId);
        int currentOrderStatus = order.getOrder_status();
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        }

        String errorMessage = null;

        if (currentOrderStatus == 0 || currentOrderStatus == 4) {
            errorMessage = "Không thể cập nhật khi đã hủy hoặc đã giao";
        } else if (currentOrderStatus == 1 && (newOrderStatus != 2 && newOrderStatus != 3)) {
            errorMessage = "Lỗi ràng buộc, không thể cập nhật";
        } else if (currentOrderStatus == 2 && newOrderStatus == 1) {
            errorMessage = "Lỗi ràng buộc, không thể cập nhật";
        } else if (currentOrderStatus == 3 && (newOrderStatus == 1 || newOrderStatus == 2)) {
            errorMessage = "Lỗi ràng buộc, không thể cập nhật";
        }

        if (errorMessage != null) {
            resp.getWriter().write(errorMessage);
        } else {
            orderDao.updateOrderStatus(orderId, newOrderStatus);
            OrderTable newOrder = orderDao.getOrderById(orderId);
            LogDao.getInstance().toggleStatus("Thay đổi trạng thái giao của đơn mã"+orderId,order.strOrder_status(),newOrder.strOrder_status(),ipAddress,1,"");
            resp.getWriter().write("Success");
        }
    }

    private boolean checkOrderSignature(int orderId) {
        try {
            Orders order = orderDao.find(orderId);
            if (order == null) return false;
            //hash lại đơn hàng
            OrdersService ordersService = new OrdersService();
            String orderHash = ordersService.proccessOrderHash(order);
            System.out.println(orderHash);
            //lấy ra id của keys tương ứng với đơn hàng
            SignedOrder signedOrder = signedOrderDAO.getById(orderId);
            System.out.println(signedOrder);
            if (signedOrder == null) return false;

            String signedOrderData = signedOrder.getSignOrder(); // lấy ra chữ ký
            System.out.println(signedOrderData);
            int publicKeyId = signedOrder.getPublicKeyId(); // lấy ra id của publickey
            System.out.println(publicKeyId);
            //lấy ra publickey từ bảng keys
            String publicKey = signedOrderDAO.getPublicKeyById(publicKeyId);
            if (publicKey == null) return false;
            System.out.println(publicKey);

            // kiểm tra chữ ký
            DigitalSignature digitalSignature = new DigitalSignature();
            digitalSignature.loadPublicKey(publicKey); // Load publicKey từ bảng 'keys'
            boolean isValid = digitalSignature.verifySignature(orderHash, signedOrderData);
            // Cập nhật trạng thái trong bảng sign-order
            int signatureStatus = isValid ? 1 : 0;
            signedOrderDAO.updateSignatureStatus(orderId, signatureStatus);
            return isValid;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}

