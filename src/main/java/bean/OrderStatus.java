package bean;

import java.io.Serializable;

public class OrderStatus implements Serializable {
    private int id;
    private int id_order;
    private String status;

    public OrderStatus(int id, int id_order, String status) {
        this.id = id;
        this.id_order = id_order;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getId_order() {
        return id_order;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "id=" + id +
                ", id_order=" + id_order +
                ", status='" + status + '\'' +
                '}';
    }
}
