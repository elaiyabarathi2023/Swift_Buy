package com.swiftbuy.admin.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swiftbuy.admin.model.Offer;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.product.repository.ProductRepository;
import com.swiftbuy.admin.repository.OfferRepository;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ProductRepository productRepository;

 

    public Offer getOfferById(Long offerId) {
        Optional<Offer> optionalOffer = offerRepository.findByIdAndIsActiveTrue(offerId);
        return optionalOffer.orElse(null);
    }

    public Offer createOffer(Offer offer) {
        if (offer.getProduct() != null && offer.getProduct().getProductId() != null) {
            Long productId = offer.getProduct().getProductId();
            ProductDetails product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Check if there's an existing active offer for the product
            Optional<Offer> existingOffer = offerRepository.findByProductAndIsActiveTrue(product);
            if (existingOffer.isPresent()) {
                throw new RuntimeException("An active offer already exists for this product");
            }

            offer.setProduct(product);
            offer.setActive(true); // Set the offer as active by default
        } else {
            throw new RuntimeException("Product information is missing");
        }

        return offerRepository.save(offer);
    }

    public Offer updateOffer(Long offerId, Offer offer) {
        if (offer.getProduct() != null && offer.getProduct().getProductId() != null) {
            ProductDetails product = productRepository.findById(offer.getProduct().getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            offer.setProduct(product);
        } else {
            throw new RuntimeException("Product information is missing");
        }

        offer.setId(offerId); // Ensure the correct offer ID is set
        return offerRepository.save(offer);
    }

    public void deactivateOffer(Long offerId) {
        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        if (optionalOffer.isPresent()) {
            Offer offer = optionalOffer.get();
           
            if (!offer.isActive()) {
                throw new RuntimeException("The offer is already inactive and cannot be deactivated.");
            }

            offer.setActive(false);
            offerRepository.save(offer);
        } else {
            throw new RuntimeException("Offer not found");
        }
    }

    public void reactivateOffer(Long offerId) {
        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        if (optionalOffer.isPresent()) {
            Offer offer = optionalOffer.get();
           
            if (offer.isActive()) {
                throw new RuntimeException("The offer is already active and cannot be reactivated.");
            }

            offer.setActive(true);
            offerRepository.save(offer);
        } else {
            throw new RuntimeException("Offer not found");
        }
    }

    public List<Offer> getAllActiveOffers() {
        Iterable<Offer> activeOffersIterable = offerRepository.findByIsActiveTrue();
        List<Offer> activeOffers = new ArrayList<>();
        activeOffersIterable.forEach(activeOffers::add);
        return activeOffers;
    }
}