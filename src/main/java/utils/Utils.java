package utils;

import java.text.NumberFormat;
import java.util.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.Normalizer;

public class Utils {
    public static String formatCurrency(double price) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = numberFormat.format(price);

        // Loại bỏ dấu "đ" và gạch từ chuỗi
        formattedPrice = formattedPrice.replaceAll("[đ₫,]", "");

        return formattedPrice.trim(); // Loại bỏ khoảng trắng thừa
    }

    public static String formatTimestamp(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(timestamp.getTime()));
    }
    public static String formatTimestampWithoutTime(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(timestamp.getTime()));
    }
    public static String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String dateFormatNoTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    public static String generateSlug(String input) {
        String convertedString =
                Normalizer
                        .normalize(input, Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "").replaceAll(" ", "-");
        return convertedString;
    }
    public static String revertDate(String date){
        String day = date.substring(0,date.indexOf("-"));
        String month = date.substring(date.indexOf("-")+1,date.lastIndexOf("-"));
        String year = date.substring(date.lastIndexOf("-")+1,date.length());
        return year+"-"+month+"-"+day;
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

    // Kiểm tra tính hợp lệ của email
    public static boolean isValidEmail(String email) {
        try {
            String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
            return email.matches(emailRegex);
        } catch (Exception e) {
            return false;
        }
    }

    // Phương thức tạo mật khẩu ngẫu nhiên
    public static String generateRandomPassword() {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialCharacters = "!@#$%^&*()_+-=[]{};':\"\\|,.<>/?";
        String combinedChars = upperCaseLetters + lowerCaseLetters + numbers + specialCharacters;

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        // Đảm bảo mật khẩu chứa ít nhất một chữ hoa, một chữ thường, một số và một ký tự đặc biệt
        password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));
        password.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

        // Điền số ký tự còn lại để đạt độ dài mong muốn, ở đây tôi chọn 12 ký tự cho mật khẩu
        while (password.length() < 12) {
            password.append(combinedChars.charAt(random.nextInt(combinedChars.length())));
        }

        // Trộn các ký tự để tăng độ an toàn
        char[] pwdArray = password.toString().toCharArray();
        for (int i = pwdArray.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = pwdArray[i];
            pwdArray[i] = pwdArray[j];
            pwdArray[j] = temp;
        }

        return new String(pwdArray);
    }
}
