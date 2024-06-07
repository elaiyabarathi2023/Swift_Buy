package com.swiftbuy.user.model;

import java.time.LocalDate;
import java.util.List;

public class CancellationDTO {
	 private Long orderId;
	    private List<OrderItem> orderItems;
	    private double totalPrice;
	    private double totalCouponDiscount;
	    private double totalOfferDiscount;
	    private LocalDate cancelledDate;
	    private Long userId;
	    
	    public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public LocalDate getCancelledDate() {
			return cancelledDate;
		}
		public void setCancelledDate(LocalDate cancelledDate) {
			this.cancelledDate = cancelledDate;
		}
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
