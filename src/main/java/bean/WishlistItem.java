package bean;

import lombok.*;


import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistItem implements Serializable {
    User user;
    Products products;
}
