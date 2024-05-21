package com.swiftbuy.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.swiftbuy.user.model.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long>{

}
