package bean;

import log.AbsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTable extends AbsModel {
    private int id;
    private String username;
    private int total_price;
    private int shipping_fee;
    private String address;
    private String phone_number;
    private List<OrderDetailTable> listDetails;
    private Timestamp createAt;
    private String payment_status;
    private int order_status;
    private String orderStatusText;


    @Override
    public String getTable() {
        return "đơn hàng";
    }

    @Override
    public String beforeData() {
        return this.toString();
    }

    @Override
    public String afterData() {
        return this.toString();
    }
    public String strOrder_status() {
        int order_status = this.getOrder_status();
        switch (order_status) {
            case 0:
                return "đã hủy";
            case 1:
                return "chờ xét duyệt";
            case 2:
                return "đang đóng gói";
            case 3:
                return "đang vận chuyển";
            case 4:
                return "đã giao";
            default:
                return "trạng thái không xác định";
        }
    }



    public void setOrderStatusText() {
        this.orderStatusText = Utility.getOrderStatus(this.order_status);

    }
}
