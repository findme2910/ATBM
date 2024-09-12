package dao;

import bean.WishList;
import bean.WishlistItem;
import db.JDBIConnector;
import mapper.WishlistItemRowMapper;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class WishlistDAO extends AbstractDAO<WishList> implements IWishlistDAO {
    private static IWishlistDAO instance;

    public static IWishlistDAO getInstance() {
        if (instance == null) {
            instance = new WishlistDAO();
        }
        return instance;
    }

    @Override
    public List<WishlistItem> getWishlistByUser(Integer userId) {
        String sql = "SELECT u.id AS user_id, p.id AS id_product,p.product_name AS product_name,p.price AS product_price,p.image AS product_image\n" +
                "FROM wishlist w\n" +
                "JOIN users u ON w.id_user = u.id\n" +
                "JOIN products p ON w.id_product = p.id\n" +
                "WHERE w.id_user = ?";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, userId)
                        .map(new WishlistItemRowMapper())
                        .list());
    }

    @Override
    public boolean addWishList(Integer userId, Integer productId) {
        String sql ="  INSERT INTO wishlist VALUES(?,?)";
        try {
            JDBIConnector.getJdbi().withHandle(handle ->
                    handle.createUpdate(sql)
                            .bind(0, userId)
                            .bind(1, productId)
                            .execute());
            return true;
        } catch (Exception e) {
            if (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
                System.out.println("Duplicate entry found: " + e.getMessage());
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public boolean deleteWishlist(Integer userId, Integer productId) {
        String sql ="DELETE FROM wishlist WHERE id_user =? AND id_product =?";
        try {
            int rowsAffected = JDBIConnector.getJdbi().withHandle(handle ->
                    handle.createUpdate(sql)
                            .bind(0, userId)
                            .bind(1, productId)
                            .execute());
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAllWishlist(Integer userId) {
        String sql ="DELETE FROM wishlist WHERE id_user =? ";
        try {
            int rowsAffected = JDBIConnector.getJdbi().withHandle(handle ->
                    handle.createUpdate(sql)
                            .bind(0, userId)
                            .execute());
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(WishlistDAO.getInstance().deleteAllWishlist(2));
        List<WishlistItem>list = WishlistDAO.getInstance().getWishlistByUser(1);
        for(WishlistItem item : list){
            System.out.println(item);
        }
    }
}
