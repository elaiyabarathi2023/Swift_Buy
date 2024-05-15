//package com.swiftbuy.user.repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.repository.CrudRepository;
//
//import com.swiftbuy.admin.model.ProductDetails;
//import com.swiftbuy.user.model.ReviewDetails;
//
//public interface ReviewRepository extends CrudRepository<Long, ReviewDetails> {
//
//	 Optional<ReviewDetails> findByUserIdAndProductIdAndOrderId(Long userId, Long productId, Long orderId);
//	    List<ReviewDetails> findByProduct(ProductDetails product);
//
//}
