


package com.swiftbuy.user.controller.AccountManangement;

import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import com.swiftbuy.user.service.AccountManangement.AddressDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressDetailsController {

    @Autowired
    private AddressDetailsServiceImpl addressDetailsService;

    @GetMapping
    public ResponseEntity<Iterable<AddressDetails>> getAllAddresses() {
        Iterable<AddressDetails> addresses = addressDetailsService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDetails> getAddressById(@PathVariable Long id) {
        AddressDetails address = addressDetailsService.getAddressById(id);
        return address != null ? ResponseEntity.ok(address) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AddressDetails> saveAddress(@RequestBody AddressDetails address) {
        AddressDetails savedAddress = addressDetailsService.saveAddress(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressDetailsService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/addressType/{addressType}")
    public ResponseEntity<List<AddressDetails>> findByAddressType(@PathVariable String addressType) {
        List<AddressDetails> addresses = addressDetailsService.findByAddressType(addressType);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressDetails>> findByUserUserId(@PathVariable Long userId) {
        List<AddressDetails> addresses = addressDetailsService.findByUserUserId(userId);
        return ResponseEntity.ok(addresses);
    }
}