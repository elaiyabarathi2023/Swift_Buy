package com.swiftbuy.admin.repository;

import com.swiftbuy.admin.model.Offer;
import com.swiftbuy.admin.model.ProductDetails;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends CrudRepository<Offer, Long> {

	Optional<Offer> findByProduct(ProductDetails product);
    // Additional custom query methods, if needed

	Iterable<Offer> findByIsActiveTrue();

	Optional<Offer> findByIdAndIsActiveTrue(Long offerId);

	Optional<Offer> findByProductAndIsActiveTrue(ProductDetails product);
}

