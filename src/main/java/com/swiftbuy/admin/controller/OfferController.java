
package com.swiftbuy.admin.controller;
 
import java.util.List;
 
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

	public ResponseEntity<List<Offer>> getAllOffers() {

		List<Offer> offers = offerService.getAllOffers();

		return ResponseEntity.ok(offers);

	}
 
	@GetMapping("/{offerId}")

	public ResponseEntity<Offer> getOfferById(@PathVariable Long offerId) {

		Offer offer = offerService.getOfferById(offerId);

		return offer != null ? ResponseEntity.ok(offer) : ResponseEntity.notFound().build();

	}
 
	@PostMapping("/add")

	public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {

		Offer createdOffer = offerService.createOffer(offer);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdOffer);

	}
 
	@ExceptionHandler(RuntimeException.class)

	public ResponseEntity<String> handleRuntimeException(RuntimeException e) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

	}
 
	@PutMapping("/{offerId}")

	public ResponseEntity<?> updateOffer(@PathVariable Long offerId, @RequestBody Offer offer) {

		try {

			Offer updatedOffer = offerService.updateOffer(offerId, offer);

			return ResponseEntity.ok(updatedOffer);

		} catch (RuntimeException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

		}

	}
 
	@DeleteMapping("/{offerId}")

	public ResponseEntity<Void> deleteOffer(@PathVariable Long offerId) {

		offerService.deleteOffer(offerId);

		return ResponseEntity.noContent().build();

	}

}