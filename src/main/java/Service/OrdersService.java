package Service;

import bean.CartItem;
import bean.OrderDetail;
import bean.OrderDetailTable;
import bean.Orders;
import bean.digitalsignature.OrderSign;
import dao.IOrdersDAO;
import dao.OrdersDAO;
import exceptions.DigitalSignatureException;
import utils.Hash;

import java.sql.Timestamp;
import java.util.List;

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

	@Override
	public int insertOrder(Orders o) {
		Integer orderId = this.ordersDAO.insertOrder(o);
		System.out.println((orderId));
		List<CartItem> list = o.getLp();
		for (CartItem p : list) {
			OrderDetail od = new OrderDetail(orderId, p.getProduct().getId(), p.getQuantity());
			this.ordersDAO.insertOrdersDetail(od);
		}
		return orderId;
	}

	@Override
	public String proccessOrderHash(Orders order) throws DigitalSignatureException {
		List<OrderDetailTable> listDetails = ordersDAO.getOrderDetailsByOrderId(order.getId());
		OrderSign orderSign = new OrderSign(order.getId(), order.getIdUser(), order.getCreateAt(), listDetails);
		System.out.println(orderSign);
		return Hash.hash(orderSign.toString());
	}
//	public String hashOrder
}