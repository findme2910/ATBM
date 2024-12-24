package Service;

import bean.Orders;
import bean.User;
import exceptions.DigitalSignatureException;

public interface IOrdersService {
	
	void insertOrderDetail(Orders o);

	int insertOrder(Orders o);

	String proccessOrderHash(Orders order) throws DigitalSignatureException;

	String proccessOrderHash(int orderId, User user) throws DigitalSignatureException;
}