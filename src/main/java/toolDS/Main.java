package toolDS;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Tạo các đối tượng View, Model và Controller
                RSADSView rsadsView = new RSADSView();
                RSADSModel rsadsModel = new RSADSModel(); // Model cho Digital Signatures
                new RSADSController(rsadsView, rsadsModel);
                // Hiển thị giao diện
                rsadsView.setVisible(true);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi: Không tìm thấy thuật toán mã hóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
