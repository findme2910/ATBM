package bean;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<CartItem> cartItemList;

    public ShoppingCart(){
        this.cartItemList = new ArrayList<>();
    }

    public ShoppingCart(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public void add(CartItem cartItem) {
        for(CartItem i: cartItemList){
            if (cartItem.getProduct().getId() == i.getProduct().getId()) {
                i.setQuantity(i.getQuantity() + 1);
                return;
            }
        }
        cartItemList.add(cartItem);
    }

    public void remove(int id) {
        for(CartItem i : cartItemList){
            if (id == i.getProduct().getId()) {
                cartItemList.remove(i);
                return;
            }
        }
    }

    public void update(Products p, int quantity) {
        for(CartItem i : cartItemList){
            if (p.getId() == i.getProduct().getId()) {
                i.setQuantity(quantity);
                return;
            }
        }
        cartItemList.add(new CartItem(p, quantity));
    }

    public int getSize(){
        int re=0;
        for(CartItem c:cartItemList){
            re +=c.getQuantity();
        }
        return re;
    }
    public List<CartItem> getCartItemList(){
        return this.cartItemList;
    }

//    public double getTotalPrice() {
//        double re=0;
//        for(CartItem c: cartItemList){
//            re+=c.getTotalPrice();
//        }
//        return re;
//    }

    public String getHello() {
        return null;
    }

    public double getTotalPrice() {
        double re=0;
        for(CartItem c: cartItemList){
            re+=c.getTotalPrice();
        }
        return re;
    }

}