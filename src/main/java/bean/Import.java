package bean;
import log.AbsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Import extends AbsModel {
    private int id;
    private int id_product;
    private int quantity;

    private String product_name;
    private double price;

    private Date date_import;
    private String status; //thêm status trong import trong database
    public Import(int i, int i1, int i2) {
        this.id_product = i;
        this.quantity = i1;
        this.price = i2;
    }

    public Import(int id, String status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public String getTable() {
        return "Nhập hàng";
    }

    @Override
    public String beforeData() {
        return toString();
    }

    @Override
    public String afterData() {
        return toString();
    }
}
