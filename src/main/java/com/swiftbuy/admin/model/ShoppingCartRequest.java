package com.swiftbuy.admin.model;

public class ShoppingCartRequest {
    private Long productId;
    private int quantity;
    private Long selectedCouponId; // Change to selectedCouponId
    private Long addressId; // Add this field

    // Getters and setters

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

    public Long getAddressId() { // Getter for addressId
        return addressId;
    }

    public void setAddressId(Long addressId) { // Setter for addressId
        this.addressId = addressId;
    }
}
