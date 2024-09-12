package mapper;

import bean.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailTableMapper implements RowMapper<OrderDetailTable> {
    @Override
    public OrderDetailTable mapRow(ResultSet resultSet) {
        try {
            OrderDetailTable orderDetailTable = new OrderDetailTable();
            orderDetailTable.setId(resultSet.getInt("id"));
            orderDetailTable.setId_product(resultSet.getInt("id_product"));
            orderDetailTable.setProduct_name(resultSet.getString("product_name"));
            orderDetailTable.setImg(resultSet.getString("img"));
            orderDetailTable.setQuantity(resultSet.getInt("quantity"));
            orderDetailTable.setPriceDetails(resultSet.getInt("priceDetails"));
            orderDetailTable.setCreate_at(resultSet.getTimestamp("create_at"));
            orderDetailTable.setReview_status(resultSet.getBoolean("review_status"));
            return orderDetailTable;

        } catch (SQLException e) {
            return null;
        }
    }
}