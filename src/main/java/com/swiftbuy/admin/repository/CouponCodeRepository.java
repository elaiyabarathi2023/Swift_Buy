package com.swiftbuy.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.swiftbuy.admin.model.CouponCodes;

public interface CouponCodeRepository extends CrudRepository<CouponCodes, Long> {

	  Optional<CouponCodes> findByCouponId(Long couponId);
	    Optional<List<CouponCodes>> findByCouponIdIn(List<Long> couponIds);
		
		
		
	



}
