package dao;

import bean.Discount;

import java.sql.Timestamp;
import java.util.List;

public interface IDiscountDAO  {

    List<Discount> getAllCoupons();

    List<Discount> getCouponById(Integer id);

    List<Discount> getCouponByName(String name);

    List<Discount> getCouponByCode(String code);

    boolean addCoupon(String name, String code, String des, Double percent, Integer quantity, Timestamp startTimestamp, Timestamp TimestampEnd);

    boolean delCoupon(Integer id);

    boolean delCouponByCode(String code);

    boolean editCoupon(Integer id, String code, String name, String des, Double percent, Integer quantity, Timestamp startTimestamp, Timestamp TimestampEnd);

    boolean editCouponByCode(String code, String name, String des, Double percent, Integer quantity, Timestamp startTimestamp, Timestamp TimestampEnd);

}
