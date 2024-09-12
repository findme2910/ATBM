package db;

import bean.Product;
import bean.User;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class JDBIConnector {
    private static final JDBIConnector instance = new JDBIConnector();
    //tạo đối tượng jdbi
    public static Jdbi jdbi;

    public static Jdbi getJdbi() {
        if (jdbi == null) {
            connect();
        }
        return jdbi;
    }

    public static JDBIConnector me() {
        return instance;
    }

    public static Jdbi get() throws SQLException {
        if (jdbi == null) {
            //khai báo connect trong này
            connect();
        }
        return jdbi;
    }

    public static void connect() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://" + DBProperties.host + ":" + DBProperties.port + "/" + DBProperties.dbName);
        dataSource.setUser(DBProperties.username);
        dataSource.setPassword(DBProperties.password);
        try {
            dataSource.setUseCompression(true);
            dataSource.setAutoReconnect(true);
        } catch (SQLException var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2);
        }
        jdbi = Jdbi.create(dataSource);
    }

    public static void main(String[] args) {
        Jdbi jdbi = JDBIConnector.getJdbi();

        // Lấy dữ liệu từ table products
            List<Product> products = jdbi.withHandle(handle -> {
            String sql = "SELECT * FROM products";
            return handle.createQuery(sql).mapToBean(Product.class).stream().collect(Collectors.toList());
        });
//        List<User> listUser= JDBIConnector.getJdbi().withHandle()

        // Lấy dữ liệu từ table products2


        // In ra kết quả
        for(Product pr : products){
            System.out.println(pr);
        }
//        System.out.println("Products from 'products2' table: " + products2);
    }
}
