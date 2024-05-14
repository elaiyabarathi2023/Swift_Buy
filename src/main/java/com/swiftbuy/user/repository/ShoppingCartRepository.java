package com.swiftbuy.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.model.UserDetails;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {



	

	List<ShoppingCart> findByUser(UserDetails userDetails);

	

	//Optional<ShoppingCart> findByUserUserIdAndProductId(Long userId, Long productId);

	Optional<ShoppingCart> findByUserUserIdAndProductProductId(Long userId, Long productId);



	List<ShoppingCart> findByUserUserId(Long userId);

	


	 

	

//	Optional<ShoppingCart> findByUserIdAndProductId(Long userId, Long productId);
	

	

}
