package controller.Admin;

import Service.UserService;
import dao.LogDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ToggleUserStatusServlet", urlPatterns = {"/disableUser", "/cancelDisableUser"})
public class ToggleUserStatusServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userID");
        String action = request.getRequestURI().substring(request.getContextPath().length());
        String actionDescription = action.equals("/disableUser") ? "vô hiệu hóa tài khoản" : "kích hoạt tài khoản";
        boolean disable = action.equals("/disableUser");
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        if (userID != null) {
            try {
                int id = Integer.parseInt(userID);
                // Thực hiện thao tác thay đổi trạng thái người dùng trong cơ sở dữ liệu
                boolean result = UserService.getInstance().toggleUserStatus(id, disable);

                if (result) {
                    LogDao.getInstance().printLog(actionDescription,UserService.getInstance().selectUser(id),ipAddress,1,"");
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Success");
                } else {
                    LogDao.getInstance().printLog("Thay đổi trạng thái thất bại",UserService.getInstance().selectUser(id),ipAddress,1,"");
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Failed to update user status.");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid user ID.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("User ID is required.");
        }
    }
}
