
//
//
//package com.swiftbuy.user.controller.AccountManangement;
//
//
//
//import com.swiftbuy.user.model.AccountManangement.AddressDetails;
//import com.swiftbuy.user.service.AccountManangement.AddressDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/addresses")
//public class AddressDetailsController {
//
//    @Autowired
//    private AddressDetailsService addressDetailsService;
//
//    @GetMapping
//    public ResponseEntity<List<AddressDetails>> getAllAddressDetails() {
//        List<AddressDetails> addressDetails = addressDetailsService.getAllAddressDetails();
//        return ResponseEntity.ok(addressDetails);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<AddressDetails> getAddressDetailsById(@PathVariable Long id) {
//        AddressDetails addressDetails = addressDetailsService.getAddressDetailsById(id);
//        return ResponseEntity.ok(addressDetails);
//    }
//
//    @PostMapping
//    public ResponseEntity<AddressDetails> createAddressDetails(@RequestBody AddressDetails addressDetails) {
//        AddressDetails createdAddressDetails = addressDetailsService.createAddressDetails(addressDetails);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddressDetails);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<AddressDetails> updateAddressDetails(@PathVariable Long id, @RequestBody AddressDetails addressDetails) {
//        AddressDetails updatedAddressDetails = addressDetailsService.updateAddressDetails(id, addressDetails);
//        return ResponseEntity.ok(updatedAddressDetails);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteAddressDetails(@PathVariable Long id) {
//        addressDetailsService.deleteAddressDetails(id);
//        return ResponseEntity.noContent().build();
//    }
//}



package com.swiftbuy.user.controller.AccountManangement;

import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import com.swiftbuy.user.service.AccountManangement.AddressDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/addresses")
//@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "AddressDetails", description = "The AddressDetails API. Contains all the operations that can be performed on address details.")
public class AddressDetailsController {

    @Autowired
    private AddressDetailsService addressDetailsService;

    @PostMapping
    public ResponseEntity<AddressDetails> createAddressDetails(@RequestBody AddressDetails addressDetails,HttpServletRequest request) {
    	Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);

        AddressDetails createdAddressDetails = addressDetailsService.createAddress(addressDetails,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddressDetails);
    }
    
    @GetMapping("/list")
    public @ResponseBody Iterable<AddressDetails> getAllAddressDetails() {
        return addressDetailsService.getAllAddressDetails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDetails> getAddressDetailsById(@PathVariable Long id) {
        Optional<AddressDetails> optionalAddressDetails = Optional.ofNullable(addressDetailsService.getAddressDetailsById(id));
        return optionalAddressDetails
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AddressDetails not found with id: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDetails> updateAddressDetails(@PathVariable Long id, @RequestBody AddressDetails addressDetails) {
        AddressDetails updatedAddressDetails;
        try {
            updatedAddressDetails = addressDetailsService.updateAddressDetails(id, addressDetails);
            return ResponseEntity.ok(updatedAddressDetails);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddressDetails(@PathVariable Long id) {
        try {
            addressDetailsService.deleteAddressDetails(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}