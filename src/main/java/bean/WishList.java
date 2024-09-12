package bean;

import java.util.ArrayList;
import java.util.List;

public class WishList {
    List<WishlistItem> items;

    public void add(WishlistItem w) {
        for (WishlistItem i : items) {
            if (w.getProducts().getId()==i.getProducts().getId() && w.getUser().getId() == i.getUser().getId()) {
                return;
            }
        }
        items.add(w);
    }
    public void remove(WishlistItem w) {
        for (WishlistItem i : items){
            if (w.getProducts().getId() == i.getProducts().getId() && w.getUser().getId()==i.getUser().getId()) {
                items.remove(i);
                return;
            }
        }
    }
}
