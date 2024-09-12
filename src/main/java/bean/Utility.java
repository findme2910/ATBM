package bean;

public class Utility {
    public static String getOrderStatus(int status) {
        switch (status) {
            case 0: return "Đã Hủy";
            case 1: return "Chờ Xét Duyệt";
            case 2: return "Đang Đóng Gói";
            case 3: return "Đang Vận Chuyển";
            case 4: return "Đã Giao";
            default: return "Không Xác Định";
        }
    }
}
