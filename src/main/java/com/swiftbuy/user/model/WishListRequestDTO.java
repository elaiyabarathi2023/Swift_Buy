package com.swiftbuy.user.model;

//public class WishListRequestDTO {
//
//}

import com.swiftbuy.user.model.WishList;
import java.util.List;

public class WishListRequestDTO {
    private WishList wishlist;
    private List<Long> productIds;

    public void WishListRequest() {
        // Default constructor
    }

    public void WishListRequest(WishList wishlist, List<Long> productIds) {
        this.wishlist = wishlist;
        this.productIds = productIds;
    }

    public WishList getWishlist() {
        return wishlist;
    }

    public void setWishlist(WishList wishlist) {
        this.wishlist = wishlist;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
