package controller;

import bean.Discount;
import bean.User;
import dao.digitalsignature.KeyDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jdbi.v3.core.Jdbi;
import db.JDBIConnector;
import Service.SendingEmail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet(name = "ReportServlet", urlPatterns = "/reportServlet")
public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String email = request.getParameter("email");

        if (userId == null || email == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu Tham Số");
            return;
        }

        try {
            // Tạo JWT với thời gian hết hạn 5 phút
            String secretKey = "af3d9c2be4f6a2e18c7a4fdfaf3c9b12f1a8b7d6c4e3d5a0a7e3b4f5c6e7d8f9"; // có thể export ra
            long expirationTime = 5 * 60 * 1000; // 5 phút
            String token = Jwts.builder()
                    .setSubject(userId)
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
            // Gửi email với liên kết chứa JWT
            String verificationLink = "http://localhost:8081/verifyToken?token=" + token;
            SendingEmail emailService = new SendingEmail(email);
            String emailContent = "Click vào liên kết sau để xác nhận và vô hiệu hóa key: " + verificationLink;
            emailService.sendTextEmail(emailContent, "Report Key");
            // Phản hồi JSON
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Email đã được gửi!\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Không thể gửi email. Vui lòng thử lại!\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
