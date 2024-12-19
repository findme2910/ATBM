package utils;

import exceptions.DigitalSignatureException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    private static final String HASH_ALGO = "MD5";

    public static String hash(String input) throws DigitalSignatureException {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGO);

            // Băm dữ liệu đầu vào (input) thành mảng byte
            byte[] hashedBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Chuyển mảng byte thành chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new DigitalSignatureException("Thuật toán không hợp lệ: " + HASH_ALGO);
        } catch (Exception e) {
            throw new DigitalSignatureException(e.getMessage());
        }
    }
}
