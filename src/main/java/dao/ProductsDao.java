package dao;

import bean.Products;
import db.JDBIConnector;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductsDao implements IProductsDao {

    public static boolean toggleProductStatus(int id, boolean disable) {
        String query = "UPDATE products SET status = ? WHERE id = ?";
        int status = disable ? 0 : 1;
        int rowsUpdated = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createUpdate(query)
                        .bind(0, status)
                        .bind(1, id)
                        .execute()
        );
        return rowsUpdated > 0;
    }


    private static final int PRODUCTS_PER_PAGE = 3;

    // lấy ra ds sp đc active.
    @Override
    public List<Products> findAll1(String name) {
        List<Products> products=JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT id,product_name,image,price,id_category FROM products WHERE status=1 AND product_name LIKE ?")
                        .bind(0,"%"+ name + "%")
                        .mapToBean(Products.class).collect(Collectors.toList()));
        return products;
    }

    public static List<Products> getAllProducts() {
        List<Products> products = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT id, product_name, image, price, id_category,status,inventory_quantity FROM products")
                        .mapToBean(Products.class)
                        .collect(Collectors.toList()));
        return products;
    }
    @Override
    public List<Products> findByCategory(int idCate, String name) {
        List<Products> products = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT id, product_name, image, price, id_category\n" +
                                "FROM products\n" +
                                "WHERE status = 1 AND id_category = ? AND product_name LIKE ?")
                        .bind(0, idCate)
                        .bind(1, "%" + name + "%")
                        .mapToBean(Products.class)
                        .list());
        return products;
    }

    @Override
    public List<Products> searchByName(String productName) {
        String sql = "SELECT id,product_name,image,price,id_category FROM products where LOWER(product_name) like LOWER(?) AND status = 1";
        return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                .bind(0, "%" + productName + "%").
                mapToBean(Products.class).stream().collect(Collectors.toList()));
    }

    @Override
    public List<Products> searchByPrice(String productPrice) {
        String sql = "SELECT id,product_name,image,price,id_category FROM products where price <= ? AND status = 1";
        return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                .bind(0, productPrice).
                mapToBean(Products.class).stream().collect(Collectors.toList()));
    }

    @Override
    public List<Products> searchByDescription(String productDes) {
        String sql = "SELECT id,product_name,image,price,id_category FROM products where LOWER(des) like LOWER(?) AND status = 1";
        return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                .bind(0, "%" + productDes + "%").
                mapToBean(Products.class).stream().collect(Collectors.toList()));
    }

    @Override
    public int getTotalPages() {
        String sql = "select count(*) as count from products WHERE status = 1";
        int totalProducts = JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql).mapTo(Integer.class).one());
        return (int) Math.ceil((double) totalProducts / getProductsPerPageConstant());
    }

    @Override
    public List<Products> getProductsPerPage(int currentPage) {
        int offset = (currentPage - 1) * getProductsPerPageConstant();
        String sql = "SELECT id, product_name, image, price, id_category FROM products WHERE status = 1 LIMIT ?, ?";
        return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                .bind(0, offset)
                .bind(1, getProductsPerPageConstant())
                .mapToBean(Products.class)
                .stream().collect(Collectors.toList()));
    }

    @Override
    public int getProductsPerPageConstant() {
        return PRODUCTS_PER_PAGE;
    }

    // lấy ra số lượng của toàn bộ loại sản phẩm.
    public static int numOfProduct(String search){
        Integer integer = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT COUNT(*)  FROM products where product_name LIKE ?")
                        .bind(0,"%"+search+"%")
                        .mapTo(Integer.class)
                        .one());
        return integer != null ?integer :0;
    }
    // lấy ra số lượng toàn bộ loại sản phẩm theo doanh mục.
    public static int numOfProCate(int idCate,String search){
        Integer integer = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT COUNT(*)  FROM products where id_category=? AND product_name LIKE ?")
                        .bind(0,idCate)
                        .bind(1,"%"+search+"%")
                        .mapTo(Integer.class)
                        .one());
        return integer != null ?integer :0;
    }


    //=============================================dưới là phần của admin==========================================================
    public static List<Products>productList(String search) {
        List<Products> products = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT products.id,products.product_name,products.image,products.price, products.id_category, products.status,products.des\n" +
                                "FROM products \n" +
                                "INNER JOIN categories ON products.id_category=categories.id\n" +
                                "WHERE products.product_name LIKE ? OR categories.name_category LIKE ?")
                        .bind(0,"%"+search+"%")
                        .bind(1,"%"+search+"%")
                        .mapToBean(Products.class).collect(Collectors.toList()));
        return products;
    }
    public static List<Products> getTenPro(int index, String search) {
        List<Products> products = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT products.id, products.product_name, products.image, products.price, products.id_category, products.status, products.des\n" +
                                "FROM products\n" +
                                "INNER JOIN categories ON products.id_category = categories.id\n" +
                                "WHERE products.product_name LIKE ? OR categories.name_category LIKE ?\n" +
                                "ORDER BY products.id\n" +
                                "LIMIT 10 OFFSET ?")
                        .bind(0, "%" + search + "%")
                        .bind(1, "%" + search + "%")
                        .bind(2, (index - 1) * 10)
                        .mapToBean(Products.class)
                        .collect(Collectors.toList()));
        return products;
    }

    // Phương thức dưới lấy ra tên Category của 1 sp bất kỳ theo id sản phẩm.
    public static String CateOfProduct(int id){
        String cateName = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT c.name_category FROM products p " +
                                "INNER JOIN categories c ON p.id_category = c.id " +
                                "WHERE p.id = ?")
                        .bind(0, id)
                        .mapTo(String.class)
                        .one()); // Sử dụng oneOrNull để tránh ngoại lệ khi không tìm thấy dữ liệu

        return cateName != null ? cateName : "";
    }
    public static void insertProduct(String name, String image, int price, int category, int status, int inventory_quantity,String desc,String ip) {
        Products products = new Products(name,image,price,category,status,inventory_quantity,desc);
        try {
            JDBIConnector.getJdbi().useHandle(handle ->
                    handle.createUpdate("INSERT INTO products(product_name, image, price, id_category, status,inventory_quantity, des) " +
                                    "VALUES(?,?,?,?,?,?,?)")
                            .bind(0, name)
                            .bind(1, image)
                            .bind(2, price)
                            .bind(3, category)
                            .bind(4, status)
                            .bind(5,inventory_quantity)
                            .bind(6, desc)
                            .execute());
            LogDao.getInstance().insertModel(products,ip,1,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteProduct(int proID){
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate("DELETE FROM products WHERE id = ?")
                        .bind(0,proID)
                        .execute());
    }
    public static Products getProductById(int proID){
        Optional<Products> products = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT products.id, products.product_name, products.image, products.price, products.id_category, products.status, products.des,products.inventory_quantity\n" +
                                "FROM products\n" +
                                "WHERE products.id=?")
                        .bind(0, proID)
                        .mapToBean(Products.class).stream().findFirst());
        return products.isEmpty() ? null : products.get();
    }

    //UPDATE product SET product_name="dfsd",image="fdsfs",price=5090,id_category=1,quantity=78,status=0,specifications="dfdsf",des="dfsdf" WHERE id=46
    public static void editProduct(String name,String image,int price,int idCategory,int status,String proDesc,int inventory_quantity,int id){
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate("UPDATE products SET product_name=?,image=?,price=?,id_category=?,status=?,des=?,inventory_quantity=? WHERE id=?")
                        .bind(0,name)
                        .bind(1,image)
                        .bind(2,price)
                        .bind(3,idCategory)
                        .bind(4,status)
                        .bind(5,proDesc)
                        .bind(6,inventory_quantity)
                        .bind(7,id)
                        .execute()
        );
    }
    // lấy ra danh sách toàn bộ sp.int id, int id_category, int id_discount, String product_name, String image, int price, String des, int status
    public static List<Products> numOfPro() {
        Jdbi jdbi = JDBIConnector.getJdbi();
        List<Products> findNewPro2 = jdbi.withHandle(handle -> {
            String sql = "SELECT id, id_category, id_discount, product_name, image, price, des FROM products" ;
            return handle.createQuery(sql).mapToBean(Products.class).stream().collect(Collectors.toList());
        });
        return findNewPro2;
    }

    public static void main(String[] args) {

       ProductsDao dao = new ProductsDao();

    }
}
