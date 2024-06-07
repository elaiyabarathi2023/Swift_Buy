package com.swiftbuy.user.service.AccountManangement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import com.swiftbuy.user.repository.AccountManangement.AddressDetailsRepo;

@Service
public class AddressDetailsService {
    @Autowired
    private AddressDetailsRepo addressDetailsRepository;

    public List<AddressDetails> getAllAddressDetails() {
        return (List<AddressDetails>) addressDetailsRepository.findAll();
    }

    public AddressDetails getAddressDetailsById(Long id, Long userId) {
        AddressDetails addressDetails = addressDetailsRepository.findByIdAndUserId(id, userId);

        if (addressDetails != null) {
            return addressDetails;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found");
        }
    }

    public AddressDetails createAddress(AddressDetails addressDetails, Long userId) {
        addressDetails.setUserId(userId);
        return addressDetailsRepository.save(addressDetails);
    }

    public AddressDetails updateAddressDetails(Long id, AddressDetails addressDetails, Long userId) {
        AddressDetails existingAddressDetails = addressDetailsRepository.findByIdAndUserId(id, userId);

        if (existingAddressDetails != null) {
            existingAddressDetails.setAddressType(addressDetails.getAddressType());
            existingAddressDetails.setPermanentAddress(addressDetails.getPermanentAddress());
            existingAddressDetails.setCurrentAddress(addressDetails.getCurrentAddress());
            existingAddressDetails.setStreetAddress(addressDetails.getStreetAddress());
            existingAddressDetails.setCity(addressDetails.getCity());
            existingAddressDetails.setState(addressDetails.getState());
            existingAddressDetails.setZipCode(addressDetails.getZipCode());
            existingAddressDetails.setCountry(addressDetails.getCountry());
            existingAddressDetails.setUserId(userId);

            return addressDetailsRepository.save(existingAddressDetails);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found");
        }
    }

    public void deleteAddressDetails(Long id, Long userId) {
        AddressDetails addressDetails = addressDetailsRepository.findByIdAndUserId(id, userId);
        if (addressDetails != null) {
            addressDetailsRepository.delete(addressDetails);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found");
        }
    }
}