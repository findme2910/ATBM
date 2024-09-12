package mapper;

import bean.CartItem;
import bean.Orders;
import bean.Products;
import bean.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Products> {
    @Override
    public Products mapRow(ResultSet resultSet) {
        try {
           Products products=new Products();
           products.setImage(resultSet.getString("image"));
           products.setProduct_name(resultSet.getString("product_name"));
           products.setPrice(resultSet.getInt("price"));
           return products;
        } catch (SQLException e) {
            return null;
        }
    }
}