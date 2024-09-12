package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailTable {
    private int id;
    private int id_product;
    private String product_name;
    private String img;
    private int quantity;
    private int priceDetails;
    private Timestamp create_at;
    private boolean review_status;
}
