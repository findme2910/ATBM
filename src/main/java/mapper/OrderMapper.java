package mapper;

import bean.CartItem;
import bean.Orders;
import bean.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Orders> {
    @Override
    public Orders mapRow(ResultSet resultSet) {
        try {
            Orders order = new Orders();
            order.setId(resultSet.getInt("id"));
            order.setIdUser(resultSet.getInt("id_user"));
            order.setAddress(resultSet.getString("address"));
             order.setTotalPrice(resultSet.getFloat("total_price"));
             order.setShippingFee(resultSet.getFloat("shipping_fee"));
             order.setPhoneNumber(resultSet.getString("phone_number"));
             return order;
        } catch (SQLException e) {
            return null;
        }
    }
}