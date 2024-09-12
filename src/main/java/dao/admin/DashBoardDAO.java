package dao.admin;

import bean.Category;
import bean.Product;
import bean.Products;
import db.JDBIConnector;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DashBoardDAO {
    /**
     * Lấy ra số lượng người dùng hiện tại
     */
    public static int getNumUser() {
        String sql = "SELECT COUNT(id) FROM users";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .mapTo(Integer.class)
                        .one()
        );
    }
    /**
     * Lấy ra số lượng sản phẩm hiện tại
     */
    public static int getNumPro() {
        String sql = "SELECT COUNT(id) FROM products";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .mapTo(Integer.class)
                        .one()
        );
    }
    //Danh sách doanh thu theo tháng
    public static List<Integer> getRevenueData() {
        List<Integer> revenueData = new ArrayList<>(Collections.nCopies(12, 0));
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createQuery("SELECT MONTH(create_at) AS month, SUM(total_price) AS monthly_revenue " +
                                "FROM orders " +
                                "WHERE YEAR(create_at) = YEAR(CURRENT_TIMESTAMP) " +
                                "GROUP BY MONTH(create_at) " +
                                "ORDER BY MONTH(create_at)")
                        .map((rs, ctx) -> {
                            int month = rs.getInt("month");
                            int revenue = rs.getInt("monthly_revenue");
                            revenueData.set(month - 1, revenue);  // month - 1 để đặt đúng vị trí trong danh sách
                            return null;
                        }).list()
        );
        return revenueData;
    }


    // Danh sách đơn hàng theo tháng
    public static List<Integer> getOrderData() {
        List<Integer> orderData = new ArrayList<>(Collections.nCopies(12, 0));
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createQuery("SELECT MONTH(create_at) AS month, COUNT(id) AS monthly_orders " +
                                "FROM orders " +
                                "WHERE YEAR(create_at) = YEAR(CURRENT_TIMESTAMP) " +
                                "GROUP BY MONTH(create_at) " +
                                "ORDER BY MONTH(create_at)")
                        .map((rs, ctx) -> {
                            int month = rs.getInt("month");
                            int orders = rs.getInt("monthly_orders");
                            orderData.set(month - 1, orders);  // month - 1 để đặt đúng vị trí trong danh sách
                            return null;
                        }).list()
        );
        return orderData;
    }
    //Tổng số lượng đơn hàng
    public static int getNumOrder() {
        String sql = "SELECT COUNT(id) FROM orders";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .mapTo(Integer.class)
                        .one()
        );
    }
    //Tổng Doanh thu
    public static int getRevenueTotal() {
        String sql = "SELECT SUM(total_price) AS total_revenue FROM orders";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .mapTo(Integer.class)
                        .one()
        );
    }
    //số lượng sản phẩm sắp hết hàng
    public static int getLowStockProductCount() {
        String sql = "SELECT COUNT(id) FROM products WHERE inventory_quantity <= ?";
        int threshold = 10; //số lượng sản phẩm nhỏ hơn hoặc bằng 10 sẽ được coi là sắp hết hàng
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, threshold)
                        .mapTo(Integer.class)
                        .one()
        );
    }
    //sản phẩm không bán được trong 3 tháng gần nhất
    public static List<Products> getUnSoldProduct() {
        String sql = "SELECT DISTINCT p.id, p.product_name, p.price, p.image, p.inventory_quantity FROM products p " +
                "LEFT JOIN order_details od ON p.id = od.id_product " +
                "LEFT JOIN orders o ON od.id_order = o.id " +
                "WHERE o.create_at IS NULL OR o.create_at < DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 3 MONTH)";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(Products.class)
                        .list()
        );
    }
    //Top 3 sản phẩm bán chạy
    public static List<Products> getSoldOutProduct() {
        String sql = "SELECT p.id, p.product_name, p.price, p.image, SUM(od.quantity) AS total_sold " +
                "FROM products p " +
                "JOIN order_details od ON p.id = od.id_product " +
                "GROUP BY p.id, p.product_name, p.price, p.image " +
                "ORDER BY total_sold DESC " +
                "LIMIT 3";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(Products.class)
                        .list()
        );
    }
    public static void main(String[] args) {
//        System.out.println(DashBoardDAO.getNumPro());
//        System.out.println(DashBoardDAO.getNumUser());
//        System.out.println(DashBoardDAO.getRevenueData().toString());
//        System.out.println(DashBoardDAO.getOrderData().toString());
//        System.out.println(DashBoardDAO.getRevenueTotal());
//        System.out.println(DashBoardDAO.getNumOrder());
//        System.out.println(DashBoardDAO.getLowStockProductCount());
//        System.out.println(DashBoardDAO.getUnSoldProduct().toString());
        System.out.println(DashBoardDAO.getSoldOutProduct().toString());
    }



}
