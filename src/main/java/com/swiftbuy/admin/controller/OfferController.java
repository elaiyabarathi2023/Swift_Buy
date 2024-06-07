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
	public ResponseEntity<Map<String, Object>> getAllOffers() {
		Map<String, Object> response = new HashMap<>();
		
			List<Offer> offers = offerService.getAllOffers();
			response.put("status", true);
			response.put("message", "All offers retrieved successfully");
			response.put("offers", offers);
			return ResponseEntity.ok(response);
		
	}

	@GetMapping("/{offerId}")
	public ResponseEntity<Map<String, Object>> getOfferById(@PathVariable Long offerId) {
		Map<String, Object> response = new HashMap<>();

		Offer offer = offerService.getOfferById(offerId);
		if (offer != null) {
			response.put("status", true);
			response.put("message", "Offer retrieved successfully");
			response.put("offer", offer);
			return ResponseEntity.ok(response);
		} else {
			response.put("status", false);
			response.put("error", "Offer not found");
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
		} catch (Exception e) {
			response.put("status", false);
			response.put("error", "Internal Server Error: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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

	@DeleteMapping("/{offerId}")
	public ResponseEntity<Map<String, Object>> deleteOffer(@PathVariable Long offerId) {
		Map<String, Object> response = new HashMap<>();

		offerService.deleteOffer(offerId);
		response.put("status", true);
		response.put("message", "Offer deleted successfully");
		return ResponseEntity.ok(response);

	}
}