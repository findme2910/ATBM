package bean;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Util {
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
    public static Timestamp formatTimestamp(Timestamp timestamp) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);
        return Timestamp.valueOf(formattedDateTime);
    }
    public static String formatCurrency(double price) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = numberFormat.format(price);

        // Loại bỏ dấu "đ" và gạch từ chuỗi
        formattedPrice = formattedPrice.replaceAll("[đ₫,]", "");

        return formattedPrice.trim(); // Loại bỏ khoảng trắng thừa
    }
    // Phương thức kiểm tra xem mật khẩu có đủ mạnh không?
    public static boolean isStrongPassword(String password) {
        try {
            // Độ dài ít nhất 8 ký tự, chứa số, chữ hoa, chữ thường và ký tự đặc biệt
            String strongPasswordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
            return password.matches(strongPasswordPattern);
        } catch (Exception e) {
            return false;
        }
    }
    public static String formatTimestampToString(Timestamp timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm");
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return localDateTime.format(formatter);
    }

}
