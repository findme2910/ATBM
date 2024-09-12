package dao;

import bean.Product;
import bean.Products;

import java.util.List;

public interface IProductDAO {
    List<Products> findAll1();
//    List<Products> findAll2();

    List<Product> findAll2();

    List<Products> findNewPro1();

    List<Products> findNewPro2();

    List<Products> findDiscountPro1();

    List<Products> findDiscountPro2();

    List<Products> findById(int id);

    void sortByPrice(List<Products> products, boolean isAscending);


    List<Products> findByPriceMin(String id);

    int getProductsPerPageConstant();

    int getTotalPages();

    List<Products> getProductsPerPage(int currentPage);

    List<Products> getRelatedProducts(int i, int id);
}
