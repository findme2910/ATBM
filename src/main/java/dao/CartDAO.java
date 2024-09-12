package dao;

import bean.Cart;
import bean.CartItemDB;
import db.JDBIConnector;


public class CartDAO implements ICartDAO {
    private static ICartDAO instance;

    public static ICartDAO getInstance() {
        if (instance == null) instance = new CartDAO();
        return instance;
    }

    @Override
    public CartItemDB getItemDB(int id) {
        String sql = "SELECT * FROM cart_items WHERE id =?";
        try {
            return JDBIConnector.get().withHandle(handle ->
                    handle.createQuery(sql).bind(1, id).mapToBean(CartItemDB.class).first());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Cart getCart(int id) {
        String sql = "SELECT * FROM carts WHERE id =?";
        try {
            return JDBIConnector.get().withHandle(handle ->
                    handle.createQuery(sql).bind(1, id).mapToBean(Cart.class).first());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public double getTotalPrice(int cartId) {
        String sql = "SELECT quantity*price AS total FROM cart_items WHERE id_cart = ?";
        try {
            return JDBIConnector.get().withHandle(handle ->
                    handle.createQuery(sql).bind(1, cartId).mapTo(Double.class).one());
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public CartItemDB add(int pid, int cid, int quantity, int price) {
        String sql = "INSERT INTO cart_items (id_product, id_cart, quantity, price) VALUES (?,?,?,?)";
        try {
            int id = JDBIConnector.get().withHandle(handle ->
                    handle.createUpdate(sql).bind(1, pid).bind(2, cid).bind(3, quantity).bind(4, price)
                            .executeAndReturnGeneratedKeys("id").mapTo(Integer.class).one());
            return CartDAO.getInstance().getItemDB(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean remove(int id) {
        String sql = "DELETE FROM cart_items WHERE id = ?";
        try {
            int exe = JDBIConnector.get().withHandle(handle ->
                    handle.createUpdate(sql).bind(1, id).execute());
            return exe == 1;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Cart createCart(int userId) {
        String sql = "INSERT INTO carts (id_user) VALUES (?)";
        try {
            int id = JDBIConnector.get().withHandle(handle ->
                    handle.createUpdate(sql).bind(1, userId)
                            .executeAndReturnGeneratedKeys("id").mapTo(Integer.class).one());
            return CartDAO.getInstance().getCart(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Cart checkCartExists(int userId) {
        String sql = "SELECT * FROM carts WHERE id_user =?";
        try {
            return JDBIConnector.get().withHandle(handle ->
                    handle.createQuery(sql).bind(1, userId).mapToBean(Cart.class).first());
        } catch (Exception e) {
            return null;
        }
    }
    

    @Override
    public boolean removeAll(int cartId, int count) {
        String sql = "DELETE FROM cart_items WHERE id_cart =?";
        try {
            int exe = JDBIConnector.get().withHandle(handle ->
                    handle.createUpdate(sql).bind(1, cartId).execute());
            return exe == count;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int count(int userId) {
        return 0;
    }
}
