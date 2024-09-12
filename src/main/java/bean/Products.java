package bean;

import dao.ProductsDao;
import lombok.Data;
import log.AbsModel;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

@Data
public class Products extends AbsModel implements Serializable {
    private int id;
    private int id_category;
    private int id_discount;
    private String product_name;
    private String image;
    private int price;
    private String des;
    private int status;
    private int inventory_quantity;
    private Date update_at;
    private int total_sold;


    //SELECT id , product_name ,picture, price, id_category, quanitity, status, specifications,pro_desc FROM product

    public int getInventory_quantity() {
        return this.inventory_quantity;
    }
    public void setInventory_quantity(int inventory_quantity) {
        this.inventory_quantity = inventory_quantity;
    }
    public int getId() {
        return id;
    }

    public int getId_category() {
        return id_category;
    }

    public int getId_discount() {
        return id_discount;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public String getDes() {
        return des;
    }

    public int getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public void setId_discount(int id_discount) {
        this.id_discount = id_discount;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Products(int id, int id_category, int id_discount, String product_name, String image, int price, String des, int status) {
        this.id = id;
        this.id_category = id_category;
        this.id_discount = id_discount;
        this.product_name = product_name;
        this.image = image;
        this.price = price;

        this.des = des;
        this.status = status;
    }

    public Products(int id, int id_category, int id_discount, String product_name, String image, int price, String des) {
        this.id = id;
        this.id_category = id_category;
        this.id_discount = id_discount;
        this.product_name = product_name;
        this.image = image;
        this.price = price;

        this.des = des;
        this.status = status;
    }

    public Products() {
    }

    public Products( String product_name, String image, int price,int id_category, int status, int inventory_quantity, String des) {
        this.id_category = id_category;
        this.product_name = product_name;
        this.image = image;
        this.price = price;
        this.des = des;
        this.status = status;
        this.inventory_quantity = inventory_quantity;
    }

    public Products(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", id_category=" + id_category +
                ", id_discount=" + id_discount +
                ", product_name='" + product_name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", des='" + des + '\'' +
                ", status=" + status +
                '}';
    }

    public String cateOfProduct(){
        String rs= ProductsDao.CateOfProduct(id);
        return rs;
    }

    public String formatPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedPrice = decimalFormat.format(price);
        return formattedPrice.replace(',', '.');
    }
    public String statusString(){
        if(this.status==1){
            return "Mở bán";
        }
        return "Hủy bán";
    }
    public static void main(String[] args) {
        Products a=new Products(4000000);
        System.out.println(a.afterData());
    }


    @Override
    public String getTable() {
        return "Sản phẩm";
    }

    @Override
    public String beforeData() {
        return this.toString();
    }

    @Override
    public String afterData() {
        return this.toString();
    }
    public String formatDescription(String description) {
        return description.replace("-", "<br>-").replace("+", "<br>+");
    }

}

