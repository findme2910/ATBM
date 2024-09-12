package bean;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Discount implements Serializable {
    Integer id;
    String code;
    String discountName;
    String description;
    Double salePercent;
    Integer quantity;
    Timestamp startDate;
    Timestamp expirationDate;

    public Discount(String code) {
        this.code = code;
    }
}
