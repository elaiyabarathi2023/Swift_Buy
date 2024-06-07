package com.swiftbuy.user.repository;
 
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.swiftbuy.user.model.ReviewDetails;
import com.swiftbuy.user.model.ReviewRequest;
import com.swiftbuy.user.model.UserDetails;
 
public interface ReviewRepository extends CrudRepository<ReviewDetails,Long> {
 
	// Optional<ReviewDetails> findByUserIdAndProductIdAndOrderId(Long userId, Long productId, Long orderId);
	  
	   

		List<ReviewDetails> findByUserId(Long userId);
 
}