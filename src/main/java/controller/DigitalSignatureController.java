package controller;

import Service.DigitalSignature.DigitalSignatureService;
import bean.OrderDetailTable;
import bean.OrderTable;
import bean.User;
import bean.digitalsignature.Keys;
import bean.digitalsignature.OrderSign;
import dao.IOrdersDAO;
import dao.OrdersDAO;
import dao.digitalsignature.IDAO;
import dao.digitalsignature.KeyDAO;
import exceptions.DigitalSignatureException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "DigitalSignatureController", value = "/SignOrder")
public class DigitalSignatureController extends BaseServlet {
    private IOrdersDAO dao;
    IDAO<Keys> keyDAO;
    PrintWriter out;
    DigitalSignatureService digitalSignatureService;

    @Override
    public void init() throws ServletException {
        super.init();
        super.init();
        this.dao = new OrdersDAO();
        keyDAO = new KeyDAO();
        this.digitalSignatureService = new DigitalSignatureService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setRequestResponse(request, response);
        out = response.getWriter();
        try {
            String action = request.getParameter("action");
            if ("genkey".equals(action)) {
                String publicKeyPath = request.getParameter("publicKeyPath");
                String privateKeyPath = request.getParameter("privateKeyPath");
                System.out.println(publicKeyPath);
                System.out.println(privateKeyPath);
                if (publicKeyPath == null || privateKeyPath == null) {
                    throw new DigitalSignatureException("Đường dẫn Public Key hoặc Private Key không hợp lệ.");
                }
                genKey();
            }
        } catch (DigitalSignatureException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println(e.getMessage());
            out.flush();
        }
    }

    public void genKey() throws IOException {
        try {
            System.out.println("Gen Key");
            digitalSignatureService = new DigitalSignatureService();
            HttpSession session = request.getSession(true);
            User user = (User) session.getAttribute("user");

            if (user == null) {
                throw new DigitalSignatureException("Chưa đăng nhập!");
            }

            if (digitalSignatureService.isExitsKeys(user)) {
                throw new DigitalSignatureException("Người dùng đã có key!");
            }

            // Tạo cặp khóa
            digitalSignatureService.genKey();
            String privateKey = digitalSignatureService.getPrivateKey();
            String publicKey = digitalSignatureService.getPublicKey();
            System.out.println(privateKey);
            System.out.println(publicKey);
            // Lưu Public Key vào database
            digitalSignatureService.saveKeyWithUser(user, privateKey, publicKey);


            // Trả về JSON thành công
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println("{");
            out.println("\"publicKey\": \"" + publicKey + "\",");
            out.println("\"privateKey\": \"" + privateKey + "\"");
            out.println("}");
            out.flush();
        } catch (IOException | DigitalSignatureException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        }
    }






    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setRequestResponse(request, response);
        try {
            String action = request.getParameter("action");
            if (action.equals("verifyUser")) {
                verifyUser();
            } else if (action.equals("sign")) {
                processSignOrder();
            }
            else if (action.equals("savePublicKey")) {
                savePublicKey(request, response);
            }
        } catch (DigitalSignatureException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println(e.getMessage());
            out.flush();
        }
    }

    private void savePublicKey(HttpServletRequest request, HttpServletResponse response) throws IOException, DigitalSignatureException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        digitalSignatureService = new DigitalSignatureService();
        if (user == null) {
            throw new DigitalSignatureException("Chưa đăng nhập!");
        }
        if (digitalSignatureService.isExitsKeys(user)) {
            throw new DigitalSignatureException("Người dùng đã có key!");
        }
        String publicKey = request.getParameter("publicKey");
        if (publicKey == null || publicKey.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Public Key không hợp lệ.");
            return;
        }

        Keys key = new Keys(user.getId(), publicKey);
        System.out.println(key.toString());
        keyDAO.insert(key);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Lưu Public Key thành công.");
    }
    private void verifyUser() throws DigitalSignatureException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        String privateKey = request.getParameter("privateKey");
        digitalSignatureService.verifyUser(user, privateKey);
    }

    private void processSignOrder() throws IOException, DigitalSignatureException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        String privateKey = request.getParameter("privateKey");//Lấy private key
        out = response.getWriter();
        //Lấy id order
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        checkOrderStatus(orderId);
        System.out.println("orderId: " + orderId);
        System.out.println("User: " + user);
        System.out.println("Private key :" + privateKey);
        //Kiểm tra xác thực ngừoi dùng
        digitalSignatureService.verifyUser(user, privateKey);
        //Tạo dữ liệu
        OrderTable orderTable = dao.getOrderById(orderId);
        List<OrderDetailTable> listOrderDetail = dao.getOrderDetailsByOrderId(orderTable.getId());
        //Lấy thông tin đơn hàng
        OrderSign orderSign = new OrderSign(orderTable.getId(), user.getId(), orderTable.getCreateAt(), listOrderDetail);
        System.out.println("orderTable: \r\n" + orderTable);
        System.out.println("orderSign: \r\n" + orderSign);
        //Ký đơn hàng
        digitalSignatureService.signOrder(orderSign, privateKey, user);
        dao.updateOrderStatus(orderId, 1);
        //Thông báo
        response.setStatus(HttpServletResponse.SC_OK);
        out.println("Ký đơn hàng thành công");
        out.flush();



    }

    private void checkOrderStatus(int orderId) throws DigitalSignatureException {
        OrderTable order = dao.getOrderById(orderId);
        int currentStatus = order.getOrder_status();
        if (currentStatus == 0 || currentStatus == 1 ||
                currentStatus == 2 || currentStatus == 3 ||
                currentStatus == 4 || currentStatus == 5) {
            throw new DigitalSignatureException("Không thể ký");
        }
    }
}