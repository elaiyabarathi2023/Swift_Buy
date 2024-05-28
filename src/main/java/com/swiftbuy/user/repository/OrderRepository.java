package com.swiftbuy.user.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.model.Order.OrderStatus;

public interface OrderRepository extends CrudRepository<Order, Long> {
  //  List<Order> findByUserUserId(Long userId);

//	List<Order> findByUserUserIdAndOrderStatusEquals(Long userId, OrderStatus cancelled);

	List<Order> findByOrderStatusEquals(OrderStatus shipped);

//Order findByOrderIdAndUserUserIdAndOrderStatusEquals(Long orderId, Long userId, OrderStatus cancelled);
	

	List<Order> findByOrderStatusAndShippedDateBetween(OrderStatus shipped, LocalDateTime startDate,
			LocalDateTime endDate);


	List<Order> findByOrderStatusAndDeliveredDateBetween(OrderStatus delivered, LocalDateTime startDate, LocalDateTime endDate);

	//Order findByOrderIdAndUserUserIdAndOrderStatus(Long orderId, Long userId, OrderStatus delivered);

//List<Order> findByUserUserIdAndOrderStatus(Long userId, OrderStatus delivered);

List<Order> findByUserId(Long userId);

List<Order> findByUserIdAndOrderStatusEquals(Long userId, OrderStatus cancelled);

Order findByOrderIdAndUserIdAndOrderStatusEquals(Long orderId, Long userId, OrderStatus cancelled);

List<Order> findByUserIdAndOrderStatus(Long userId, OrderStatus delivered);

Order findByOrderIdAndUserIdAndOrderStatus(Long orderId, Long userId, OrderStatus delivered);

	
  
	



	
}

