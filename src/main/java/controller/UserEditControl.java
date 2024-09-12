package controller;

import bean.User;
import dao.UserDAO;
import org.springframework.util.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(urlPatterns = "/userEdit")
public class UserEditControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if ("uEdit".equals(req.getParameter("action"))) {
            req.getRequestDispatcher("user-profile.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User userSession = (User) session.getAttribute("user");
        String surname = getParameterOrDefault(req, "surname", userSession.getSurName());
        String lastname = getParameterOrDefault(req, "lastname", userSession.getLastName());
        String username = getParameterOrDefault(req, "username", userSession.getUsername());
        String phone = getParameterOrDefault(req, "phone", userSession.getPhone());
        String password = req.getParameter("password");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        UserDAO ud = new UserDAO();
        String notifySuccess = null;
        String notifyFails = null;

        boolean dataChanged = !surname.equals(userSession.getSurName()) ||
                !lastname.equals(userSession.getLastName()) ||
                !username.equals(userSession.getUsername()) ||
                !phone.equals(userSession.getPhone());

        if (dataChanged) {
            String result = ud.userChangeInfo(surname, lastname, username, phone, userSession.getEmail());
            if ("success".equals(result)) {
                notifySuccess = "Thay thông tin người dùng thành công";
            } else {
                notifyFails = "Thay đổi không thành công";
            }
        }
        else{
                notifyFails = "Chưa có thông tin người dùng thay đổi";
        }

        if (newPassword != null && !newPassword.isEmpty() && confirmPassword != null && !confirmPassword.isEmpty()) {
            String hashedOldPassword = DigestUtils.md5DigestAsHex(password.getBytes());
            if (!hashedOldPassword.equals(userSession.getPassword())) {
                notifyFails = "Mật khẩu cũ không chính xác";
            } else if (!newPassword.equals(confirmPassword)) {
                notifyFails = "Mật khẩu mới và xác nhận không khớp";
            } else {
                String hashedNewPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
                String result = ud.userChangePassword(userSession.getEmail(), hashedNewPassword);
                if ("success".equals(result)) {
                    notifySuccess = "Thay đổi mật khẩu thành công";
                } else {
                    notifyFails = "Thay đổi không thành công";
                }
            }
        }

        updateUserSession(session, userSession, surname, lastname, username, phone, notifySuccess, notifyFails);
        resp.sendRedirect("userEdit?action=uEdit");
    }

    private String getParameterOrDefault(HttpServletRequest req, String paramName, String defaultValue) {
        String value = req.getParameter(paramName);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }

    private void updateUserSession(HttpSession session, User userSession, String surname, String lastname, String username, String phone, String notifySuccess, String notifyFails) {
        userSession.setSurName(surname);
        userSession.setLastName(lastname);
        userSession.setUsername(username);
        userSession.setPhone(phone);
        session.setAttribute("user", userSession);
        if (notifySuccess != null) {
            session.setAttribute("notifySuccess", notifySuccess);
            session.removeAttribute("notifyFails");
        } else if(notifyFails!=null){
            session.setAttribute("notifyFails", notifyFails);
            session.removeAttribute("notifySuccess");
        }
    }
}
