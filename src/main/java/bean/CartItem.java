package bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CartItem {
    private Products product;
    private int quantity;

    public CartItem(Products product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public boolean addQuantity(int quantity){
        this.quantity+=quantity;
        return true;
    }
    public double getTotalPrice(){
        return this.quantity*this.product.getPrice();
    }
}
