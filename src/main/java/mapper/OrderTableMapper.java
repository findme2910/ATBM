package mapper;


import bean.OrderTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderTableMapper implements RowMapper<OrderTable> {
    @Override
    public OrderTable mapRow(ResultSet rs){
        try{
        OrderTable orderTable = new OrderTable();
        orderTable.setId(rs.getInt("id"));
        orderTable.setUsername(rs.getString("username"));
        orderTable.setTotal_price(rs.getInt("total_price"));
        orderTable.setShipping_fee(rs.getInt("shipping_fee"));
        orderTable.setAddress(rs.getString("address"));
        orderTable.setPhone_number(rs.getString("phone_number"));
        orderTable.setCreateAt(rs.getTimestamp("create_at"));
        orderTable.setPayment_status(rs.getString("payment_status"));
        orderTable.setOrder_status(rs.getInt("order_status"));
        return orderTable;}
        catch (SQLException e) {
            return null;
        }
    }
}