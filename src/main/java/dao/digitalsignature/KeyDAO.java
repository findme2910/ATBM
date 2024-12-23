package dao.digitalsignature;

import bean.digitalsignature.Keys;
import db.JDBIConnector;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class KeyDAO implements IDAO<Keys> {
    private static final boolean ACTIVE = true;
    //    private static final boolean DISABLE = false;
    private final Jdbi jdbi = JDBIConnector.getJdbi();
    private static KeyDAO instance;

    public static KeyDAO getInstance() {
        if (instance == null) instance = new KeyDAO();
        return instance;
    }

    @Override
    public boolean insert(Keys key) {
        String sql = "INSERT INTO `keys` (userId, publicKey, `status`) VALUES (?, ?, 1);";
        try (Handle handle = jdbi.open()) {
            int result = handle.createUpdate(sql)
                    .bind(0, key.getUserId())
                    .bind(1, key.getPublicKey())
                    .execute();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean disable(int id) {
        Keys k = get(id);
        k.setStatus(!ACTIVE);
        System.out.println(k);
        return update(k);
    }

    public boolean active(int id) {
        Keys k = get(id);
        k.setStatus(ACTIVE);
        return update(k);
    }

    @Override
    public boolean update(Keys key) {
        System.out.println(key);
        System.out.println(key.isStatus());
        String sql = "UPDATE `keys` SET publicKey = ?, status = ? WHERE id = ?;";
        try (Handle handle = jdbi.open()) {
            int result = handle.createUpdate(sql)
                    .bind(0, key.getPublicKey())
                    .bind(1, key.isStatus())
                    .bind(2, key.getId())
                    .execute();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Keys> getAll() {
        String sql = "SELECT * FROM `keys` ";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .mapToBean(Keys.class)
                    .list();
        }
    }

    @Override
    public Keys get(int userId) {
        String sql = "SELECT * FROM `keys` WHERE userId = ?;";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind(0, userId)
                    .mapToBean(Keys.class)
                    .findOne()
                    .orElse(null);
        }
    }
    //kiểm tra user có publickey hay chưa
    public boolean hasActivePublicKey(int userId) {
        String sql = "SELECT status FROM `keys` WHERE userId = ?;";
        try (Handle handle = jdbi.open()) {
            Integer status = handle.createQuery(sql)
                    .bind(0, userId)
                    .mapTo(Integer.class)
                    .findOne()
                    .orElse(null); // Nếu không có dòng nào, trả về null
            // Kiểm tra kết quả
            return status != null && status == 1; // true nếu status = 1
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Lỗi trong quá trình truy vấn
        }
    }

    public static void main(String[] args) {
        KeyDAO keyDAO = KeyDAO.getInstance();
        int testUserId = 12; // Thay bằng userId bạn muốn kiểm tra
        boolean hasPublicKey = keyDAO.hasActivePublicKey(testUserId);
        if (hasPublicKey) {
            System.out.println("User " + testUserId + " đã có publicKey.");
        } else {
            System.out.println("User " + testUserId + " chưa có publicKey.");
        }
////      Tao key
//        Keys key = new Keys();
//        key.setUserId(12);
//        key.setPublicKey("PublicKey22");
//        key.setUserSignature("ChuKyUser22");
//
////        System.out.println(keyDAO.insert(key));
//
//        // Update the key
//        key.setUserId(12);
//        System.out.println(keyDAO.update(key));





//        System.out.println(keyDAO.getKey(1));
//
//        System.out.println(keyDAO.disable(1));
    }
}
