package Service;

import bean.Orders;

public interface IOrdersService {
	
	void insertOrderDetail(Orders o);

	int insertOrder(Orders o);
}