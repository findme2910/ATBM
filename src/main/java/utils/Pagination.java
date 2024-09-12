package utils;

import bean.Products;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
    private int records;
    private List<Products> listProduct;

    public Pagination(int records, List<Products> listProduct) {
        super();
        this.records = records;
        this.listProduct = listProduct;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public List<Products> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Products> listProduct) {
        this.listProduct = listProduct;
    }

    public List<Products> getProductPerPage(int currentPage) {
        List<Products> products = new ArrayList<>();
        int startIndex = (currentPage - 1) * records;
        int endIndex = Math.min((startIndex + records), listProduct.size());
        for (int i = startIndex; i < endIndex; i++) {
            products.add(listProduct.get(i));
        }
        return products;
    }
}