package com.swiftbuy.user.model;

public class ShoppingCartRequest {

    private Long productId;
    private int quantity;
    private Long selectedCouponId;
    private Long userId;

    // Getters and setters

    public ShoppingCartRequest(long l, int i, Object object) {
		// TODO Auto-generated constructor stub
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getSelectedCouponId() {
        return selectedCouponId;
    }

    public void setSelectedCouponId(Long selectedCouponId) {
        this.selectedCouponId = selectedCouponId;
    }
}