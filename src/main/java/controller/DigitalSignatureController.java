package controller;

import Service.DigitalSignature.DigitalSignatureService;
import bean.User;
import bean.digitalsignature.Keys;
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
import java.security.NoSuchAlgorithmException;

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
        try {
            this.digitalSignatureService = new DigitalSignatureService();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
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
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }






    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setRequestResponse(request, response);
        try {
            String action = request.getParameter("action");
//            if (action.equals("verifyUser")) {
//                verifyUser();
//            } else
            if (action.equals("sign")) {
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void savePublicKey(HttpServletRequest request, HttpServletResponse response) throws IOException, DigitalSignatureException, NoSuchAlgorithmException {
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
//    private void verifyUser() throws DigitalSignatureException {
//        HttpSession session = request.getSession(true);
//        User user = (User) session.getAttribute("user");
//        String privateKey = request.getParameter("privateKey");
//        digitalSignatureService.verifyUser(user, privateKey);
//    }

    private void processSignOrder() throws Exception {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        out = response.getWriter();
        //Lấy id của order
        int orderId = (int) session.getAttribute("orderId");
        //Lấy mã hash của order
        String orderHashed = (String) session.getAttribute("orderHashed");
        //Lấy chữ ký của order
        String signedOrder = request.getParameter("signedOrder");
//verify user
        if (digitalSignatureService.verifyUser(user, signedOrder, orderHashed)) {
            //insert order
            digitalSignatureService.insertSignOrder(orderId, signedOrder, user.getId());
//Về trang chủ
            response.sendRedirect("HomePageController");
        } else {
            throw new DigitalSignatureException("Chữ kí không hợp lệ. Hãy kí lại!");
        }



    }


}