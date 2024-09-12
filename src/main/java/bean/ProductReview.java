package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReview {
    int id;
    int id_product;
    int id_user;
    String user_name; //chứa user_name của người dùng
    int rating; //rating từ 1-5
    String content;
    Integer parent_id;
    String picture;
    Timestamp create_at;
}
