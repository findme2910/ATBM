package controller;

import Service.SendingEmail;
import dao.IOrdersDAO;
import dao.OrdersDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CancelOrderServlet", value = "/cancelOrder")
public class CancelOrderServlet extends HttpServlet {
    private OrdersDAO orderDao;

    @Override
    public void init() throws ServletException {
        super.init();
        orderDao = new OrdersDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain; charset=UTF-8");

        int orderId = Integer.parseInt(req.getParameter("orderId"));
        String userEmail = req.getParameter("userEmail");

        try {
            // Cập nhật trạng thái đơn hàng
            orderDao.updateOrderStatus(orderId, 0);
            // Gửi email thông báo
            SendingEmail emailService = new SendingEmail(userEmail);
            String messageContent = "Đơn hàng mã " + orderId + " của bạn đã bị hủy do đã bị thay đổi dữ liệu. Nếu có thắc mắc, vui lòng liên hệ với chúng tôi.";
            String subject = "Thông báo hủy đơn hàng";
            String emailResult = emailService.sendTextEmail(messageContent, subject);

            if ("success".equals(emailResult)) {
                resp.getWriter().write("success");
            } else {
                resp.getWriter().write("Lỗi khi gửi email.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("Lỗi khi hủy đơn hàng.");
        }
    }
}
