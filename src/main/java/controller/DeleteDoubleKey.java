package controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import db.JDBIConnector;
import org.jdbi.v3.core.Jdbi;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/delete-double-key")
public class DeleteDoubleKey extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Đọc dữ liệu userId từ request body
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        // Parse JSON body để lấy userId
        JsonObject jsonBody = JsonParser.parseString(sb.toString()).getAsJsonObject();
        int userId = jsonBody.get("userId").getAsInt();

        // Xóa publicKey trong bảng keys
        boolean success = false;
        try {
            Jdbi jdbi = JDBIConnector.get();
            int rowsDeleted = jdbi.withHandle(handle ->
                    handle.createUpdate("DELETE FROM keys WHERE userId = :userId")
                            .bind("userId", userId)
                            .execute()
            );
            success = rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Trả về phản hồi JSON
        response.setContentType("application/json");
        response.getWriter().write("{\"success\": " + success + "}");
    }
}
