package com.swiftbuy.user.repository.AccountManangement;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.swiftbuy.user.model.AccountManangement.AddressDetails;

@Repository
public interface AddressDetailsRepo extends CrudRepository<AddressDetails, Long> {
	List<AddressDetails> findByAddressType(String addressType);
	AddressDetails findByUserUserId(Long userId);
	AddressDetails findByIdAndUserUserId(Long id, Long userId);
	
	
	
}
