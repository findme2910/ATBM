package dao.digitalsignature;

import bean.digitalsignature.SignedOrder;
import db.JDBIConnector;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class SignedOrderDAO implements IDAO<SignedOrder> {

    private final Jdbi jdbi = JDBIConnector.getJdbi();
    private static SignedOrderDAO instance;

    public static SignedOrderDAO getInstance() {
        if (instance == null) {
            instance = new SignedOrderDAO();
        }
        return instance;
    }
    @Override
    public List<SignedOrder> getAll() {
        String sql = "SELECT * FROM `signed-orders` ";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .mapToBean(SignedOrder.class)
                    .list();
        }
    }

    @Override
    public SignedOrder get(int id) {
        return null;
    }
    @Override
    public boolean insert(SignedOrder signedOrder) {
        try (Handle handle = jdbi.open()) {
            int result = handle.createUpdate("INSERT INTO `signed-orders` (orderId ,publicKeyId , signOrder, orderStatus) VALUES ( ? ,?, ?, 1)")
                    .bind(0, signedOrder.getOrderId())
                    .bind(1, signedOrder.getPublicKeyId())
                    .bind(2, signedOrder.getSignOrder())
                    .execute();
            return result > 0;
        }
    }


    @Override
    public boolean update(SignedOrder signedOrder) {
        String sql = "UPDATE `signed-orders` SET orderId = ?, publicKeyId = ?, signOrder = ?, orderStatus = ? WHERE id = ?";
        try (Handle handle = jdbi.open()) {
            int result = handle.createUpdate(sql)
                    .bind(0, signedOrder.getOrderId())
                    .bind(1, signedOrder.getPublicKeyId())
                    .bind(2, signedOrder.getSignOrder())
                    .bind(3, signedOrder.getOrderStatus())
                    .bind(4, signedOrder.getId())
                    .execute();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean remove(int id) {
        String sql = "UPDATE `signed-orders` SET orderStatus = 0 WHERE id = ?";
        try (Handle handle = jdbi.open()) {
            int result = handle.createUpdate(sql)
                    .bind(0, id)
                    .execute();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public SignedOrder getById(int orderId) {
        String sql = "SELECT * FROM `signed-orders` WHERE orderId = ?";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind(0, orderId)
                    .mapToBean(SignedOrder.class)
                    .one();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SignedOrder> getByPublicKeyId(int publicKeyId) {
        String sql = "SELECT * FROM `signed-orders` WHERE publicKeyId = ?";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind(0, publicKeyId)
                    .mapToBean(SignedOrder.class)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Lấy ra danh sách trạng thái sig dựa vào orderID
    public int getSignatureStatus(int orderId) {
        String sql = "SELECT orderStatus FROM `signed-orders` WHERE orderId = ?";
        try (Handle handle = JDBIConnector.getJdbi().open()) {
            return handle.createQuery(sql)
                    .bind(0, orderId)
                    .mapTo(int.class)
                    .findOne()
                    .orElse(2); // Mặc định trả về 2 (Chưa được ký) nếu không có dữ liệu
        }
    }

    public static void main(String[] args) {
        SignedOrderDAO signedOrderDAO = SignedOrderDAO.getInstance();

//        SignedOrder newOrder = new SignedOrder(5, 1, "SignatureData", 0);
//        boolean inserted = dao.insert(newOrder);
//        System.out.println("Insert success: " + inserted);

        int order = signedOrderDAO.getSignatureStatus(1);
        System.out.println(order);
//
//        if (order.isPresent()) {
//            SignedOrder updatedOrder = order.get();
//            updatedOrder.setOrderStatus(1);
//            boolean updated = signedOrderDAO.update(updatedOrder);
//            System.out.println("Update success: " + updated);
//        }
//        boolean removed = dao.remove(1);
//        System.out.println("Remove success (status set to 0): " + removed);
        System.out.println(signedOrderDAO.getAll());
    }
}
