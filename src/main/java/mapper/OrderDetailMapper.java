package mapper;

import bean.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailMapper implements RowMapper<OrderDetail> {
    @Override
    public OrderDetail mapRow(ResultSet resultSet) {
        try {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder_id(resultSet.getInt("id_order"));
            orderDetail.setProduct_id(resultSet.getInt("id_product"));
            orderDetail.setQuantity(resultSet.getInt("quantity"));
            return orderDetail;

        } catch (SQLException e) {
            return null;
        }
    }
}