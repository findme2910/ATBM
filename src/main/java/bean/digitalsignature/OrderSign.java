package bean.digitalsignature;

import bean.OrderDetailTable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
public class OrderSign implements Serializable {
    private int id;
    private int userId;
    private Timestamp createAt;
    private List<OrderDetailTable> listDetails;

    public OrderSign(int id, int userId, Timestamp createAt, List<OrderDetailTable> listDetails) {
        this.id = id;
        this.userId = userId;
        this.createAt = createAt;
        this.listDetails = listDetails;
    }

    public OrderSign() {
    }

    @Override
    public String toString() {
        return "OrderSign{" +
                "id=" + id +
                ", userId=" + userId +
                ", createAt=" + createAt +
                ", listDetails=" + listDetails +
                '}';
    }
}
