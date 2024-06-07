package com.swiftbuy.user.repository.AccountManangement;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.swiftbuy.admin.model.CouponCodes;
import com.swiftbuy.user.model.AccountManangement.AddressDetails;

@Repository
public interface AddressDetailsRepo extends CrudRepository<AddressDetails, Long> {

    List<AddressDetails> findByAddressType(String addressType);
    List<AddressDetails> findByUserId(Long userId);
    AddressDetails findByIdAndUserId(Long id, Long userId);
    long countByUserId(Long userId);
	AddressDetails findByUserIdAndIsSelectedTrue(Long userId);
}