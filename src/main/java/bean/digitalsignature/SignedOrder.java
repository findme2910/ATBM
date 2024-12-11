package bean.digitalsignature;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class SignedOrder implements Serializable {
    private int id;
    private int orderId;
    private int publicKeyId;
    private String signOrder;
    private int orderStatus;

    public SignedOrder() {
    }

    public SignedOrder(int orderId, int publicKeyId, String signOrder, int orderStatus) {
        this.orderId = orderId;
        this.publicKeyId = publicKeyId;
        this.signOrder = signOrder;
        this.orderStatus = orderStatus;
    }

    public SignedOrder(int id, int orderId, int publicKeyId, String signOrder, int orderStatus) {
        this.id = id;
        this.orderId = orderId;
        this.publicKeyId = publicKeyId;
        this.signOrder = signOrder;
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "SignedOrder{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", publicKeyId=" + publicKeyId +
                ", signOrder='" + signOrder + '\'' +
                ", orderStatus=" + orderStatus +
                '}' + "\r\n";
    }


}
