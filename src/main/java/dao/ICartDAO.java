package dao;

import bean.Cart;
import bean.CartItemDB;

public interface ICartDAO {

    CartItemDB getItemDB(int id);

    Cart getCart(int id);

    double getTotalPrice(int cartId);

    CartItemDB add(int pid, int cid, int quantity, int price);

    Cart createCart(int userId);

    Cart checkCartExists(int userId);

    boolean remove(int itemId);

    boolean removeAll(int userId, int count);

    int count(int userId);
}
