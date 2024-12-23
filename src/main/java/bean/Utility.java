package bean;

public class Utility {
    //order status bên người dùng
    public static String getOrderStatus(int status) {
        switch (status) {
            case 0: return "Đã Hủy";
            case 1: return "Chờ Xét Duyệt";
            case 2: return "Đang Đóng Gói";
            case 3: return "Đang Vận Chuyển";
            case 4: return "Đã Giao";
            case 6:
                return "Chưa ký";
            default: return "Không Xác Định";
        }
    }
    //trạng thái keyorderstatus
    public static String getKeyOrderStatus(int status){
        switch (status) {
            case -1: return "Đã bị thay đổi";
            case 0: return "Chưa Verify";
            case 1: return "Đã Verify";
            case 2: return "Chưa được ký";
            default: return "Không Xác Định";
        }
    }
}
