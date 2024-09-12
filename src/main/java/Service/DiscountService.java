package Service;

import bean.Discount;
import dao.DiscountDAO;

import java.util.List;

public class DiscountService implements IDiscountService {
    private static IDiscountService instance;

    public static IDiscountService getInstance() {
        if (instance == null) {
            instance = new DiscountService();
        }
        return instance;
    }


    @Override
    public List<Discount> getAllCoupons() {
        try {
            return DiscountDAO.getInstance().getAllCoupons();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Discount getCouponById(Integer id) {
        try {
            return DiscountDAO.getInstance().getCouponById(id).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Discount getCouponByName(String name) {
        try {
            return DiscountDAO.getInstance().getCouponByName(name).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Discount getCouponByCode(String code) {
        try {
            return DiscountDAO.getInstance().getCouponByCode(code).get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
