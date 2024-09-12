package dao;

import bean.*;

import java.util.List;

public interface IOrdersDAO {
	
	Integer insertOrder(Orders o);

	Integer insertOrdersDetail(OrderDetail od);
	List<Orders> getOrder();

	List<OrderTable> getOrdersByUser(User user);

	List<OrderDetail> getDetailsByOrder(List<Integer> ordersId);
	 List<OrderTable> getOrderforAdmin();
	 List<OrderDetailTable> getOrderDetailsByOrderId(int orderId);

	OrderTable getOrderById(int orderId);

	void updateOrderStatus(int orderId, int orderStatus);

	void updatePaymentStatus(int orderId, String paymentStatus);

	List<OrderTable> getOrdersByUserAndStatus(User user, int status);

	List<OrderDetailTable> getOrderDetailsByOrderIdAndReviewStatus(int id);
}