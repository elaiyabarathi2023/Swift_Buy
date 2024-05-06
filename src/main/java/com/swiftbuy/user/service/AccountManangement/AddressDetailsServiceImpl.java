package com.swiftbuy.user.service.AccountManangement;

import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import com.swiftbuy.user.repository.AccountManangement.AddressDetailsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressDetailsServiceImpl implements AddressDetailsService {

    @Autowired
    private AddressDetailsRepo addressDetailsRepository;

    @Override
    public Iterable<AddressDetails> getAllAddresses() {
        return addressDetailsRepository.findAll();
    }

    @Override
    public AddressDetails getAddressById(Long id) {
        return addressDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public AddressDetails saveAddress(AddressDetails address) {
        return addressDetailsRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        addressDetailsRepository.deleteById(id);
    }

    @Override
    public List<AddressDetails> findByAddressType(String addressType) {
        return addressDetailsRepository.findByAddressType(addressType);
    }

    @Override
    public List<AddressDetails> findByUserUserId(Long userId) {
        return addressDetailsRepository.findByUserUserId(userId);
    }
}