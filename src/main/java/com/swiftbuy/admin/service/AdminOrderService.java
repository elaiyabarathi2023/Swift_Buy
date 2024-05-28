package com.swiftbuy.admin.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.swiftbuy.admin.model.DeliveredDTO;
import com.swiftbuy.admin.model.ShippingDTO;
import com.swiftbuy.user.model.CancellationDTO;
import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.repository.OrderRepository;
import com.swiftbuy.user.repository.UserRepository;
import com.swiftbuy.user.service.OrderUtil;

@Service
public class AdminOrderService {
	@Autowired
	OrderUtil orderUtil;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;

	public Order markOrderAsShipped(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		if (order.getOrderStatus() == Order.OrderStatus.CANCELLED) {
			throw new IllegalStateException("Cannot mark a cancelled order as shipped");
		}

		if (order.getOrderStatus() != Order.OrderStatus.PLACED) {
			throw new IllegalStateException("Cannot mark an order as shipped that is not in PLACED status");
		}

		order.setOrderStatus(Order.OrderStatus.SHIPPED);
		order.setShippedDate(LocalDateTime.now()); // Set the shippedDate with the current date and time
		return orderRepository.save(order);
	}

	public Order markOrderAsDelivered(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		if (order.getOrderStatus() == Order.OrderStatus.CANCELLED) {
			throw new IllegalStateException("Cannot mark a cancelled order as delivered");
		}

		if (order.getOrderStatus() != Order.OrderStatus.SHIPPED) {
			throw new IllegalStateException("Cannot mark an order as delivered that is not in SHIPPED status");
		}

		order.setOrderStatus(Order.OrderStatus.DELIVERED);
		order.setDeliveredDate(LocalDateTime.now()); // Set the deliveredDate with the current date and time
		return orderRepository.save(order);
	}

	public List<ShippingDTO> getAllShippedOrders() {
		LocalDateTime startDate = LocalDateTime.now().minusDays(30); // For example, last 7 days
		LocalDateTime endDate = LocalDateTime.now();

		List<Order> shippedOrders = orderRepository.findByOrderStatusAndShippedDateBetween(Order.OrderStatus.SHIPPED,
				startDate, endDate);

		List<ShippingDTO> shippingDTOs = new ArrayList<>();

		for (Order order : shippedOrders) {
			shippingDTOs.add(orderUtil.convertOrderToShippingDTO(order));
		}

		return shippingDTOs;
	}

	public List<CancellationDTO> getAllCancelledOrders() {

		List<Order> cancelledOrders = orderRepository.findByOrderStatusEquals(Order.OrderStatus.CANCELLED);
		List<CancellationDTO> cancellationDTOs = new ArrayList<>();
		for (Order order : cancelledOrders) {
			cancellationDTOs.add(orderUtil.convertOrderToCancellationDTO(order));
		}
		return cancellationDTOs;
	}

	public List<DeliveredDTO> getAllDeliveredOrders() {
		LocalDateTime startDate = LocalDateTime.now().minusDays(30);
		LocalDateTime endDate = LocalDateTime.now();

		List<Order> deliveredOrders = orderRepository
				.findByOrderStatusAndDeliveredDateBetween(Order.OrderStatus.DELIVERED, startDate, endDate);

		List<DeliveredDTO> DeliveredDTOs = new ArrayList<>();

		for (Order order : deliveredOrders) {
			DeliveredDTOs.add(orderUtil.convertOrderToDeliveredDTO(order));
		}

		return DeliveredDTOs;
	}

}
