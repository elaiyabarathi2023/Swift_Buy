package com.swiftbuy.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.swiftbuy.user.model.Order;

public interface OrderRepository  extends CrudRepository<Order, Long>{

}
