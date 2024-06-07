package com.swiftbuy.admin.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="coupons")

public class CouponCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="coupon_id",nullable=false)
    private Long couponId;

    @ManyToMany(mappedBy = "coupons")
    @JsonIgnore
    private Set<ProductDetails> products;

    private String name;
    private String description;

    @Column(nullable = true)
    private double discountPercentage;

    // Default constructor
    public CouponCodes() {
    }
    @Column(nullable = false)
    private boolean isDeleted = false;
    // Getters and setters

    public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

//    public Set<ProductDetails> getProducts() {
//        return products;
//    }
//
//    public void setProducts(Set<ProductDetails> products) {
//        this.products = products;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}