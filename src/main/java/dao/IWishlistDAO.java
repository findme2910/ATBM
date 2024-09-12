package dao;

import bean.WishList;
import bean.WishlistItem;

import java.util.List;

public interface IWishlistDAO {
    List<WishlistItem> getWishlistByUser(Integer userId);

    boolean addWishList(Integer userId, Integer productId);

    boolean deleteWishlist(Integer userId, Integer productId);

    boolean deleteAllWishlist(Integer userId);

}
