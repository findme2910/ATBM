package dao;

import bean.Products;

import java.util.List;

public interface IProductsDao {
    List<Products> findAll1(String name);
    List<Products> findByCategory(int idCate,String name);

    List<Products> searchByName(String productName);

    List<Products> searchByPrice(String productPrice);

    List<Products> searchByDescription(String productDes);

    int getTotalPages();

    List<Products> getProductsPerPage(int currentPage);

    int getProductsPerPageConstant();

}
