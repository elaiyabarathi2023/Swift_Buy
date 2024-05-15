package com.swiftbuy.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.model.UserDetails;

public interface OrderRepository  extends CrudRepository<Order, Long>{

	List<Order> findByUser(UserDetails user);

	Optional<UserDetails> findByIdAndUser(Long orderId, UserDetails user);

	

}
