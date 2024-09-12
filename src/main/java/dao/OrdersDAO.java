package dao;

import bean.*;
import db.JDBIConnector;
import mapper.*;
import org.jdbi.v3.core.Jdbi;

import javax.persistence.criteria.Order;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersDAO extends AbstractDAO<Orders> implements IOrdersDAO {

	public Products findImageBy(int productId) {
		String sql = "select * from products where id = ?";
		return query(sql, new ProductMapper(), productId).get(0);
	}

	@Override
	public Integer insertOrder(Orders o) {
		String sql = "insert into orders(id_user, total_price, shipping_fee, address, phone_number,payment_status,order_status) values(?,?,?,?,?,?,1)";
		return insert(sql, o.getIdUser(), o.getTotalPrice(), o.getShippingFee(), o.getAddress(), o.getPhoneNumber(),o.getPayment_status());
	}

	@Override
	public List<Orders> getOrder() {
		String sql = "select * from orders";
		return query(sql, new OrderMapper());
	}

	public List<OrderTable> getOrderforAdmin() {
		String sql = "SELECT o.id AS id, u.user_name AS username, o.create_at AS create_at, " +
				"o.total_price AS total_price, o.shipping_fee AS shipping_fee, " +
				"o.address AS address, o.phone_number AS phone_number, " +
				"o.payment_status AS payment_status, o.order_status AS order_status " +
				"FROM orders o " +
				"JOIN users u ON o.id_user = u.id";
		return query(sql, new OrderTableMapper());
	}

	public List<OrderDetailTable> getOrderDetailsByOrderId(int orderId) {
		String sql = "SELECT od.id as id, od.id_product as id_product,od.review_status as review_status, p.product_name AS product_name, p.image as img, od.quantity as quantity, " +
				"(od.quantity * p.price) AS priceDetails, o.create_at as create_at " +
				"FROM order_details od " +
				"JOIN products p ON od.id_product = p.id " +
				"JOIN orders o ON od.id_order = o.id " +
				"WHERE od.id_order = ?";
		return query(sql, new OrderDetailTableMapper(), orderId);
	}
// phương thức lấy ra tất cả orderdetails chưa bình luận
	public List<OrderDetailTable> getOrderDetailsByOrderIdAndReviewStatus(int orderId) {
		String sql = "SELECT od.id as id, od.id_product as id_product, od.review_status as review_status, p.product_name AS product_name, p.image as img, od.quantity as quantity, " +
				"(od.quantity * p.price) AS priceDetails, o.create_at as create_at " +
				"FROM order_details od " +
				"JOIN products p ON od.id_product = p.id " +
				"JOIN orders o ON od.id_order = o.id " +
				"WHERE od.id_order = ? AND od.review_status = 0";
		return query(sql, new OrderDetailTableMapper(), orderId);
	}
	// phương thức này được gọi khi comment
	public void updateReviewStatus(int orderDetailId) {
		String sql = "UPDATE order_details SET review_status = 1 WHERE id = ?";
		JDBIConnector.getJdbi().useHandle(handle ->
				handle.createUpdate(sql)
						.bind(0, orderDetailId)
						.execute()
		);
	}
	@Override
	public OrderTable getOrderById(int orderId) {
		String sql = "SELECT o.id AS id, u.user_name AS username, o.create_at AS create_at, " +
				"o.total_price AS total_price, o.shipping_fee AS shipping_fee, " +
				"o.address AS address, o.phone_number AS phone_number, " +
				"o.payment_status AS payment_status, o.order_status AS order_status " +
				"FROM orders o " +
				"JOIN users u ON o.id_user = u.id " +
				"WHERE o.id = ?";
		return query(sql, new OrderTableMapper(), orderId).get(0);
	}
//update trạng thái đơn hàng
	@Override
	public void updateOrderStatus(int orderId, int orderStatus) {
		String sqlUpdateStatus = "UPDATE orders SET order_status = ? WHERE id = ?";
		update(sqlUpdateStatus, orderStatus, orderId);
		//cập nhật lại sản phẩm khi orderstatus = 0
		if (orderStatus == 0) { // Đã hủy đơn hàng
			String sqlGetOrderDetails = "SELECT id_product as product_id, quantity FROM order_details WHERE id_order  = ?";
			List<OrderDetail> orderDetails = JDBIConnector.getJdbi().withHandle(handle ->
					handle.createQuery(sqlGetOrderDetails)
							.bind(0, orderId)
							.mapToBean(OrderDetail.class)
							.list()
			);
			for (OrderDetail detail : orderDetails) {
				String sqlUpdateInventory = "UPDATE products SET inventory_quantity = inventory_quantity + ? WHERE id = ?";
				this.update(sqlUpdateInventory, detail.getQuantity(), detail.getProduct_id());
			}
		}
	}

// Update trạng thái thanh toán
	@Override
	public void updatePaymentStatus(int orderId, String paymentStatus) {
		String sql = "UPDATE orders SET payment_status = ? WHERE id = ?";
		update(sql, paymentStatus, orderId);
	}

	@Override
	public List<OrderTable> getOrdersByUserAndStatus(User user, int status) {
		String sql = "SELECT o.id AS id, u.user_name AS username, o.create_at AS create_at, " +
				"o.total_price AS total_price, o.shipping_fee AS shipping_fee, " +
				"o.address AS address, o.phone_number AS phone_number, " +
				"o.payment_status AS payment_status, o.order_status AS order_status " +
				"FROM orders o " +
				"JOIN users u ON o.id_user = u.id " +
				"WHERE o.id_user = ? AND o.order_status = ?";
		return query(sql, new OrderTableMapper(), user.getId(), status);
	}


	@Override
	public List<OrderTable> getOrdersByUser(User user) {
		String sql = "SELECT o.id AS id, u.user_name AS username, o.create_at AS create_at, " +
				"o.total_price AS total_price, o.shipping_fee AS shipping_fee, " +
				"o.address AS address, o.phone_number AS phone_number, " +
				"o.payment_status AS payment_status, o.order_status AS order_status " +
				"FROM orders o " +
				"JOIN users u ON o.id_user = u.id " +
				"WHERE o.id_user = ?";
		return query(sql, new OrderTableMapper(), user.getId());
	}

	//khi insert một order details sẽ thay đổi quantity trong product
	@Override
	public  Integer insertOrdersDetail(OrderDetail od) {
		String insertOrderDetailSql="insert into order_details(id_order, id_product, quantity,review_status) values(?,?,?,0)";
		String updateProductSql = "UPDATE products SET inventory_quantity = inventory_quantity - ? WHERE id = ?";
		Jdbi jdbi = JDBIConnector.getJdbi();
		return jdbi.inTransaction(handle -> {
			int rowsAffected = handle.createUpdate(insertOrderDetailSql)
					.bind(0, od.getOrder_id())
					.bind(1, od.getProduct_id())
					.bind(2, od.getQuantity())
					.execute();

			if (rowsAffected > 0) {
				int updateRows = handle.createUpdate(updateProductSql)
						.bind(0, od.getQuantity())
						.bind(1, od.getProduct_id())
						.execute();

				if (updateRows > 0) {
					return rowsAffected;
				}
			}

			return 0;
		});
	}

	@Override
	public List<OrderDetail> getDetailsByOrder(List<Integer> ordersId) {
		// Tạo chuỗi các tham số
		String params = ordersId.stream()
				.map(Object::toString)
				.collect(Collectors.joining(", "));
		// Tạo câu truy vấn SQL với chuỗi các tham số
		String sql = "SELECT * FROM order_details WHERE id_order IN (" + params + ")";

		return query(sql, new OrderDetailMapper());
	}

	public Orders findBy(int orderId) {
		String sql = "select * from orders where id = ?";
		return query(sql, new OrderMapper(), orderId).get(0);
	}

	public static void main(String[] args) {
		IOrdersDAO order = new OrdersDAO();
//		OrderDetail orderDetail = new OrderDetail();
//		orderDetail.setOrder_id(2);
//		orderDetail.setProduct_id(6);
//		orderDetail.setQuantity(10);
//		//System.out.println(order.getOrderforAdmin())
//		User user = new User();
//		user.setId(11);
//		System.out.println(order.getOrdersByUserAndStatus(user,0));
		Orders orders = new Orders(1,5,20,"158/d","0987817240","Chưa Thanh Toán");
		System.out.println(	order.insertOrder(orders));


	}
}