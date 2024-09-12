package bean;

import lombok.Data;
import org.jdbi.v3.core.mapper.Nested;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class CartItemDB implements Serializable {
    private int id;
    @Nested("p")
    private Products p;
    @Nested("cart")
    private Cart cart;
    private int quantity;
    private int price;
    private Timestamp createdAt;

    @Nested("p")
    public Products getProduct() {
        return p;
    }

    @Nested("p")
    public void setProduct(Products p) {
        this.p = p;
    }

    @Nested("cart")
    public Cart getCart() {
        return cart;
    }

    @Nested("cart")
    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
