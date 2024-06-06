package com.swiftbuy.user.controller.AccountManangement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import com.swiftbuy.user.service.AccountManangement.AddressDetailsService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/addresses")
public class AddressDetailsController {

    @Autowired
    private AddressDetailsService addressDetailsService;

    @PostMapping
    public ResponseEntity<AddressDetails> createAddressDetails(@RequestBody AddressDetails addressDetails, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);

        AddressDetails createdAddressDetails = addressDetailsService.createAddress(addressDetails, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddressDetails);
    }

    @GetMapping("/list")
    public @ResponseBody Iterable<AddressDetails> getAllAddressDetails() {
        return addressDetailsService.getAllAddressDetails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDetails> getAddressDetailsById(@PathVariable Long id, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);
try {
        AddressDetails addressDetails = addressDetailsService.getAddressDetailsById(id, userId);

       
            return ResponseEntity.ok(addressDetails);
}
         catch(ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDetails> updateAddressDetails(@PathVariable Long id, @RequestBody AddressDetails addressDetails, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);

        AddressDetails updatedAddressDetails;
        try {
            updatedAddressDetails = addressDetailsService.updateAddressDetails(id, addressDetails, userId);
            return ResponseEntity.ok(updatedAddressDetails);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddressDetails(@PathVariable Long id, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);

        try {
            addressDetailsService.deleteAddressDetails(id, userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}