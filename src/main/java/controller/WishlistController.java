package controller;

import bean.*;
import com.google.gson.JsonObject;
import dao.WishlistDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "wishlistController", value = "/wishlistController")
public class WishlistController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("user");
        if (user == null) getServletContext().getRequestDispatcher("/login-register/login.jsp").forward(req, resp);
        else {
            String ip = req.getHeader("X-FORWARDED-FOR");
            if (ip == null) ip = req.getRemoteAddr();
            List<WishlistItem> wishlistItemList = WishlistDAO.getInstance().getWishlistByUser(user.getId());
            req.setAttribute("wishlistItemList", wishlistItemList);
            req.getRequestDispatcher("wishList.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        JsonObject responseJson = new JsonObject();
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            responseJson.addProperty("status", "failed");
            out.write(responseJson.toString());
            out.close();
            return;
        }

        String productId = req.getParameter("id");
        String action = req.getParameter("action");

        switch (action) {
            case "add":
                if (productId != null && !productId.isEmpty()) {
                    int id = Integer.parseInt(productId);
                    if (WishlistDAO.getInstance().addWishList(user.getId(), id)) {
                        responseJson.addProperty("status", "success");
                    } else {
                        WishlistDAO.getInstance().deleteWishlist(user.getId(), id);
                        responseJson.addProperty("status", "isExists");
                    }
                } else {
                    responseJson.addProperty("status", "insertFailed");
                }
                break;
            case "remove":
                if (productId != null && !productId.isEmpty()) {
                    int id = Integer.parseInt(productId);
                    if (WishlistDAO.getInstance().deleteWishlist(user.getId(), id)) {
                        responseJson.addProperty("status", "success");
                    } else {
                        responseJson.addProperty("status", "removeFailed");
                    }
                } else {
                    responseJson.addProperty("status", "removeFailed");
                }
                break;
            case "removeAll":
                if(WishlistDAO.getInstance().deleteAllWishlist(user.getId())) {
                    responseJson.addProperty("status", "success");
                }else{
                    responseJson.addProperty("status", "removeFailed");
                }
                break;
            default:
                responseJson.addProperty("status", "failed");
                break;
        }
        out.write(responseJson.toString());
        out.close();

    }
}
