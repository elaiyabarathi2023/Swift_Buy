package com.swiftbuy.user.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.model.Order.OrderStatus;

public interface OrderRepository extends CrudRepository<Order, Long>,
		PagingAndSortingRepository<Order, Long> {
	

	 Page<Order> findByOrderStatusEquals(OrderStatus orderStatus, Pageable pageable);

	 Page<Order> findByOrderStatusAndShippedDateBetween(OrderStatus orderStatus, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

	 Page<Order> findByOrderStatusAndDeliveredDateBetween(OrderStatus orderStatus, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

	Page<Order> findByUserId(Long userId, Pageable pageable);

	  Page<Order> findByUserIdAndOrderStatusEquals(Long userId, OrderStatus cancelled, Pageable pageable);

	Order findByOrderIdAndUserIdAndOrderStatusEquals(Long orderId, Long userId, OrderStatus cancelled);

	 Page<Order> findByUserIdAndOrderStatus(Long userId, OrderStatus delivered, Pageable pageable);
	

	Order findByOrderIdAndUserIdAndOrderStatus(Long orderId, Long userId, OrderStatus delivered);

}
