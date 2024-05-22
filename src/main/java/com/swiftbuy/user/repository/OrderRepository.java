package com.swiftbuy.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.model.UserDetails;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUserUserId(Long userId);
  
	



	//Optional<Order> findByUserUserIdAndId(Long userId, Long orderId);
}

