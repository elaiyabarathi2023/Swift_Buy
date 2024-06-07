package com.swiftbuy.user.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "checkout")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @JsonIgnore
    private LocalDateTime shippedDate;

    @JsonIgnore
    private LocalDateTime deliveredDate;

    @JsonIgnore
    private LocalDate cancelledDate;

    public LocalDate getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(LocalDate cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    private LocalDate orderedDate;

    public LocalDate getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(LocalDate orderedDate) {
        this.orderedDate = orderedDate;
    }

    private Long userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    private double totalPrice;
    private double totalCouponDiscount;
    private double totalOfferDiscount;

    public enum OrderStatus {
        PLACED, SHIPPED, DELIVERED, CANCELLED
    }

    private OrderStatus orderStatus = OrderStatus.PLACED;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalOfferDiscount() {
        return totalOfferDiscount;
    }

    public void setTotalOfferDiscount(double totalOfferDiscount) {
        this.totalOfferDiscount = totalOfferDiscount;
    }

    public double getTotalCouponDiscount() {
        return totalCouponDiscount;
    }

    public void setTotalCouponDiscount(double totalCouponDiscount) {
        this.totalCouponDiscount = totalCouponDiscount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public LocalDateTime getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDateTime shippedDate) {
        this.shippedDate = shippedDate;
    }

    public LocalDateTime getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }
}