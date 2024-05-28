package com.swiftbuy.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.swiftbuy.user.model.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

	//List<ShoppingCart> findByUser(UserDetails userDetails);
	//Optional<ShoppingCart> findByUserUserIdAndProductProductId(Long userId, Long productId);
	//List<ShoppingCart> findByUserUserId(Long userId);
	List<ShoppingCart> findByUserId(Long userId);
	//Optional<ShoppingCart> findByUserIdAndProductProductId(Long userId, Long productId);
	Optional<ShoppingCart> findByUserIdAndProductProductId(Long userId, Long productId);
}
