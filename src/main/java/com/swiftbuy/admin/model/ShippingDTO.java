package com.swiftbuy.admin.model;

import java.time.LocalDateTime;
import java.util.List;

import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.model.OrderItem;

public class ShippingDTO {
	private Long orderId;
    private List<OrderItem> orderItems;
    private double totalPrice;
    private Long userId;
    
    public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
     public LocalDateTime getShippedDate() {
		return shippedDate;
	}
	public void setShippedDate(LocalDateTime shippedDate) {
		this.shippedDate = shippedDate;
	}
	private LocalDateTime shippedDate;
    private double totalCouponDiscount;
    private double totalOfferDiscount;
    private Order.OrderStatus orderStatus;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public double getTotalCouponDiscount() {
		return totalCouponDiscount;
	}
	public void setTotalCouponDiscount(double totalCouponDiscount) {
		this.totalCouponDiscount = totalCouponDiscount;
	}
	public double getTotalOfferDiscount() {
		return totalOfferDiscount;
	}
	public void setTotalOfferDiscount(double totalOfferDiscount) {
		this.totalOfferDiscount = totalOfferDiscount;
	}
	public Order.OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Order.OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}