package dao;

import bean.ProductReview;
import bean.Products;
import db.JDBIConnector;

import java.util.List;
import java.util.stream.Collectors;

public class ProductReviewDao {
    // Lấy ra danh sách các comment thuộc về sản phẩm nào đó.
    public static List<ProductReview> getListReview(int productId) {
        String sql = "SELECT product_reviews.id, product_reviews.id_product, product_reviews.id_user, users.user_name as user_name,users.picture as picture, product_reviews.rating, product_reviews.content, product_reviews.create_at " +
                "FROM product_reviews " +
                "JOIN users ON product_reviews.id_user = users.id " +
                "WHERE product_reviews.id_product = ?";
        List<ProductReview> productReviewList = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, productId)
                        .mapToBean(ProductReview.class)
                        .collect(Collectors.toList()));
        return productReviewList;
    }
    // Lấy ra danh sách các comment có rating cụ thể
    public List<ProductReview> getListReviewByRating(int productId, int rating) {
        String sql = "SELECT product_reviews.id, product_reviews.id_product, product_reviews.id_user, users.user_name as user_name,users.picture as picture, product_reviews.rating, product_reviews.content, product_reviews.create_at " +
                "FROM product_reviews " +
                "JOIN users ON product_reviews.id_user = users.id " +
                "WHERE product_reviews.id_product = ? AND product_reviews.rating = ?";
        List<ProductReview> productReviewList = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, productId)
                        .bind(1, rating)
                        .mapToBean(ProductReview.class)
                        .collect(Collectors.toList()));
        return productReviewList;
    }
    public List<ProductReview> getListReviewByRating1(int productId) {
        return getListReviewByRating(productId, 1);
    }

    public List<ProductReview> getListReviewByRating2(int productId) {
        return getListReviewByRating(productId, 2);
    }

    public List<ProductReview> getListReviewByRating3(int productId) {
        return getListReviewByRating(productId, 3);
    }

    public List<ProductReview> getListReviewByRating4(int productId) {
        return getListReviewByRating(productId, 4);
    }

    public List<ProductReview> getListReviewByRating5(int productId) {
        return getListReviewByRating(productId, 5);
    }
    public void insertProductReview(ProductReview review) {
        String sql = "INSERT INTO product_reviews (id_product, id_user, rating, content) VALUES (?, ?, ?, ?)";
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, review.getId_product())
                        .bind(1, review.getId_user())
                        .bind(2, review.getRating())
                        .bind(3, review.getContent())
                        .execute()
        );
    }
    // Lấy ra danh sách các phản hồi cho một bình luận cụ thể
    public List<ProductReview> getReplies(int reviewId) {
        String sql = "SELECT product_reviews.id, product_reviews.id_product, product_reviews.id_user, users.user_name as user_name,users.picture as picture, product_reviews.rating, product_reviews.content, product_reviews.create_at, product_reviews.parent_id " +
                "FROM product_reviews " +
                "JOIN users ON product_reviews.id_user = users.id " +
                "WHERE product_reviews.parent_id = ?";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, reviewId)
                        .mapToBean(ProductReview.class)
                        .collect(Collectors.toList()));
    }

    public static void main(String[] args) {
        ProductReviewDao dao = new ProductReviewDao();
//        ProductReview review = new ProductReview();
//        review.setId_product(4);
//        review.setId_user(3);
//        review.setRating(4);
//        review.setContent("Sản phẩm ngon, sẽ ủng hộ");
//        System.out.println(dao.getListReviewByRating(3,5));
//       dao.insertProductReview(review);
//        System.out.println(dao.getListReview(3));
    }
}
