package com.swiftbuy.user.service;

import org.springframework.stereotype.Service;

import com.swiftbuy.admin.model.DeliveredDTO;
import com.swiftbuy.admin.model.ShippingDTO;
import com.swiftbuy.user.model.CancellationDTO;
import com.swiftbuy.user.model.Order;
@Service
public class OrderUtil {

    public  CancellationDTO convertOrderToCancellationDTO(Order order) {
        CancellationDTO cancellationDTO = new CancellationDTO();
        cancellationDTO.setCancelledDate(order.getCancelledDate());
        cancellationDTO.setOrderId(order.getOrderId());
        cancellationDTO.setOrderItems(order.getOrderItems());
        cancellationDTO.setTotalPrice(order.getTotalPrice());
        cancellationDTO.setTotalCouponDiscount(order.getTotalCouponDiscount());
        cancellationDTO.setTotalOfferDiscount(order.getTotalOfferDiscount());
        cancellationDTO.setOrderStatus(order.getOrderStatus());
        cancellationDTO.setUserId(order.getUserId());
        return cancellationDTO;
    }

    public  ShippingDTO convertOrderToShippingDTO(Order order) {
        ShippingDTO shippingDTO = new ShippingDTO();
        shippingDTO.setOrderId(order.getOrderId());
        shippingDTO.setOrderItems(order.getOrderItems());
        shippingDTO.setTotalPrice(order.getTotalPrice());
        shippingDTO.setTotalCouponDiscount(order.getTotalCouponDiscount());
        shippingDTO.setTotalOfferDiscount(order.getTotalOfferDiscount());
        shippingDTO.setOrderStatus(order.getOrderStatus());
        shippingDTO.setShippedDate(order.getShippedDate());
        shippingDTO.setUserId(order.getUserId());
        return shippingDTO;
    }

    public  DeliveredDTO convertOrderToDeliveredDTO(Order order) {
        DeliveredDTO deliveryDTO = new DeliveredDTO();
        deliveryDTO.setOrderId(order.getOrderId());
        deliveryDTO.setOrderItems(order.getOrderItems());
        deliveryDTO.setTotalPrice(order.getTotalPrice());
        deliveryDTO.setTotalCouponDiscount(order.getTotalCouponDiscount());
        deliveryDTO.setTotalOfferDiscount(order.getTotalOfferDiscount());
        deliveryDTO.setOrderStatus(order.getOrderStatus());
        deliveryDTO.setUserId(order.getUserId());
        deliveryDTO.setDeliveredDate(order.getDeliveredDate());
        return deliveryDTO;
    }
}