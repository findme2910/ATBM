package mapper;

import bean.WishlistItem;
import bean.User;
import bean.Products;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishlistItemRowMapper implements RowMapper<WishlistItem> {
    @Override
    public WishlistItem map(ResultSet rs, StatementContext ctx) throws SQLException {
        WishlistItem wishlistItem = new WishlistItem();
        User user = new User();
        user.setId(rs.getInt("user_id"));
        wishlistItem.setUser(user);

        Products product = new Products();
        product.setId(rs.getInt("id_product"));
        product.setProduct_name(rs.getString("product_name"));
        product.setPrice(rs.getInt("product_price"));
        product.setImage(rs.getString("product_image"));
        wishlistItem.setProducts(product);

        return wishlistItem;
    }
}
