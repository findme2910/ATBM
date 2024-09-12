package controller;

import dao.AccountDAO;
import dao.UserDAO;
import org.springframework.util.DigestUtils;
import utils.PasswordUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/ForgotPassword")
public class ForgotPasswordControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("passF");
        session.setAttribute("email", req.getParameter("key1"));
        session.setAttribute("hash", req.getParameter("key2"));
        String action = req.getParameter("action");
        if(action != null && action.equals("createPass")){
            req.getRequestDispatcher("login-register/form_create_nPassword.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("email");
        String hash = (String) session.getAttribute("hash");
        String newPassword = req.getParameter("newPassword");
        String rePassword = req.getParameter("rePassword");
        String hashPassword = PasswordUtils.encryptPassword(newPassword);
        AccountDAO dao = new AccountDAO();
        if((newPassword == null || newPassword.isEmpty()) || (rePassword == null || rePassword.isEmpty())){
            String passF = "Bạn nhập còn thiếu";
            session.setAttribute("passF", passF);
            req.getRequestDispatcher("login-register/form_create_nPassword.jsp").forward(req, resp);
        }
        if((newPassword != null && !newPassword.isEmpty()) || (rePassword != null && !rePassword.isEmpty())){
            if(newPassword.equals(rePassword)){
                String str = dao.forgetPassword(email, hashPassword);
                if(str.equalsIgnoreCase("Success")){
                    session.removeAttribute("passF");
                    session.setAttribute("action", "Đã cập nhật thành công mật khẩu");
                    String active = dao.activeAccount(email, hash);
                    AccountDAO.getInstance().updateLoginFail(email, 0);
                    resp.sendRedirect("login");
                }else {
                    String passF = "Mời bạn nhập lại";
                    session.setAttribute("passF", passF);
                    req.getRequestDispatcher("login-register/form_create_nPassword.jsp").forward(req, resp);
                }
            }else {
                String passF = "Mật khẩu không giống nhau";
                session.setAttribute("passF", passF);
                req.getRequestDispatcher("login-register/form_create_nPassword.jsp").forward(req, resp);
            }
        }
    }
}