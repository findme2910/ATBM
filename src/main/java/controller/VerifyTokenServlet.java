package controller;
import dao.digitalsignature.KeyDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "VerifyTokenServlet", urlPatterns = "/verifyToken")
public class VerifyTokenServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        if (token == null || token.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu hoặc không hợp lệ Token");
            return;
        }

        try {
            String secretKey = "af3d9c2be4f6a2e18c7a4fdfaf3c9b12f1a8b7d6c4e3d5a0a7e3b4f5c6e7d8f9";
            // Xác minh token
            String userId = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            // Nếu hợp lệ, gọi expireActivePublicKeys
            KeyDAO keyDAO = new KeyDAO();
            int rowsUpdated = keyDAO.expireActivePublicKeys(Integer.parseInt(userId));
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<html><body style='text-align:center; font-family:Arial;'>");
            if (rowsUpdated > 0) {
                response.getWriter().write("<h1>Xác minh Thành công! khóa công khai đã được vô hiệu hóa.</h1>");
                response.getWriter().write("<a href='http://localhost:8081/login' style='font-size:18px; color:blue;'>Đăng nhập ngay</a>");
            } else {
                response.getWriter().write("<h1>Không tìm thấy khóa công khai nào để vô hiệu hóa.</h1>");
            }
            response.getWriter().write("</body></html>");

        } catch (ExpiredJwtException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<html><body style='text-align:center; font-family:Arial;'>");
            response.getWriter().write("<h1>Token đã hết hạn. Vui lòng yêu cầu lại.</h1>");
            response.getWriter().write("<a href='http://localhost:8081/login' style='font-size:18px; color:blue;'>Đăng nhập ngay</a>");
            response.getWriter().write("</body></html>");
        } catch (SignatureException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<html><body style='text-align:center; font-family:Arial;'>");
            response.getWriter().write("<h1>Token không hợp lệ</h1>");
            response.getWriter().write("<a href='http://localhost:8081/login' style='font-size:18px; color:blue;'>Đăng nhập ngay</a>");
            response.getWriter().write("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request");
        }
    }
}

