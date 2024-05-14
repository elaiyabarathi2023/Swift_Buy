package com.swiftbuy.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.user.model.AccountManangement.AddressDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductDetails product;

    private int quantity;
    @ManyToOne(fetch = FetchType.EAGER)
    private AddressDetails address;

    public AddressDetails getAddress() {
		return address;
	}

	public void setAddress(AddressDetails address) {
		this.address = address;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties(value = {"shoppingCarts"})
    private UserDetails user;

    private Long selectedCouponId;

    // Getters and setters

    public Long getSelectedCouponId() {
        return selectedCouponId;
    }

    public void setSelectedCouponId(Long selectedCouponId) {
        this.selectedCouponId = selectedCouponId;
    }

	public Long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}

	public ProductDetails getProduct() {
		return product;
	}

	public void setProduct(ProductDetails product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public UserDetails getUser() {
		return user;
	}

	public void setUser(UserDetails user) {
		this.user = user;
	}

    // Other getters and setters
}