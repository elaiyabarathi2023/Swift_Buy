package com.swiftbuy.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swiftbuy.admin.model.Offer;
import com.swiftbuy.admin.service.OfferService;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllActiveOffers() {
        Map<String, Object> response = new HashMap<>();
        
            List<Offer> activeOffers = offerService.getAllActiveOffers();
            response.put("status", true);
            response.put("message", "All active offers retrieved successfully");
            response.put("offers", activeOffers);
            return ResponseEntity.ok(response);
        
    }
    @GetMapping("/{offerId}")
    public ResponseEntity<Map<String, Object>> getOfferById(@PathVariable Long offerId) {
        Map<String, Object> response = new HashMap<>();

        Offer offer = offerService.getOfferById(offerId);
        if (offer != null && offer.isActive()) {
            response.put("status", true);
            response.put("message", "Active offer retrieved successfully");
            response.put("offer", offer);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", false);
            response.put("error", "Active offer not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createOffer(@RequestBody Offer offer) {
        Map<String, Object> response = new HashMap<>();
        try {
            Offer createdOffer = offerService.createOffer(offer);
            response.put("status", true);
            response.put("message", "Offer created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("status", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } 
    }

    @PutMapping("/{offerId}")
    public ResponseEntity<Map<String, Object>> updateOffer(@PathVariable Long offerId, @RequestBody Offer offer) {
        Map<String, Object> response = new HashMap<>();
        try {
            Offer updatedOffer = offerService.updateOffer(offerId, offer);
            response.put("status", true);
            response.put("message", "Offer updated successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("status", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{offerId}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivateOffer(@PathVariable Long offerId) {
        Map<String, Object> response = new HashMap<>();
        try {
            offerService.deactivateOffer(offerId);
            response.put("status", true);
            response.put("message", "Offer deactivated successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("status", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
        
    

    @PutMapping("/{offerId}/reactivate")
    public ResponseEntity<String> reactivateOffer(@PathVariable Long offerId) {
        try {
            offerService.reactivateOffer(offerId);
            return new ResponseEntity<>("Offer reactivated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
