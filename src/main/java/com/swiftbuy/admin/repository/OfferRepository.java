package com.swiftbuy.admin.repository;

import com.swiftbuy.admin.model.Offer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends CrudRepository<Offer, Long> {
    // Additional custom query methods, if needed
}

