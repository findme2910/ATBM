package Service;

import java.util.List;

import bean.CartItem;
import bean.OrderDetail;
import dao.IOrdersDAO;
import dao.OrdersDAO;
import bean.Orders;
import bean.Product;
import Service.IOrdersService;

public class OrdersService implements IOrdersService {
	private final IOrdersDAO ordersDAO;

	public OrdersService() {
		this.ordersDAO = new OrdersDAO();
	}
	@Override
	public void insertOrderDetail(Orders o) {
		Integer orderId = this.ordersDAO.insertOrder(o);
		System.out.println((orderId));
		List<CartItem> list = o.getLp();
		for (CartItem p : list) {
			OrderDetail od = new OrderDetail(orderId,p.getProduct().getId(),p.getQuantity());
			this.ordersDAO.insertOrdersDetail(od);
		}
	}
}