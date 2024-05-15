package com.swiftbuy.admin.repository;



import com.swiftbuy.admin.model.AdminOfferForProducts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminOfferForProductsRepository extends CrudRepository<AdminOfferForProducts, Long> {
    // You can add custom query methods here if needed
}

