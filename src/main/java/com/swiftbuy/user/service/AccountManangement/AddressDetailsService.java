// Interface
package com.swiftbuy.user.service.AccountManangement;

import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import java.util.List;

public interface AddressDetailsService {
    Iterable<AddressDetails> getAllAddresses();
    AddressDetails getAddressById(Long id);
    AddressDetails saveAddress(AddressDetails address);
    void deleteAddress(Long id);
    List<AddressDetails> findByAddressType(String addressType);
    List<AddressDetails> findByUserUserId(Long userId);
}
