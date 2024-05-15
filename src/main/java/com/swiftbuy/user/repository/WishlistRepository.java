package com.swiftbuy.user.repository;

import org.springframework.data.repository.CrudRepository;

//public class UserWishlistRepo {
//
//}




import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.model.WishList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;




public interface WishlistRepository extends CrudRepository<WishList, Long> {
	List<WishList> findByUser(UserDetails user);
	
//	WishList findByUserIdAndProduct_Id(UserDetails user, Long productId);

//	 Iterable<WishList> findByUserId(Long userId);
}