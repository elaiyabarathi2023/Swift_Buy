package com.swiftbuy.admin.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


public class ProductCouponRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	
	private long productcoupon_id;
    

	
	private Long productId;
	@JsonIgnoreProperties({"productCouponRequests"})
	@Column(name= "coupon_id",nullable=true)
    private List<Long> couponIds;
	
	
	public long getProductcoupon_id() {
		return productcoupon_id;
	}
	public void setProductcoupon_id(long productcoupon_id) {
		this.productcoupon_id = productcoupon_id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public List<Long> getCouponIds() {
		return couponIds;
	}
	public void setCouponIds(List<Long> couponIds) {
		this.couponIds = couponIds;
	}

//    public ProductCouponRequest() {
//    }
//
//    public ProductCouponRequest(Long productId, List<Long> couponIds) {
//        this.productId = productId;
//        this.couponIds = couponIds;
//    }
//
//    public Long getProductId() {
//        return productId;
//    }
//
//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }
//
//    public List<Long> getCouponIds() {
//        return couponIds;
//    }
//
//    public void setCouponIds(List<Long> couponIds) {
//        this.couponIds = couponIds;
//    }
//    
//    public long getProductcoupon_id() {
//		return productcoupon_id;
//	}
//
//	public void setProductcoupon_id(long productcoupon_id) {
//		this.productcoupon_id = productcoupon_id;
//	}

	
}
