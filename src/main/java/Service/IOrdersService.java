package Service;

import bean.Orders;
import exceptions.DigitalSignatureException;

public interface IOrdersService {
	
	void insertOrderDetail(Orders o);

	int insertOrder(Orders o);

	String proccessOrderHash(Orders order) throws DigitalSignatureException;
}