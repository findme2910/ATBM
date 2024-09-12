package controller.Admin;

import Service.UserService;
import bean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserManagement", value = "/maUser")
public class UserManagement extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        int roleInt = parseIntOrDefault(req.getParameter("roleID"), 0, req, "Invalid roleID. Please enter a valid number.");
        int uidInt = parseIntOrDefault(req.getParameter("uid"), 1, req, "Invalid uid. Please enter a valid number.");

        req.setAttribute("roleInt2", roleInt);
        req.setAttribute("tag", uidInt);

        List<User> dsUser = UserService.getInstance().listOfRoleWithSearch(roleInt);
        req.setAttribute("dsUser", dsUser);

        // Chuyển hướng đến trang quanlyuser.jsp (dù có lỗi hay không)
        req.getRequestDispatcher("admin_page/quanlyuser.jsp").forward(req, resp);
    }

    private int parseIntOrDefault(String value, int defaultValue, HttpServletRequest req, String errorMessage) {
        if (value != null && !value.isEmpty()) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                req.setAttribute("error", errorMessage);
            }
        }
        return defaultValue;
    }
}
