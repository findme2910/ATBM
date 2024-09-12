package bean;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class Orders {
    private int id;
    private int idUser;
    private float totalPrice;
    private float shippingFee;
    private String address;
    private String phoneNumber;
    private Timestamp createAt;
    private String payment_status;
    private int order_status;
    private List<CartItem> lp;


    public Orders(int id, int idUser, float totalPrice, float shippingFee, String address, String phoneNumber, Timestamp createAt) {
        this.id = id;
        this.idUser = idUser;
        this.totalPrice = totalPrice;
        this.shippingFee = shippingFee;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.createAt = createAt;
    }

    public Orders(int idUser, float totalPrice, float shippingFee, String address,
                  String phoneNumber, int status, List<CartItem> lp) {
        this.idUser = idUser;
        this.totalPrice = totalPrice;
        this.shippingFee = shippingFee;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.lp = lp;
    }

    public Orders() {

    }

    public Orders(int idUser, float totalPrice, float shippingFee, String address, String phoneNumber,String payment_status) {
        this.idUser = idUser;
        this.totalPrice = totalPrice;
        this.shippingFee = shippingFee;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.payment_status=payment_status;
    }

    public List<CartItem> getLp() {
        return lp;
    }

    public void setLp(List<CartItem> lp) {
        this.lp = lp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public float getShippingFee() {
        return shippingFee;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }




    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setShippingFee(float shippingFee) {
        this.shippingFee = shippingFee;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", totalPrice=" + totalPrice +
                ", shippingFee=" + shippingFee +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createAt=" + createAt +
                ", lp=" + lp +
                '}';
    }
}
