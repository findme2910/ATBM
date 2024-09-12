package dao;

import bean.Discount;
import db.JDBIConnector;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class DiscountDAO implements IDiscountDAO {
    private static IDiscountDAO instance;

    public static IDiscountDAO getInstance() {
        if (instance == null) {
            instance = new DiscountDAO();
        }
        return instance;
    }

    /**
     * TESTED
     * Retrieves all the coupons from the database.
     */
    @Override
    public List<Discount> getAllCoupons() {
        String sql = "SELECT * FROM discounts";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql).mapToBean(Discount.class).collect(Collectors.toList())
        );
    }

    /**
     * TESTED
     * Retrieves a coupon from the database by its id.
     */
    @Override
    public List<Discount> getCouponById(Integer id) {
        String sql = "SELECT * FROM discounts WHERE id =?";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql).bind(0, id).mapToBean(Discount.class).collect(Collectors.toList())
        );
    }

    /**
     * TESTED
     * Retrieves a coupon from the database by its name.
     */
    @Override
    public List<Discount> getCouponByName(String name) {
        String sql = "SELECT * FROM discounts WHERE discountName =?";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql).bind(0, name).mapToBean(Discount.class).collect(Collectors.toList())
        );
    }

    @Override
    public List<Discount> getCouponByCode(String code) {
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT * FROM `discounts` WHERE code = ?").bind(0, code).mapToBean(Discount.class).stream().collect(Collectors.toList())
        );
    }



    public static void main(String[] args) {
        System.out.println(DiscountDAO.getInstance().getCouponByCode("30THANG4"));
    }

    /**
     * TESTED
     * Adds a new coupon to the database.
     */
    @Override
    public boolean addCoupon(String name, String code, String des, Double percent, Integer quantity, Timestamp startTimestamp, Timestamp TimestampEnd) {
        String sql = "INSERT INTO `discounts`(`code`, `discountName`, `description`, `sale_percent`, `quantity`, `startTimestamp`, `expirationTimestamp`) VALUES(?,?,?,?,?,?,?)";
        int exe = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, code)
                        .bind(1, name)
                        .bind(2, des)
                        .bind(3, percent)
                        .bind(4, quantity)
                        .bind(5, startTimestamp)
                        .bind(6, TimestampEnd)
                        .execute());
        return exe == 1;
    }

    /**
     * TESTED
     * Deletes a coupon from the database by its id.
     */
    @Override
    public boolean delCoupon(Integer id) {
        String sql = "DELETE FROM discounts WHERE id=?";
        int exe = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, id)
                        .execute());
        return exe == 1;
    }

    @Override
    public boolean delCouponByCode(String code) {
        String sql = "DELETE FROM discounts WHERE code=?";
        int exe = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, code)
                        .execute());
        return exe == 1;
    }

    /**
     * TESTED
     * Edits a coupon in the database by its id.
     */
    @Override
    public boolean editCoupon(Integer id, String code, String name, String des, Double percent, Integer quantity, Timestamp startTimestamp, Timestamp TimestampEnd) {
        String sql = "UPDATE `discounts` SET `code`=?, `discountName`=?, `description`=?, `sale_percent`=?, `quantity`=?, `startTimestamp`=?, `expirationTimestamp`=? WHERE id =?";
        int exe = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, code)
                        .bind(1, name)
                        .bind(2, des)
                        .bind(3, percent)
                        .bind(4, quantity)
                        .bind(5, startTimestamp)
                        .bind(6, TimestampEnd)
                        .bind(7, id)
                        .execute());
        return exe == 1;
    }

    @Override
    public boolean editCouponByCode(String code, String name, String des, Double percent, Integer quantity, Timestamp startTimestamp, Timestamp TimestampEnd) {
        String sql = "UPDATE `discounts` SET `discountName`=?, `description`=?, `sale_percent`=?, `quantity`=?, `startTimestamp`=?, `expirationTimestamp`=? WHERE code=?";
        int exe = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, name)
                        .bind(1, des)
                        .bind(2, percent)
                        .bind(3, quantity)
                        .bind(4, startTimestamp)
                        .bind(5, TimestampEnd)
                        .bind(6, code)
                        .execute());
        return exe == 1;
    }
}
