package utils;

import org.springframework.util.DigestUtils;

public class PasswordUtils {

    // Hàm để mã hóa mật khẩu với MD5 và chuyển đổi thành chuỗi hex
    public static String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    // Hàm để kiểm tra mật khẩu
    public static boolean verifyPassword(String password, String hashedPassword) {
        // Mã hóa lại mật khẩu nhập vào
        String hashedInputPassword = DigestUtils.md5DigestAsHex(password.getBytes());

        // So sánh mật khẩu đã mã hóa với mật khẩu đã lưu
        return hashedPassword.equals(hashedInputPassword);
    }

    public static void main(String[] args) {
        // Mã hóa mật khẩu
        String password = "myPassword123";
        String encryptedPassword = encryptPassword(password);
        System.out.println("Encrypted password: " + encryptedPassword);

        // Kiểm tra mật khẩu
        boolean isMatch = verifyPassword("myPassword123", encryptedPassword);
        System.out.println("Password match: " + isMatch);

        // Kiểm tra với mật khẩu sai
        boolean isMatchWrong = verifyPassword("wrongPassword", encryptedPassword);
        System.out.println("Password match with wrong password: " + isMatchWrong);
    }
}