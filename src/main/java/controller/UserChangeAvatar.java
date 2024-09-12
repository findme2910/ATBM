package controller;

import bean.User;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import config.CloudinaryConfig;
import db.JDBIConnector;
import org.jdbi.v3.core.Jdbi;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/upload")
@MultipartConfig
public class UserChangeAvatar extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UserChangeAvatar.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("profilePic");
        if (filePart != null && filePart.getSize() > 0) {
            LOGGER.info("Received file: " + filePart.getSubmittedFileName());

            // Nén ảnh và chuyển đổi sang byte array
            try (InputStream fileContent = filePart.getInputStream();
                 ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

                BufferedImage image = ImageIO.read(fileContent);
                if (image == null) {
                    throw new IOException("Cannot read the uploaded image. ImageIO.read returned null.");
                }

                ImageIO.write(image, "jpg", buffer);  // Nén ảnh sang định dạng JPG
                byte[] bytes = buffer.toByteArray();

                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");

                if (user != null) {
                    try {
                        Cloudinary cloudinary = CloudinaryConfig.getCloudinary();
                        Map uploadResult = cloudinary.uploader().upload(bytes, ObjectUtils.emptyMap());
                        String imageUrl = (String) uploadResult.get("secure_url");
                        LOGGER.info("Uploaded to Cloudinary: " + imageUrl);

                        // Lưu URL vào cơ sở dữ liệu
                        saveFilePathToDatabase(imageUrl, user.getId());
                        user.setPicture(imageUrl);
                        session.setAttribute("user", user);
                        LOGGER.info("Saved URL to database and updated user session.");

                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "File upload failed during Cloudinary upload.", e);
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "File upload failed.");
                        return;
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "File processing failed.", e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "File processing failed.");
                return;
            }
            response.sendRedirect("userEdit?action=uEdit");
        } else {
            LOGGER.warning("File not provided or empty.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File not provided or empty.");
        }
    }


    private void saveFilePathToDatabase(String filePath, int idUser) {
        Jdbi jdbi = JDBIConnector.getJdbi();
        String sql = "UPDATE users SET picture = ? WHERE id = ?";
        jdbi.withHandle(handle -> handle.createUpdate(sql)
                .bind(0, filePath)
                .bind(1, idUser)
                .execute());
        LOGGER.info("Saved file path to database for user ID: " + idUser);
    }

}
