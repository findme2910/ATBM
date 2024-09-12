package controller;

import Service.SendingEmail;
import bean.User;
import dao.AccountDAO;
import org.springframework.util.DigestUtils;
import utils.PasswordUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@WebServlet(urlPatterns = {"/signup"})
public class SignUpControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("login-register/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=utf-8");
        PrintWriter out = resp.getWriter();

        //============================================
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String surname = req.getParameter("surname");
        String lastname = req.getParameter("lastname");
        String phone = req.getParameter("phone");
        String pass = req.getParameter("pass");
        String rePass = req.getParameter("rePass");
        //============================================
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        }


        // Mã hóa mật khẩu sang md5
        String hashpass = DigestUtils.md5DigestAsHex(pass.getBytes());

        // Tạo mã xác nhận ngẫu nhiên bằng cách sử dụng md5 và số ngẫu nhiên để tạo ra đường link đăng kí cho mỗi người
        String myHash;
        Random random = new Random();
        random.nextInt(999999);
        myHash = DigestUtils.md5DigestAsHex(String.valueOf(random).getBytes());

        // Khởi tạo trước một đối tượng user
        User user;
        // Kiểm tra user có tồn tại trước đó hay không
        AccountDAO acc = new AccountDAO();
        user = acc.checkAccountExist(email);

        // Nếu user khác null thì đăng kí, không thì sẽ chuyền về là tài khoản đã đăng kí
        if(user == null){
            if(username.length() <= 3 || !(Character.isLetter(username.charAt(0)))){

                out.println("{\"error\":\"Tên tài khoản phải trên 3 kí tự và Kí tự đầu tiên phải là chữ cái.\"}");
            } else if(phone.length() != 10) {
                out.println("{\"error\":\"Số điện thoại phải là 10 chữ số\"}");
            } else if (!pass.equals(rePass)) {
                out.println("{\"error\":\"Mật khẩu không trùng khớp\"}");
            } else {

                String str = acc.signUp(email, hashpass, username, surname, lastname, phone, myHash,ipAddress,1,"");
                if(str.equals("success")){
                    SendingEmail se = new SendingEmail(email, myHash);
                    se.sendMail();

                    out.println("{\"success\":\"true\"}");
                } else {
                    out.println("{\"error\":\"Đã có lỗi xảy ra trong quá trình đăng ký\"}");

                }
            }
        } else {
            out.println("{\"error\":\"Email này đã được đăng kí, vui lòng sử dụng email khác\"}");
        }
        out.close();
    }
}